package rubedo.blocks.behavior;

import java.util.Collection;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public interface IBlockBehavior {
	Set<String> getTextures();

	void setIcon(String texture, IIcon icon);

	IIcon getIcon(int side, int meta);

	Collection<ItemStack> getSubBlocks(Item item);

	ForgeDirection getFacing(int meta);
}
