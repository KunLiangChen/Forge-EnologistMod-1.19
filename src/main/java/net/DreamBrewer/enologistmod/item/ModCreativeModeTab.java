package net.DreamBrewer.enologistmod.item;
import net.DreamBrewer.enologistmod.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TOOL_TAB = new CreativeModeTab("tool_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ALCHEMY_BOTTLE.get());
        }
    };

    public static final CreativeModeTab MINERAL_TAB = new CreativeModeTab("mineral_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.NORMAL_GOLD_ORE.get());
        }
    };
}
