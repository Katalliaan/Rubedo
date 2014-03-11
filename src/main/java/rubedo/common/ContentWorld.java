package rubedo.common;

import rubedo.world.WorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentWorld {
	public static int copperDensity = 8;
	public static int copperMinY = 20;
	public static int copperMaxY = 64;
	
	public ContentWorld() {
		GameRegistry.registerWorldGenerator(new WorldGenerator());
	}
}
