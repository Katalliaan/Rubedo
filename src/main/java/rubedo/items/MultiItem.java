package rubedo.items;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import rubedo.RubedoCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class MultiItem extends Item {
	protected IIcon iconBlank;

	private HashMap<String, IIcon> registeredIcons;

	public MultiItem() {
		super();
		this.maxStackSize = 1;
		this.setUnlocalizedName("MultiItem");

		this.registeredIcons = new LinkedHashMap<String, IIcon>();
	}

	public Map<String, IIcon> getRenderList() {
		return this.registeredIcons;
	}

	public abstract int getIconCount();

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderPasses(int metadata) {
		return this.getIconCount();
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.iconBlank = iconRegister.registerIcon(RubedoCore.modid + ":blank");
		this.getRenderList().put("blank", this.iconBlank);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.iconBlank;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		return this.getIcon(stack, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public abstract IIcon getIcon(ItemStack stack, int renderPass);

	@Override
	public boolean isFull3D() {
		return true;
	}
}
