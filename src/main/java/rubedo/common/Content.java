package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import rubedo.items.tools.ToolSword;
import cpw.mods.fml.common.registry.GameRegistry;

public class Content {
	public static CreativeTabs creativeTab;
	
	public Content() {
		Content.creativeTab = new CreativeTabs("Rubedo");
		
		registerItems();
		registerToolMaterials();
		registerToolRecipes();
	}
	
	private void registerItems() {	
		toolSword = new ToolSword(3301);
	}
	
	private void registerToolMaterials() {
		toolHeadMaterials = new HashMap<String, ItemStack>();
		{
			toolHeadMaterials.put("flint", new ItemStack(Item.flint));
			toolHeadMaterials.put("copper", new ItemStack(Item.ingotGold));
			toolHeadMaterials.put("iron", new ItemStack(Item.ingotIron));
		}
		
		toolRodMaterials = new HashMap<String, ItemStack>();
		{
			toolRodMaterials.put("wood", new ItemStack(Item.stick));
			toolRodMaterials.put("leather", new ItemStack(Item.leather));
			toolRodMaterials.put("bone", new ItemStack(Item.bone));
			toolRodMaterials.put("blazerod", new ItemStack(Item.blazeRod));
		}
		
		toolCapMaterials = new HashMap<String, ItemStack>();
		{
			toolCapMaterials.put("wood", new ItemStack(Block.planks));
			toolCapMaterials.put("stone", new ItemStack(Block.stone));
			toolCapMaterials.put("copper", new ItemStack(Item.ingotGold));
			toolCapMaterials.put("iron", new ItemStack(Item.ingotIron));
		}
	}
	
	private void registerToolRecipes() {
		for (Entry<String, ItemStack> headEntry : Content.toolHeadMaterials.entrySet())
    	for (Entry<String, ItemStack> rodEntry : Content.toolRodMaterials.entrySet())
    	for (Entry<String, ItemStack> capEntry : Content.toolCapMaterials.entrySet()) {
    		//Sword Recipes
    		GameRegistry.addRecipe(new ShapedRecipes(3, 3, 
    				new ItemStack[] {
    					null, headEntry.getValue(), null,
    					null, rodEntry.getValue(),  null,
    					null, capEntry.getValue(),  null
    				},
    				toolSword.buildTool(
    						headEntry.getKey(), 
    						rodEntry.getKey(), 
    						capEntry.getKey())));
    	}
	}
	
	public static ToolSword toolSword;
	
	public static Map<String, ItemStack> toolHeadMaterials;
	public static Map<String, ItemStack> toolRodMaterials;
	public static Map<String, ItemStack> toolCapMaterials;
}
