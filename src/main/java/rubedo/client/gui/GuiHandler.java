package rubedo.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rubedo.container.ContainerMagmaFurnace;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static enum Types {
		MAGMA_FURNACE
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if (entity == null)
			return null;

		switch (Types.values()[ID]) {
		case MAGMA_FURNACE:
			return new ContainerMagmaFurnace(player.inventory, entity);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if (entity == null)
			return null;

		switch (Types.values()[ID]) {
		case MAGMA_FURNACE:
			return new GuiMagmaFurnace(player.inventory, entity);
		default:
			return null;
		}
	}
}
