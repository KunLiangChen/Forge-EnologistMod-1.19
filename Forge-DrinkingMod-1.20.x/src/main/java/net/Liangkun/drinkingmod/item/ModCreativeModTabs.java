package net.Liangkun.drinkingmod.item;

import net.Liangkun.drinkingmod.DrinkingMod;
import net.Liangkun.drinkingmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DrinkingMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB =
            CREATIVE_MODE_TABS.register(
                    "tutorial_tab", // register's name
                    () -> CreativeModeTab.builder() // register's supplier
                            .icon(() -> new ItemStack(ModItems.CUP.get()))
                            .title(Component.translatable("creativetab.tutorial_tab"))
                            .displayItems(
                                    (pParameters,pOutput) -> {
                                        pOutput.accept(ModItems.CUP.get());
                                        pOutput.accept(ModItems.DRYJELLYHEAD.get());

                                        pOutput.accept(ModBlocks.DRINKINGBUCKET.get());
                                        pOutput.accept(ModBlocks.JELLYFISHIHEAD.get());
                            })
                            .build()
            );

    public static final RegistryObject<CreativeModeTab> MATERIAL_TAB = CREATIVE_MODE_TABS.register("material_tab",
            ()->CreativeModeTab.builder().icon(()->new ItemStack(ModBlocks.DETERIORATION_ODDNESS_GOLD_ORA.get()))
                    .title(Component.translatable("creativetab.material_tab"))
                    .displayItems((pParameters,pOutput)->{
                        pOutput.accept(ModBlocks.DETERIORATION_ODDNESS_GOLD_ORA.get());
                        pOutput.accept(ModItems.DETERIORATION_ODDNESS_GOLD_RAW_ORA.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> DRINKINGWINE_TAB = CREATIVE_MODE_TABS.register("drinkingwine_tab",
            ()->CreativeModeTab.builder().icon(()->new ItemStack(ModItems.WHEAT_WINE.get()))
                    .title(Component.translatable("creativetab.drinkingwine_tab"))
                    .displayItems((pParameters,pOutput)->{
                        pOutput.accept(ModItems.WHEAT_WINE.get());
                    }).build());



    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }


}
