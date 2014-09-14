package rubedo.blocks;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import rubedo.RubedoCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBase extends Block {
	protected IIcon[] icons;
	protected String[] textures;

	public BlockBase(Material material, String[] textures) {
		super(material);
		this.textures = textures;
	}
	
	public int getTextureIndex(String name) {
		return Arrays.asList(this.textures).indexOf(name);
	}

	@Override
    public int damageDropped (int meta)
    {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new Icon[textures.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon(RubedoCore.getId() + ":" + textures[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        return meta < icons.length ? icons[meta] : icons[0];
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubBlocks (int id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < icons.length; i++)
        {
            list.add(new ItemStack(id, 1, i));
        }
    }
}
