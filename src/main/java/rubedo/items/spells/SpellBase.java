package rubedo.items.spells;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import rubedo.common.Content;
import rubedo.common.ContentSpells;
import rubedo.common.ContentSpells.Material;
import rubedo.items.MultiItem;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// TODO: build out more
public abstract class SpellBase extends MultiItem {

	public SpellBase(int id) {
		super(id);
		this.setUnlocalizedName("SpellBase");
		this.setCreativeTab(Content.creativeTab);
	}

	public abstract String getName();

	protected SpellProperties getSpellProperties(ItemStack stack) {
		return new SpellProperties(stack);
	}

	public void onPlayerStoppedUsing(ItemStack itemStack, World world,
			EntityPlayer entityPlayer, int itemInUseCount) {
		float castTime = (this.getMaxItemUseDuration(itemStack) - itemInUseCount) / 20.0F;

		NBTTagCompound tags = itemStack.getTagCompound();

		if (castTime >= ContentSpells.spellFocusMaterials.get(tags
				.getCompoundTag("RubedoSpell").getString("focus")).castTime) {
			castSpell(
					world,
					entityPlayer,
					ContentSpells.spellBaseMaterials.get(tags.getCompoundTag(
							"RubedoSpell").getString("base")).power,
					ContentSpells.spellEffectMaterials.get(tags.getCompoundTag(
							"RubedoSpell").getString("effect")).effectType,
					ContentSpells.spellBaseMaterials.get(tags.getCompoundTag(
							"RubedoSpell").getString("base")).focusModifier);
		}
	}

	// TODO: figure out how this works
	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack itemStack) {
		// NBTTagCompound tags = itemStack.getTagCompound();

		// return (int) (72000 *
		// ContentSpells.spellFocusMaterials.get(tags.getCompoundTag("RubedoSpell").getString("focus")).castTime);
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	public abstract void castSpell(World world, EntityPlayer entityPlayer,
			int power, String effectType, float focusModifier);

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
				// Effect
				name = "effect_" + properties.getEffectMaterial();
				break;
			case 2 :
				// Focus
				name = "focus_" + properties.getFocusMaterial();
				break;
		}

		Icon icon = getRenderList().get(name);
		if (icon == null)
			icon = getRenderList().get("blank");

		return icon;
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

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list) {
		for (Entry<String, Material> baseEntry : ContentSpells.spellBaseMaterials
				.entrySet())
			for (Entry<String, Material> focusEntry : ContentSpells.spellFocusMaterials
					.entrySet())
				for (Entry<String, Material> effectEntry : ContentSpells.spellEffectMaterials
						.entrySet()) {
					if (focusEntry.getValue().focusType == this.getName()) {
						if (focusEntry.getValue().focusType == "projectile") {
							list.add(Content.spellProjectile.buildSpell(
									baseEntry.getKey(), focusEntry.getKey(),
									effectEntry.getKey()));
						} else if (focusEntry.getValue().focusType == "self") {
							list.add(Content.spellSelf.buildSpell(
									baseEntry.getKey(), focusEntry.getKey(),
									effectEntry.getKey()));
						} else if (focusEntry.getValue().focusType == "area") {
							list.add(Content.spellArea.buildSpell(
									baseEntry.getKey(), focusEntry.getKey(),
									effectEntry.getKey()));
						}
					}
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

		String effectType = ContentSpells.spellEffectMaterials.get(effect).effectType;

		// Set the name, capitalized
		compound.setCompoundTag("display", new NBTTagCompound());
		compound.getCompoundTag("display").setString(
				"Name",
				base.substring(0, 1).toUpperCase() + base.substring(1) + " "
						+ getName().substring(0, 1).toUpperCase()
						+ getName().substring(1) + " "
						+ effectType.substring(0, 1).toUpperCase()
						+ effectType.substring(1));

		spell.setTagCompound(compound);

		return spell;
	}
}
