package net.DreamBrewer.enologistmod.block;
import net.DreamBrewer.enologistmod.EnologistMod;
import net.DreamBrewer.enologistmod.block.custom.AlchemyBottle;
import net.DreamBrewer.enologistmod.item.ModCreativeModeTab;
import net.DreamBrewer.enologistmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, EnologistMod.MOD_ID);

    public static final RegistryObject<Block> ALCHEMY_BOTTLE = registerBlock("alchemy_bottle",
            () -> new AlchemyBottle(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(0.5f).requiresCorrectToolForDrops().noOcclusion()), ModCreativeModeTab.TOOL_TAB);

    public static final RegistryObject<Block> NORMAL_GOLD_ORE = registerBlock("normal_gold_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(1.0f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)), ModCreativeModeTab.MINERAL_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
