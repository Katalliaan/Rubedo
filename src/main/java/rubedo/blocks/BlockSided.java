package rubedo.blocks;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import rubedo.RubedoCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSided extends Block {
	protected Map<ForgeDirection, IIcon> icons;
	protected String baseTexture;

	public BlockSided(Material material, String baseTexture) {
		super(material);
		this.baseTexture = baseTexture;
		this.setCreativeTab(RubedoCore.creativeTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.icons = new LinkedHashMap<ForgeDirection, IIcon>();

		for (ForgeDirection direction : ForgeDirection.values()) {
			String name = this.baseTexture + "_"
					+ direction.toString().toLowerCase();
			this.icons.put(direction,
					iconRegister.registerIcon(RubedoCore.modid + ":" + name));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons.get(ForgeDirection.getOrientation(side));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
	}
}
