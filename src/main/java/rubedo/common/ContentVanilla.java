package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import rubedo.items.ItemToolHead;
import rubedo.util.ReflectionHelper;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class ContentVanilla extends Singleton<ContentVanilla> implements IContent {		
	protected ContentVanilla() {
		super(ContentVanilla.class);
	}

	public void load() {
		remapToolHeads();
	}

	@Override
	public void config(Configuration config) {
		
	}

	@Override
	public void register() {
		
	}
	
	private void remapToolHeads() {
		Map<String, String> remaps = new HashMap<String, String>();
		
		remaps.put("sword_head_wood", "wooden_sword");
		remaps.put("axe_head_wood", "wooden_axe");
		remaps.put("shovel_head_wood", "wooden_shovel");
		remaps.put("pickaxe_head_wood", "wooden_pickaxe");
		remaps.put("scythe_head_wood", "wooden_hoe");
		
		remaps.put("sword_head_flint", "stone_sword");
		remaps.put("axe_head_flint", "stone_axe");
		remaps.put("shovel_head_flint", "stone_shovel");
		remaps.put("pickaxe_head_flint", "stone_pickaxe");
		remaps.put("scythe_head_flint", "stone_hoe");
		
		remaps.put("sword_head_iron", "iron_sword");
		remaps.put("axe_head_iron", "iron_axe");
		remaps.put("shovel_head_iron", "iron_shovel");
		remaps.put("pickaxe_head_iron", "iron_pickaxe");
		remaps.put("scythe_head_iron", "iron_hoe");
		
		remaps.put("sword_head_gold", "golden_sword");
		remaps.put("axe_head_gold", "golden_axe");
		remaps.put("shovel_head_gold", "golden_shovel");
		remaps.put("pickaxe_head_gold", "golden_pickaxe");
		remaps.put("scythe_head_gold", "golden_hoe");
		
		for (Entry<String, String> entry : remaps.entrySet()) {
			Item item = new ItemToolHead(entry.getKey());
			ReflectionHelper.setStatic(Items.class, entry.getValue(), item);
			RemapHelper.overwriteEntry(Item.itemRegistry, "minecraft:"+entry.getValue(), item);
		}
	}
}
