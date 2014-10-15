package rubedo.blocks.behavior;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockBehaviorActivatable implements IBlockBehavior {

	protected IBlockBehavior behavior;
	protected IBlockBehavior active;

	public BlockBehaviorActivatable(IBlockBehavior active,
			IBlockBehavior behavior) {
		this.behavior = behavior;
		this.active = active;
	}

	@Override
	public Set<String> getTextures() {
		Set<String> textures = new HashSet<String>(this.behavior.getTextures());
		textures.addAll(this.active.getTextures());
		return textures;
	}

	@Override
	public void setIcon(String texture, IIcon icon) {
		if (this.active.getTextures().contains(texture))
			this.active.setIcon(texture, icon);
		else
			this.behavior.setIcon(texture, icon);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		boolean active = meta % 2 > 0;
		int subMeta = meta / 2;
		if (active)
			return this.active.getIcon(side, subMeta);
		else
			return this.behavior.getIcon(side, subMeta);
	}

	@Override
	public Collection<ItemStack> getSubBlocks(Item item) {
		return this.behavior.getSubBlocks(item);
	}

}
