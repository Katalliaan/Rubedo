package rubedo.blocks.behavior;

import java.util.ArrayList;
import java.util.Arrays;
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

	public static BlockBehaviorMulti fromTextures(String[] textures) {
		return new BlockBehaviorMulti(
				BlockBehaviorSimple.fromTextures(textures));
	}

	public BlockBehaviorMulti(IBlockBehavior[] behaviors) {
		this.behaviors = behaviors;
	}

	public int getTextureIndex(String texture) {
		this.getTextures();
		return Arrays.asList(this.behaviors).indexOf(
				this.textureCache.get(texture));
	}

	public int getTextureMeta(String texture) {
		return this.getMeta(this.getTextureIndex(texture));
	}

	public int getMeta(int id) {
		return id * (16 / this.behaviors.length);
	}

	public int getId(int meta) {
		return meta / (16 / this.behaviors.length);
	}

	public int getSubMeta(int meta) {
		return meta % (16 / this.behaviors.length);
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
		return this.behaviors[this.getId(meta)].getIcon(side,
				this.getSubMeta(meta));
	}

	@Override
	public Collection<ItemStack> getSubBlocks(Item item) {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for (int i = 0; i < this.behaviors.length; i++) {
			stacks.add(new ItemStack(item, 1, this.getMeta(i)));
		}
		return stacks;
	}
}
