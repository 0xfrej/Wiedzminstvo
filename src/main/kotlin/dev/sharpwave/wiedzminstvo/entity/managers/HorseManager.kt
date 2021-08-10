package dev.sharpwave.wiedzminstvo.entity.managers

import dev.sharpwave.wiedzminstvo.config.HorseConfig
import dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse.IStoredHorse
import dev.sharpwave.wiedzminstvo.locale.HorseStrings
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.OwnerSyncShowStatsPacket
import dev.sharpwave.wiedzminstvo.network.main.packets.PlayWhistlePacket
import dev.sharpwave.wiedzminstvo.sound.WhistleSounds
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getHorseCap
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getOwnerCap
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getPlayerFromUUID
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getWorldData
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.sendHorseUpdateInRange
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.setHorseLastSeen
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.setHorseNum
import dev.sharpwave.wiedzminstvo.world.data.HorsesWorldData
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity
import net.minecraft.entity.passive.horse.AbstractHorseEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Hand
import net.minecraft.util.RegistryKey
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract
import net.minecraftforge.fml.network.PacketDistributor
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import java.util.*
import java.util.stream.Collectors


object HorseManager {
    // TODO: make horse disappear, relocate and then again appear with an effect
    fun callHorse(player: PlayerEntity?): Boolean {
        if (player != null) {
            val horseOwner = getOwnerCap(player)
            if (horseOwner != null) {
                if (horseOwner.horseNBT.isEmpty) {
                    player.displayClientMessage(
                        TranslationTextComponent(HorseStrings.HORSE_ERROR_NO_HORSE).withStyle(
                            TextFormatting.RED
                        ), true
                    )
                    return false
                }
                if (!canCallHorse(player)) return false
                val rand = Random()
                val playerPosition = player.position()

                player.level.playSound(
                    player,
                    playerPosition.x,
                    playerPosition.y,
                    playerPosition.z,
                    WhistleSounds.randomWhistle!!,
                    SoundCategory.PLAYERS,
                    1f,
                    (1.4 + rand.nextGaussian() / 3).toFloat()
                )
                PlayWhistlePacket.send(
                    PacketDistributor.PLAYER.with { player as ServerPlayerEntity? },
                    PlayWhistlePacket()
                )
                val e = findHorseWithStorageID(horseOwner.storageUUID, player.level)
                if (e != null) {
                    val horse = getHorseCap(e)
                    if (horse!!.storageUUID == horseOwner.storageUUID) {
                        if (e.level.dimension() === player.level.dimension()) {
                            e.unRide()
                            if (e.position().distanceTo(player.position()) <= HorseConfig.walkRange!!.get()) {
                                // Horse walks //Follow range attribute
                                e.getAttribute(Attributes.FOLLOW_RANGE)!!.baseValue =
                                    HorseConfig.walkRange!!.get()
                                e.navigation.moveTo(player, HorseConfig.walkSpeed!!.get())
                            } else {
                                // TP-ing the horse
                                e.setPos(player.x, player.y, player.z)
                            }
                            setHorseLastSeen(player)
                            sendHorseUpdateInRange(e)
                            return true
                        } else {
                            // Removing any loaded horses in other dims when a
                            // new one is spawned
                            saveHorse(e)
                            e.setPos(e.x, -200.0, e.z)
                            e.remove()
                        }
                    }
                }

                // Spawning a new horse with a new num
                val newHorse = horseOwner.createHorseEntity(player.level)!!
                newHorse.setPos(player.x, player.y, player.z)
                player.level.addFreshEntity(newHorse)
                val h = getHorseCap(newHorse)
                setHorseNum((newHorse.level as ServerWorld?)!!, h!!.storageUUID, h.horseNum)
                sendHorseUpdateInRange(newHorse)
                setHorseLastSeen(player)
                return true
            }
        }
        return false
    }

    // TODO: Can set horse only if it's tamed and the player is it's owner
    /*
    * TODO: Rework setting horse by equipping special saddle which has some special effects or another idea
    * is to have custom horse inventory slot where you can equip hook for trophies (if possible this is first option)
    */
    fun setHorse(player: PlayerEntity?) {
        if (player != null) {
            if (player.vehicle == null) {
                player.displayClientMessage(
                    TranslationTextComponent(HorseStrings.HORSE_ERROR_NOT_RIDING).withStyle(
                        TextFormatting.RED
                    ), true
                )
                return
            }
            val e: Entity? = player.vehicle
            if (e is AbstractHorseEntity) {
                if (!canSetHorse(player, e)) return
                val storedHorse = getHorseCap(e)
                val owner = storedHorse!!.ownerUUID
                val playerID = player.gameProfile.id.toString()
                val owned = storedHorse.isOwned
                if (owned && owner != playerID) {
                    player.displayClientMessage(
                        TranslationTextComponent(HorseStrings.HORSE_ERROR_ALREADY_OWNED).withStyle(
                            TextFormatting.RED
                        ), true
                    )
                    return
                }
                if (owned && owner == playerID) {
                    player.displayClientMessage(
                        TranslationTextComponent(HorseStrings.HORSE_ERROR_ALREADY_PERSONAL).withStyle(
                            TextFormatting.RED
                        ), true
                    )
                    return
                }
                val horseOwner = getOwnerCap(player)
                val ownedID = horseOwner!!.storageUUID

                // TODO: DEBUG DISBANIDNG!!
                // Marking any old horses as disbanded
                if (ownedID.isNotEmpty()) {
                    val ent: Entity? = findHorseWithStorageID(horseOwner.storageUUID, player.level)
                    if (ent != null) {
                        getHorseCap(ent)?.let { clearHorse(it) }
                    } else {
                        player.level.server?.allLevels?.forEach { serverWorld ->
                            val data: HorsesWorldData = getWorldData(serverWorld)
                            data.disbandHorse(ownedID)
                        }
                    }
                }
                horseOwner.clearHorse()

                // Setting the new horse
                horseOwner.setHorse(e, player)
                setHorseLastSeen(player)
                setHorseNum((e.level as ServerWorld?)!!, storedHorse.storageUUID, storedHorse.horseNum)
                player.displayClientMessage(TranslationTextComponent(HorseStrings.HORSE_ERROR_ALREADY_PERSONAL), true)
                sendHorseUpdateInRange(e)
            }
        }
    }

    fun showHorseStats(player: ServerPlayerEntity) {
        val owner = getOwnerCap(player)
        if (owner!!.horseNBT.isEmpty) {
            player.displayClientMessage(
                TranslationTextComponent(HorseStrings.HORSE_ERROR_NO_HORSE).withStyle(TextFormatting.RED),
                true
            )
            return
        }
        val e: Entity? = findHorseWithStorageID(owner.storageUUID, player.level)
        if (e != null) {
            saveHorse(e)
        }
        OwnerSyncShowStatsPacket.send(PacketDistributor.PLAYER.with { player }, OwnerSyncShowStatsPacket(owner))
    }

    fun clearHorse(horse: IStoredHorse) {
        horse.isOwned = false
        horse.horseNum = 0
        horse.ownerUUID = ""
        horse.storageUUID = ""
    }

    private fun findHorseWithStorageID(id: String, world: World): AbstractHorseEntity? {
        val server = world.server
        val entities: MutableList<Entity> = ArrayList()
        for (w in server?.allLevels!!) entities.addAll(w.entities.collect(Collectors.toList()))
        for (e in entities) {
            if (e is AbstractHorseEntity) {
                val horse = getHorseCap(e)
                if (horse!!.storageUUID == id) return e
            }
        }
        return null
    }

    // Clear armor, saddle, and any chest items
    fun prepDeadHorseForRespawning(e: Entity) {
        val cap = e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
        cap.ifPresent { itemHandler: IItemHandler ->
            for (i in 0 until itemHandler.slots) {
                itemHandler.extractItem(i, 64, false)
            }
        }
        if (e is AbstractChestedHorseEntity) {
            e.setChest(false)
        }
        e.clearFire()
        (e as LivingEntity).health = e.maxHealth
    }

    // TODO: Add check if horse won't be spawned in mid air
    private fun canCallHorse(player: PlayerEntity): Boolean {
        if (isAreaProtected(player, null)) {
            player.displayClientMessage(
                TranslationTextComponent(HorseStrings.HORSE_ERROR_AREA_BANNED).withStyle(TextFormatting.RED),
                true
            )
            return false
        }
        // TODO: add this check also to client side to prevent spawning the sound
        if (player.vehicle != null) {
            player.displayClientMessage(
                TranslationTextComponent(HorseStrings.HORSE_ERROR_RIDING).withStyle(TextFormatting.RED),
                true
            )
            return false
        }
        // TODO: Implement octree search or maybe run octree search for empty space only if this fails
        val startX: Double = player.x - 1
        val startY: Double = player.y
        val startZ: Double = player.z - 1
        val endX: Double = player.x + 1
        val endY: Double = player.y + 2
        val endZ: Double = player.z + 1
        val world: World = player.level
        var x = startX
        while (x <= endX) {
            var y = startY
            while (y <= endY) {
                var z = startZ
                while (z <= endZ) {
                    val pos = BlockPos(x, y, z)
                    val state = world.getBlockState(pos)
                    @Suppress("DEPRECATION")
                    if (state.block.getCollisionShape(state, world, pos, ISelectionContext.empty()) !== VoxelShapes.empty()) {
                        player.displayClientMessage(
                            TranslationTextComponent(HorseStrings.HORSE_ERROR_NO_SPACE).withStyle(
                                TextFormatting.RED
                            ), true
                        )
                        return false
                    }
                    z++
                }
                y++
            }
            x++
        }
        if (HorseConfig.callableDimsWhitelist !== null || HorseConfig.callableDimsWhitelist!!.get().isEmpty()) {
            val allowedDims: List<String> = HorseConfig.callableDimsWhitelist!!.get()
            val playerDim: RegistryKey<World> = player.level.dimension()
            for (i in allowedDims.indices) {
                if (allowedDims[i] == playerDim.location().toString()) return true
            }
            player.displayClientMessage(
                TranslationTextComponent(HorseStrings.HORSE_ERROR_DIM).withStyle(TextFormatting.RED),
                true
            )
            return false
        }
        val maxDistance: Int = HorseConfig.maxCallingDistance!!.get()
        if (maxDistance != -1) {
            val owner = getOwnerCap(player)
            var lastSeenPos = owner!!.lastSeenPosition
            var lastSeenDim: RegistryKey<World> = owner.lastSeenDim
            if (lastSeenPos == Vector3d.ZERO) return true
            val server: MinecraftServer = player.level.server!!
            val livingHorse: Entity? = findHorseWithStorageID(owner.storageUUID, player.level)
            if (livingHorse != null) {
                lastSeenPos = livingHorse.position()
                lastSeenDim = livingHorse.level.dimension()
            }
            val movementFactorHorse: Double =
                server.getLevel(lastSeenDim)!!.dimensionType().coordinateScale()
            val movementFactorOwner: Double = player.level.dimensionType().coordinateScale()
            val movementFactorTotal =
                if (movementFactorHorse > movementFactorOwner) movementFactorHorse / movementFactorOwner else movementFactorOwner / movementFactorHorse
            val distance = lastSeenPos.distanceTo(player.position()) / movementFactorTotal
            if (distance <= maxDistance) return true
            player.displayClientMessage(
                TranslationTextComponent(HorseStrings.HORSE_ERROR_RANGE).withStyle(TextFormatting.RED),
                true
            )
            return false
        }
        return true
    }

    // TODO: check if horse is friend of player
    private fun canSetHorse(player: PlayerEntity, entity: Entity?): Boolean {
        if (isAreaProtected(player, entity)) {
            player.displayClientMessage(
                TranslationTextComponent(HorseStrings.HORSE_ERROR_AREA_BANNED).withStyle(TextFormatting.RED),
                true
            )
            return false
        }
        return true
    }

    fun saveHorse(e: Entity) {
        if (e is AbstractHorseEntity) {
            if (e.hurtTime != 0) return
            val world: World = e.level
            val horse = getHorseCap(e)
            if (horse != null && horse.isOwned) {
                val ownerid = horse.ownerUUID
                val owner = getPlayerFromUUID(ownerid, world)
                if (owner != null) {
                    // Owner is online
                    val horseOwner = getOwnerCap(owner)
                    if (horseOwner != null) {
                        val nbt = e.serializeNBT()
                        horseOwner.horseNBT = nbt
                        horseOwner.lastSeenDim = e.level.dimension()
                        horseOwner.lastSeenPosition = e.position()
                    } else {
                        world.server?.allLevels?.forEach { serverWorld ->
                            val data: HorsesWorldData = getWorldData(serverWorld)
                            data.addOfflineSavedHorse(horse.storageUUID, e.serializeNBT())
                        }
                    }
                } else {
                    val data: HorsesWorldData = getWorldData(world as ServerWorld)
                    data.addOfflineSavedHorse(horse.storageUUID, e.serializeNBT())
                }
            }
        }
    }

    // TODO: Possibly generalize this and include in helper?
    private fun isAreaProtected(player: PlayerEntity, fakeHorse: Entity?): Boolean {
        var fakeHorseShadow = fakeHorse
        val owner = getOwnerCap(player)
        if (fakeHorseShadow == null) fakeHorseShadow = owner!!.createHorseEntity(player.level)
        fakeHorseShadow!!.setPos(player.x, player.y, player.z)
        val interactEvent = EntityInteract(player, Hand.MAIN_HAND, fakeHorseShadow)
        val attackEvent = AttackEntityEvent(player, fakeHorseShadow)
        MinecraftForge.EVENT_BUS.post(interactEvent)
        MinecraftForge.EVENT_BUS.post(attackEvent)
        return interactEvent.isCanceled || attackEvent.isCanceled
    }
}