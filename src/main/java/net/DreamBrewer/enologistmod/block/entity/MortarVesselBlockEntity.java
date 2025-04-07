package net.DreamBrewer.enologistmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MortarVesselBlockEntity extends BlockEntity implements MenuProvider {
    public MortarVesselBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MORTAR_VESSEL.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("研磨碗");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    public void drops() {
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MortarVesselBlockEntity MortarVesselBlockEntity) {
    }
}
