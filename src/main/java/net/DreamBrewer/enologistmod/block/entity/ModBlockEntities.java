package net.DreamBrewer.enologistmod.block.entity;

import net.DreamBrewer.enologistmod.EnologistMod;
import net.DreamBrewer.enologistmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EnologistMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<WineBarrelBlockEntity>> WINE_BARREL =
            BLOCK_ENTITIES.register("wine_barrel", () ->
                    BlockEntityType.Builder.of(WineBarrelBlockEntity::new,
                            ModBlocks.WINE_BARREL.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}