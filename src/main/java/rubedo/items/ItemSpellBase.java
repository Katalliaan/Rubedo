package rubedo.items;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import rubedo.RubedoCore;
import rubedo.common.ContentSpells;
import rubedo.common.Language;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.spells.SpellBase;
import rubedo.util.Singleton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpellBase extends Item {

	private static ContentSpells content = Singleton
			.getInstance(ContentSpells.class);
	private static Map<String, ItemSpellBase> baseMap = new LinkedHashMap<String, ItemSpellBase>();

	public static Map<String, ItemSpellBase> getBaseMap() {
		if (baseMap.size() == 0) {
				for (MaterialMultiItem material : content.getMaterials()) {
					if (material.headMaterial != null) {
						String baseName = "base_" + material.name;
						ItemSpellBase base = new ItemSpellBase(baseName);
						base.material = material;
						baseMap.put(baseName, base);
					}
			}
		}

		return baseMap;
	}

	protected String name;
	protected MaterialMultiItem material;

	public String getName() {
		return this.name;
	}

	public MaterialMultiItem getMaterial() {
		return this.material;
	}

	protected IIcon icon;
	protected static Map<String, String> textureNames = new LinkedHashMap<String, String>();

	public ItemSpellBase(String name) {
		this.name = name;
		this.setCreativeTab(RubedoCore.creativeTabSpells);
		this.setMaxDamage(0);
		this.setHasSubtypes(false);
		this.setMaxStackSize(1);

		String material = name.split("_")[1];
		String type = name.split("_")[0];

		textureNames.put(name, material + " spells.base");
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icon = iconRegister.registerIcon(RubedoCore.modid + ":spells/"
				+ this.name);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return RubedoCore.modid + ".items.spells." + this.name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String[] split = textureNames.get(this.name).split(" ");

		return Language.getFormattedLocalization("spells.base", true)
				.put("$material", "materials." + split[0]).getResult();
	}
}
