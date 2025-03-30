package net.DreamBrewer.enologistmod.item;

import net.DreamBrewer.enologistmod.EnologistMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EnologistMod.MOD_ID);

    public static final RegistryObject<Item> ALCHEMY_BOTTLE = ITEMS.register("alchemy_bottle",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TOOL_TAB)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
