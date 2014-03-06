package rubedo.items.spells;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rubedo.common.ContentSpells;
import rubedo.common.ContentSpells;
import rubedo.common.ContentSpells.Material;
import rubedo.items.MultiItem;
import rubedo.items.tools.ToolProperties;

// TODO: build out more
public abstract class SpellBase extends MultiItem {

	public SpellBase(int id) {
		super(id);
		this.setUnlocalizedName("SpellBase");
	}

	public abstract String getName();

	protected SpellProperties getSpellProperties(ItemStack stack) {
		return new SpellProperties(stack);
	}

	@Override
	public int getIconCount() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack stack, int renderPass) {
		SpellProperties properties = getSpellProperties(stack);

		String name = "blank";

		switch (renderPass) {
			case 0 :
				// Base
				name = "base_" + properties.getBaseMaterial();
				break;
			case 1 :
				// Focus
				name = "focus_" + properties.getFocusMaterial();
				break;
			case 2 :
				// Effect
				name = "effect_" + properties.getEffectMaterial();
				break;
		}

		return getRenderList().get(name);
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		super.registerIcons(iconRegister);

		for (Entry<String, Material> baseEntry : ContentSpells.spellBaseMaterials
				.entrySet()) {
			String name = "base_" + baseEntry.getKey();
			getRenderList().put(name,
					iconRegister.registerIcon("rubedo:spells/" + name));
		}

		for (Entry<String, Material> focusEntry : ContentSpells.spellFocusMaterials
				.entrySet()) {
			String name = "focus_" + focusEntry.getKey();
			getRenderList().put(name,
					iconRegister.registerIcon("rubedo:spells/" + name));
		}

		for (Entry<String, Material> effectEntry : ContentSpells.spellEffectMaterials
				.entrySet()) {
			String name = "effect_" + effectEntry.getKey();
			getRenderList().put(name,
					iconRegister.registerIcon("rubedo:spells/" + name));
		}
	}

	// TODO: replace by proper tooltip system for spells
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		SpellProperties properties = getSpellProperties(stack);

		list.add("Base: " + properties.getBaseMaterial());
		list.add("Focus: " + properties.getFocusMaterial());
		list.add("Effect: " + properties.getEffectMaterial());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void getSubItems(int id, CreativeTabs tabs, List list) {
    	for (Entry<String, Material> baseEntry : ContentSpells.spellBaseMaterials.entrySet())
    	for (Entry<String, Material> focusEntry : ContentSpells.spellFocusMaterials.entrySet())
    	for (Entry<String, Material> effectEntry : ContentSpells.spellEffectMaterials.entrySet()) {
    		list.add(this.buildSpell(baseEntry.getKey(), focusEntry.getKey(), effectEntry.getKey()));
		
		 
    	}
    }
	
	public abstract ItemStack buildSpell(String base, String focus,
			String effect);

	public ItemStack buildSpell(ItemStack spell, String base, String focus,
			String effect) {
		// Set the correct tool properties
		NBTTagCompound compound = new NBTTagCompound();
		compound.setCompoundTag("RubedoSpell", new NBTTagCompound());
		compound.getCompoundTag("RubedoSpell").setString("base", base);
		compound.getCompoundTag("RubedoSpell").setString("focus", focus);
		compound.getCompoundTag("RubedoSpell").setString("effect", effect);

		// Set the name, capitalized
		compound.setCompoundTag("display", new NBTTagCompound());
		compound.getCompoundTag("display").setString(
				"Name",
				base.substring(0, 1).toUpperCase() + base.substring(1) + " "
						+ getName().substring(0, 1).toUpperCase()
						+ getName().substring(1));

		spell.setTagCompound(compound);

		return spell;
	}
}
