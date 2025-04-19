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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
/* 代码组注意！：以后实现实体的时候注意对于通用方法全部用static实现，需要调用方块本身就传参 */
//代码组注意：实现这种实体时注意itemHandler，tick，hasRecipe，craftItem这几个方法是主要修改点
//其他方法大不相同，策划组没发疯就不用去想着改。
//TODO：策划组确实发疯了（实现蓄水系统），现在要对他们发动自杀式袭击炸死他们
public class WineBarrelBlockEntity extends BlockEntity implements MenuProvider {
    // 物品处理器 - 管理12个槽位的物品存储
    // 槽位说明：
    //TODO: 分配好各个槽位
    private final ItemStackHandler itemHandler = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 64;// TODO: changes depending on what is in it
    private int water = 0;
    private int waterCapability = 16;
    private int wineMaking = 0;
    private int wineFinish = 16;
    private int identicalWine = 0;
    private int flag=0;//标志酒是否酿好
    public WineBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WINE_BARREL.get(), pos, state);
        this.data = new ContainerData() {
            @Override //根据索引值获取数据的值
            public int get(int index) {
                return switch (index) {
                    case 0 -> WineBarrelBlockEntity.this.progress;
                    case 1 -> WineBarrelBlockEntity.this.maxProgress;
                    case 2 -> WineBarrelBlockEntity.this.water;
                    case 3 -> WineBarrelBlockEntity.this.waterCapability;
                    case 4 -> WineBarrelBlockEntity.this.wineMaking;
                    case 5 -> WineBarrelBlockEntity.this.wineFinish;
                    case 6 -> WineBarrelBlockEntity.this.identicalWine;
                    default -> 0;
                };
            }

            @Override //根据索引值设定数据的值
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WineBarrelBlockEntity.this.progress = value;
                    case 1 -> WineBarrelBlockEntity.this.maxProgress = value;
                    case 2 -> WineBarrelBlockEntity.this.water = value;
                    case 3 -> WineBarrelBlockEntity.this.waterCapability = value;
                    case 4 -> WineBarrelBlockEntity.this.wineMaking = value;
                    case 5 -> WineBarrelBlockEntity.this.wineFinish = value;
                    case 6 -> WineBarrelBlockEntity.this.identicalWine = value;
                }
            }

            @Override
            public int getCount() {
                return 7;
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
        nbt.putInt("wine_barrel.progress",this.progress);
        nbt.putInt("wine_barrel.water_contain",this.water);
        nbt.putInt("wine_barrel.brew_progress",this.wineMaking);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("wine_barrel.progress");
        water = nbt.getInt("wine_barrel.water_contain");
        wineMaking = nbt.getInt("wine_barrel.brew_progress");
//        progress = nbt.getInt("gem_infusing_station.progress"); TODO: figure out what it is
    }

    public void drops() { // TODO: everything drops, water doesn't
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    /** 这一块是实现方块机制的核心，这个类似loop函数会一直被调用。
     * <p>
     * 酒桶的第 10 个槽（从 0 开始）-- 水槽里面有总共 16 槽。
     * 里面如果有水桶，{@code water += 3}，水桶变成空桶。
     * 如果有水瓶，{@code water += 1}，水瓶变成空瓶（能访问到放进去的对象最好）（找对应 ID）。
     * 如果 {@code water} 达到最大值 {@code waterCapability} 就不再执行上述加水逻辑。
     * <p>
     * TODO: 做到能实现不同的配方，而不是单一一种。
     *
     * @param level
     * @param pos
     * @param state
     * @param pEntity
     */
    public static void tick(Level level, BlockPos pos, BlockState state, WineBarrelBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }
        if (pEntity.water < pEntity.waterCapability&&pEntity.flag==0) {//水未满
            if (pEntity.itemHandler.getStackInSlot(9).is(Items.WATER_BUCKET)) {
                pEntity.water = Math.min(pEntity.water + 3, pEntity.waterCapability);
                pEntity.itemHandler.extractItem(9, 1, false); // 把水桶干掉
                pEntity.itemHandler.setStackInSlot(9, new ItemStack(Items.BUCKET)); // 把空弄出来
            } else if (pEntity.itemHandler.getStackInSlot(9).is(Items.POTION)) {
                pEntity.water += 1;
                pEntity.itemHandler.extractItem(9, 1, false);
                pEntity.itemHandler.setStackInSlot(9, new ItemStack(Items.GLASS_BOTTLE));
            }
        }else if(pEntity.wineMaking < pEntity.wineFinish&&pEntity.flag==0){//水已满,而酿造进度未满
            if(hasRecipe(pEntity)) {
                pEntity.progress++;
                if(pEntity.progress >= pEntity.maxProgress) {
                    brewIncrease(pEntity);
                    craftItem(pEntity);
                }
                setChanged(level, pos, state);
            } else {
                pEntity.resetProgress();
                setChanged(level, pos, state);
            }
        }else{//酿造进度已满，可以接酒
            if(pEntity.wineMaking==0){
                pEntity.flag=0;
                return;
            }
            pEntity.flag=1;
            if(pEntity.itemHandler.getStackInSlot(10).is(ModItems.BEER_MUG.get())&&pEntity.wineMaking>0){
                pEntity.itemHandler.extractItem(10, 1, false);
                pEntity.itemHandler.setStackInSlot(11, new ItemStack(ModItems.FULL_BEER_MUG.get(),
                        pEntity.itemHandler.getStackInSlot(11).getCount() +1));
                pEntity.water--;
                pEntity.wineMaking--;
            }
        }
    }
    /**
     * <p>
     * 本方法用于实现酿造进度控制，完成一次progress将会完成一格酿造进度，直到十五格完全完成
     * 随后重置progress进度
     * @param pEntity 是酒桶
     * **/
    public static void brewIncrease(WineBarrelBlockEntity pEntity)
    {
        pEntity.wineMaking++;
        pEntity.resetProgress();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    /** 产出机制，根据sb策划的要求去实现
     * <p>
     * 水槽住满之后，就抽取原料（16份小麦、16份小麦种子、16份糖）。
     * 每消耗1份小麦、1份小麦种子、1份糖产出泡泡一次，循环16次。
     * 也就是全部都消耗完时，酒就酿好了。
     *
     * @param pEntity 就是酒桶本身
     */
    private static void craftItem(WineBarrelBlockEntity pEntity) {
            if(pEntity.identicalWine==1){
                int wheatToConsume = 1;
                int wheatSeedsToConsume = 1;
                int sugarToConsume = 1;

                // 从0-8号槽位中消耗小麦
                for (int i = 0; i < 9 && wheatToConsume > 0; i++) {
                    ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.is(Items.WHEAT)) {
                        // int toExtract = Math.min(stack.getCount(), wheatToConsume);
                        pEntity.itemHandler.extractItem(i, 1, false);
                        wheatToConsume -= 1;
                    }
                }

                // 从0-8号槽位中消耗小麦种子
                for (int i = 0; i < 9 && wheatSeedsToConsume > 0; i++) {
                    ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.is(Items.WHEAT_SEEDS)) {
                        pEntity.itemHandler.extractItem(i, 1, false);
                        wheatSeedsToConsume -= 1;
                    }
                }

                // 从0-8号槽位中消耗糖
                for (int i = 0; i < 9 && sugarToConsume > 0; i++) {
                    ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.is(Items.SUGAR)) {
                        // int toExtract = Math.min(stack.getCount(), sugarToConsume);
                        pEntity.itemHandler.extractItem(i, 1, false);
                        sugarToConsume -= 1;
                    }
                }

                pEntity.resetProgress();

            }
    }
    //各类配方通过实现这个方法来做
    private static boolean hasRecipe(WineBarrelBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        //总条件
        if(canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, new ItemStack(ModItems.BEER_MUG.get(), 1))) {
            // 检查0-8插槽中物品的数量
            int wheatCount = 0;  // 小麦计数
            int sugarCount = 0;  // 糖计数
            int wheatSeedsCount = 0; //小麦种子计数
            int filledSlots = 0; // 已填充的格子数量

            // 遍历0-8号槽位
            for (int i = 0; i < 9; i++) {
                ItemStack stack = entity.itemHandler.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    filledSlots++; // 有物品的格子数量+1

                    if (stack.is(Items.WHEAT)) {
                        wheatCount += stack.getCount(); // 累计小麦数量
                    } else if (stack.is(Items.SUGAR)) {
                        sugarCount += stack.getCount(); // 累计糖数量
                    } else if (stack.is(Items.WHEAT_SEEDS)) {
                        wheatSeedsCount += stack.getCount(); // 累计小麦种子数量
                    }
                }
            }
            boolean hasRequiredIngredients;// 确保材料数量
            boolean hasEnoughSlots; // 确保有且只有这些材料
            boolean hasEnoughWater;// 确保水足够
            int countOfRecipes = 2;//配方数,根据实际数量修改
            int k;

            for(k=1;k<=countOfRecipes;k++){
                if(k==1){
                    //配方一
                    // 判断条件：至少有1个小麦、1个糖，1个种子
                    hasRequiredIngredients = wheatCount >= 1 && sugarCount >= 1 && wheatSeedsCount >= 1;
                    hasEnoughSlots = filledSlots == 3;
                    hasEnoughWater = entity.water >= entity.waterCapability;
                    if(hasRequiredIngredients && hasEnoughSlots && hasEnoughWater){
                        if(entity.identicalWine != 0&&entity.identicalWine != k) return false;
                        entity.identicalWine = k;
                        break;
                    }
                }else if(k==2){
                    //配方二
                    // 判断条件：。。。
                    hasRequiredIngredients = wheatCount >= 1 && sugarCount >= 1 && wheatSeedsCount >= 1;
                    hasEnoughSlots = filledSlots == 3;
                    hasEnoughWater = entity.water >= entity.waterCapability;
                    if(false) {
                        // 如果满足多个配方，则返回false
                        if(entity.identicalWine != 0&&entity.identicalWine != k)return false;
                        entity.identicalWine = k;
                        break;
                    }
                }
            }
            if(k>countOfRecipes)return false;
            return true;
        }
        // 如果不满足总条件，则返回false
        return false;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(11).getItem() == stack.getItem() || inventory.getItem(11).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(11).getMaxStackSize() > inventory.getItem(11).getCount();
    }


}
