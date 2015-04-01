package werkbench.bench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class BenchContainer extends Container
{

    private final BenchTileEntity bench;

    private final World world;
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    boolean loading = false;

    /**
     * Container object for the workbench
     *
     * @param inventoryPlayer the player's inventory
     * @param bench           the bench TileEntity
     * @param world           the world object
     */
    public BenchContainer(InventoryPlayer inventoryPlayer, BenchTileEntity bench, World world)
    {
        this.world = world;
        this.bench = bench;

        loadCraftGridFromTileEntity();

        bindPlayerInventory(inventoryPlayer);
        bindCraftGrid(this.bench);

        if (this.bench.getHasLeftChest())
        {
            if (this.bench.chestIsDouble(this.bench.getLeftChestDirection()))
            {
                bindLeftChestDouble(this.bench);
            } else
            {
                bindLeftChestSingle(this.bench);
            }
        }
        if (this.bench.getHasRightChest())
        {
            if (this.bench.chestIsDouble(this.bench.getRightChestDirection()))
            {
                bindRightChestDouble(this.bench);
            } else
            {
                bindRightChestSingle(this.bench);
            }
        }

        // Add the crafting output to the right side
        // This line saves and syncs the craftgrid perfectly, but doesn't display the output clientside
        //addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.bench, this.craftResult, 0, 253, 70));
        // If I switch it to use the craftMatrix rather than the bench tileEntity (and below in the bindCraftGrid method), it displays the output just fine clientside
        // but then it doesn't sync...nicely...I'd have to save the craftmatrix back to the TE, but load/update if the TE had changed...it gets hairy.
        addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 253, 70));
    }

    /**
     * Add the crafting grid to the GUI
     *
     * @param bench the bench TileEntity
     */
    private void bindCraftGrid(BenchTileEntity bench)
    {
        int slot, x, y;
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                slot = j + i * 3;
                x = 184 + j * 18;
                y = 52 + i * 18;
                //addSlotToContainer(new Slot(bench, slot, x, y));

                // ..as long as I also set this to the craftMatrix, it updates clientside
                addSlotToContainer(new Slot(this.craftMatrix, slot, x, y));
            }
        }
    }

    private void bindLeftChestDouble(BenchTileEntity bench)
    {
        bindLeftChestSingle(bench);

        TileEntityChest chestLeft = bench.getLeftChestDoubleTileEntity();
        if (chestLeft != null)
        {
            int slot, x, y;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    slot = j + i * 9;
                    x = 8 + i * 18;
                    y = 38 + j * 18;
                    addSlotToContainer(new Slot(chestLeft, slot, x, y));
                }
            }
        }
    }

    /**
     * Add the left chest slots
     *
     * @param BenchTileEntity the bench tile entity
     */
    private void bindLeftChestSingle(BenchTileEntity bench)
    {
        TileEntityChest chestLeft = bench.getLeftChestSingleTileEntity();
        if (chestLeft != null)
        {
            int slot, x, y;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    slot = j + i * 9;
                    x = 62 + i * 18;
                    y = 38 + j * 18;
                    addSlotToContainer(new Slot(chestLeft, slot, x, y));
                }
            }
        }
    }

    /**
     * Add the player's inventory slots to the GUI
     *
     * @param inventoryPlayer the player's inventory
     */
    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        int slot, x, y;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                slot = j + i * 9 + 9;
                x = 130 + j * 18;
                y = 124 + i * 18;
                addSlotToContainer(new Slot(inventoryPlayer, slot, x, y));
                if (i == 0)
                {
                    x = 130 + j * 18;
                    y = 182;
                    addSlotToContainer(new Slot(inventoryPlayer, j, x, y));
                }
            }
        }
    }

    private void bindRightChestDouble(BenchTileEntity bench)
    {
        bindRightChestSingle(bench);

        TileEntityChest chestRight = bench.getRightChestDoubleTileEntity();
        if (chestRight != null)
        {
            int slot, x, y;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    slot = j + i * 9;
                    x = 360 + i * 18;
                    y = 38 + j * 18;
                    addSlotToContainer(new Slot(chestRight, slot, x, y));
                }
            }
        }

    }

    /**
     * Add the right chest slots
     *
     * @param BenchTileEntity the bench tile entity
     */
    private void bindRightChestSingle(BenchTileEntity bench)
    {
        TileEntityChest chestRight = bench.getRightChestSingleTileEntity();
        if (chestRight != null)
        {
            int slot, x, y;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    slot = j + i * 9;
                    x = 306 + i * 18;
                    y = 38 + j * 18;
                    addSlotToContainer(new Slot(chestRight, slot, x, y));
                }
            }
        }
    }

    private void saveCraftGridToTileEntity()
    {
        for (int s = 0; s < bench.getSizeInventory(); s++)
        {
            bench.setInventorySlotContents(s, craftMatrix.getStackInSlot(s));
        }
    }

    private void loadCraftGridFromTileEntity()
    {
        loading = true;
        for (int s = 0; s < bench.getSizeInventory(); s++)
        {
            craftMatrix.setInventorySlotContents(s, bench.getStackInSlot(s));
        }
        loading = false;
    }

    /**
     * Determine if the player can interact with the container
     *
     * @param entityPlayer the player entity
     * @return boolean
     */
    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }

    /**
     * Send inventory changes to listeners and sync craftMatrix with TE inventory
     */
    @Override
    public void detectAndSendChanges()
    {
        for (int i = 0; i < this.inventorySlots.size(); ++i)
        {
            ItemStack itemstack = ((Slot) this.inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack) this.inventoryItemStacks.get(i);

            if (!ItemStack.areItemStacksEqual(itemstack1, itemstack))
            {
                itemstack1 = itemstack == null ? null : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (int j = 0; j < this.crafters.size(); ++j)
                {
                    ((ICrafting) this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
                }
            }
        }
    }

    /**
     * Update the crafting result when the grid changes
     *
     * @param inventory
     */
    @Override
    public void onCraftMatrixChanged(IInventory inventory)
    {
        craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.world));
        if (!bench.getWorldObj().isRemote && !loading) saveCraftGridToTileEntity();
    }

    /**
     * Called when a player shift-clicks on a slot.
     *
     * Shift clicking currently ignores the first slot: http://pics.jakimfett.com/2015-01-17_21-05-09.png
     *
     * @param player EntityPlayer object
     * @param slotID int ID of the slot
     * @return ItemStack for the slotID
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotID == 0)
            {
                if (!this.mergeItemStack(itemstack1, 9, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (slotID >= 10 && slotID < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            } else if (slotID >= 37 && slotID < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            } else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

}
