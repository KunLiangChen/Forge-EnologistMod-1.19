package net.DreamBrewer.enologistmod.block.entity;

import net.DreamBrewer.enologistmod.item.ModItems;
import net.DreamBrewer.enologistmod.screen.WineBarrelMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//代码组注意：实现这种实体时注意itemHandler，tick，hasRecipe，craftItem这几个方法是主要修改点
//其他方法大差不差，策划组没发疯就不用去想着改。
//TODO：策划组确实发疯了（实现蓄水系统），现在要对他们发动自杀式袭击炸死他们
public class WineBarrelBlockEntity extends BlockEntity implements MenuProvider {
    // 物品处理器 - 管理12个槽位的物品存储
    // 槽位说明：
    //TODO:分配好各个槽位
    private final ItemStackHandler itemHandler = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 64; // TODO: changes depending on what is in it


    public WineBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WINE_BARREL.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WineBarrelBlockEntity.this.progress;
                    case 1 -> WineBarrelBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WineBarrelBlockEntity.this.progress = value;
                    case 1 -> WineBarrelBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("酿造器");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new WineBarrelMenu(id,inventory,this,this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
//        nbt.putInt("gem_infusing_station.progress", this.progress); TODO: figure out what it is

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
//        progress = nbt.getInt("gem_infusing_station.progress"); TODO: figure out what it is
    }

    public void drops() { // TODO: everything drops, water doesn't
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    //这一块是实现方块机制的核心，这个类似loop函数会一直被调用。
    public static void tick(Level level, BlockPos pos, BlockState state, WineBarrelBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }

        // TODO: do your own idea, no copy!!!
        if(hasRecipe(pEntity)) {
            pEntity.progress++;
            setChanged(level, pos, state);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
    //产出机制，根据策划的要求去实现
    private static void craftItem(WineBarrelBlockEntity pEntity) {

        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(1, 1, false);//消耗物品
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.ZIRCON.get(),
                    pEntity.itemHandler.getStackInSlot(2).getCount() + 1));//放置产出

            pEntity.resetProgress();
        }
    }
    //各类配方通过实现这个方法来做
    private static boolean hasRecipe(WineBarrelBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        //这个布尔值判断某个操位是否有某个东西。
        boolean hasRawGemInFirstSlot = entity.itemHandler.getStackInSlot(1).getItem() == ModItems.RAW_ZIRCON.get();

        return hasRawGemInFirstSlot && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, new ItemStack(ModItems.ZIRCON.get(), 1));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }


}
