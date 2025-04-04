package net.DreamBrewer.enologistmod;

import com.mojang.logging.LogUtils;
import net.DreamBrewer.enologistmod.block.ModBlocks;
import net.DreamBrewer.enologistmod.block.entity.ModBlockEntities;
import net.DreamBrewer.enologistmod.item.ModItems;
import net.DreamBrewer.enologistmod.screen.ModMenuTypes;
import net.DreamBrewer.enologistmod.screen.WineBarrelScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EnologistMod.MOD_ID)
public class EnologistMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "enologistmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public EnologistMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.WINE_BARREL_MENU.get(), WineBarrelScreen::new);
        }
    }
}
