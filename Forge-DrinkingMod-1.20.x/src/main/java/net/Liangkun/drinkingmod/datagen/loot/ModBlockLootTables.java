package net.Liangkun.drinkingmod.datagen.loot;

import net.Liangkun.drinkingmod.block.ModBlocks;
import net.Liangkun.drinkingmod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.DRINKINGBUCKET.get());
        this.dropSelf(ModBlocks.JELLYFISHIHEAD.get());
        this.add(ModBlocks.DETERIORATION_ODDNESS_GOLD_ORA.get(),
                block -> createIronLikeOreDrop(ModBlocks.DETERIORATION_ODDNESS_GOLD_ORA.get(), ModItems.DETERIORATION_ODDNESS_GOLD_RAW_ORA.get()));

    }
    protected LootTable.Builder createIronLikeOreDrop(Block pBlock, Item item){
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                    LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,2.0F)))
                            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));

    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
