package rubedo.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import rubedo.common.ContentWorld;

public class BlockMetalOre extends BlockBase {

	public BlockMetalOre() {
		super(Material.rock, new String[ContentWorld.metals.size()]);

		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeStone);

		for (int i = 0; i < ContentWorld.metals.size(); i++) {
			if (ContentWorld.metals.get(i).isGenerated == true)
				this.textures[i] = ContentWorld.metals.get(i).name + "_ore";
		}
	}
}
