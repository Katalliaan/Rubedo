package rubedo.items.tools;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rubedo.RubedoCore;
import rubedo.common.ContentTools;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ToolSword extends ToolBase {

	public ToolSword(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "sword";
	}
	
	@Override
	public float getWeaponDamage() { return 4.0F; }
	@Override
	public int getItemDamageOnHit() { return 1;	}
	@Override
	public int getItemDamageOnBreak() {	return 2; }
	@Override
	public float getEffectiveBlockSpeed () { return 15.0f; }
	@Override
	public Material[] getEffectiveMaterials() {	
		return new Material[] { Material.plants, Material.vine, 
				Material.coral, Material.leaves }; 
	}
	@Override
	public Block[] getEffectiveBlocks() { return new Block[] { Blocks.web }; }
	
	@Override
	public List<Integer> getAllowedEnchantments() {
		Integer[] allowedEnchants = new Integer[] { 
				Enchantment.sharpness.effectId, 
				Enchantment.smite.effectId, 
				Enchantment.baneOfArthropods.effectId,
				Enchantment.knockback.effectId,
				Enchantment.fireAspect.effectId,
				Enchantment.looting.effectId };
		return Arrays.asList(allowedEnchants);
	}
	
	@Override
	public EnumAction getItemUseAction (ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
	
	@Override
	public int getMaxItemUseDuration (ItemStack par1ItemStack)
    {
        return 72000;
    }
	
	@Override
	public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
    {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return super.onItemRightClick(stack, world, player);
    }
	
	@Override
	public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
    {
        return false;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void onUpdate (ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        super.onUpdate(stack, world, entity, par4, par5);
        if (entity instanceof EntityPlayerSP)
        {
            EntityPlayerSP player = (EntityPlayerSP) entity;
            ItemStack usingItem = player.getItemInUse();
            if (usingItem != null && usingItem.getItem() == this)
            {
                player.movementInput.moveForward *= 2.5F;
                player.movementInput.moveStrafe *= 2.5F;
            }
        }
    }
	
	/**
	 * Build the tool from its parts
	 */
	@Override
	public ItemStack buildTool(String head, String rod, String cap) {
		ContentTools contentTools = (ContentTools) RubedoCore.contentUnits.get(ContentTools.class);
		ItemStack tool = new ItemStack(contentTools.getItem(ToolSword.class));
		
		super.buildTool(tool, head, rod, cap);
		
		return tool;
	}
}
