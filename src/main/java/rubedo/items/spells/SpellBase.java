package rubedo.items.spells;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
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

	public SpellBase() {
		super();
		this.setUnlocalizedName("SpellBase");
		this.setCreativeTab(RubedoCore.creativeTabSpells);
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
	public IIcon getIcon(ItemStack stack, int renderPass) {
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

		IIcon icon = getRenderList().get(name);
		if (icon == null)
			icon = getRenderList().get("blank");

		return icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		super.registerIcons(iconRegister);

		for (Entry<String, Material> baseEntry : ContentSpells.spellBaseMaterials
				.entrySet()) {
			String name = "base_" + baseEntry.getKey();
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.modid + ":spells/"
							+ name));
		}

		for (Entry<String, Material> focusEntry : ContentSpells.spellFocusMaterials
				.entrySet()) {
			String name = "focus_" + focusEntry.getKey();
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.modid + ":spells/"
							+ name));
		}

		for (Entry<String, Material> effectEntry : ContentSpells.spellEffectMaterials
				.entrySet()) {
			String name = "effect_" + effectEntry.getKey();
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.modid + ":spells/"
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

		list.add(Language.FormatterCodes.DARK_GREEN.toString()
				+ Language.FormatterCodes.ITALIC.toString()
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
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (Entry<String, Material> baseEntry : ContentSpells.spellBaseMaterials
				.entrySet())
			for (Entry<String, Material> focusEntry : ContentSpells.spellFocusMaterials
					.entrySet())
				for (Entry<String, Material> effectEntry : ContentSpells.spellEffectMaterials
						.entrySet()) {
					if (focusEntry.getValue().focusType.equals(this.getName())) {
						list.add(this.buildSpell(baseEntry.getKey(),
								focusEntry.getKey(), effectEntry.getKey()));
					}
				}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		SpellProperties properties = getSpellProperties(stack);

		// This is how you set teh pretty colors!
		String modifier = Language.FormatterCodes.DARK_RED.toString();

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
			compound.setTag("RubedoSpell", new NBTTagCompound());
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
