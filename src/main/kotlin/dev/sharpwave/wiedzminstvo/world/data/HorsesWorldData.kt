package dev.sharpwave.wiedzminstvo.world.data


import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.utils.Storage
import net.minecraft.nbt.CompoundNBT
import net.minecraft.world.server.ServerWorld
import net.minecraft.world.storage.WorldSavedData


class HorsesWorldData : WorldSavedData(name) {
    private val entries: MutableMap<String, Int> = HashMap()
    private val killedHorses: MutableList<String> = ArrayList()
    private val disbandedHorses: MutableList<String> = ArrayList()
    private val offlineSavedHorses: MutableMap<String, CompoundNBT> = HashMap()
    private var i = 0

    override fun load(nbt: CompoundNBT) {
        var i = 0
        while (nbt.contains("" + i)) {
            val subTag = nbt.getCompound("" + i)
            val storageID = subTag.getString("id")
            val num = subTag.getInt("num")
            entries[storageID] = num
            i++
        }
        i = 0
        val killed = nbt.getCompound("killed")
        while (killed.contains("" + i)) {
            killedHorses.add(killed.getCompound("" + i).getString("id"))
            i++
        }
        i = 0
        val disbanded = nbt.getCompound("disbanded")
        while (disbanded.contains("" + i)) {
            disbandedHorses.add(disbanded.getCompound("" + i).getString("id"))
            i++
        }
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        val tag = CompoundNBT()
        entries.forEach { (storageID: String?, num: Int?) ->
            val subTag = CompoundNBT()
            subTag.putString("id", storageID)
            subTag.putInt("num", num)
            tag.put("" + i, subTag)
            i++
        }
        i = 0
        val killed = CompoundNBT()
        for (k in killedHorses.indices) {
            val subTag = CompoundNBT()
            subTag.putString("id", killedHorses[k])
            killed.put("" + k, subTag)
        }
        tag.put("killed", killed)
        val disbanded = CompoundNBT()
        for (k in disbandedHorses.indices) {
            val subTag = CompoundNBT()
            subTag.putString("id", disbandedHorses[k])
            disbanded.put("" + k, subTag)
        }
        tag.put("disbanded", disbanded)
        return tag
    }

    fun addHorseNum(id: String, num: Int) {
        entries[id] = num
        setDirty()
    }

    fun getHorseNum(id: String): Int {
        return entries[id] ?: return 0
    }

    fun disbandHorse(id: String) {
        disbandedHorses.add(id)
        setDirty()
    }

    fun isDisbanded(id: String): Boolean {
        return disbandedHorses.contains(id)
    }

    fun clearDisbanded(id: String) {
        disbandedHorses.remove(id)
        setDirty()
    }

    fun markKilled(id: String) {
        killedHorses.add(id)
        setDirty()
    }

    fun wasKilled(id: String): Boolean {
        return killedHorses.contains(id)
    }

    fun clearKilled(id: String) {
        killedHorses.remove(id)
        setDirty()
    }

    fun addOfflineSavedHorse(id: String, nbt: CompoundNBT) {
        offlineSavedHorses[id] = nbt
        setDirty()
    }

    fun wasOfflineSaved(id: String): Boolean {
        return offlineSavedHorses.containsKey(id)
    }

    fun getOfflineSavedHorse(id: String): CompoundNBT? {
        return offlineSavedHorses[id]
    }

    fun clearOfflineSavedHorse(id: String) {
        offlineSavedHorses.remove(id)
        setDirty()
    }

    companion object {
        private const val name: String = WiedzminstvoMod.MODID + "_horses"

        fun getInstance(world: ServerWorld): HorsesWorldData {
            return Storage.getInstance(world, name) { HorsesWorldData() }
        }
    }
}