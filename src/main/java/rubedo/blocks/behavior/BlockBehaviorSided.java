package rubedo.blocks.behavior;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Maps;

public class BlockBehaviorSided implements IBlockBehavior {
	protected IBlockBehavior base;
	protected Map<String, IIcon[]> icons;
	protected Map<String, Map.Entry<String, ForgeDirection>> textures;

	public BlockBehaviorSided(IBlockBehavior base) {
	}

	@Override
	public Set<String> getTextures() {
		if (this.icons == null) {
			this.icons = new HashMap<String, IIcon[]>();
			this.textures = new HashMap<String, Map.Entry<String, ForgeDirection>>();
			for (String baseTexture : this.base.getTextures()) {
				this.icons.put(baseTexture,
						new IIcon[ForgeDirection.values().length]);
				for (ForgeDirection direction : ForgeDirection.values()) {
					this.textures.put(baseTexture + "_"
							+ direction.toString().toLowerCase(),
							Maps.immutableEntry(baseTexture, direction));
				}
			}
		}
		return this.icons.keySet();
	}

	@Override
	public void setIcon(String texture, IIcon icon) {
		if (this.base.getTextures().contains(texture)) {
			this.base.setIcon(texture, icon);
		} else {
			this.icons.get(this.textures.get(texture).getKey())[this.textures
					.get(texture).getValue().ordinal()] = icon;
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		IIcon icon = this.base.getIcon(side, meta);
		if (this.icons.containsKey(icon.getIconName())) {
			icon = this.icons.get(icon.getIconName())[side];
		}
		return icon;
	}

	@Override
	public Collection<ItemStack> getSubBlocks(Item item) {
		return this.base.getSubBlocks(item);
	}
}
