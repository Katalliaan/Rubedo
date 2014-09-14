package rubedo.items.tools;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import rubedo.RubedoCore;
import rubedo.common.ContentTools;
import rubedo.common.Language;
import rubedo.common.Language.Formatting;
import rubedo.items.MultiItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//TODO: add getStrVsBlock
public abstract class ToolBase extends MultiItem {
	public ToolBase() {
		super();
		this.setUnlocalizedName("ToolBase");
		this.setCreativeTab(RubedoCore.creativeTab);

		this.maxStackSize = 1;
		setNoRepair();
		canRepair = false;
	}

	public abstract String getName();
	public float getWeaponDamage() {
		return 0.0F;
	}
	public abstract int getItemDamageOnHit();
	public abstract int getItemDamageOnBreak();
	public abstract float getEffectiveBlockSpeed();
	public float getEffectiveMaterialSpeed() {
		return 1.5F;
	}
	public float getBaseSpeed() {
		return 1.0f;
	}
	public abstract Material[] getEffectiveMaterials();
	public abstract Block[] getEffectiveBlocks();

	public abstract List<Integer> getAllowedEnchantments();

	protected ToolProperties getToolProperties(ItemStack stack) {
		if (!(stack.getItem() instanceof ToolBase))
			return null;

		return new ToolProperties(stack, this);
	}

	// TODO: determine if these are unnecessary
//	@Override
//	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z,
//			EntityPlayer player) {
//		ToolProperties properties = getToolProperties(stack);
//		World world = player.worldObj;
//		Block block = world.getBlock(x, y, z);
//		
//		int meta = world.getBlockMetadata(x, y, z);
//
//		boolean canHarvest = properties.getMiningLevel() >= MinecraftForge
//				.getBlockHarvestLevel(block, meta, getName());
//
//		if (canHarvest)
//			return super.onBlockStartBreak(stack, x, y, z, player);
//		else {
//			BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(x, y, z,
//					world, block, meta, player);
//			event.setCanceled(true);
//			MinecraftForge.EVENT_BUS.post(event);
//			return event.isCanceled();
//		}
//	}
//	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
//		return MinecraftForge.getBlockHarvestLevel(par1Block, 0, getName()) <= this
//				.getToolProperties(itemStack).getMiningLevel();
//	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        if (!toolClass.equals(getName()))
        	return -1;
        
		ToolProperties properties = getToolProperties(stack);
		
		return properties.getMiningLevel();
    }

	@Override
	public int getIconCount() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass) {
		ToolProperties properties = getToolProperties(stack);

		String name = "blank";

		if (properties.isValid()) {
			switch (renderPass) {
				case 0 :
					// Head
					if (!properties.isBroken())
						name = getName() + "_head_"
								+ properties.getHeadMaterial();
					else
						name = getName() + "_head_"
								+ properties.getHeadMaterial() + "_broken";
					if (!getRenderList().containsKey(name))
						name = getName() + "_head_flint_broken";
					break;
				case 1 :
					// Rod
					name = getName() + "_rod_" + properties.getRodMaterial();
					if (!getRenderList().containsKey(name))
						name = getName() + "_rod_wood";
					break;
				case 2 :
					// Cap
					name = getName() + "_cap_" + properties.getCapMaterial();
					if (!getRenderList().containsKey(name))
						name = getName() + "_cap_wood";
					break;
			}
		}

		if (!getRenderList().containsKey(name))
			name = "blank";
		
		IIcon icon = getRenderList().get(name);

		return icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		super.registerIcons(iconRegister);

		for (Entry<String, ContentTools.Material> headEntry : ContentTools.toolHeads
				.entrySet()) {
			String name = getName() + "_head_" + headEntry.getKey();
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.modid + ":tools/"
							+ name));
			getRenderList().put(
					name + "_broken",
					iconRegister.registerIcon(RubedoCore.modid + ":tools/"
							+ name + "_broken"));
		}

		for (Entry<String, ContentTools.Material> rodEntry : ContentTools.toolRods
				.entrySet()) {
			String name = getName() + "_rod_" + rodEntry.getKey();
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.modid + ":tools/"
							+ name));
		}

		for (Entry<String, ContentTools.Material> capEntry : ContentTools.toolCaps
				.entrySet()) {
			String name = getName() + "_cap_" + capEntry.getKey();
			getRenderList().put(
					name,
					iconRegister.registerIcon(RubedoCore.modid + ":tools/"
							+ name));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return par1ItemStack.isItemEnchanted();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return hasEffect(par1ItemStack) && (pass == 0);
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack,
			ItemStack par2ItemStack) {
		return false;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	// Tool interactions

	@Override
	public boolean hitEntity(ItemStack stack,
			EntityLivingBase par2EntityLivingBase,
			EntityLivingBase par3EntityLivingBase) {
		ToolProperties properties = this.getToolProperties(stack);
		return ToolUtil.hitEntity(properties, par2EntityLivingBase,
				par3EntityLivingBase);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block,
			int x, int y, int z, EntityLivingBase player) {
		if ((double) block.getBlockHardness(world, x, y, z) != 0.0D) {
			ToolProperties properties = this.getToolProperties(stack);
			return ToolUtil.onBlockDestroyed(properties, world, Block.getIdFromBlock(block), x, y,
					z, player);
		}
		return false;
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return ToolUtil.getStrVsBlock(this.getToolProperties(stack), block,
				meta);
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return ToolUtil.isDamaged(this.getToolProperties(stack));
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return this.getToolProperties(stack).getDurability();
	}

	// Misc

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (Entry<String, ContentTools.Material> headEntry : ContentTools.toolHeads
				.entrySet())
			for (Entry<String, ContentTools.Material> rodEntry : ContentTools.toolRods
					.entrySet())
				for (Entry<String, ContentTools.Material> capEntry : ContentTools.toolCaps
						.entrySet()) {
					list.add(this.buildTool(headEntry.getKey(),
							rodEntry.getKey(), capEntry.getKey()));
				}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		ToolProperties properties = getToolProperties(stack);

		list.add("\u00A72\u00A7o"
				+ Language
						.getFormattedLocalization("tools.toolRod", true)
						.put("$material1",
								"materials." + properties.getCapMaterial(),
								Formatting.CAPITALIZED)
						.put("$material2",
								"materials." + properties.getRodMaterial(),
								Formatting.LOWERCASE).getResult());
		list.add("");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		ToolProperties properties = getToolProperties(stack);

		String key;
		String modifier;
		if (properties.isBroken()) {
			modifier = "\u00A74";
			key = "tools.toolNameBroken";
		} else {
			modifier = "";
			key = "tools.toolName";
		}

		return modifier
				+ I18n.format(key, true, "$material",
								"materials." + properties.getHeadMaterial(),
								Formatting.CAPITALIZED, 
								"$tool.type", "tools.type." + getName(),
								Formatting.CAPITALIZED);
	}

	public abstract ItemStack buildTool(String head, String rod, String cap);

	public ItemStack buildTool(ItemStack tool, String head, String rod,
			String cap) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("RubedoTool", new NBTTagCompound());
		tool.setTagCompound(compound);

		// Set the correct tool properties
		ToolProperties properties = this.getToolProperties(tool);
		properties.setHeadMaterial(head);
		properties.setRodMaterial(rod);
		properties.setCapMaterial(cap);

		if (getWeaponDamage() > 0)
			properties.generateAttackDamageNBT();

		return tool;
	}
}
