package rubedo;

import java.util.logging.Logger;

import net.minecraftforge.common.MinecraftForge;
import rubedo.common.Content;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "rubedo", name = "Rubedo", version = "0.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class RubedoCore {
	// The instance of your mod that Forge uses.
	@Instance(value = "rubedo")
	public static RubedoCore instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "rubedo.client.ClientProxy", serverSide = "rubedo.CommonProxy")
	public static CommonProxy proxy;
	
	// Shared logger
	public static final Logger logger = Logger.getLogger("Rubedo");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		content = new Content();
		
		proxy.registerRenderers();
	}

	@EventHandler
	public void initialization(FMLInitializationEvent event) {
		
		// Adding AI EventHandlers
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.EntityAnimalEventHandler());
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.EntityLivingEventHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	public Content content;
}
