package rubedo.blocks.behavior;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockBehaviorSimple implements IBlockBehavior {
	protected String texture;
	protected IIcon icon;

	public static BlockBehaviorSimple[] fromTextures(String[] textures) {
		BlockBehaviorSimple[] behaviors = new BlockBehaviorSimple[textures.length];
		for (int i = 0; i < textures.length; i++) {
			if (textures[i] != null)
				behaviors[i] = new BlockBehaviorSimple(textures[i]);
		}
		return behaviors;
	}

	public BlockBehaviorSimple(String texture) {
		this.texture = texture;
	}

	@Override
	public void setIcon(String texture, IIcon icon) {
		if (texture.equals(this.texture))
			this.icon = icon;
	}

	@Override
	public Set<String> getTextures() {
		return Collections.singleton(this.texture);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.icon;
	}

	@Override
	public Collection<ItemStack> getSubBlocks(Item item) {
		return Collections.singleton(new ItemStack(item));
	}
}
