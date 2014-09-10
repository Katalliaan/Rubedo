package rubedo.common;

import java.util.List;

import rubedo.items.MultiItem;
import rubedo.items.tools.ToolAxe;
import rubedo.items.tools.ToolPickaxe;
import rubedo.items.tools.ToolScythe;
import rubedo.items.tools.ToolShovel;
import rubedo.items.tools.ToolSword;
import net.minecraftforge.common.Configuration;

public class ContentHelper {
	
	public void setMultiItems(List<Class<? extends MultiItem>> classes) {
		this.
	}

	public void config(Configuration config, List<Class<? extends MultiItem>> classes) {
		for (Class<? extends MultiItem> clazz : classes) {
			Config.initId(clazz.getSimpleName());
		}
	}

	public void register() {
		toolSword = new ToolSword(Config.getId("ToolSword"));
		toolPickaxe = new ToolPickaxe(Config.getId("ToolPickaxe"));
		toolShovel = new ToolShovel(Config.getId("ToolShovel"));
		toolAxe = new ToolAxe(Config.getId("ToolAxe"));
		toolScythe = new ToolScythe(Config.getId("ToolScythe"));
	}

}
