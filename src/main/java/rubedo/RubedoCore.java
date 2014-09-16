package rubedo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import rubedo.common.Config;
import rubedo.common.ContentAI;
import rubedo.common.ContentSpells;
import rubedo.common.ContentTools;
import rubedo.common.ContentVanilla;
import rubedo.common.ContentWorld;
import rubedo.common.IContent;
import rubedo.util.Singleton;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RubedoCore.modid, name = RubedoCore.name, version = RubedoCore.version)
public class RubedoCore {
	public static final String modid = "rubedo";
	public static final String name = "@NAME@";
	public static final String version = "@VERSION@";
	
	static {
		Singleton.getInstance(ContentVanilla.class).load();
	}

	// The instance of your mod that Forge uses.
	@Instance(value = "rubedo")
	public static RubedoCore instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "rubedo.client.ClientProxy", serverSide = "rubedo.CommonProxy")
	public static CommonProxy proxy;

	// Shared logger
	public static final Logger logger = Logger.getLogger("Rubedo");

	public static final CreativeTabs creativeTab = new CreativeTabs("Rubedo") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.bedrock);
		}
	};

	// Mod content
	private Map<Class<? extends IContent>, IContent> contentUnits;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		contentUnits = new LinkedHashMap<Class<? extends IContent>, IContent>();
		contentUnits.put(ContentWorld.class, Singleton.getInstance(ContentWorld.class));
		contentUnits.put(ContentTools.class, Singleton.getInstance(ContentTools.class));
		contentUnits.put(ContentSpells.class, Singleton.getInstance(ContentSpells.class));
		contentUnits.put(ContentAI.class, Singleton.getInstance(ContentAI.class));

		// Load the configs
		Config.load(event, contentUnits.values());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		for (IContent content : contentUnits.values())
			content.register();

		// Register the renderers
		RubedoCore.proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
