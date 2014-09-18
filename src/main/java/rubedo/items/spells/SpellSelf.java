package rubedo.items.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rubedo.RubedoCore;
import rubedo.common.ContentSpells;
import rubedo.util.Singleton;

public class SpellSelf extends SpellBase {

	public SpellSelf() {
		super();
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
		ContentSpells contentSpells = Singleton
				.getInstance(ContentSpells.class);
		ItemStack spell = new ItemStack(contentSpells.getItem(SpellSelf.class));

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}
}
