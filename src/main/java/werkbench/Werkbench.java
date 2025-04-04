package werkbench;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.registries.GameRegistries;
import jakimbox.helper.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import werkbench.bench.BenchBlock;
import werkbench.bench.BenchTileEntity;
import werkbench.handler.GUIHandler;
import werkbench.network.MessageHandler;
import werkbench.proxy.CommonProxy;
import werkbench.reference.Compendium;
import werkbench.reference.Config;

@Mod(
    modid = Compendium.Naming.id,
    name = Compendium.Naming.name,
    version = Compendium.Version.full,
    useMetadata = false,
    acceptedMinecraftVersions = "[1.21.5,1.22)",
    dependencies = "required-after:forge@[55.0.3,);required-after:jakimbox"
)
public class Werkbench
{

    // Instancing
    @Instance(value = Compendium.Naming.id)
    public static Werkbench INSTANCE;

    // Public metadata about the mod, used by Forge for display on the client's mod list
    @Mod.Metadata(Compendium.Naming.id)
    protected static ModMetadata metadata;

    @SidedProxy(clientSide = "werkbench.proxy.client.ClientProxy", serverSide = "werkbench.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static BenchBlock werkbench;

    protected static final GUIHandler guiHandler = new GUIHandler();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Register instance.
        INSTANCE = this;

        // Load configuration.
        LogHelper.debug("Loading configuration...");
        Config.init();
        MinecraftForge.EVENT_BUS.register(new Config());

        LogHelper.debug("Set Werkbench MetaData info...");
        metadata = Compendium.MetaData.init(metadata);

        LogHelper.debug("Registering Werkbench block...");
        werkbench = new BenchBlock();
        ForgeRegistries.BLOCKS.register(werkbench);
        GameRegistry.registerTileEntity(BenchTileEntity.class, new ResourceLocation(Compendium.Naming.id, Compendium.Naming.tileEntity));

        LogHelper.debug("Registering GUI handler for werkbench...");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);

        // Register packet handler
        MessageHandler.init();

        LogHelper.debug("Werkbench done loading");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // Recipe moved to JSON file in data/werkbench/recipes/werkbench.json
        proxy.registerRenderers();
    }
}
