package rubedo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import rubedo.common.Config;
import rubedo.common.ContentAI;
import rubedo.common.ContentTools;
import rubedo.common.ContentWorld;
import rubedo.common.IContent;
import util.ReflectionHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "rubedo", name = "Rubedo", version = "0.1.3d")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
public class RubedoCore {
	public static String getId() {
		return RubedoCore.class.getAnnotation(Mod.class).modid();
	}
	public static String getName() {
		return RubedoCore.class.getAnnotation(Mod.class).name();
	}
	public static String getVersion() {
		return RubedoCore.class.getAnnotation(Mod.class).version();
	}

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
	public static Map<Class<? extends IContent>, IContent> contentUnits;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		contentUnits = new LinkedHashMap<Class<? extends IContent>, IContent>();
		contentUnits.put(ContentWorld.class, new ContentWorld());
		contentUnits.put(ContentTools.class, new ContentTools());
		//contentUnits.put(ContentSpells.class, new ContentSpells());
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
		MinecraftForge.setBlockHarvestLevel(Block.obsidian, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(Block.netherrack, "pickaxe", 3);
		Block.netherrack.setHardness(1.5F);
		MinecraftForge
				.setBlockHarvestLevel(Block.oreNetherQuartz, "pickaxe", 3);
		Block.oreNetherQuartz.setHardness(3.0F);
		MinecraftForge.setBlockHarvestLevel(Block.netherBrick, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(Block.whiteStone, "pickaxe", 4);

		// Backup flint recipe
		GameRegistry.addShapelessRecipe(new ItemStack(Item.flint),
				new ItemStack(Item.bowlEmpty.setContainerItem(Item.bowlEmpty)),
				new ItemStack(Block.gravel));
		
		// Remove nether portals
		Config.initId("BlockPortal");
		BlockPortal portal = new BlockPortal(Config.getId("BlockPortal")) {
			public boolean tryToCreatePortal(World world, int x, int y, int z)
		    {
				//return super.tryToCreatePortal(world, x, y, z);
				return false;
		    }
		};
		portal.setHardness(-1.0F).setStepSound(Block.soundGlassFootstep).setLightValue(0.75F).setUnlocalizedName("portal").setTextureName("portal");
		
		ReflectionHelper.setStatic(Block.class, "portal", portal);
	}
}
