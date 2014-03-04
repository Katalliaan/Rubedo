package rubedo.common;

import rubedo.tools.ToolBase;

public class Content {
	public Content() {
		registerItems();
	}

	private void registerItems() {
		testTool = new ToolBase(3300, 100);
	}
	
	public static ToolBase testTool;
}
