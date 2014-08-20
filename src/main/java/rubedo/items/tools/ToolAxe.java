package rubedo.items.tools;

import java.util.List;

import rubedo.common.ContentTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class ToolAxe extends ToolBase {

	public ToolAxe(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "axe";
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
		return new Material[]{Material.leaves, Material.plants,
				Material.pumpkin, Material.vine, Material.wood};
	}

	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[]{Block.planks, Block.bookShelf, Block.wood,
				Block.chest, Block.stoneDoubleSlab, Block.stoneSingleSlab,
				Block.pumpkin, Block.pumpkinLantern, Block.leaves, Block.vine,
				Block.cocoaPlant};
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack buildTool(String head, String rod, String cap) {
		ItemStack tool = new ItemStack(ContentTools.toolAxe);

		super.buildTool(tool, head, rod, cap);

		return tool;
	}
}
