package rubedo.common.materials;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.common.ContentWorld;
import rubedo.items.ItemSpellBase;
import rubedo.items.ItemToolHead;

public abstract class MaterialMultiItem {
	public enum MaterialType {
		OTHER, METAL_MUNDANE, METAL_ARCANE, METAL_BRONZE
	}

	public String name;

	public MaterialType type = MaterialType.OTHER;

	public boolean isColdWorkable = true;
	public int durability;
	public float modDurability;
	public float modSpeed;
	public int damage;
	public float speed;
	public int mundaneLevel;
	public float modDamage;
	public int arcaneLevel;
	public int miningLevel;
	public ItemStack headMaterial;
	public ItemStack rodMaterial;
	public ItemStack capMaterial;
	public ItemStack baseMaterial;
	public ItemStack spellFocusMaterial;
	public String spellFocusType;
	public ItemStack spellEffectMaterial;
	public String spellEffectType;

	private Map<String, ItemStack> toolHeads = new LinkedHashMap<String, ItemStack>();
	private Map<String, ItemStack> spellParts = new LinkedHashMap<String, ItemStack>();

	public ItemStack getToolHead(String tool) {
		if (this.headMaterial != null && !this.toolHeads.containsKey(tool))
			this.toolHeads.put(tool, new ItemStack(ItemToolHead.getHeadMap()
					.get(tool + "_head_" + this.name), 1));
		return this.toolHeads.get(tool);
	}

	public ItemStack getSpellPart(String part) {
		if (this.headMaterial != null && !this.toolHeads.containsKey(part))
			this.spellParts.put(part, new ItemStack(ItemSpellBase.getBaseMap()
					.get(part + "_" + this.name), 1));
		return this.spellParts.get(part);
	}

	public static class Wood extends MaterialMultiItem {
		public Wood() {
			this.name = "wood";
			this.durability = 2;
			this.damage = 0;
			this.speed = 1.0F;
			this.miningLevel = 0;
			this.modDurability = 0.5f;
			this.modSpeed = 1.0f;
			this.mundaneLevel = 0;
			this.modDamage = 0.5f;
			this.rodMaterial = new ItemStack(Items.stick);
			this.capMaterial = new ItemStack(Blocks.planks, 1,
					OreDictionary.WILDCARD_VALUE);
			this.headMaterial = new ItemStack(Blocks.planks, 1,
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
			this.spellEffectMaterial = new ItemStack(Items.flint);
			this.spellEffectType = "break";
		}
	}

	public static class Bone extends MaterialMultiItem {
		public Bone() {
			this.name = "bone";
			this.modDurability = 1.2f;
			this.modSpeed = 0.6f;
			this.rodMaterial = new ItemStack(Items.bone);
			this.spellEffectMaterial = new ItemStack(Items.bone);
		}
	}

	public static class Leather extends MaterialMultiItem {
		public Leather() {
			this.name = "leather";
			this.modDurability = 1.0f;
			this.modSpeed = 0.8f;
			this.rodMaterial = new ItemStack(Items.leather);
		}
	}

	public static class Blazerod extends MaterialMultiItem {
		public Blazerod() {
			this.name = "blazerod";
			this.modDurability = 2.0f;
			this.modSpeed = 0.75f;
			this.rodMaterial = new ItemStack(Items.blaze_rod);
			this.spellEffectMaterial = new ItemStack(Items.blaze_rod);
			this.spellEffectType = "fire";
		}
	}

	public static class Copper extends MaterialMultiItem {
		public Copper() {
			this.name = "copper";
			this.type = MaterialType.METAL_MUNDANE;
			this.durability = 130;
			this.mundaneLevel = 1;
			this.modDamage = 0.8f;
			this.arcaneLevel = 3;
			this.damage = 1;
			this.speed = 2.0f;
			this.miningLevel = 1;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_ingot"));
			this.capMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_ingot"));
			this.baseMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_ingot"));
		}
	}

	public static class Iron extends MaterialMultiItem {
		public Iron() {
			this.name = "iron";
			this.type = MaterialType.METAL_MUNDANE;
			this.isColdWorkable = false;
			this.durability = 250;
			this.mundaneLevel = 3;
			this.modDamage = 0.7f;
			this.arcaneLevel = 2;
			this.damage = 2;
			this.speed = 6.0f;
			this.miningLevel = 2;
			this.headMaterial = new ItemStack(Items.iron_ingot, 1, 0);
			this.capMaterial = new ItemStack(Items.iron_ingot);
			this.baseMaterial = new ItemStack(Items.iron_ingot);
		}
	}

	public static class Gold extends MaterialMultiItem {
		public Gold() {
			this.name = "gold";
			this.type = MaterialType.METAL_ARCANE;
			this.isColdWorkable = false;
			this.durability = 35;
			this.mundaneLevel = 0;
			this.modDamage = 1.0f;
			this.arcaneLevel = 8;
			this.damage = 0;
			this.speed = 6.0f;
			this.miningLevel = 0;
			this.headMaterial = new ItemStack(Items.gold_ingot, 1, 0);
			this.capMaterial = new ItemStack(Items.gold_ingot);
			this.baseMaterial = new ItemStack(Items.gold_ingot);
		}
	}

	public static class Orichalcum extends MaterialMultiItem {
		public Orichalcum() {
			this.name = "orichalcum";
			this.type = MaterialType.METAL_BRONZE;
			this.isColdWorkable = false;
			this.durability = 200;
			this.mundaneLevel = 3;
			this.arcaneLevel = 3;
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
			this.type = MaterialType.METAL_ARCANE;
			this.isColdWorkable = false;
			this.durability = 75;
			this.mundaneLevel = 2;
			this.arcaneLevel = 10;
			this.damage = 1;
			this.speed = 8.0f;
			this.miningLevel = 2;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("silver_ingot"));
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("silver_ingot"));
			this.baseMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("silver_ingot"));
		}
	}

	public static class Steel extends MaterialMultiItem {
		public Steel() {
			this.name = "steel";
			this.type = MaterialType.METAL_MUNDANE;
			this.isColdWorkable = false;
			this.durability = 1500;
			this.mundaneLevel = 2;
			this.arcaneLevel = 4;
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
			this.type = MaterialType.METAL_BRONZE;
			this.isColdWorkable = false;
			this.durability = 500;
			this.mundaneLevel = 4;
			this.arcaneLevel = 5;
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
			this.type = MaterialType.METAL_BRONZE;
			this.isColdWorkable = false;
			this.durability = 750;
			this.mundaneLevel = 5;
			this.arcaneLevel = 6;
			this.damage = 3;
			this.speed = 10.0f;
			this.miningLevel = 4;
			this.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("hepatizon_ingot"));
		}
	}

	public static class Arrow extends MaterialMultiItem {
		public Arrow() {
			this.name = "arrow";
			this.spellFocusMaterial = new ItemStack(Items.arrow);
			this.spellFocusType = "projectile";
		}
	}

	public static class Bottle extends MaterialMultiItem {
		public Bottle() {
			this.name = "bottle";
			this.spellFocusMaterial = new ItemStack(Items.glass_bottle);
			this.spellFocusType = "self";
		}
	}

	public static class Gunpowder extends MaterialMultiItem {
		public Gunpowder() {
			this.name = "gunpowder";
			this.spellFocusMaterial = new ItemStack(Items.gunpowder);
			this.spellFocusType = "area";
		}
	}

	public static class Snow extends MaterialMultiItem {
		public Snow() {
			this.name = "snow";
			this.spellEffectMaterial = new ItemStack(Items.snowball);
			this.spellEffectType = "water";
		}
	}
}
