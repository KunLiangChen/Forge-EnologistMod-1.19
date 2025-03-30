package net.DreamBrewer.enologistmod.item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TOOL_TAB = new CreativeModeTab("enologistmod") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ALCHEMY_BOTTLE.get());
        }
    };
}
