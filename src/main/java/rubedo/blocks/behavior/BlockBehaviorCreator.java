package rubedo.blocks.behavior;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBehaviorCreator<BlockBehavior extends IBlockBehavior> {
	public BlockBehavior behavior;

	public static <U extends IBlockBehavior> BlockBehaviorCreator<U> create(
			U behavior) {
		return new BlockBehaviorCreator<U>(behavior);
	}

	public static BlockBehaviorCreator<? extends IBlockBehavior> fromTextures(
			String... textures) {
		if (textures.length > 1) {
			return create(BlockBehaviorMulti.fromTextures(textures));
		} else {
			return create(new BlockBehaviorSimple(textures[0]));
		}
	}

	protected BlockBehaviorCreator(BlockBehavior behavior) {
		this.behavior = behavior;
	}

	public BlockBehavior select() {
		return this.behavior;
	}

	public BlockBehaviorCreator<BlockBehaviorSided> toSided(
			ForgeDirection... directions) {
		return create(new BlockBehaviorSided(directions, this.behavior));
	}

	public BlockBehaviorCreator<BlockBehaviorFacing> toFacing(
			IBlockBehavior front) {
		return create(new BlockBehaviorFacing(front, this.behavior));
	}

	public BlockBehaviorCreator<BlockBehaviorActivatable> toActivatable(
			IBlockBehavior active) {
		return create(new BlockBehaviorActivatable(active, this.behavior));
	}

	public <U extends TileEntity> BlockBehaviorCreator<BlockBehaviorEntity<U>> addEntity(
			Class<U> teClass) {
		return create(new BlockBehaviorEntity<U>(teClass, this.behavior));
	}
}
