package rubedo.items.tools;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import rubedo.common.ContentTools;
import rubedo.common.materials.MaterialTool;
import rubedo.util.Singleton;

public class ToolPickaxe extends ToolBase {

	public ToolPickaxe() {
		super();
	}

	@Override
	public String getName() {
		return "pickaxe";
	}

	@Override
	public float getWeaponDamage() {
		return 3.0F;
	}

	@Override
	public int getItemDamageOnHit() {
		return 2;
	}

	@Override
	public int getItemDamageOnBreak() {
		return 1;
	}

	@Override
	public float getEffectiveBlockSpeed() {
		return 4.0F;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase hitEntity,
			EntityLivingBase attackingEntity) {
		ToolProperties properties = this.getToolProperties(stack);

		if (!properties.isBroken()) {
			// need the attack damage as well to get around hurt resist timer
			// MC will allow any damage greater than the previous attack when
			// in hurt mode
			float damage = properties.getAttackDamage()
					+ properties.getSpecial();

			hitEntity.attackEntityFrom(new DamageSourceArmorBreak("arpen",
					attackingEntity), damage);
		}

		return super.hitEntity(stack, hitEntity, attackingEntity);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[]{Material.iron, Material.anvil, Material.rock};
	}

	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public ItemStack buildTool(MaterialTool head, MaterialTool rod, MaterialTool cap) {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		ItemStack tool = new ItemStack(contentTools.getItem(ToolPickaxe.class));

		super.buildTool(tool, head, rod, cap);

		return tool;
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		Integer[] allowedEnchants = new Integer[]{
				Enchantment.efficiency.effectId,
				Enchantment.fortune.effectId,
				Enchantment.silkTouch.effectId,
				Enchantment.unbreaking.effectId,

				Enchantment.sharpness.effectId,
				Enchantment.smite.effectId,
				Enchantment.baneOfArthropods.effectId,
				Enchantment.knockback.effectId,
				Enchantment.fireAspect.effectId,
				Enchantment.looting.effectId };
		return Arrays.asList(allowedEnchants);
	}

	public static class DamageSourceArmorBreak extends EntityDamageSource {
		public DamageSourceArmorBreak(String name, Entity entity) {
			super(name, entity);
			this.setDamageBypassesArmor();
		}
	}
}
