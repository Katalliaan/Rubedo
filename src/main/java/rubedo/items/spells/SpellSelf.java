package rubedo.items.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rubedo.common.Content;

public class SpellSelf extends SpellBase {

	public SpellSelf(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "self";
	}

	@Override
	public void castSpell(World world, EntityPlayer entityPlayer, int power,
			String effectType, float focusModifier) {
		SpellEffects.hitEntity(world, entityPlayer, power, effectType);
	}

	@Override
	public ItemStack buildSpell(String base, String focus, String effect) {
		ItemStack spell = new ItemStack(Content.spellSelf);

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}

}
