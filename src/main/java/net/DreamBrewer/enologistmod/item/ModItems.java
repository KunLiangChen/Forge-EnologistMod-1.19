package net.DreamBrewer.enologistmod.item;

import net.DreamBrewer.enologistmod.EnologistMod;
import net.DreamBrewer.enologistmod.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EnologistMod.MOD_ID);

//    public static final RegistryObject<Item> ALCHEMY_BOTTLE = ITEMS.register("alchemy_bottle",
//            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TOOL_TAB)));

    public static final RegistryObject<Item> NORMAL_GOLD = ITEMS.register("normal_gold",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MINERAL_TAB)));

    //各种酒
    public static final RegistryObject<Item> BEERITEM = ITEMS.register("beer_item",
        () -> new BeerItem(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static final RegistryObject<Item> APPLE_WINE_ITEM = ITEMS.register("apple_wine_item",
            () -> new AppleWineItem(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static final RegistryObject<Item> CRUDEVODKA = ITEMS.register("crude_vodka",
            () -> new CrudeVodka(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static final RegistryObject<Item> CRUDERUM = ITEMS.register("crude_rum",
            () -> new CrudeRum(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static final RegistryObject<Item> SWEETBERRYWINE = ITEMS.register("sweet_berry_wine",
            () -> new SweetBerryWine(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static final RegistryObject<Item> GLOWBERRYWINE = ITEMS.register("glow_berry_wine",
            () -> new GlowBerryWine(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static final RegistryObject<Item> HEAVENLYDELIRIUM = ITEMS.register("heavenly_delirium",
            () -> new HeavenlyDelirium(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static final RegistryObject<Item> ENDECHOWINE = ITEMS.register("end_echo_wine",
            () -> new EndEchoWine(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static final RegistryObject<Item> MUSHROOMWINE = ITEMS.register("mushroom_wine",
            () -> new MushroomWine(new Item.Properties().tab(ModCreativeModeTab.WINE_TAB)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
