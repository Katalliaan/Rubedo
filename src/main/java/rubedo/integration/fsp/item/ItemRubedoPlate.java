package rubedo.integration.fsp.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import rubedo.RubedoCore;
import rubedo.common.ContentWorld;
import rubedo.common.ContentWorld.Metal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRubedoPlate extends Item {
	public IIcon[] icon;

	public ItemRubedoPlate() {
		this.setHasSubtypes(true);
		this.setUnlocalizedName("rubedoPlate");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int index) {
		if (index < this.icon.length) {
			return this.icon[index];
		}
		return this.icon[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		this.icon = new IIcon[ContentWorld.metals.size()];
		for (int i = 0; i < ContentWorld.metals.size(); i++) {
			Metal metal = ContentWorld.metals.get(i);
			this.icon[i] = ir.registerIcon(RubedoCore.modid + ":plate" + metal);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int i = 0; i < this.icon.length; i++) {
			if (ContentWorld.metals.get(i).name != "copper")
				par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return super.getUnlocalizedName() + "."
				+ ContentWorld.metals.get(par1ItemStack.getItemDamage());
	}
}
