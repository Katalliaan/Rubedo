package rubedo.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import rubedo.blocks.behavior.BlockBehaviorMulti;
import rubedo.common.ContentWorld;

public class BlockMetalOre extends BlockBase<BlockBehaviorMulti> {
	private static String[] getTextures() {
		List<String> textures = new ArrayList<String>();
		for (int i = 0; i < ContentWorld.metals.size(); i++) {
			if (ContentWorld.metals.get(i).isGenerated)
				textures.add(ContentWorld.metals.get(i).name + "_ore");
		}
		return textures.toArray(new String[0]);
	}

	public BlockMetalOre() {
		super(Material.rock, BlockBehaviorMulti.fromTextures(getTextures()));

		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setStepSound(Block.soundTypeStone);

		MinecraftForge.EVENT_BUS.register(this);
	}
}
