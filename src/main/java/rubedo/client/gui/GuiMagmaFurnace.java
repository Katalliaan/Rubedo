package rubedo.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import rubedo.RubedoCore;
import rubedo.container.ContainerMagmaFurnace;
import rubedo.tileentity.TileEntityMagmaFurnace;

public class GuiMagmaFurnace extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			RubedoCore.modid, "textures/gui/magma_furnace_gui.png");
	private TileEntityMagmaFurnace entity;

	public GuiMagmaFurnace(InventoryPlayer inventory, TileEntity entity) {
		super(new ContainerMagmaFurnace(inventory, entity));

		this.xSize = 200;
		this.ySize = 166;

		if (entity instanceof TileEntityMagmaFurnace)
			this.entity = (TileEntityMagmaFurnace) entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n
				.format(this.entity.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(name, this.xSize / 2
				- this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(
				I18n.format("container.inventory", new Object[0]), 8,
				this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int p_146976_2_, int p_146976_3_) {
		this.mc.renderEngine.bindTexture(texture);

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect(x, y, 0, 0, this.xSize - 24, this.ySize);

		int burn = this.entity.getBurnTimeRemainingScaled(14);
		int cook = this.entity.getCookProgressScaled(24);

		if (burn != -1)
			this.drawTexturedModalRect(x + 22, y + 36 + (14 - burn), 176, 0,
					14 - burn, burn);

		if (cook > 0)
			this.drawTexturedModalRect(x + 69, y + 35, 176, 14, cook, 17);
	}

}
