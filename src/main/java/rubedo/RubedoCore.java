package rubedo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import cpw.mods.fml.common.registry.GameRegistry;

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
	public static Map<Class<? extends IContent>, IContent> contentUnits;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		contentUnits = new LinkedHashMap<Class<? extends IContent>, IContent>();
		contentUnits.put(ContentWorld.class, new ContentWorld());
		contentUnits.put(ContentTools.class, new ContentTools());
		contentUnits.put(ContentSpells.class, new ContentSpells());
		contentUnits.put(ContentAI.class, new ContentAI());

		// Load the configs
		Config.load(event);
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
		// Mining balance changes
		Blocks.obsidian.setHarvestLevel("pickaxe", 2);
		Blocks.netherrack.setHarvestLevel("pickaxe", 3);
		Blocks.netherrack.setHardness(1.5F);
		Blocks.quartz_ore.setHarvestLevel("pickaxe", 3);
		Blocks.quartz_ore.setHardness(3.0F);
		Blocks.nether_brick.setHarvestLevel("pickaxe", 3);
		Blocks.end_stone.setHarvestLevel("pickaxe", 4);

		// Backup flint recipe
		GameRegistry.addShapelessRecipe(new ItemStack(Items.flint),
				new ItemStack(Items.bowl.setContainerItem(Items.bowl)),
				new ItemStack(Blocks.gravel));

		//TODO: figure out how Nether Portals are made
		/*BlockPortal portal = new BlockPortal() {
			@Override
			public boolean tryToCreatePortal(World world, int x, int y, int z)
		    {
				//return super.tryToCreatePortal(world, x, y, z);
				return false;
		    }
		};
		portal.setHardness(-1.0F).setStepSound(Block.soundTypeGlass).setLightLevel(0.75F).setBlockName("portal").setBlockTextureName("portal");
		
		ReflectionHelper.setStatic(Block.class, "portal", portal);*/
	}
}
