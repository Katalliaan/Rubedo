package rubedo.items;

import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import rubedo.common.Content;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMetal extends Item {
	
	public static Map<String, Integer> meta;
	
	private String[] textures;
	private Icon[] icons;
	
	public ItemMetal(int id, String[] textures) {
		super(id);
		this.setCreativeTab(Content.creativeTab);
		this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.textures = textures;
	}
	
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textures.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            if (!(textures[i].equals(""))) {
                this.icons[i] = iconRegister.registerIcon("rubedo:" + textures[i]);
            }
        }
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        return "rubedo.items." + textures[stack.getItemDamage()].replace('/', '.');
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems (int id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < textures.length; i++)
            if (!(textures[i].equals("")))
                list.add(new ItemStack(id, 1, i));
    }
}
