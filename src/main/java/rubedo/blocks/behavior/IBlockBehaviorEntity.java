package rubedo.blocks.behavior;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IBlockBehaviorEntity<TBehavior extends TileEntity> extends
		IBlockBehavior {

	public TBehavior createTileEntity(World world, int metadata);
}
