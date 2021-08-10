package dev.sharpwave.wiedzminstvo.datagen

import com.google.gson.GsonBuilder
import dev.sharpwave.wiedzminstvo.datagen.support.BaseLootTableProvider
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


class BlockLootTables(generator: DataGenerator) : BaseLootTableProvider<Block>(generator) {

    override fun addTables() {

    }

    private fun createStandardTable(name: String, block: Block): LootTable.Builder {
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

    override fun buildTables(): Map<ResourceLocation, LootTable> {
        val tables: MutableMap<ResourceLocation, LootTable> = HashMap()
        for ((key, value) in lootTables) {
            tables[key.lootTable] = value.setParamSet(LootParameterSets.BLOCK).build()
        }
        return tables
    }
}

