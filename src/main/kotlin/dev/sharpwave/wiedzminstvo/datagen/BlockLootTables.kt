package dev.sharpwave.wiedzminstvo.datagen

import com.google.gson.GsonBuilder
import net.minecraft.block.Block
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.data.LootTableProvider
import net.minecraft.loot.*
import net.minecraft.loot.functions.CopyName
import net.minecraft.loot.functions.CopyNbt
import net.minecraft.loot.functions.SetContents
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.LogManager
import java.io.IOException


abstract class BlockLootTables(protected val generator: DataGenerator) : LootTableProvider(
    generator
) {
    protected val lootTables: MutableMap<Block, LootTable.Builder> = HashMap()
    protected abstract fun addTables()
    protected fun createStandardTable(name: String, block: Block): LootTable.Builder {
        val builder = LootPool.lootPool()
            .name(name)
            .setRolls(ConstantRange.exactly(1))
            .add(
                ItemLootEntry.lootTableItem(block)
                    .apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY))
                    .apply(
                        CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY)
                            .copy("inv", "BlockEntityTag.inv", CopyNbt.Action.REPLACE)
                            .copy("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE)
                    )
                    .apply(
                        SetContents.setContents()
                            .withEntry(DynamicLootEntry.dynamicEntry(ResourceLocation("minecraft", "contents")))
                    )
            )
        return LootTable.lootTable().withPool(builder)
    }

    override fun run(cache: DirectoryCache) {
        addTables()
        val tables: MutableMap<ResourceLocation, LootTable> = HashMap()
        for ((key, value) in lootTables) {
            tables[key.lootTable] = value.setParamSet(LootParameterSets.BLOCK).build()
        }
        writeTables(cache, tables)
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

