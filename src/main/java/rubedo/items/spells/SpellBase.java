package rubedo.items.spells;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import rubedo.RubedoCore;
import rubedo.common.ContentSpells;
import rubedo.common.Language;
import rubedo.common.Language.Formatting;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.MultiItem;
import rubedo.util.Singleton;
import rubedo.util.soulnetwork.SoulNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// TODO: build out more
public abstract class SpellBase extends MultiItem {
	private static ContentSpells content = Singleton
			.getInstance(ContentSpells.class);

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
		SoulNetworkHandler.checkAndForceItemOwner(itemStack, entityPlayer);

		float castTime = (this.getMaxItemUseDuration(itemStack) - itemInUseCount) / 20.0F;

		if (castTime >= 1.0f) {
			castSpell(world, entityPlayer, itemStack);
		}
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world,
			Entity holdingEntity, int p_77663_4_, boolean inHand) {
		if (!world.isRemote && holdingEntity instanceof EntityPlayer && inHand) {
			if (((EntityPlayer) holdingEntity).getFoodStats().getFoodLevel() > 0) {
				String playerName = SoulNetworkHandler
						.getUsername(((EntityPlayer) holdingEntity));
				if (SoulNetworkHandler.getCurrentEssence(playerName) < 1000) {
					((EntityPlayer) holdingEntity).addExhaustion(2.0f);
					SoulNetworkHandler.addCurrentEssenceToMaximum(playerName,
							25, 1000);
				}
			}
		}
	}

	// TODO: figure out how this works
	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack itemStack) {
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
		case 0:
			// Base
			name = "base_" + properties.getBaseMaterial();
			break;
		case 1:
			// Effect
			name = "effect_" + properties.getEffectMaterial();
			break;
		case 2:
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

		for (rubedo.common.materials.MaterialMultiItem material : content
				.getMaterials()) {
			if (material.baseMaterial != null) {
				String name = "base_" + material.name;
				getRenderList().put(
						name,
						iconRegister.registerIcon(RubedoCore.modid + ":spells/"
								+ name));
			}

			if (material.spellFocusMaterial != null) {
				String name = "focus_" + material.name;
				getRenderList().put(
						name,
						iconRegister.registerIcon(RubedoCore.modid + ":spells/"
								+ name));
			}

			if (material.spellEffectMaterial != null) {
				String name = "effect_" + material.name;
				getRenderList().put(
						name,
						iconRegister.registerIcon(RubedoCore.modid + ":spells/"
								+ name));
			}
		}
	}

	// TODO: replace by proper tooltip system for spells
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		list.add("Cost: " + properties.getCost());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (rubedo.common.materials.MaterialMultiItem base : content
				.getMaterials())
			if (base.baseMaterial != null)
				for (rubedo.common.materials.MaterialMultiItem focus : content
						.getMaterials())
					if (focus.spellFocusType == this.getName())
						for (rubedo.common.materials.MaterialMultiItem effect : content
								.getMaterials())
							if (effect.spellEffectMaterial != null)
								list.add(this.buildSpell(base, focus, effect));
	}

	public abstract ItemStack buildSpell(MaterialMultiItem base,
			MaterialMultiItem focus, MaterialMultiItem effect);

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

	public void buildSpell(ItemStack spell, MaterialMultiItem base,
			MaterialMultiItem focus, MaterialMultiItem effect) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("RubedoSpell", new NBTTagCompound());
		spell.setTagCompound(compound);

		// Set the correct spell properties
		SpellProperties properties = this.getSpellProperties(spell);
		properties.setBaseMaterial(base);
		properties.setFocusMaterial(focus);
		properties.setEffectMaterial(effect);
	}
}
