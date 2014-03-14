package rubedo.items.tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import rubedo.common.ContentTools;

public class ToolPickaxe extends ToolBase {

	public ToolPickaxe(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "pickaxe";
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
		return new Material[0];
	}
	
	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public ItemStack buildTool(String head, String rod, String cap) {
		ItemStack tool = new ItemStack(ContentTools.toolPickaxe);
		
		super.buildTool(tool, head, rod, cap);
		
		return tool;
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		// TODO Auto-generated method stub
		return null;
	}

	//TODO: implement this
	/*public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block == Block.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (par1Block != Block.blockDiamond && par1Block != Block.oreDiamond ? (par1Block != Block.oreEmerald && par1Block != Block.blockEmerald ? (par1Block != Block.blockGold && par1Block != Block.oreGold ? (par1Block != Block.blockIron && par1Block != Block.oreIron ? (par1Block != Block.blockLapis && par1Block != Block.oreLapis ? (par1Block != Block.oreRedstone && par1Block != Block.oreRedstoneGlowing ? (par1Block.blockMaterial == Material.rock ? true : (par1Block.blockMaterial == Material.iron ? true : par1Block.blockMaterial == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
    }*/
}
