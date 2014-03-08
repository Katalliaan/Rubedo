package rubedo.common;

import net.minecraft.creativetab.CreativeTabs;
import rubedo.items.spells.SpellProjectile;
import rubedo.items.spells.SpellSelf;
import rubedo.items.tools.ToolSword;

public class Content {
	public static CreativeTabs creativeTab;
	public static ContentTools tools;
	public static ContentSpells spells;
	
	public Content() {
		Content.creativeTab = new CreativeTabs("Rubedo");
		
		registerItems();
		
		tools = new ContentTools();		
		spells = new ContentSpells();
	}
	
	private void registerItems() {	
		toolSword = new ToolSword(3301);
		
		spellProjectile = new SpellProjectile(3321);
		spellSelf = new SpellSelf(3322);
	}
	
	public static ToolSword toolSword;
	
	public static SpellProjectile spellProjectile;
	public static SpellSelf spellSelf;
}
