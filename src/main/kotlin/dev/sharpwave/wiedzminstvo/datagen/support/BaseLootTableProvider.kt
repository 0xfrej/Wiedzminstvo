package dev.sharpwave.wiedzminstvo.datagen.support

import com.google.gson.GsonBuilder
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.data.LootTableProvider
import net.minecraft.loot.LootTable
import net.minecraft.loot.LootTableManager
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.LogManager
import java.io.IOException


abstract class BaseLootTableProvider<T>(protected val generator: DataGenerator) : LootTableProvider(generator) {
    protected val lootTables: MutableMap<T, LootTable.Builder> = HashMap()

    protected abstract fun addTables()

    protected abstract fun buildTables(): Map<ResourceLocation, LootTable>

    override fun run(cache: DirectoryCache) {
        addTables()
        writeTables(cache, buildTables())
    }

    private fun writeTables(cache: DirectoryCache, tables: Map<ResourceLocation, LootTable>) {
        val outputFolder = this.generator.outputFolder
        tables.forEach { (key: ResourceLocation, lootTable: LootTable?) ->
            val path =
                outputFolder.resolve("data/" + key.namespace + "/loot_tables/" + key.path + ".json")
            try {
                IDataProvider.save(
                    GSON,
                    cache,
                    LootTableManager.serialize(lootTable),
                    path
                )
            } catch (e: IOException) {
                LOGGER.error("Couldn't write loot table {}", path, e)
            }
        }
    }

    override fun getName(): String {
        return "Wiedzminstvo LootTables"
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
        private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    }
}

