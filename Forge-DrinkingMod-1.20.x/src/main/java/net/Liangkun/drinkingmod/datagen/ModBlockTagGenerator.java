package net.Liangkun.drinkingmod.datagen;

import net.Liangkun.drinkingmod.DrinkingMod;
import net.Liangkun.drinkingmod.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator  extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DrinkingMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.DETERIORATION_ODDNESS_GOLD_ORA.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.DETERIORATION_ODDNESS_GOLD_ORA.get());
    }
}
