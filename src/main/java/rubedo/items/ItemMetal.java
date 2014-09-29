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

		this.textureNames = new LinkedHashMap<String, String>();

		this.textureNames.put("iron_nugget", "iron materials.nuggetName");

		for (Metal metal : ContentWorld.metals) {
			this.textureNames.put(metal.name + "_nugget", metal.name
					+ " materials.nuggetName");
			this.textureNames.put(metal.name + "_ingot", metal.name
					+ " materials.ingotName");
		}

		this.textureNames.put("copper_gem", "copper materials.cuprite");

		this.textures = this.textureNames.keySet().toArray(
				new String[this.textureNames.size()]);
	}

	public int getTextureIndex(String name) {
		return Arrays.asList(this.textures).indexOf(name);
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icons = new IIcon[this.textures.length];

		for (int i = 0; i < this.icons.length; ++i) {
			if (!(this.textures[i].equals(""))) {
				this.icons[i] = iconRegister.registerIcon(RubedoCore.modid
						+ ":" + this.textures[i]);
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return RubedoCore.modid + ".items."
				+ this.textures[stack.getItemDamage()].replace('/', '.');
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String[] split = this.textureNames.get(
				this.textures[stack.getItemDamage()]).split(" ");

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
		for (int i = 0; i < this.textures.length; i++)
			if (!(this.textures[i].equals("")))
				list.add(new ItemStack(item, 1, i));
	}
}
