package rubedo.items.spells;

import rubedo.common.Content;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
		if (effectType == "fire") {
			if (!entityPlayer.isImmuneToFire())
				entityPlayer.setFire(power);
		}
	}

	@Override
	public ItemStack buildSpell(String base, String focus, String effect) {
		ItemStack spell = new ItemStack(Content.spellSelf);

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}

}
