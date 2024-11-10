package net.Liangkun.drinkingmod.datagen;

import net.Liangkun.drinkingmod.DrinkingMod;
import net.Liangkun.drinkingmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DrinkingMod.MOD_ID, exFileHelper);
    }

    @Override
    public void registerStatesAndModels() {
        blockWithItem(ModBlocks.DRINKINGBUCKET);
        blockWithItem(ModBlocks.DETERIORATION_ODDNESS_GOLD_ORA);
        blockWithItem(ModBlocks.JELLYFISHIHEAD);
        simpleBlockWithItem(ModBlocks.CRUDE_BEGINNER_BREWER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/crude_beginner_brewer")));

    }
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
