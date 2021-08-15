package dev.sharpwave.wiedzminstvo.datagen

import com.google.common.collect.ImmutableSet
import dev.sharpwave.wiedzminstvo.datagen.support.BaseLootTableProvider
import dev.sharpwave.wiedzminstvo.registries.BlockRegistry
import net.minecraft.advancements.criterion.EnchantmentPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.data.DataGenerator
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.*
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.loot.conditions.MatchTool
import net.minecraft.loot.conditions.SurvivesExplosion
import net.minecraft.loot.functions.ExplosionDecay
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import java.util.function.Function
import java.util.stream.Stream


class BlockLootTables(generator: DataGenerator) : BaseLootTableProvider<Block>(generator) {
    //private val HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
    //private val HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert()
    //private val HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))
    //private val HAS_SHEARS_OR_SILK_TOUCH: ILootCondition.IBuilder = HAS_SHEARS.or(HAS_SILK_TOUCH)
    //private val HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert()
    private val EXPLOSION_RESISTANT: Set<Item> = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT,Blocks.SKELETON_SKULL,Blocks.WITHER_SKELETON_SKULL,Blocks.PLAYER_HEAD,Blocks.ZOMBIE_HEAD,Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD,Blocks.SHULKER_BOX,Blocks.BLACK_SHULKER_BOX,Blocks.BLUE_SHULKER_BOX,Blocks.BROWN_SHULKER_BOX,Blocks.CYAN_SHULKER_BOX,Blocks.GRAY_SHULKER_BOX,Blocks.GREEN_SHULKER_BOX,Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX,Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX,Blocks.YELLOW_SHULKER_BOX).map { obj: Block -> obj.asItem() }.collect(ImmutableSet.toImmutableSet())
    //private val NORMAL_LEAVES_SAPLING_CHANCES = floatArrayOf(0.05f, 0.0625f, 0.083333336f, 0.1f)
    //private val JUNGLE_LEAVES_SAPLING_CHANGES = floatArrayOf(0.025f, 0.027777778f, 0.03125f, 0.041666668f, 0.1f)

    private fun <T> applyExplosionDecay(provider: IItemProvider, consumer: ILootFunctionConsumer<T>): T {
        return (if (!EXPLOSION_RESISTANT.contains(provider.asItem())) consumer.apply(ExplosionDecay.explosionDecay()) else consumer.unwrap()) as T
    }

    private fun <T> applyExplosionCondition(provider: IItemProvider, consumer: ILootConditionConsumer<T>): T {
        return (if (!EXPLOSION_RESISTANT.contains(provider.asItem())) consumer.`when`(SurvivesExplosion.survivesExplosion()) else consumer.unwrap()) as T
    }

    private fun createSingleItemTable(provider: IItemProvider): LootTable.Builder {
        return LootTable.lootTable().withPool(applyExplosionCondition(provider,LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(provider))))
    }

    override fun addTables() {
        dropSelf(BlockRegistry.ARENARIA)
        dropSelf(BlockRegistry.BEGGARTICK)
        dropSelf(BlockRegistry.BISON_GRASS)
        dropSelf(BlockRegistry.BLUE_LOTUS)
        dropSelf(BlockRegistry.WINTER_CHERRY)
    }

    override fun buildTables(): Map<ResourceLocation, LootTable> {
        val tables: MutableMap<ResourceLocation, LootTable> = HashMap()
        for ((key, value) in lootTables) {
            tables[key.lootTable] = value.setParamSet(LootParameterSets.BLOCK).build()
        }
        return tables
    }

    private fun dropOther(block: Block, provider: IItemProvider) {
        add(block, createSingleItemTable(provider))
    }

    private fun dropSelf(block: Block) {
        dropOther(block, block)
    }

    private fun add(block: Block, builderFactory: Function<Block, LootTable.Builder>) {
        add(block, builderFactory.apply(block))
    }

    private fun add(block: Block, builder: LootTable.Builder) {
        lootTables[block] = builder
    }
}

