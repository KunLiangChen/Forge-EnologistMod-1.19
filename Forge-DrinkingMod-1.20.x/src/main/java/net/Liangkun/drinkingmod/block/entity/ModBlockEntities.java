package net.Liangkun.drinkingmod.block.entity;

import net.Liangkun.drinkingmod.DrinkingMod;

import net.Liangkun.drinkingmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DrinkingMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<CrudeBeginnerBrewerBlockEntity>> CRUDE_BEGINNER_BREWER_BE =
            BLOCK_ENTITIES.register("crude_beginner_brewer_be", () ->
                    BlockEntityType.Builder.of(CrudeBeginnerBrewerBlockEntity::new,
                            ModBlocks.CRUDE_BEGINNER_BREWER.get()).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
