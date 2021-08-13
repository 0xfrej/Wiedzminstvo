package dev.sharpwave.wiedzminstvo.events

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.HorseOwnerProvider
import dev.sharpwave.wiedzminstvo.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.HorseProvider
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.IStoredHorse
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
import net.minecraft.world.gen.feature.Features.Configs
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraftforge.event.entity.player.PlayerEvent.*
import net.minecraftforge.event.world.ChunkEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber


@EventBusSubscriber(modid = WiedzminstvoMod.ID)
object HorseEntityEvents {
    @SubscribeEvent
    fun onAttachCaps(event: AttachCapabilitiesEvent<Entity?>) {
        if (event.getObject() is PlayerEntity) event.addCapability(
            ResourceLocation(
                WiedzminstvoMod.ID,
                "horse_owner"
            ), HorseOwnerProvider()
        )
        if (event.getObject() is AbstractHorseEntity) event.addCapability(
            ResourceLocation(
                WiedzminstvoMod.ID,
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
                val entitylists: Array<ClassInheritanceMultiMap<Entity>> = chk.getEntityLists()
                for (list in entitylists) {
                    for (e in list) {
                        if (e is AbstractHorseEntity) {
                            val horse: IStoredHorse = HorseHelper.getHorseCap(e)
                            if (horse.isOwned()) {
                                val data: StoredHorsesWorldData = HorseHelper.getWorldData(world as ServerWorld)
                                if (data.isDisbanded(horse.getStorageUUID())) {
                                    HorseManager.clearHorse(horse)
                                    data.clearDisbanded(horse.getStorageUUID())
                                } else {
                                    val globalNum: Int =
                                        HorseHelper.getHorseNum(e.world as ServerWorld, horse.getStorageUUID())
                                    if (globalNum > horse.getHorseNum()) {
//										e.setPosition(e.getPosX(), -200, e.getPosZ());
                                        e.remove()
                                        WiedzminstvoMod.logger.debug(e.toString() + " was instantly despawned because its number is " + horse.getHorseNum() + " and the global num is " + globalNum)
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
        val oldHorse: IHorseOwner = HorseHelper.getOwnerCap(original)
        val newHorse: IHorseOwner = HorseHelper.getOwnerCap(newPlayer)
        newHorse.setHorseNBT(oldHorse.getHorseNBT())
        newHorse.setHorseNum(oldHorse.getHorseNum())
        newHorse.setStorageUUID(oldHorse.getStorageUUID())
    }

    // Save Horse to player cap when it unloads
    @SubscribeEvent
    fun onChunkUnload(event: ChunkEvent.Unload) {
        val world = event.world
        if (!world.isRemote()) {
            val chk = event.chunk
            if (chk is Chunk) {
                val entitylists: Array<ClassInheritanceMultiMap<Entity?>> = chk.getEntityLists()
                for (list in entitylists) {
                    for (e in list) {
                        HorseManager.saveHorse(e)
                    }
                }
            }
        }
    }

    // Save Horse to player cap when it unloads
    @SubscribeEvent
    fun onStopTracking(event: StopTracking) {
        val player = event.player
        val world: World = player.world
        val e: Entity = event.target
        if (!world.isRemote && e.isAlive()) {
            HorseManager.saveHorse(e)
        }
    }

    // Send horse update to client when is starting to be tracked
    @SubscribeEvent
    fun onStartTracking(event: StartTracking) {
        val player = event.player
        if (!player.world.isRemote) {
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
        if (e is AbstractHorseEntity && !e.world.isRemote) {
            if (Configs.SERVER.enableDebug.get() || Configs.SERVER.continuousAntiDupeChecking.get()) {
                val horse: IStoredHorse = HorseHelper.getHorseCap(e)
                if (Configs.SERVER.enableDebug.get()) e.setCustomName(
                    StringTextComponent(
                        "Is Owned: " + horse.isOwned().toString() + ", Storage UUID: " + horse.getStorageUUID()
                            .toString() + ", Horse Number: " + horse.getHorseNum()
                            .toString() + ", Horse UUID: " + e.getUniqueID()
                    )
                )
                if (Configs.SERVER.continuousAntiDupeChecking.get()) {
                    val thisNum: Int = horse.getHorseNum()
                    val globalNum: Int = HorseHelper.getHorseNum(e.world as ServerWorld, horse.getStorageUUID())
                    if (globalNum > thisNum) {
//						e.setPosition(e.getPosX(), -200, e.getPosZ());
                        e.remove()
                    }
                }
            }
        }
    }

    // Notify player of horse death
    @SubscribeEvent
    fun onLivingDeath(event: LivingDeathEvent) {
        val e: Entity = event.entity
        if (!e.world.isRemote && e is AbstractHorseEntity) {
            val horse: IStoredHorse = HorseHelper.getHorseCap(e)
            if (horse.isOwned()) {
                val owner: PlayerEntity = HorseHelper.getPlayerFromUUID(horse.getOwnerUUID(), e.world)
                if (owner != null) {
                    val horseOwner: IHorseOwner = HorseHelper.getOwnerCap(owner)
                    if (Configs.SERVER.deathIsPermanent.get()) {
                        horseOwner.clearHorse()
                        owner.sendStatusMessage(
                            TranslationTextComponent("callablehorses.alert.death").mergeStyle(
                                TextFormatting.RED
                            ), false
                        )
                    } else {
                        HorseManager.saveHorse(e)
                        val deadHorse: AbstractHorseEntity = horseOwner.createHorseEntity(owner.world)
                        HorseManager.prepDeadHorseForRespawning(deadHorse)
                        horseOwner.setHorseNBT(deadHorse.serializeNBT())
                        horseOwner.setLastSeenPosition(Vector3d.ZERO)
                    }
                } else {
                    CallableHorses.LOGGER.debug(e.toString() + " was marked as killed.")
                    e.world.getServer().getWorlds().forEach { serverworld ->
                        HorseHelper.getWorldData(serverworld).markKilled(horse.getStorageUUID())
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
        if (!world.isRemote && joiningEntity is PlayerEntity) {
            val player = joiningEntity as PlayerEntity
            val owner: IHorseOwner = HorseHelper.getOwnerCap(player)
            val ownedHorse: String = owner.getStorageUUID()
            if (!ownedHorse.isEmpty()) {
                val data: StoredHorsesWorldData = HorseHelper.getWorldData(world as ServerWorld)
                if (data.wasKilled(ownedHorse)) {
                    data.clearKilled(ownedHorse)
                    if (Configs.SERVER.deathIsPermanent.get()) {
                        owner.clearHorse()
                        player.sendStatusMessage(
                            TranslationTextComponent("callablehorses.alert.offlinedeath").mergeStyle(
                                TextFormatting.RED
                            ), false
                        )
                    } else {
                        val deadHorse: AbstractHorseEntity = owner.createHorseEntity(world)
                        HorseManager.prepDeadHorseForRespawning(deadHorse)
                        owner.setHorseNBT(deadHorse.serializeNBT())
                        owner.setLastSeenPosition(Vector3d.ZERO)
                    }
                }
                if (data.wasOfflineSaved(ownedHorse)) {
                    val newNBT: CompoundNBT = data.getOfflineSavedHorse(ownedHorse)
                    owner.setHorseNBT(newNBT)
                    data.clearOfflineSavedHorse(ownedHorse)
                }
            }
        }
    }
}