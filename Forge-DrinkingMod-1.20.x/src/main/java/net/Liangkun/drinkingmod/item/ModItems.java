package net.Liangkun.drinkingmod.item;

import net.Liangkun.drinkingmod.DrinkingMod;
import net.Liangkun.drinkingmod.item.custom.WheatWine;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static  final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DrinkingMod.MOD_ID);

    public static final RegistryObject<Item> CUP = ITEMS.register("drinkingcup",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRYJELLYHEAD = ITEMS.register("dryjellyhead",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> DETERIORATION_ODDNESS_GOLD_RAW_ORA = ITEMS.register(
            "deterioration_oddness_gold_raw_ora",()->new Item(new Item.Properties())
    );
    public static final RegistryObject<Item> WHEAT_WINE = ITEMS.register("wheat_wine",() -> new WheatWine(new Item.Properties()));
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
