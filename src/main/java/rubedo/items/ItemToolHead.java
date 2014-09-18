package rubedo.items;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import rubedo.RubedoCore;
import rubedo.common.ContentTools;
import rubedo.common.Language;
import rubedo.common.materials.MaterialTool;
import rubedo.items.tools.ToolBase;
import rubedo.util.Singleton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolHead extends Item {

	private static ContentTools content = Singleton.getInstance(ContentTools.class);
	private static Map<String, ItemToolHead> headMap = new LinkedHashMap<String, ItemToolHead>();

	public static Map<String, ItemToolHead> getHeadMap() {
		if (headMap.size() == 0) {
			for (Class<? extends ToolBase> kind : content.getKinds()) {
				String kindName = content.getItem(kind).getName();

				for (MaterialTool material : content.getMaterials()) {
					if (material.headMaterial != null) {
						String toolName = kindName + "_head_" + material.name;
						headMap.put(toolName, new ItemToolHead(toolName));
					}
				}
			}
		}

		return headMap;
	}

	protected String name;
	protected IIcon icon;
	protected static Map<String, String> textureNames = new LinkedHashMap<String, String>();

	public ItemToolHead(String name) {
		this.name = name;
		this.setCreativeTab(RubedoCore.creativeTab);
		this.setMaxDamage(0);
		this.setHasSubtypes(false);

		String material = name.split("_")[2];
		String type = name.split("_")[0];

		textureNames.put(name, material+" tools.type."+type);
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icon = iconRegister.registerIcon(RubedoCore.modid
				+ ":tools/" + this.name);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return RubedoCore.modid + ".items.tools." + this.name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String[] split = textureNames.get(this.name)
				.split(" ");

		return Language.getFormattedLocalization("tools.toolHead", true)
				.put("$material", "materials." + split[0])
				.put("$tool.type", split[1]).getResult();
	}
}
