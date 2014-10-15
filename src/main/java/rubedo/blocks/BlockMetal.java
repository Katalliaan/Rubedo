package rubedo.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import rubedo.blocks.behavior.BlockBehaviorMulti;
import rubedo.common.ContentWorld;

public class BlockMetal extends BlockBase<BlockBehaviorMulti> {

	private static String[] getTextures() {
		String[] textures = new String[ContentWorld.metals.size()];
		for (int i = 0; i < ContentWorld.metals.size(); i++) {
			textures[i] = ContentWorld.metals.get(i).name + "_block";
		}
		return textures;
	}

	public BlockMetal() {
		super(Material.iron, BlockBehaviorMulti.fromTextures(getTextures()));

		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setStepSound(Block.soundTypeMetal);
	}
}
