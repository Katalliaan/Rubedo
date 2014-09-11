package rubedo.items.tools;

import java.util.List;

import rubedo.RubedoCore;
import rubedo.common.ContentTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class ToolShovel extends ToolBase {

	public ToolShovel(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "shovel";
	}

	@Override
	public float getWeaponDamage() { return 2.0F; }
	
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
		return new Material[]{Material.craftedSnow, Material.grass,
				Material.ground, Material.sand, Material.snow};
	}
	
	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack buildTool(String head, String rod, String cap) {
		ContentTools contentTools = (ContentTools) RubedoCore.contentUnits.get(ContentTools.class);
		ItemStack tool = new ItemStack(contentTools.getItem(ToolShovel.class));

		super.buildTool(tool, head, rod, cap);

		return tool;
	}

	public boolean canHarvestBlock(Block par1Block) {
		return par1Block == Block.snow ? true : par1Block == Block.blockSnow;
	}
}
