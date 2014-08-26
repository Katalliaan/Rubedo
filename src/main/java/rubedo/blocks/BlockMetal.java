package rubedo.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import rubedo.common.ContentWorld;

public class BlockMetal extends BlockBase {

	public BlockMetal(int blockID) {
		super(blockID, Material.iron, new String[ContentWorld.metals.size()]);

		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(Block.soundMetalFootstep);

		for (int i = 0; i < ContentWorld.metals.size(); i++) {
			this.textures[i] = ContentWorld.metals.get(i).name + "_block";
		}
	}
}
