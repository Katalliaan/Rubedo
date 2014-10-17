package rubedo.blocks.behavior;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
	protected List<ForgeDirection> directions;

	public BlockBehaviorSided(ForgeDirection[] directions, IBlockBehavior base) {
		this.base = base;
		this.directions = Arrays.asList(directions);
	}

	@Override
	public Set<String> getTextures() {
		if (this.icons == null) {
			this.icons = new HashMap<String, IIcon[]>();
			this.textures = new HashMap<String, Map.Entry<String, ForgeDirection>>();
			for (String baseTexture : this.base.getTextures()) {
				this.icons.put(baseTexture, new IIcon[this.directions.size()]);
				for (ForgeDirection direction : this.directions) {
					this.textures.put(baseTexture + "_"
							+ direction.toString().toLowerCase(),
							Maps.immutableEntry(baseTexture, direction));
				}
			}
		}
		Set<String> set = new HashSet<String>(this.textures.keySet());
		set.addAll(this.base.getTextures());
		return set;
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
		String name = icon.getIconName().split(":")[1];
		if (this.directions.contains(ForgeDirection.getOrientation(side))
				&& icon != null && this.icons.containsKey(name)) {
			icon = this.icons.get(name)[side];
		}
		return icon;
	}

	@Override
	public Collection<ItemStack> getSubBlocks(Item item) {
		return this.base.getSubBlocks(item);
	}

	@Override
	public ForgeDirection getFacing(int meta) {
		return this.base.getFacing(meta);
	}
}
