package rubedo.common;

import net.minecraft.client.renderer.texture.IconRegister;
import rubedo.items.tools.ToolBase;

public class Content {
	public Content() {
		registerItems();
	}

	private void registerItems() {
		testTool = new ToolBase(3300) {
			@Override
		    public void registerIcons (IconRegister iconRegister)
		    {
				getRenderList().put(0, "rubedo:testHead");
				getRenderList().put(1, "rubedo:testHandle");
				super.registerIcons(iconRegister);
		    }
		};
	}
	
	public static ToolBase testTool;
	public static ToolBase testSpell;
}
