package werkbench.proxy.client;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.event.ModelRegistryEvent;
import jakimbox.prefab.render.BasicItemRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import werkbench.Werkbench;
import werkbench.bench.BenchTileEntity;
import werkbench.bench.BenchTileEntityRenderer;
import werkbench.proxy.CommonProxy;

/**
 *
 * @author jakimfett
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers()
    {
        RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

        BenchTileEntityRenderer benchTileEntityRenderer = new BenchTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(BenchTileEntity.class, benchTileEntityRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Werkbench.werkbench), new BasicItemRenderer(benchTileEntityRenderer, new BenchTileEntity()));
    }
}
