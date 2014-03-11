package rubedo.blocks;

import java.util.Map;

import net.minecraft.block.material.Material;

public class BlockMetal extends BlockBase {
	
	public static Map<String, Integer> meta;

	public BlockMetal(int blockID, String[] textures) {
		super(blockID, Material.iron, textures);
	}
}
