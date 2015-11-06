package rubedo.integration.hee;

import cpw.mods.fml.common.registry.GameRegistry;

public class HEEIntegration {
	
	public static void postInit() {
		String[] blockNames = { 
				"end_powder_ore",
				"end_stone_terrain",
				"stardust_ore",
				"igneous_rock_ore",
				"endium_ore",
				"instability_orb_ore"				
		};
		
		for ( String blockName : blockNames) {
			GameRegistry.findBlock("HardcoreEnderExpansion", blockName).setHarvestLevel("pickaxe", 5);
		}
			
	}
}
