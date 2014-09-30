package rubedo;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = RubedoIntegration.modid, name = RubedoIntegration.name, version = RubedoIntegration.version)
public class RubedoIntegration {
	public static final String modid = "rubedoIntegration";
	public static final String name = "@NAME@";
	public static final String version = "@VERSION@";

	// The instance of your mod that Forge uses.
	@Instance(value = "rubedoIntegration")
	public static RubedoIntegration instance;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// TODO: add this in when ATG fixes the bug with ReplaceBiomeBlocks
		// MinecraftForge.EVENT_BUS.register(new ReplaceBiomeBlocksHandler());
		// MinecraftForge.TERRAIN_GEN_BUS.register(new
		// ReplaceBiomeBlocksHandler());
	}

	@EventHandler
	public void post(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("ATG"))
			ATGIntegration.init();
	}
}
