package rubedo.items.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rubedo.RubedoCore;
import rubedo.common.ContentSpells;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.util.Singleton;
import rubedo.util.soulnetwork.SoulNetworkHandler;

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

		SoulNetworkHandler.syphonAndDamageFromNetwork(itemStack, entityPlayer,
				properties.getCost());
		SpellEffects.hitEntity(world, entityPlayer, properties);
	}

	@Override
	public ItemStack buildSpell(MaterialMultiItem base,
			MaterialMultiItem focus, MaterialMultiItem effect) {
		ContentSpells contentSpells = Singleton
				.getInstance(ContentSpells.class);
		ItemStack spell = new ItemStack(contentSpells.getItem(SpellSelf.class));

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}
}
