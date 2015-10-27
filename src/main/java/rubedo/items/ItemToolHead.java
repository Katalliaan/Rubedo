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
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.tools.ToolBase;
import rubedo.util.Singleton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolHead extends Item {

	private static ContentTools content = Singleton
			.getInstance(ContentTools.class);
	private static Map<String, ItemToolHead> headMap = new LinkedHashMap<String, ItemToolHead>();

	public static Map<String, ItemToolHead> getHeadMap() {
		if (headMap.size() == 0) {
			for (Class<? extends ToolBase> kind : content.getKinds()) {
				String kindName = content.getItem(kind).getName();

				for (MaterialMultiItem material : content.getMaterials()) {
					if (material.headMaterial != null) {
						String toolName = kindName + "_head_" + material.name;
						ItemToolHead head = new ItemToolHead(toolName, kind);
						head.material = material;
						headMap.put(toolName, head);
					}
				}
			}

			for (MaterialMultiItem material : content.getMaterials()) {
				if (material.headMaterial != null) {
					String toolName = "_head_" + material.name;
					ItemToolHead unrefined = new ItemToolHead("unrefined"
							+ toolName, ToolBase.class);
					ItemToolHead hot = new ItemToolHead("hot" + toolName,
							ToolBase.class);
					unrefined.material = material;
					hot.material = material;
					headMap.put("unrefined" + toolName, unrefined);
					headMap.put("hot" + toolName, hot);
				}
			}
		}

		return headMap;
	}

	protected String name;
	protected Class<? extends ToolBase> kind;
	protected MaterialMultiItem material;

	public String getName() {
		return this.name;
	}

	public MaterialMultiItem getMaterial() {
		return this.material;
	}

	protected IIcon icon;
	protected static Map<String, String> textureNames = new LinkedHashMap<String, String>();

	public ItemToolHead(String name, Class<? extends ToolBase> kind) {
		this.name = name;
		this.kind = kind;
		this.setCreativeTab(RubedoCore.creativeTab);
		this.setMaxDamage(0);
		this.setHasSubtypes(false);

		String material = name.split("_")[2];
		String type = name.split("_")[0];

		textureNames.put(name, material + " tools.type." + type);
	}

	public Class<? extends ToolBase> getKind() {
		return this.kind;
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icon = iconRegister.registerIcon(RubedoCore.modid + ":tools/"
				+ this.name);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return RubedoCore.modid + ".items.tools." + this.name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String[] split = textureNames.get(this.name).split(" ");

		return Language.getFormattedLocalization("tools.toolHead", true)
				.put("$material", "materials." + split[0])
				.put("$tool.type", split[1]).getResult();
	}
}
