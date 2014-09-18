package rubedo.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import rubedo.RubedoCore;
import rubedo.common.ContentWorld;
import rubedo.common.ContentWorld.Metal;
import rubedo.common.Language;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMetal extends Item {

	private String[] textures;
	private IIcon[] icons;
	private HashMap<String, String> textureNames;

	public ItemMetal() {
		super();
		this.setCreativeTab(RubedoCore.creativeTab);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);

		textureNames = new LinkedHashMap<String, String>();

		textureNames.put("iron_nugget", "iron materials.nuggetName");

		for (Metal metal : ContentWorld.metals) {
			textureNames.put(metal.name + "_nugget", metal.name
					+ " materials.nuggetName");
			textureNames.put(metal.name + "_ingot", metal.name
					+ " materials.ingotName");
		}

		this.textures = textureNames.keySet().toArray(
				new String[textureNames.size()]);
	}

	public int getTextureIndex(String name) {
		return Arrays.asList(this.textures).indexOf(name);
	}

	public IIcon getIconFromDamage(int meta) {
		return icons[meta];
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icons = new IIcon[textures.length];

		for (int i = 0; i < this.icons.length; ++i) {
			if (!(textures[i].equals(""))) {
				this.icons[i] = iconRegister.registerIcon(RubedoCore.modid
						+ ":" + textures[i]);
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return RubedoCore.modid + ".items."
				+ textures[stack.getItemDamage()].replace('/', '.');
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String[] split = textureNames.get(textures[stack.getItemDamage()])
				.split(" ");

		if (split[1].contains("tools.type.")) {
			return Language.getFormattedLocalization("tools.toolHead", true)
					.put("$material", "materials." + split[0])
					.put("$tool.type", split[1]).getResult();
		}

		return Language.getFormattedLocalization(split[1], true)
				.put("$material", "materials." + split[0]).getResult();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < textures.length; i++)
			if (!(textures[i].equals("")))
				list.add(new ItemStack(item, 1, i));
	}
}
