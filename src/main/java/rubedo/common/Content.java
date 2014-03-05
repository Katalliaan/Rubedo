package rubedo.common;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import rubedo.items.tools.ToolBase;

public class Content {
	public static CreativeTabs creativeTab;
	
	public Content() {
		Content.creativeTab = new CreativeTabs("Rubedo");
		
		registerItems();
	}

	private void registerItems() {
		testTool = new ToolBase(3300) {
			@Override
		    public void registerIcons (IconRegister iconRegister)
		    {
				getRenderList().put(0, "rubedo:testHead");
				getRenderList().put(1, "rubedo:testHandle");
				
				this.setUnlocalizedName("TestTool");
				
				super.registerIcons(iconRegister);
		    }
		};
	}
	
	public static ToolBase testTool;
	public static ToolBase testSpell;
}
