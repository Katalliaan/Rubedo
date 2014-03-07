package rubedo.items.spells;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import rubedo.common.Content;
import rubedo.common.ContentSpells;

public class SpellProjectile extends SpellBase {

	public SpellProjectile(int id) {
		super(id);
		this.setCreativeTab(Content.creativeTab);
	}

	@Override
	public String getName() {
		return "projectile";
	}

	/**
	 * called when the player releases the use item button. Args: itemstack,
	 * world, entityplayer, itemInUseCount
	 */
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int itemInUseCount) {
		int j = this.getMaxItemUseDuration(par1ItemStack) - itemInUseCount;

		float chargeTime = (float) j / 20.0F;
		chargeTime = (chargeTime * chargeTime + chargeTime * 2.0F) / 3.0F;

		if ((double) chargeTime < 0.1D) {
			return;
		}

		if (chargeTime >= 1.0F) {			
			NBTTagCompound tags = par1ItemStack.getTagCompound();
			
			int power = ContentSpells.spellBaseMaterials.get(tags.getCompoundTag("RubedoSpell").getString("base")).power;
			String type = ContentSpells.spellEffectMaterials.get(tags.getCompoundTag("RubedoSpell").getString("effect")).effectType;

			EntitySpellProjectile entitySpellProjectile = new EntitySpellProjectile(
					par2World, par3EntityPlayer, 2.0F, type, power);

			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(entitySpellProjectile);
			}
		}

	}
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
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

	/**
	 * Build the tool from its parts
	 */
	@Override
	public ItemStack buildSpell(String base, String focus, String effect) {
		ItemStack spell = new ItemStack(Content.spellProjectile);

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}
}
