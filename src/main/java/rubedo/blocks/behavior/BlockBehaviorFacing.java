package rubedo.blocks.behavior;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBehaviorFacing implements IBlockBehavior {

	protected IBlockBehavior behavior;
	protected IBlockBehavior front;

	public BlockBehaviorFacing(IBlockBehavior front, IBlockBehavior behavior) {
		this.behavior = behavior;
		this.front = front;
	}

	public IBlockBehavior getParent() {
		return this.behavior;
	}

	@Override
	public Set<String> getTextures() {
		Set<String> textures = new HashSet<String>(this.behavior.getTextures());
		textures.addAll(this.front.getTextures());
		return textures;
	}

	@Override
	public void setIcon(String texture, IIcon icon) {
		if (this.front.getTextures().contains(texture))
			this.front.setIcon(texture, icon);
		else
			this.behavior.setIcon(texture, icon);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		// TODO: better meta system, we basically reserve a bit here for
		// activatable blocks
		if (side == meta / 2)
			return this.front.getIcon(side, meta);
		else
			return this.behavior.getIcon(side, meta);
	}

	@Override
	public Collection<ItemStack> getSubBlocks(Item item) {
		return this.behavior.getSubBlocks(item);
	}

	@Override
	public ForgeDirection getFacing(int meta) {
		return ForgeDirection.getOrientation(meta / 2);
	}
}
