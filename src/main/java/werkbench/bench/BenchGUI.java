package werkbench.bench;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import werkbench.helper.SpatialHelper;
import werkbench.network.MessageHandler;
import werkbench.network.message.FurnaceUpdateRequestMessage;
import werkbench.reference.Compendium;
import werkbench.reference.Compendium.AdjacentBlockType;
import werkbench.reference.Compendium.RelativeBenchSide;
import werkbench.reference.Config;

public class BenchGUI extends GuiContainer
{
    private BenchTileEntity bench;
    private boolean doFurnaceUpdate;
    private int tickCount;

    public BenchGUI(InventoryPlayer inventoryPlayer, BenchTileEntity bench, World world)
    {
        super(new BenchContainer(inventoryPlayer, bench));
        this.bench = bench;

        this.xSize = 420;

        this.ySize = 206;

        resetTickCount();

    }

    private void incrementTickCount()
    {
        tickCount++;
        if (tickCount >= Config.maxGUIUpdatePacketTickCount)
        {
            resetTickCount();
        }
    }

    private void renderDoubleChestLeft()
    {
        this.mc.renderEngine.bindTexture(Compendium.Resource.GUI.doubleChest);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2 + 30;
        drawTexturedModalRect(x, y, 0, 0, 122, 176);
    }

    private void renderDoubleChestRight()
    {
        this.mc.renderEngine.bindTexture(Compendium.Resource.GUI.doubleChest);
        int x = (width - xSize) / 2 + 298;
        int y = (height - ySize) / 2 + 30;
        drawTexturedModalRect(x, y, 0, 0, 122, 176);
    }

    private void renderFurnaceLeft()
    {
        if (doFurnaceUpdate)
        {
            sendFurnaceGUIUpdateRequest(RelativeBenchSide.LEFT);
        }
        this.mc.renderEngine.bindTexture(Compendium.Resource.GUI.furnace);

        // @TODO - make these number self explanitory
        int x = (width - xSize) / 2 + 46;
        int y = (height - ySize) / 2 + 40;
        renderProgressBars(RelativeBenchSide.LEFT, x, y);
    }

    private void renderFurnaceRight()
    {
        if (doFurnaceUpdate)
        {
            sendFurnaceGUIUpdateRequest(RelativeBenchSide.RIGHT);
        }
        this.mc.renderEngine.bindTexture(Compendium.Resource.GUI.furnace);

        // @TODO - make these number self explanitory
        int x = (width - xSize) / 2 + 298;
        int y = (height - ySize) / 2 + 40;

        renderProgressBars(RelativeBenchSide.RIGHT, x, y);
    }

    private void renderProgressBars(RelativeBenchSide side, int x, int y)
    {
        int[] furnaceSideValues = bench.getFurnaceValuesForSide(side);
        int cookProgress = 0;
        int burnLevel = 0;
        if (furnaceSideValues != null)
        {
            if (furnaceSideValues[0] > 0 && furnaceSideValues[2] > 0)
            {
                burnLevel = (furnaceSideValues[0] * 29 / furnaceSideValues[2]);
            }
            if (furnaceSideValues[1] > 0)
            {
                cookProgress = (furnaceSideValues[1] * 22 / 200);
            }
        }
        drawTexturedModalRect(x, y, 0, 105, 76, 76);
        if (SpatialHelper.getBlockForRelativeSide(bench, side) == AdjacentBlockType.FURNACE_ACTIVE)
        {
            if (burnLevel != 0)
            {
                drawTexturedModalRect(x, y + 76 - burnLevel, 0, 76 + 14 - burnLevel / 2, 76, burnLevel);
            }
            if (cookProgress != 0)
            {
                drawTexturedModalRect(x + 26, y + 12, 76, 0, cookProgress, 15);
            }
        }

        drawTexturedModalRect(x, y, 0, 0, 76, 76);
    }

    private void renderSingleChestLeft()
    {
        this.mc.renderEngine.bindTexture(Compendium.Resource.GUI.singleChest);
        int x = (width - xSize) / 2 + 54;
        int y = (height - ySize) / 2 + 30;
        drawTexturedModalRect(x, y, 0, 0, 68, 176);
    }

    private void renderSingleChestRight()
    {
        this.mc.renderEngine.bindTexture(Compendium.Resource.GUI.singleChest);
        int x = (width - xSize) / 2 + 298;
        int y = (height - ySize) / 2 + 30;
        drawTexturedModalRect(x, y, 0, 0, 68, 176);
    }

    private void resetTickCount()
    {
        tickCount = 0;
        doFurnaceUpdate = true;
    }

    private void sendFurnaceGUIUpdateRequest(RelativeBenchSide side)
    {
        TileEntity tileEntity = SpatialHelper.getTileEntityForRelativeSide(bench, side);
        if (tileEntity instanceof TileEntityFurnace)
        {
            MessageHandler.INSTANCE.sendToServer(new FurnaceUpdateRequestMessage(((TileEntityFurnace) tileEntity), bench, side));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int mousex, int mousey)
    {
        incrementTickCount();
        this.mc.renderEngine.bindTexture(Compendium.Resource.GUI.background);
        int x = (width - xSize) / 2 + 122;
        int y = (height - ySize) / 2 + 40;
        drawTexturedModalRect(x, y, 0, 0, 176, 166);

        AdjacentBlockType leftBlock = SpatialHelper.getBlockForRelativeSide(bench, RelativeBenchSide.LEFT);
        AdjacentBlockType rightBlock = SpatialHelper.getBlockForRelativeSide(bench, RelativeBenchSide.RIGHT);

        if (leftBlock == AdjacentBlockType.CHEST_SINGLE || leftBlock == AdjacentBlockType.CHEST_DOUBLE)
        {
            if (bench.isChestDouble(SpatialHelper.getDirectionFromRelativeSide(bench, RelativeBenchSide.LEFT)))
            {
                renderDoubleChestLeft();
            } else
            {
                renderSingleChestLeft();
            }
        }

        if (leftBlock == AdjacentBlockType.FURNACE_INACTIVE || leftBlock == AdjacentBlockType.FURNACE_ACTIVE)
        {
            renderFurnaceLeft();
        }
        if (rightBlock == AdjacentBlockType.CHEST_SINGLE || rightBlock == AdjacentBlockType.CHEST_DOUBLE)
        {
            if (bench.isChestDouble(SpatialHelper.getDirectionFromRelativeSide(bench, RelativeBenchSide.RIGHT)))
            {
                renderDoubleChestRight();
            } else
            {
                renderSingleChestRight();
            }
        }
        if (rightBlock == AdjacentBlockType.FURNACE_INACTIVE || rightBlock == AdjacentBlockType.FURNACE_ACTIVE)
        {
            renderFurnaceRight();
        }
        if (doFurnaceUpdate)
        {
            doFurnaceUpdate = false;
        }
    }

    public int getLeft()
    {
        return this.guiLeft;
    }

    public int getTop()
    {
        return this.guiTop;
    }

}
