package rubedo.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import rubedo.common.ContentWorld;

public class BlockMetalOre extends BlockBase {
		
	public BlockMetalOre(int blockID) {
		super(blockID, Material.rock, new String[ContentWorld.metals.size()]);
		
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(Block.soundStoneFootstep);

		for (int i = 0; i < ContentWorld.metals.size(); i++) {
			this.textures[i] = ContentWorld.metals.get(i).name + "_ore";
		}
	}
}
