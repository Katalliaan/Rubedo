package rubedo.blocks;

import java.util.Map;

import net.minecraft.block.material.Material;

public class BlockMetalOre extends BlockBase {
	
	public static Map<String, Integer> meta;
	
	public BlockMetalOre(int blockID, String[] textures) {
		super(blockID, Material.rock, textures);
	}
}
