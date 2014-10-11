package rubedo.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public class BlockMagmaFurnace extends BlockContainer {

	public BlockMagmaFurnace(boolean isActive) {
		super(Material.rock);
		this.isActive = isActive;
	}

}
