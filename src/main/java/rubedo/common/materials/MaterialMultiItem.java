package rubedo.common.materials;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.common.ContentWorld;
import rubedo.items.ItemToolHead;

public abstract class MaterialMultiItem {
	public String name;

	public int durability;
	public float modDurability;
	public int damage;
	public float speed;
	public int special;
	public int miningLevel;
	public ItemStack headMaterial;
	public ItemStack rodMaterial;
	public ItemStack capMaterial;

	private Map<String, ItemStack> toolHeads = new LinkedHashMap<String, ItemStack>();

	public ItemStack getToolHead(String tool) {
		if (this.headMaterial != null && !this.toolHeads.containsKey(tool))
			this.toolHeads.put(tool, new ItemStack(ItemToolHead.getHeadMap()
					.get(tool + "_head_" + this.name), 1));
		return this.toolHeads.get(tool);
	}

	public static class Wood extends MaterialMultiItem {
		public Wood() {
			this.name = "wood";
			this.modDurability = 0.5f;
			this.special = 0;
			this.rodMaterial = new ItemStack(Items.stick);
			this.capMaterial = new ItemStack(Blocks.planks, 1,
					OreDictionary.WILDCARD_VALUE);
		}
	}

	public static class Flint extends MaterialMultiItem {
		public Flint() {
			this.name = "flint";
			this.durability = 60;
			this.damage = 0;
			this.speed = 4.0f;
			this.miningLevel = 0;
			this.headMaterial = new ItemStack(Items.flint, 1, 0);
		}
	}

	public static class Bone extends MaterialMultiItem {
		public Bone() {
			this.name = "bone";
			this.modDurability = 1.2f;
			this.rodMaterial = new ItemStack(Items.bone);
		}
	}

	public static class Leather extends MaterialMultiItem {
		public Leather() {
			this.name = "leather";
			this.modDurability = 1.0f;
			this.rodMaterial = new ItemStack(Items.leather);
		}
	}

	public static class Blazerod extends MaterialMultiItem {
		public Blazerod() {
			this.name = "blazerod";
			this.modDurability = 2.0f;
			this.rodMaterial = new ItemStack(Items.blaze_rod);
		}
	}

	public static class Copper extends MaterialMultiItem {
		public Copper() {
			this.name = "copper";
			this.durability = 130;
			this.special = 2;
			this.damage = 1;
			this.speed = 2.0f;
			this.miningLevel = 1;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_ingot"));
			this.capMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_ingot"));
		}
	}

	public static class Iron extends MaterialMultiItem {
		public Iron() {
			this.name = "iron";
			this.durability = 250;
			this.special = 1;
			this.damage = 2;
			this.speed = 6.0f;
			this.miningLevel = 2;
			this.headMaterial = new ItemStack(Items.iron_ingot, 1, 0);
			this.capMaterial = new ItemStack(Items.iron_ingot);
		}
	}

	public static class Gold extends MaterialMultiItem {
		public Gold() {
			this.name = "gold";
			this.durability = 35;
			this.special = 3;
			this.damage = 0;
			this.speed = 6.0f;
			this.miningLevel = 0;
			this.headMaterial = new ItemStack(Items.gold_ingot, 1, 0);
			this.capMaterial = new ItemStack(Items.gold_ingot);
		}
	}

	public static class Orichalcum extends MaterialMultiItem {
		public Orichalcum() {
			this.name = "orichalcum";
			this.durability = 200;
			this.special = 3;
			this.damage = 2;
			this.speed = 4.0f;
			this.miningLevel = 1;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("orichalcum_ingot"));
		}
	}

	public static class Silver extends MaterialMultiItem {
		public Silver() {
			this.name = "silver";
			this.durability = 75;
			this.special = 2;
			this.damage = 1;
			this.speed = 8.0f;
			this.miningLevel = 2;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("silver_ingot"));
		}
	}

	public static class Steel extends MaterialMultiItem {
		public Steel() {
			this.name = "steel";
			this.durability = 1500;
			this.special = 2;
			this.damage = 3;
			this.speed = 9.0f;
			this.miningLevel = 3;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("steel_ingot"));
		}
	}

	public static class Mythril extends MaterialMultiItem {
		public Mythril() {
			this.name = "mythril";
			this.durability = 500;
			this.special = 4;
			this.damage = 1;
			this.speed = 12.0f;
			this.miningLevel = 4;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("mythril_ingot"));
		}
	}

	public static class Hepatizon extends MaterialMultiItem {
		public Hepatizon() {
			this.name = "hepatizon";
			this.durability = 750;
			this.special = 5;
			this.damage = 3;
			this.speed = 10.0f;
			this.miningLevel = 4;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("hepatizon_ingot"));
		}
	}
}
