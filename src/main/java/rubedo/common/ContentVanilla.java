package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import rubedo.items.ItemToolHead;
import rubedo.util.ReflectionHelper;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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
		// Mining balance changes
		Blocks.obsidian.setHarvestLevel("pickaxe", 2);
		Blocks.netherrack.setHarvestLevel("pickaxe", 3);
		Blocks.netherrack.setHardness(1.5F);
		Blocks.quartz_ore.setHarvestLevel("pickaxe", 3);
		Blocks.quartz_ore.setHardness(3.0F);
		Blocks.nether_brick.setHarvestLevel("pickaxe", 3);
		Blocks.end_stone.setHarvestLevel("pickaxe", 4);

		// Backup flint recipe
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.flint), 
				new ItemStack(Items.bowl.setContainerItem(Items.bowl)),
				new ItemStack(Blocks.gravel)));
		
		//TODO: figure out how Nether Portals are made
		/*BlockPortal portal = new BlockPortal() {
			@Override
			public boolean tryToCreatePortal(World world, int x, int y, int z)
		    {
				//return super.tryToCreatePortal(world, x, y, z);
				return false;
		    }
		};
		portal.setHardness(-1.0F).setStepSound(Block.soundTypeGlass).setLightLevel(0.75F).setBlockName("portal").setBlockTextureName("portal");
		
		ReflectionHelper.setStatic(Block.class, "portal", portal);*/
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
