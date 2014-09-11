package rubedo.items.spells;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import rubedo.RubedoCore;
import rubedo.common.ContentSpells;
import rubedo.common.ContentSpells.Material;
import rubedo.common.Language;
import rubedo.common.Language.Formatting;
import rubedo.items.MultiItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// TODO: build out more
public abstract class SpellBase extends MultiItem {

	public SpellBase(int id) {
		super(id);
		this.setUnlocalizedName("SpellBase");
		this.setCreativeTab(RubedoCore.creativeTab);
	}

	public abstract String getName();

	protected SpellProperties getSpellProperties(ItemStack stack) {
		return new SpellProperties(stack, this);
	}

	public void onPlayerStoppedUsing(ItemStack itemStack, World world,
			EntityPlayer entityPlayer, int itemInUseCount) {
		float castTime = (this.getMaxItemUseDuration(itemStack) - itemInUseCount) / 20.0F;

		SpellProperties properties = getSpellProperties(itemStack);

		if (castTime >= properties.getCastTime()) {
			castSpell(world, entityPlayer, itemStack);
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
			ItemStack itemStack);

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
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.getId() + ":spells/"
							+ name));
		}

		for (Entry<String, Material> focusEntry : ContentSpells.spellFocusMaterials
				.entrySet()) {
			String name = "focus_" + focusEntry.getKey();
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.getId() + ":spells/"
							+ name));
		}

		for (Entry<String, Material> effectEntry : ContentSpells.spellEffectMaterials
				.entrySet()) {
			String name = "effect_" + effectEntry.getKey();
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.getId() + ":spells/"
							+ name));
		}
	}

	// TODO: replace by proper tooltip system for spells
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		SpellProperties properties = getSpellProperties(stack);

		list.add("\u00a72\u00a7o"
				+ Language
						.getFormattedLocalization("spells.spellBase", true)
						.put("$base",
								"materials." + properties.getBaseMaterial(),
								Formatting.CAPITALIZED)
						.put("$focus",
								"spells.foci." + properties.getFocusMaterial(),
								Formatting.LOWERCASE).getResult());
		list.add("");
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
							list.add(ContentSpells.spellProjectile.buildSpell(
									baseEntry.getKey(), focusEntry.getKey(),
									effectEntry.getKey()));
						} else if (focusEntry.getValue().focusType == "self") {
							list.add(ContentSpells.spellSelf.buildSpell(
									baseEntry.getKey(), focusEntry.getKey(),
									effectEntry.getKey()));
						} else if (focusEntry.getValue().focusType == "area") {
							list.add(ContentSpells.spellArea.buildSpell(
									baseEntry.getKey(), focusEntry.getKey(),
									effectEntry.getKey()));
						}
					}
				}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemDisplayName(ItemStack stack) {
		SpellProperties properties = getSpellProperties(stack);

		// This is how you set teh pretty colors!
		String modifier = "\u00a74";

		return modifier
				+ Language
						.getFormattedLocalization("spells.spellName", true)
						.put("$focusName",
								"spells.spellName.foci."
										+ properties.getFocusMaterial(),
								Formatting.CAPITALIZED)
						.put("$effect",
								"spells.effects."
										+ properties.getEffectMaterial(),
								Formatting.CAPITALIZED).getResult();
	}

	public abstract ItemStack buildSpell(String base, String focus,
			String effect);

	public ItemStack buildSpell(ItemStack spell, String base, String focus,
			String effect) {
		NBTTagCompound compound = new NBTTagCompound();
		if (!compound.hasKey("RubedoSpell")) {
			compound.setCompoundTag("RubedoSpell", new NBTTagCompound());
			spell.setTagCompound(compound);
		}

		// Set the correct tool properties
		SpellProperties properties = this.getSpellProperties(spell);
		properties.setBaseMaterial(base);
		properties.setFocusMaterial(focus);
		properties.setEffectMaterial(effect);

		return spell;
	}
}
