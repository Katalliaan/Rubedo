package rubedo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import rubedo.common.Config;
import rubedo.common.ContentAI;
import rubedo.common.ContentSpells;
import rubedo.common.ContentTools;
import rubedo.common.ContentWorld;
import rubedo.common.IContent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "rubedo", name = "Rubedo", version = "0.1.2")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
public class RubedoCore {
	public static String getId() { return RubedoCore.class.getAnnotation(Mod.class).modid(); }
	public static String getName() { return RubedoCore.class.getAnnotation(Mod.class).name(); }
	public static String getVersion() { return RubedoCore.class.getAnnotation(Mod.class).version(); }
	
	// The instance of your mod that Forge uses.
	@Instance(value = "rubedo")
	public static RubedoCore instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "rubedo.client.ClientProxy", serverSide = "rubedo.CommonProxy")
	public static CommonProxy proxy;
	
	// Shared logger
	public static final Logger logger = Logger.getLogger("Rubedo");

	public static final CreativeTabs creativeTab = new CreativeTabs("Rubedo");
	
	// Mod content
	public static List<IContent> contentUnits;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		contentUnits = new ArrayList<IContent>();
		contentUnits.addAll(Arrays.asList(new IContent[] {
				new ContentWorld(),
				new ContentTools(),
				// new ContentSpells(),
				new ContentAI()
		}));
		
		// Load the configs
		Config.load(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		for (IContent content : contentUnits)
			content.register();
		
		// Register the renderers
		RubedoCore.proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Mining balance changes
		MinecraftForge.setBlockHarvestLevel(Block.obsidian, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(Block.netherrack, "pickaxe", 3);
		Block.netherrack.setHardness(1.5F);
		MinecraftForge.setBlockHarvestLevel(Block.oreNetherQuartz, "pickaxe", 3);
		Block.oreNetherQuartz.setHardness(3.0F);
		MinecraftForge.setBlockHarvestLevel(Block.netherBrick, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(Block.whiteStone, "pickaxe", 4);
	}
}
