package net.Liangkun.drinkingmod.datagen;

import net.Liangkun.drinkingmod.DrinkingMod;
import net.Liangkun.drinkingmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DrinkingMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.CUP);
        simpleItem(ModItems.DRYJELLYHEAD);
        simpleItem(ModItems.WHEAT_WINE);
        simpleItem(ModItems.DETERIORATION_ODDNESS_GOLD_RAW_ORA);

    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DrinkingMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
