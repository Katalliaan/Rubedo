package rubedo.items.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rubedo.common.ContentSpells;

public class SpellProjectile extends SpellBase {

	public SpellProjectile(int id) {
		super(id);
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
		ItemStack spell = new ItemStack(ContentSpells.spellProjectile);

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}
}
