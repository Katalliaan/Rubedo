package rubedo.common;

import net.minecraft.creativetab.CreativeTabs;
import rubedo.items.tools.ToolSword;

public class Content {
	public static CreativeTabs creativeTab;
	public static ContentTools tools;
	
	public Content() {
		Content.creativeTab = new CreativeTabs("Rubedo");
		
		registerItems();
		
		tools = new ContentTools();
	}
	
	private void registerItems() {	
		toolSword = new ToolSword(3301);
	}
	
	public static ToolSword toolSword;
}
