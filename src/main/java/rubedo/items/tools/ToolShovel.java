package rubedo.items.tools;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import rubedo.common.ContentTools;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public class ToolShovel extends ToolBase {

	private Item vanillaEquivalent;

	public ToolShovel() {
		super();

		this.vanillaEquivalent = new ItemSpade(ToolMaterial.EMERALD) {
		};
		this.vanillaEquivalent.setUnlocalizedName("shovelDiamond").setTextureName("diamond_shovel");
		GameRegistry.registerItem(this.vanillaEquivalent, "dummy_shovel");
	}

	@Override
	public String getName() {
		return "shovel";
	}

	@Override
	public float getWeaponDamage() {
		return 2.0F;
	}

	@Override
	public int getItemDamageOnHit() {
		return 2;
	}

	@Override
	public int getItemDamageOnBreak() {
		return 1;
	}

	@Override
	public float getEffectiveBlockSpeed() {
		return 4.0F;
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] { Material.craftedSnow, Material.grass,
				Material.ground, Material.sand, Material.snow };
	}

	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		Integer[] allowedEnchants = new Integer[] {
				Enchantment.efficiency.effectId, Enchantment.fortune.effectId,
				Enchantment.silkTouch.effectId,
				Enchantment.unbreaking.effectId,

				Enchantment.sharpness.effectId, Enchantment.smite.effectId,
				Enchantment.baneOfArthropods.effectId,
				Enchantment.knockback.effectId,
				Enchantment.fireAspect.effectId, Enchantment.looting.effectId };
		return Arrays.asList(allowedEnchants);
	}

	@Override
	public ItemStack buildTool(rubedo.common.materials.MaterialMultiItem head,
			rubedo.common.materials.MaterialMultiItem rod,
			rubedo.common.materials.MaterialMultiItem cap) {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		ItemStack tool = new ItemStack(contentTools.getItem(ToolShovel.class));

		super.buildTool(tool, head, rod, cap);

		return tool;
	}

	public boolean canHarvestBlock(Block par1Block) {
		return par1Block == Blocks.snow_layer ? true : par1Block == Blocks.snow;
	}

	@Override
	protected Item getEquivalentTool() {
		return this.vanillaEquivalent;
	}
}
