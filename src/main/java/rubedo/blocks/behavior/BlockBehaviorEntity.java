package rubedo.blocks.behavior;

import java.util.Collection;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBehaviorEntity<TEntity extends TileEntity> implements
		IBlockBehaviorEntity<TEntity> {

	protected IBlockBehavior behavior;
	protected Class<? extends TEntity> teClass;

	public BlockBehaviorEntity(Class<? extends TEntity> teClass,
			IBlockBehavior behavior) {
		this.behavior = behavior;
		this.teClass = teClass;
	}

	public IBlockBehavior getParent() {
		return this.behavior;
	}

	@Override
	public Set<String> getTextures() {
		return this.behavior.getTextures();
	}

	@Override
	public void setIcon(String texture, IIcon icon) {
		this.behavior.setIcon(texture, icon);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.behavior.getIcon(side, meta);
	}

	@Override
	public Collection<ItemStack> getSubBlocks(Item item) {
		return this.behavior.getSubBlocks(item);
	}

	@Override
	public TEntity createTileEntity(World world, int metadata) {
		try {
			return this.teClass.newInstance();
		} catch (Throwable cat) {
			throw new RuntimeException(cat);
		}
	}

	@Override
	public ForgeDirection getFacing(int meta) {
		return this.behavior.getFacing(meta);
	}
}
