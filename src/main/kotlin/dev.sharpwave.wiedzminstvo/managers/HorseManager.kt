package dev.sharpwave.wiedzminstvo.managers

import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.IStoredHorse
import dev.sharpwave.wiedzminstvo.sound.WhistleSounds.randomWhistle
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getHorseCap
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getOwnerCap
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getPlayerFromUUID
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.getWorldData
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.sendHorseUpdateInRange
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.setHorseLastSeen
import dev.sharpwave.wiedzminstvo.utils.HorseHelper.setHorseNum
import dev.sharpwave.wiedzminstvo.worlddata.StoredHorsesWorldData
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
    fun callHorse(player: PlayerEntity?): Boolean {
        if (player != null) {
            val horseOwner = getOwnerCap(player)
            if (horseOwner != null) {
                if (horseOwner.horseNBT.isEmpty) {
                    player.sendStatusMessage(
                        TranslationTextComponent("callablehorses.error.nohorse").mergeStyle(
                            TextFormatting.RED
                        ), true
                    )
                    return false
                }
                if (!canCallHorse(player)) return false
                val rand = Random()
                player.world.playSound(
                    player,
                    player.getPosition(),
                    randomWhistle,
                    SoundCategory.PLAYERS,
                    1f,
                    (1.4 + rand.nextGaussian() / 3).toFloat()
                )
                CallableHorses.network.send(
                    PacketDistributor.PLAYER.with { player as ServerPlayerEntity? },
                    PlayWhistlePacket()
                )
                val e = findHorseWithStorageID(horseOwner.storageUUID, player.world)
                if (e != null) {
                    val horse = getHorseCap(e)
                    if (horse!!.storageUUID == horseOwner.storageUUID) {
                        if (e.world.func_230315_m_() === player.world.func_230315_m_()) {
                            e.removePassengers()
                            if (e.getPositionVec().distanceTo(player.getPositionVec()) <= SERVER.horseWalkRange.get()) {
                                // Horse walks //Follow range attribute
                                e.getAttribute(Attributes.FOLLOW_RANGE)!!.baseValue =
                                    SERVER.horseWalkRange.get()
                                e.getNavigator().tryMoveToEntityLiving(player, SERVER.horseWalkSpeed.get())
                            } else {
                                // TP-ing the horse
                                e.setPosition(player.getPosX(), player.getPosY(), player.getPosZ())
                            }
                            setHorseLastSeen(player)
                            sendHorseUpdateInRange(e)
                            return true
                        } else {
                            // Removing any loaded horses in other dims when a
                            // new one is spawned
                            saveHorse(e)
                            e.setPosition(e.getPosX(), -200, e.getPosZ())
                            e.remove()
                        }
                    }
                }

                // Spawning a new horse with a new num
                val newHorse = horseOwner.createHorseEntity(player.world)
                newHorse.setPosition(player.getPosX(), player.getPosY(), player.getPosZ())
                player.world.addEntity(newHorse)
                val h = getHorseCap(newHorse!!)
                setHorseNum((newHorse.world as ServerWorld?)!!, h!!.storageUUID, h.horseNum)
                sendHorseUpdateInRange(newHorse)
                setHorseLastSeen(player)
                return true
            }
        }
        return false
    }

    fun setHorse(player: PlayerEntity?) {
        if (player != null) {
            if (player.getRidingEntity() == null) {
                player.sendStatusMessage(
                    TranslationTextComponent("callablehorses.error.notriding").mergeStyle(
                        TextFormatting.RED
                    ), true
                )
                return
            }
            val e: Entity = player.getRidingEntity()
            if (e is AbstractHorseEntity) {
                if (!canSetHorse(player, e)) return
                val storedHorse = getHorseCap(e)
                val owner = storedHorse!!.ownerUUID
                val playerID = player.gameProfile.id.toString()
                val owned = storedHorse.isOwned
                if (owned && owner != playerID) {
                    player.sendStatusMessage(
                        TranslationTextComponent("callablehorses.error.alreadyowned").mergeStyle(
                            TextFormatting.RED
                        ), true
                    )
                    return
                }
                if (owned && owner == playerID) {
                    player.sendStatusMessage(
                        TranslationTextComponent("callablehorses.error.alreadypersonal").mergeStyle(
                            TextFormatting.RED
                        ), true
                    )
                    return
                }
                val horseOwner = getOwnerCap(player)
                val ownedID = horseOwner!!.storageUUID

                // Marking any old horses as disbanded
                if (!ownedID.isEmpty()) {
                    val ent: Entity? = findHorseWithStorageID(horseOwner.storageUUID, player.world)
                    if (ent != null) {
                        clearHorse(getHorseCap(ent))
                    } else {
                        player.world.getServer().getWorlds().forEach { serverworld ->
                            val data: StoredHorsesWorldData = getWorldData(serverworld)
                            data.disbandHorse(ownedID)
                        }
                    }
                }
                horseOwner.clearHorse()

                // Setting the new horse
                horseOwner.setHorse(e, player)
                setHorseLastSeen(player)
                setHorseNum((e.world as ServerWorld?)!!, storedHorse.storageUUID, storedHorse.horseNum)
                player.sendStatusMessage(TranslationTextComponent("callablehorses.success"), true)
                sendHorseUpdateInRange(e)
            }
        }
    }

    fun showHorseStats(player: ServerPlayerEntity) {
        val owner = getOwnerCap(player)
        if (owner!!.horseNBT.isEmpty) {
            player.sendStatusMessage(
                TranslationTextComponent("callablehorses.error.nohorse").mergeStyle(TextFormatting.RED),
                true
            )
            return
        }
        val e: Entity? = findHorseWithStorageID(owner.storageUUID, player.world)
        if (e != null) {
            saveHorse(e)
        }
        CallableHorses.network.send(PacketDistributor.PLAYER.with { player }, OwnerSyncShowStatsPacket(owner))
    }

    fun clearHorse(horse: IStoredHorse?) {
        horse!!.isOwned = false
        horse.horseNum = 0
        horse.ownerUUID = ""
        horse.storageUUID = ""
    }

    fun findHorseWithStorageID(id: String, world: World): AbstractHorseEntity? {
        val server = world.server
        val entities: MutableList<Entity> = ArrayList()
        for (w in server.getWorlds()) entities.addAll(w.entities.collect<List<Entity>, Any>(Collectors.toList<Entity>()))
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
            e.setChested(false)
        }
        e.extinguish()
        (e as LivingEntity).health = e.maxHealth
    }

    fun canCallHorse(player: PlayerEntity): Boolean {
        if (isAreaProtected(player, null)) {
            player.sendStatusMessage(
                TranslationTextComponent("callablehorses.error.area").mergeStyle(TextFormatting.RED),
                true
            )
            return false
        }
        if (player.getRidingEntity() != null) {
            player.sendStatusMessage(
                TranslationTextComponent("callablehorses.error.riding").mergeStyle(TextFormatting.RED),
                true
            )
            return false
        }
        if (SERVER.checkForSpace.get()) {
            val startX: Double
            val startY: Double
            val startZ: Double
            val endX: Double
            val endY: Double
            val endZ: Double
            startX = player.getPosX() - 1
            startY = player.getPosY()
            startZ = player.getPosZ() - 1
            endX = player.getPosX() + 1
            endY = player.getPosY() + 2
            endZ = player.getPosZ() + 1
            val world: World = player.world
            var x = startX
            while (x <= endX) {
                var y = startY
                while (y <= endY) {
                    var z = startZ
                    while (z <= endZ) {
                        val pos = BlockPos(x, y, z)
                        val state = world.getBlockState(pos)
                        if (state.block.getCollisionShape(state, world, pos, null) !== VoxelShapes.empty()) {
                            player.sendStatusMessage(
                                TranslationTextComponent("callablehorses.error.nospace").mergeStyle(
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
        }
        if (!SERVER.callableInEveryDimension.get()) {
            val allowedDims: List<String> = SERVER.callableDimsWhitelist.get()
            val playerDim: RegistryKey<World> = player.world.func_234923_W_()
            for (i in allowedDims.indices) {
                if (allowedDims[i] == playerDim.func_240901_a_().toString()) return true
            }
            player.sendStatusMessage(
                TranslationTextComponent("callablehorses.error.dim").mergeStyle(TextFormatting.RED),
                true
            )
            return false
        }
        val maxDistance: Int = SERVER.maxCallingDistance.get()
        if (maxDistance != -1) {
            val owner = getOwnerCap(player)
            var lastSeenPos = owner!!.lastSeenPosition
            var lastSeenDim: RegistryKey<World?> = owner.lastSeenDim
            if (lastSeenPos == Vector3d.ZERO) return true
            val server: MinecraftServer = player.world.getServer()
            val livingHorse: Entity? = findHorseWithStorageID(owner.storageUUID, player.world)
            if (livingHorse != null) {
                lastSeenPos = livingHorse.getPositionVec()
                lastSeenDim = livingHorse.world.func_234923_W_() // Dimension
                // registry
                // key
            }
            val movementFactorHorse: Double =
                server.getWorld(lastSeenDim).func_230315_m_().func_242724_f() // getDimensionType,
            // getMovementFactor
            val movementFactorOwner: Double = player.world.func_230315_m_().func_242724_f()
            val movementFactorTotal =
                if (movementFactorHorse > movementFactorOwner) movementFactorHorse / movementFactorOwner else movementFactorOwner / movementFactorHorse
            val distance = lastSeenPos.distanceTo(player.getPositionVec()) / movementFactorTotal
            if (distance <= maxDistance) return true
            player.sendStatusMessage(
                TranslationTextComponent("callablehorses.error.range").mergeStyle(TextFormatting.RED),
                true
            )
            return false
        }
        return true
    }

    fun canSetHorse(player: PlayerEntity, entity: Entity?): Boolean {
        if (isAreaProtected(player, entity)) {
            player.sendStatusMessage(
                TranslationTextComponent("callablehorses.error.setarea").mergeStyle(TextFormatting.RED),
                true
            )
            return false
        }
        return true
    }

    fun saveHorse(e: Entity) {
        if (e is AbstractHorseEntity) {
            if (e.hurtTime != 0) return
            val world: World = e.world
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
                        horseOwner.lastSeenDim = e.world.func_234923_W_()
                        horseOwner.lastSeenPosition = e.getPositionVec()
                    } else {
                        world.server.getWorlds().forEach { serverworld ->
                            val data: StoredHorsesWorldData = getWorldData(serverworld)
                            data.addOfflineSavedHorse(horse.storageUUID!!, e.serializeNBT())
                        }
                    }
                } else {
                    val data: StoredHorsesWorldData = getWorldData(world as ServerWorld)
                    data.addOfflineSavedHorse(horse.storageUUID!!, e.serializeNBT())
                }
            }
        }
    }

    private fun isAreaProtected(player: PlayerEntity, fakeHorse: Entity?): Boolean {
        var fakeHorse = fakeHorse
        val owner = getOwnerCap(player)
        if (fakeHorse == null) fakeHorse = owner!!.createHorseEntity(player.world)
        fakeHorse.setPosition(player.getPosX(), player.getPosY(), player.getPosZ())
        val interactEvent = EntityInteract(player, Hand.MAIN_HAND, fakeHorse)
        val attackEvent = AttackEntityEvent(player, fakeHorse)
        MinecraftForge.EVENT_BUS.post(interactEvent)
        MinecraftForge.EVENT_BUS.post(attackEvent)
        return interactEvent.isCanceled || attackEvent.isCanceled
    }
}