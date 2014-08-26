package rubedo.items.tools;

import java.util.List;

import rubedo.common.ContentTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ToolAxe extends ToolBase {

	public ToolAxe(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "axe";
	}

	@Override
	public float getWeaponDamage() {
		return 4.0F;
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
		return 2.0F;
	}

	@Override
	public boolean hitEntity(ItemStack stack,
			EntityLivingBase par2EntityLivingBase,
			EntityLivingBase par3EntityLivingBase) {
		ToolProperties properties = this.getToolProperties(stack);

		if (!properties.isBroken())
			par2EntityLivingBase.addPotionEffect(new PotionEffect(Potion.wither
					.getId(), 100, 1, false));

		return super.hitEntity(stack, par2EntityLivingBase,
				par3EntityLivingBase);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[]{Material.plants, Material.pumpkin, Material.wood};
	}

	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack buildTool(String head, String rod, String cap) {
		ItemStack tool = new ItemStack(ContentTools.toolAxe);

		super.buildTool(tool, head, rod, cap);

		return tool;
	}
}
