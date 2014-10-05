package rubedo.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolProperties;

public class ToolBaseRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case EQUIPPED:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		ToolProperties toolProperties = new ToolProperties(stack,
				(ToolBase) stack.getItem());

		ToolBase tool = toolProperties.getItem();
		int iconCount = tool.getIconCount();

		List<IIcon> icons = new ArrayList<IIcon>();
		for (int i = 0; i < tool.getIconCount(); i++) {
			icons.add(tool.getIcon(stack, i));
		}

		Tessellator tess = Tessellator.instance;
		float[] xMax = new float[iconCount];
		float[] yMin = new float[iconCount];
		float[] xMin = new float[iconCount];
		float[] yMax = new float[iconCount];
		float depth = 1f / 16f;

		float[] width = new float[iconCount];
		float[] height = new float[iconCount];
		float[] xDiff = new float[iconCount];
		float[] yDiff = new float[iconCount];
		float[] xSub = new float[iconCount];
		float[] ySub = new float[iconCount];
		for (int i = 0; i < iconCount; ++i) {
			IIcon icon = icons.get(i);
			xMin[i] = icon.getMinU();
			xMax[i] = icon.getMaxU();
			yMin[i] = icon.getMinV();
			yMax[i] = icon.getMaxV();
			width[i] = icon.getIconWidth();
			height[i] = icon.getIconHeight();
			xDiff[i] = xMin[i] - xMax[i];
			yDiff[i] = yMin[i] - yMax[i];
			xSub[i] = 0.5f * (xMax[i] - xMin[i]) / width[i];
			ySub[i] = 0.5f * (yMax[i] - yMin[i]) / height[i];
		}
		GL11.glPushMatrix();

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		tess.startDrawingQuads();
		tess.setNormal(0, 0, 1);
		for (int i = 0; i < iconCount; ++i) {
			tess.addVertexWithUV(0, 0, 0, xMax[i], yMax[i]);
			tess.addVertexWithUV(1, 0, 0, xMin[i], yMax[i]);
			tess.addVertexWithUV(1, 1, 0, xMin[i], yMin[i]);
			tess.addVertexWithUV(0, 1, 0, xMax[i], yMin[i]);
		}
		tess.draw();

		tess.startDrawingQuads();
		tess.setNormal(0, 0, -1);
		for (int i = 0; i < iconCount; ++i) {
			tess.addVertexWithUV(0, 1, -depth, xMax[i], yMin[i]);
			tess.addVertexWithUV(1, 1, -depth, xMin[i], yMin[i]);
			tess.addVertexWithUV(1, 0, -depth, xMin[i], yMax[i]);
			tess.addVertexWithUV(0, 0, -depth, xMax[i], yMax[i]);
		}
		tess.draw();

		tess.startDrawingQuads();
		tess.setNormal(-1, 0, 0);
		float pos;
		float iconPos;

		for (int i = 0; i < iconCount; ++i) {
			float w = width[i], m = xMax[i], d = xDiff[i], s = xSub[i];
			for (int k = 0, e = (int) w; k < e; ++k) {
				pos = k / w;
				iconPos = m + d * pos - s;
				tess.addVertexWithUV(pos, 0, -depth, iconPos, yMax[i]);
				tess.addVertexWithUV(pos, 0, 0, iconPos, yMax[i]);
				tess.addVertexWithUV(pos, 1, 0, iconPos, yMin[i]);
				tess.addVertexWithUV(pos, 1, -depth, iconPos, yMin[i]);
			}
		}

		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(1, 0, 0);
		float posEnd;

		for (int i = 0; i < iconCount; ++i) {
			float w = width[i], m = xMax[i], d = xDiff[i], s = xSub[i];
			float d2 = 1f / w;
			for (int k = 0, e = (int) w; k < e; ++k) {
				pos = k / w;
				iconPos = m + d * pos - s;
				posEnd = pos + d2;
				tess.addVertexWithUV(posEnd, 1, -depth, iconPos, yMin[i]);
				tess.addVertexWithUV(posEnd, 1, 0, iconPos, yMin[i]);
				tess.addVertexWithUV(posEnd, 0, 0, iconPos, yMax[i]);
				tess.addVertexWithUV(posEnd, 0, -depth, iconPos, yMax[i]);
			}
		}

		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0, 1, 0);

		for (int i = 0; i < iconCount; ++i) {
			float h = height[i], m = yMax[i], d = yDiff[i], s = ySub[i];
			float d2 = 1f / h;
			for (int k = 0, e = (int) h; k < e; ++k) {
				pos = k / h;
				iconPos = m + d * pos - s;
				posEnd = pos + d2;
				tess.addVertexWithUV(0, posEnd, 0, xMax[i], iconPos);
				tess.addVertexWithUV(1, posEnd, 0, xMin[i], iconPos);
				tess.addVertexWithUV(1, posEnd, -depth, xMin[i], iconPos);
				tess.addVertexWithUV(0, posEnd, -depth, xMax[i], iconPos);
			}
		}

		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0, -1, 0);

		for (int i = 0; i < iconCount; ++i) {
			float h = height[i], m = yMax[i], d = yDiff[i], s = ySub[i];
			for (int k = 0, e = (int) h; k < e; ++k) {
				pos = k / h;
				iconPos = m + d * pos - s;
				tess.addVertexWithUV(1, pos, 0, xMin[i], iconPos);
				tess.addVertexWithUV(0, pos, 0, xMax[i], iconPos);
				tess.addVertexWithUV(0, pos, -depth, xMax[i], iconPos);
				tess.addVertexWithUV(1, pos, -depth, xMin[i], iconPos);
			}
		}

		tess.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		GL11.glPopMatrix();
	}
}
