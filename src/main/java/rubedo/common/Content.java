package rubedo.common;

import java.util.Arrays;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import rubedo.items.tools.ToolSword;

public class Content {
	public static CreativeTabs creativeTab;
	
	public Content() {
		Content.creativeTab = new CreativeTabs("Rubedo");
		
		registerItems();
		registerToolMaterials();
	}

	private void registerItems() {	
		toolSword = new ToolSword(3301);
	}
	
	private void registerToolMaterials() {
		toolHeadMaterials = Arrays.asList(new String [] {
				"flint", "copper", "iron"
		});
		
		toolRodMaterials = Arrays.asList(new String [] {
				"wood", "leather", "bone", "blazerod"
		});
		
		toolCapMaterials = Arrays.asList(new String [] {
				"wood", "stone", "copper", "iron"
		});
	}
	
	public static ToolSword toolSword;
	
	public static List<String> toolHeadMaterials;
	public static List<String> toolRodMaterials;
	public static List<String> toolCapMaterials;
}
