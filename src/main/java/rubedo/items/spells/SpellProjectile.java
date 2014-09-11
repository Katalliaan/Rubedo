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

	public void castSpell(World world, EntityPlayer entityPlayer, ItemStack itemStack) {
		SpellProperties properties = getSpellProperties(itemStack);
		float focusModifier = properties.getFocusModifier();
		String effectType = properties.getEffectType();
		int power = properties.getPower();

		EntitySpellProjectile entitySpellProjectile = new EntitySpellProjectile(
				world, entityPlayer, properties);

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
