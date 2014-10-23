package rubedo.items.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rubedo.common.ContentSpells;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.util.Singleton;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;

public class SpellProjectile extends SpellBase {

	public SpellProjectile() {
		super();
	}

	@Override
	public String getName() {
		return "projectile";
	}

	@Override
	public void castSpell(World world, EntityPlayer entityPlayer,
			ItemStack itemStack) {
		SpellProperties properties = this.getSpellProperties(itemStack);

		SoulNetworkHandler.syphonAndDamageFromNetwork(itemStack, entityPlayer,
				properties.getCost());
		EntitySpellProjectile entitySpellProjectile = new EntitySpellProjectile(
				world, entityPlayer, properties);

		if (!world.isRemote) {
			world.spawnEntityInWorld(entitySpellProjectile);
		}
	}

	@Override
	public ItemStack buildSpell(MaterialMultiItem base,
			MaterialMultiItem focus, MaterialMultiItem effect) {
		ContentSpells contentSpells = Singleton
				.getInstance(ContentSpells.class);
		ItemStack spell = new ItemStack(
				contentSpells.getItem(SpellProjectile.class));

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}
}
