package rubedo;

import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import rubedo.integration.atg.ATGIntegration;
import rubedo.integration.fsp.FSPIntegration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = RubedoIntegration.modid, name = RubedoIntegration.name, version = RubedoIntegration.version, dependencies = "after:rubedo; after:ATG; after:enhancedbiomes; after:Steamcraft;")
public class RubedoIntegration {
	public static final String modid = "rubedoIntegration";
	public static final String name = "@NAME@";
	public static final String version = "@VERSION@";

	// The instance of your mod that Forge uses.
	@Instance(value = "rubedoIntegration")
	public static RubedoIntegration instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if (Loader.isModLoaded("ATG")) {
			MinecraftForge.EVENT_BUS.register(this);
			ATGIntegration.preInit();
		}

		if (Loader.isModLoaded("Steamcraft"))
			FSPIntegration.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (Loader.isModLoaded("ATG"))
			ATGIntegration.init();

		if (Loader.isModLoaded("Steamcraft"))
			FSPIntegration.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("ATG"))
			ATGIntegration.postInit();
	}

	@SubscribeEvent
	public void onGuiInit(InitGuiEvent event) {
		if (Loader.isModLoaded("ATG") && event.gui instanceof GuiCreateWorld) {
			ATGIntegration.onGuiInit(event);
		}
	}
}
