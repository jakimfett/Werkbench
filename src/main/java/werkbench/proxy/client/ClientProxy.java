package werkbench.proxy.client;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntity;
import werkbench.Werkbench;
import werkbench.bench.BenchTileEntity;
import werkbench.proxy.CommonProxy;
import werkbench.render.BenchTileEntityRenderer;

/**
 *
 * @author jakimfett
 */
public class ClientProxy extends CommonProxy
{
    @SubscribeEvent
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(BenchTileEntity.class, BenchTileEntityRenderer::new);
    }

    @SubscribeEvent
    public void registerModels(ModelEvent.RegisterAdditional event)
    {
        // Register block and item models here if needed
    }
}
