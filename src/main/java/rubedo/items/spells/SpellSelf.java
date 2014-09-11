package rubedo.items.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rubedo.common.ContentSpells;

public class SpellSelf extends SpellBase {

	public SpellSelf(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "self";
	}

	@Override
	public void castSpell(World world, EntityPlayer entityPlayer,
			ItemStack itemStack) {
		SpellProperties properties = getSpellProperties(itemStack);

		SpellEffects.hitEntity(world, entityPlayer, properties);
	}

	@Override
	public ItemStack buildSpell(String base, String focus, String effect) {
		ItemStack spell = new ItemStack(ContentSpells.spellSelf);

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}
}
