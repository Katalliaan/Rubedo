package rubedo.blocks.behavior;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockBehaviorMulti implements IBlockBehavior {
	protected IBlockBehavior[] behaviors;
	private Map<String, IBlockBehavior> textureCache;

	public BlockBehaviorMulti(IBlockBehavior[] behaviors) {
		this.behaviors = behaviors;
	}

	@Override
	public Set<String> getTextures() {
		// Cache texture lookup in key set
		if (this.textureCache == null) {
			this.textureCache = new HashMap<String, IBlockBehavior>();
			for (IBlockBehavior behavior : this.behaviors) {
				for (String texture : behavior.getTextures()) {
					this.textureCache.put(texture, behavior);
				}
			}
		}
		return this.textureCache.keySet();
	}

	@Override
	public void setIcon(String texture, IIcon icon) {
		this.textureCache.get(texture).setIcon(texture, icon);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		int id = meta / 16;
		int subMeta = meta % 16;
		return this.behaviors[id].getIcon(side, subMeta);
	}

	@Override
	public Collection<ItemStack> getSubBlocks(Item item) {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for (IBlockBehavior behavior : this.behaviors) {
			stacks.addAll(behavior.getSubBlocks(item));
		}
		return stacks;
	}
}
