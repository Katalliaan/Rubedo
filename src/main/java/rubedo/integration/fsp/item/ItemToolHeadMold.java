package rubedo.integration.fsp.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import rubedo.RubedoCore;
import rubedo.common.ContentTools;
import rubedo.util.Singleton;
import flaxbeard.steamcraft.api.CrucibleLiquid;
import flaxbeard.steamcraft.api.ICrucibleMold;

public class ItemToolHeadMold extends Item implements ICrucibleMold {

	private static ContentTools content = Singleton
			.getInstance(ContentTools.class);

	private IIcon icon;
	private ResourceLocation texture = new ResourceLocation(
			"rubedo:textures/models/moldToolHead.png");

	public ItemToolHeadMold() {
		this.maxStackSize = 1;
		this.setUnlocalizedName("moldToolHead");
		this.setTextureName("moldToolHead");
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icon = iconRegister.registerIcon(RubedoCore.modid
				+ ":moldToolHead");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return RubedoCore.modid + ".items.integration.moldToolHead";
	}

	@Override
	public boolean canUseOn(CrucibleLiquid liquid) {
		return content.getMaterial(liquid.name) != null
				&& !content.getMaterial(liquid.name).isColdWorkable;
	}

	@Override
	public ItemStack getItemFromLiquid(CrucibleLiquid liquid) {
		return content.getMaterial(liquid.name).getToolHead("unrefined");
	}

	@Override
	public int getCostToMold(CrucibleLiquid liquid) {
		return 27;
	}

	@Override
	public ResourceLocation getBlockTexture() {
		return this.texture;
	}
}
