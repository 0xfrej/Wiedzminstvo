package dev.sharpwave.wiedzminstvo.events

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.HorseOwnerProvider
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.HorseProvider
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.IStoredHorse
import dev.sharpwave.wiedzminstvo.config.HorseConfig
import dev.sharpwave.wiedzminstvo.config.MainConfig
import dev.sharpwave.wiedzminstvo.managers.HorseManager
import dev.sharpwave.wiedzminstvo.utils.HorseHelper
import dev.sharpwave.wiedzminstvo.worlddata.StoredHorsesWorldData
import net.minecraft.entity.Entity
import net.minecraft.entity.passive.horse.AbstractHorseEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ClassInheritanceMultiMap
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraftforge.event.entity.player.PlayerEvent.*
import net.minecraftforge.event.world.ChunkEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber


@EventBusSubscriber(modid = WiedzminstvoMod.MODID)
object HorseEntityEvents {
    @SubscribeEvent
    fun onAttachCaps(event: AttachCapabilitiesEvent<Entity?>) {
        if (event.getObject() is PlayerEntity) event.addCapability(
            ResourceLocation(
                WiedzminstvoMod.MODID,
                "horse_owner"
            ), HorseOwnerProvider()
        )
        if (event.getObject() is AbstractHorseEntity) event.addCapability(
            ResourceLocation(
                WiedzminstvoMod.MODID,
                "stored_horse"
            ), HorseProvider()
        )
    }

    // Remove horses with lower num when they are loaded
    @SubscribeEvent
    fun onChunkLoad(event: ChunkEvent.Load) {
        val world = event.world
        if (!world.isClientSide) {
            val chk = event.chunk
            if (chk is Chunk) {
                val entitySections: Array<ClassInheritanceMultiMap<Entity>> = chk.entitySections
                for (list in entitySections) {
                    for (e in list) {
                        if (e is AbstractHorseEntity) {
                            val horse: IStoredHorse? = HorseHelper.getHorseCap(e)
                            if (horse?.isOwned == true) {
                                val data: StoredHorsesWorldData = HorseHelper.getWorldData(world as ServerWorld)
                                if (data.isDisbanded(horse.storageUUID)) {
                                    HorseManager.clearHorse(horse)
                                    data.clearDisbanded(horse.storageUUID)
                                } else {
                                    val globalNum: Int =
                                        HorseHelper.getHorseNum(e.level as ServerWorld, horse.storageUUID)
                                    if (globalNum > horse.horseNum) {
//										e.setPosition(e.getPosX(), -200, e.getPosZ());
                                        e.remove()
                                        WiedzminstvoMod.logger.debug(e.toString() + " was instantly despawned because its number is " + horse.horseNum + " and the global num is " + globalNum)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Clone player cap on teleport/respawn
    @SubscribeEvent
    fun onClone(event: Clone) {
        val original = event.original
        val newPlayer = event.player
        val oldHorse: IHorseOwner = HorseHelper.getOwnerCap(original)!!
        val newHorse: IHorseOwner = HorseHelper.getOwnerCap(newPlayer)!!

        newHorse.horseNBT = oldHorse.horseNBT
        newHorse.horseNum = oldHorse.horseNum
        newHorse.storageUUID = oldHorse.storageUUID
    }

    // Save Horse to player cap when it unloads
    @SubscribeEvent
    fun onChunkUnload(event: ChunkEvent.Unload) {
        val world = event.world
        if (!world.isClientSide) {
            val chk = event.chunk
            if (chk is Chunk) {
                val entitylists: Array<ClassInheritanceMultiMap<Entity?>> = chk.entitySections
                for (list in entitylists) {
                    for (e in list) {
                        if (e != null) {
                            HorseManager.saveHorse(e)
                        }
                    }
                }
            }
        }
    }

    // Save Horse to player cap when it unloads
    @SubscribeEvent
    fun onStopTracking(event: StopTracking) {
        val player = event.player
        val world: World = player.level
        val e: Entity = event.target
        if (!world.isClientSide && e.isAlive) {
            HorseManager.saveHorse(e)
        }
    }

    // Send horse update to client when is starting to be tracked
    @SubscribeEvent
    fun onStartTracking(event: StartTracking) {
        val player = event.player
        if (!player.level.isClientSide) {
            val target: Entity = event.target
            if (target is AbstractHorseEntity) {
                HorseHelper.sendHorseUpdateToClient(target, player)
            }
        }
    }

    // Debug
    @SubscribeEvent
    fun onLivingUpdate(event: LivingUpdateEvent) {
        val e: Entity = event.entityLiving
        if (e is AbstractHorseEntity && !e.level.isClientSide) {
            if (MainConfig.isDebugEnabled?.get() == true) {
                val horse: IStoredHorse = HorseHelper.getHorseCap(e)!!
                e.setCustomName(
                    StringTextComponent(
                        "Is Owned: " + horse.isOwned.toString() + ", Storage UUID: " + horse.storageUUID
                            .toString() + ", Horse Number: " + horse.horseNum
                            .toString() + ", Horse UUID: " + e.uuid
                    )
                )
            }
            if (HorseConfig.continuousAntiDupeChecking?.get() == true) {
                val horse: IStoredHorse = HorseHelper.getHorseCap(e)!!
                val thisNum: Int = horse.horseNum
                val globalNum: Int = HorseHelper.getHorseNum(e.level as ServerWorld, horse.storageUUID)
                if (globalNum > thisNum) {
//						e.setPosition(e.getPosX(), -200, e.getPosZ());
                    e.remove()
                }
            }
        }
    }

    // Notify player of horse death
    @SubscribeEvent
    fun onLivingDeath(event: LivingDeathEvent) {
        val e: Entity = event.entity
        if (!e.level.isClientSide && e is AbstractHorseEntity) {
            val horse: IStoredHorse = HorseHelper.getHorseCap(e)!!
            if (horse.isOwned) {
                val owner: PlayerEntity? = HorseHelper.getPlayerFromUUID(horse.ownerUUID, e.level)
                if (owner != null) {
                    val horseOwner: IHorseOwner = HorseHelper.getOwnerCap(owner)!!
                    if (HorseConfig.deathIsPermanent?.get() == true) {
                        horseOwner.clearHorse()
                        owner.displayClientMessage(
                            TranslationTextComponent("wiedzminstvo.horse.alert.death").withStyle(
                                TextFormatting.RED
                            ), false
                        )
                    } else {
                        HorseManager.saveHorse(e)
                        val deadHorse: AbstractHorseEntity = horseOwner.createHorseEntity(owner.level)!!
                        HorseManager.prepDeadHorseForRespawning(deadHorse)
                        horseOwner.horseNBT = deadHorse.serializeNBT()
                        horseOwner.lastSeenPosition = Vector3d.ZERO
                    }
                } else {
                    WiedzminstvoMod.logger.debug(e.toString() + " was marked as killed.")
                    e.level.server?.allLevels?.forEach { serverWorld ->
                        HorseHelper.getWorldData(serverWorld).markKilled(horse.storageUUID)
                    }
                }
            }
        }
    }

    // Notify player of offline horse death
    @SubscribeEvent
    fun onJoinWorld(event: EntityJoinWorldEvent) {
        val joiningEntity: Entity = event.entity
        val world = event.world
        if (!world.isClientSide && joiningEntity is PlayerEntity) {
            val player = joiningEntity
            val owner: IHorseOwner = HorseHelper.getOwnerCap(player)!!
            val ownedHorse: String = owner.storageUUID
            if (!ownedHorse.isEmpty()) {
                val data: StoredHorsesWorldData = HorseHelper.getWorldData(world as ServerWorld)
                if (data.wasKilled(ownedHorse)) {
                    data.clearKilled(ownedHorse)
                    if (HorseConfig.deathIsPermanent?.get() == true) {
                        owner.clearHorse()
                        player.displayClientMessage(
                            TranslationTextComponent("wiedzminstvo.horse.alert.offlinedeath").withStyle(
                                TextFormatting.RED
                            ), false
                        )
                    } else {
                        val deadHorse: AbstractHorseEntity = owner.createHorseEntity(world)!!
                        HorseManager.prepDeadHorseForRespawning(deadHorse)
                        owner.horseNBT = deadHorse.serializeNBT()
                        owner.lastSeenPosition = Vector3d.ZERO
                    }
                }
                if (data.wasOfflineSaved(ownedHorse)) {
                    val newNBT: CompoundNBT = data.getOfflineSavedHorse(ownedHorse)!!
                    owner.horseNBT = newNBT
                    data.clearOfflineSavedHorse(ownedHorse)
                }
            }
        }
    }
}