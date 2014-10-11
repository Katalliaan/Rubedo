package rubedo.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import rubedo.RubedoCore;
import rubedo.blocks.behavior.IBlockBehavior;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBase extends Block {
	protected IBlockBehavior behavior;

	public BlockBase(Material material, IBlockBehavior behavior) {
		super(material);
		this.behavior = behavior;
		this.setCreativeTab(RubedoCore.creativeTab);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for (String texture : this.behavior.getTextures()) {
			this.behavior
					.setIcon(
							texture,
							iconRegister.registerIcon(RubedoCore.modid + ":"
									+ texture));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.behavior.getIcon(side, meta);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.addAll(this.behavior.getSubBlocks(item));
	}
}
