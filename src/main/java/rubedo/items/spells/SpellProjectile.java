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

	public void castSpell(World world, EntityPlayer entityPlayer, int power,
			String effectType, float focusModifier) {

		EntitySpellProjectile entitySpellProjectile = new EntitySpellProjectile(
				world, entityPlayer, focusModifier, effectType, power);

		if (!world.isRemote) {
			world.spawnEntityInWorld(entitySpellProjectile);
		}
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
