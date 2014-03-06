package rubedo.items.spells;

import net.minecraft.item.ItemStack;
import rubedo.common.Content;

public class SpellProjectile extends SpellBase {

	public SpellProjectile(int id) {
		super(id);
        //this.setCreativeTab(Content.creativeTab);
	}
	
	@Override
	public String getName() {
		return "sword";
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
