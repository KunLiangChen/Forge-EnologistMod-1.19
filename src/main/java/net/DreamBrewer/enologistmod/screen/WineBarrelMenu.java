package net.DreamBrewer.enologistmod.screen;

import net.DreamBrewer.enologistmod.block.ModBlocks;
import net.DreamBrewer.enologistmod.block.entity.WineBarrelBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WineBarrelMenu extends AbstractContainerMenu {
    public final WineBarrelBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    public WineBarrelMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(7));
    }

    public WineBarrelMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.WINE_BARREL_MENU.get(), id);
        checkContainerSize(inv, 12);//这里的数字是槽位数量，槽位数量必须与方块实体的槽位数量一致
        blockEntity = (WineBarrelBlockEntity) entity;
        this.level = inv.player.level;
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        
        //插槽于此处初始化，包括其存在位置
        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            // 左上角3×3网格（输入材料槽）
            int startX = 19;
            int startY = 15;
            
            // 添加3×3网格的9个槽位
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    this.addSlot(new SlotItemHandler(handler, col + row * 3, 
                            startX + col * 18, startY + row * 18));
                }
            }
            
            // 右侧垂直槽位（可能是过滤器或特殊材料）
            this.addSlot(new SlotItemHandler(handler, 9, 80, 10));  // 顶部槽位
            this.addSlot(new SlotItemHandler(handler, 10, 139, 31)); // 中间槽位
            this.addSlot(new SlotItemHandler(handler, 11, 139, 61)); // 底部槽位
        });
        
        addDataSlots(data);
    }
    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public boolean isBrewing()
    {
        return data.get(4) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 31; // This is the height in pixels of your arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getWineProgress(){
        int wineMaking = this.data.get(4);
        int wineFinishing = this.data.get(5);
        int progressArrowSize = 66;
        return wineFinishing !=0 && wineMaking != 0 ? wineMaking*progressArrowSize/wineFinishing : 0;
    }

    public int getWineType()
    {
        return this.data.get(6);
    }

    public int getWaterProgress() {
        int progress = this.data.get(2);
        int maxProgress = this.data.get(3);
        int progressArrowSize = 66;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 12;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.WINE_BARREL.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
