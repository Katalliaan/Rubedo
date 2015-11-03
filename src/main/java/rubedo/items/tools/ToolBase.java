package rubedo.items.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import rubedo.RubedoCore;
import rubedo.common.ContentTools;
import rubedo.common.Language;
import rubedo.common.Language.Formatting;
import rubedo.common.materials.MaterialMultiItem.MaterialType;
import rubedo.items.MultiItem;
import rubedo.util.Singleton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//TODO: add getStrVsBlock
public abstract class ToolBase extends MultiItem {
	private static ContentTools content = Singleton
			.getInstance(ContentTools.class);

	protected List<EnumEnchantmentType> allowedEnchants = new ArrayList<EnumEnchantmentType>();

	public ToolBase() {
		super();
		this.setUnlocalizedName("ToolBase");
		this.setCreativeTab(RubedoCore.creativeTabTools);
		this.setFull3D();

		this.maxStackSize = 1;
		this.setNoRepair();
		this.canRepair = false;

		this.allowedEnchants.add(EnumEnchantmentType.all);
		this.allowedEnchants.add(EnumEnchantmentType.breakable);
		this.allowedEnchants.add(EnumEnchantmentType.weapon);
	}

	public abstract String getName();

	public float getWeaponDamage() {
		return 0.0F;
	}

	public abstract int getItemDamageOnHit();

	public abstract int getItemDamageOnBreak();

	public float getBaseSpeed() {
		return 1.0f;
	}

	public abstract Material[] getEffectiveMaterials();

	public abstract Block[] getEffectiveBlocks();

	public List<EnumEnchantmentType> getAllowedEnchantments() {
		return this.allowedEnchants;
	};

	public ToolProperties getToolProperties(ItemStack stack) {
		if (!(stack.getItem() instanceof ToolBase))
			return null;

		return new ToolProperties(stack, this);
	}

	// only tests against metadata 0, but getHarvestLevel overrides for blocks
	// with preset harvestlevels
	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		ToolProperties tool = this.getToolProperties(itemStack);

		if (par1Block.getHarvestLevel(0) <= tool.getMiningLevel()) {
			for (int i = 0; i < tool.getItem().getEffectiveBlocks().length; i++) {
				if (tool.getItem().getEffectiveBlocks()[i] == par1Block) {
					return true;
				}
			}

			for (int i = 0; i < tool.getItem().getEffectiveMaterials().length; i++) {
				if (tool.getItem().getEffectiveMaterials()[i] == par1Block
						.getMaterial()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		if (toolClass == null || !toolClass.equals(this.getName()))
			return -1;

		ToolProperties properties = this.getToolProperties(stack);

		return properties.getMiningLevel();
	}

	@Override
	public int getIconCount() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass) {
		ToolProperties properties = this.getToolProperties(stack);

		String name = "blank";

		if (properties.isValid()) {
			switch (renderPass) {
			case 0:
				// Head
				if (!properties.isBroken())
					name = this.getName() + "_head_"
							+ properties.getHeadMaterial().name;
				else
					name = this.getName() + "_head_"
							+ properties.getHeadMaterial().name + "_broken";
				if (!this.getRenderList().containsKey(name))
					name = this.getName() + "_head_flint_broken";
				break;
			case 1:
				// Rod
				name = this.getName() + "_rod_"
						+ properties.getRodMaterial().name;
				if (!this.getRenderList().containsKey(name))
					name = this.getName() + "_rod_wood";
				break;
			case 2:
				// Cap
				name = this.getName() + "_cap_"
						+ properties.getCapMaterial().name;
				if (!this.getRenderList().containsKey(name))
					name = this.getName() + "_cap_wood";
				break;
			}
		}

		if (!this.getRenderList().containsKey(name))
			name = "blank";

		IIcon icon = this.getRenderList().get(name);

		return icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		super.registerIcons(iconRegister);

		for (rubedo.common.materials.MaterialMultiItem material : content
				.getMaterials()) {
			if (material.headMaterial != null) {
				String name = this.getName() + "_head_" + material.name;
				this.getRenderList().put(
						name,
						iconRegister.registerIcon(RubedoCore.modid + ":tools/"
								+ name));
				this.getRenderList().put(
						name + "_broken",
						iconRegister.registerIcon(RubedoCore.modid + ":tools/"
								+ name + "_broken"));
			}
			if (material.rodMaterial != null) {
				String name = this.getName() + "_rod_" + material.name;
				this.getRenderList().put(
						name,
						iconRegister.registerIcon(RubedoCore.modid + ":tools/"
								+ name));
			}
			if (material.capMaterial != null) {
				String name = this.getName() + "_cap_" + material.name;
				this.getRenderList().put(
						name,
						iconRegister.registerIcon(RubedoCore.modid + ":tools/"
								+ name));
			}
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
		return this.hasEffect(par1ItemStack) && (pass == 0);
	}

	@Override
	public boolean getIsRepairable(ItemStack tool, ItemStack repair) {
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
		if (block.getBlockHardness(world, x, y, z) != 0.0D) {
			ToolProperties properties = this.getToolProperties(stack);
			return ToolUtil.onBlockDestroyed(properties, world,
					Block.getIdFromBlock(block), x, y, z, player);
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

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer,
			World world, int xCoord, int yCoord, int zCoord, int par7,
			float par8, float par9, float par10) {
		if (!entityPlayer
				.canPlayerEdit(xCoord, yCoord, zCoord, par7, itemStack)) {
			return false;
		} else {
			if (!this.getToolProperties(itemStack).isBroken()) {
				ItemStack currentItemstack = entityPlayer
						.getCurrentEquippedItem();
				if (currentItemstack.getItem() == this) {
					ItemStack equivalentTool = new ItemStack(
							this.getEquivalentTool());
					entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = equivalentTool;

					PlayerInteractEvent event = new PlayerInteractEvent(
							entityPlayer,
							PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK,
							xCoord, yCoord, zCoord, par7, world);

					boolean acted = MinecraftForge.EVENT_BUS.post(event)
							|| this.getEquivalentTool().onItemUse(
									equivalentTool, entityPlayer, world,
									xCoord, yCoord, zCoord, par7, par8, par9,
									par10);

					ToolUtil.damageTool(this.getToolProperties(itemStack),
							entityPlayer, entityPlayer.getHeldItem()
									.getItemDamage());

					entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = currentItemstack;

					return acted;
				}
			}

			return false;
		}
	}

	protected abstract Item getEquivalentTool();

	// Misc

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (rubedo.common.materials.MaterialMultiItem head : content
				.getMaterials())
			if (head.headMaterial != null)
				for (rubedo.common.materials.MaterialMultiItem rod : content
						.getMaterials())
					if (rod.rodMaterial != null)
						for (rubedo.common.materials.MaterialMultiItem cap : content
								.getMaterials())
							if (cap.capMaterial != null)
								list.add(this.buildTool(head, rod, cap));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		ToolProperties properties = this.getToolProperties(stack);

		list.add(Language.FormatterCodes.DARK_GREEN.toString()
				+ Language.FormatterCodes.ITALIC.toString()
				+ Language
						.getFormattedLocalization("tools.toolRod", true)
						.put("$material1",
								"materials." + properties.getCapMaterial().name,
								Formatting.CAPITALIZED)
						.put("$material2",
								"materials." + properties.getRodMaterial().name,
								Formatting.LOWERCASE).getResult());

		switch (properties.getHeadMaterial().type) {
		case METAL_MUNDANE:
			list.add(Language.FormatterCodes.RED.toString()
					+ Language
							.getFormattedLocalization("materials.metal", true)
							.put("$type", "materials.types.mundane")
							.getResult());
			break;
		case METAL_ARCANE:
			list.add(Language.FormatterCodes.YELLOW.toString()
					+ Language
							.getFormattedLocalization("materials.metal", true)
							.put("$type", "materials.types.arcane").getResult());
			break;
		case METAL_BRONZE:
			list.add(Language.FormatterCodes.GOLD.toString()
					+ Language
							.getFormattedLocalization("materials.metal", true)
							.put("$type", "materials.types.bronze").getResult());
		default:
			break;
		}

		list.add("");

		if (this.getName().equals("pickaxe")) {
			String color = "";
			if (properties.getHeadMaterial().type == MaterialType.METAL_BRONZE
					&& properties.getStack().isItemEnchanted()) {
				color = Language.FormatterCodes.GOLD.toString();
			}
			list.add(color + "Mining Level " + properties.getMiningLevel());
		}

		list.add("Durability: " + properties.getDurability());
		list.add("Speed: "
				+ new DecimalFormat("#.##").format(properties.getHeadMaterial().speed
						* properties.getRodMaterial().modSpeed));

		list.add("");

		if (properties.getHeadMaterial().arcaneLevel > 0) {
			list.add("Enchantable "
					+ Language.getFormattedLocalization(
							"tools.special.level."
									+ properties.getHeadMaterial().arcaneLevel,
							true).getResult());
		}

		if (!this.getName().equals("shovel") && !this.getName().equals("sword")
				&& properties.getSpecial() > 0)
			list.add(Language.getFormattedLocalization(
					"tools.special." + this.getName(), true).getResult()
					+ " "
					+ Language.getFormattedLocalization(
							"tools.special.level." + properties.getSpecial(),
							true).getResult());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		ToolProperties properties = this.getToolProperties(stack);

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
				+ Language
						.getFormattedLocalization(key, true)
						.put("$material",
								"materials."
										+ properties.getHeadMaterial().name,
								Formatting.CAPITALIZED)
						.put("$tool.type", "tools.type." + this.getName(),
								Formatting.CAPITALIZED).getResult();
	}

	public abstract ItemStack buildTool(
			rubedo.common.materials.MaterialMultiItem head,
			rubedo.common.materials.MaterialMultiItem rod,
			rubedo.common.materials.MaterialMultiItem cap);

	public ItemStack buildTool(ItemStack tool,
			rubedo.common.materials.MaterialMultiItem head,
			rubedo.common.materials.MaterialMultiItem rod,
			rubedo.common.materials.MaterialMultiItem cap) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("RubedoTool", new NBTTagCompound());
		tool.setTagCompound(compound);

		// Set the correct tool properties
		ToolProperties properties = this.getToolProperties(tool);
		properties.setHeadMaterial(head);
		properties.setRodMaterial(rod);
		properties.setCapMaterial(cap);

		if (this.getWeaponDamage() > 0)
			properties.generateAttackDamageNBT();

		return tool;
	}
}
