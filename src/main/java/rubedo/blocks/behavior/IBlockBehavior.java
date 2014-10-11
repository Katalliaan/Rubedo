package rubedo.blocks.behavior;

import java.util.Collection;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IBlockBehavior {
	@SideOnly(Side.CLIENT)
	Set<String> getTextures();

	@SideOnly(Side.CLIENT)
	void setIcon(String texture, IIcon icon);

	@SideOnly(Side.CLIENT)
	IIcon getIcon(int side, int meta);

	Collection<ItemStack> getSubBlocks(Item item);
}
