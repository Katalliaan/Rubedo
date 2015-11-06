package rubedo.integration.hee;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

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
			Block foo = GameRegistry.findBlock("HardcoreEnderExpansion", blockName);
			foo.setHarvestLevel("pickaxe", 5);
		}
			
	}
}
