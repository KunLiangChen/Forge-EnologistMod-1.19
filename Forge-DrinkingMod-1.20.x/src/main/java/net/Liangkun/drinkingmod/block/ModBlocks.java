package net.Liangkun.drinkingmod.block;

import net.Liangkun.drinkingmod.DrinkingMod;
import net.Liangkun.drinkingmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DrinkingMod.MOD_ID);

    public static final RegistryObject<Block> DRINKINGBUCKET = registerBlock("drinkingbucket",
            ()->new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> JELLYFISHIHEAD = registerBlock("jellyfishhead",
            ()->new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));

    public static final RegistryObject<Block> DETERIORATION_ODDNESS_GOLD_ORA = registerBlock("deterioration_oddness_gold_ora",
            ()->new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).strength(2.5f).requiresCorrectToolForDrops()
            , UniformInt.of(5,15)));

    private  static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static  <T extends Block> RegistryObject<Item> registerBlockItem(String name,RegistryObject<T> block){

        return ModItems.ITEMS.register(name,()->new BlockItem(block.get(),new Item.Properties()));
    }

    public  static  void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }


}


