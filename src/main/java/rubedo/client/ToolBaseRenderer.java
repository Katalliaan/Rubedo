package rubedo.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import rubedo.RubedoCore;
import rubedo.tools.ToolBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ToolBaseRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type)
        {
        case ENTITY:
            return true;
        case EQUIPPED:
            GL11.glTranslatef(0.03f, 0F, -0.09375F);
            return true;
        case EQUIPPED_FIRST_PERSON:
        	return true;
        case INVENTORY:
            return true;
        default:
            RubedoCore.logger.warning("[Rubedo] unhandled tool render type");
        case FIRST_PERSON_MAP:
            return false;
        }
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return handleRenderType(item, type) & helper.ordinal() 
					< ItemRendererHelper.EQUIPPED_BLOCK.ordinal();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		ToolBase 	tool 			= (ToolBase) item.getItem();
		Icon[] 		icons 			= null;
		int			iconsCount		= 0;
		Entity 		entity 			= (data.length > 1) ? (Entity) data[1] : null;
		boolean		isInInventory 	= type == ItemRenderType.INVENTORY;
		
		// Get the icons to render
		{
            if (!isInInventory && entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entity;
                ItemStack itemInUse = player.getItemInUse();
                if (itemInUse != null)
                {
                    int useCount = player.getItemInUseCount();
                    icons = new Icon[useCount];
					for (int i = 0; i < icons.length; i++)
						icons[i] = tool.getIcon(item, i, player, itemInUse, useCount);
                }
            }

            if (icons == null) {
            	icons = new Icon[tool.getIconCount()];
	            for (int i = 0; i < icons.length; i++)
	            	icons[i] = tool.getIcon(item, i);
            }

            // Check the amount of non-null items
            for (int i = 0; i < icons.length; i++)
            	if (icons[i] != null)
            		iconsCount++;
            
            // There are some null icons, clean the item array
            if (iconsCount != icons.length) {
            	Icon[] cleansedIcons = new Icon[iconsCount];
            	int j = 0;
            	for (int i = 0; i < icons.length; i++)
                	if (icons[i] != null) {
                		cleansedIcons[j] = icons[i];
                		j++;
                	}
            	
            	icons = cleansedIcons;
            }
        }
		
		// Draw all the icons
		{
			Tessellator tess = Tessellator.instance;
	        float[] xMax = new float[iconsCount];
	        float[] yMin = new float[iconsCount];
	        float[] xMin = new float[iconsCount];
	        float[] yMax = new float[iconsCount];
	        int[] sheetWidth = new int[iconsCount];
	        int[] sheetHeight = new int[iconsCount];
	        float depth = 1f / 16f;
	
	        float[] width = new float[iconsCount];
	        float[] height = new float[iconsCount];
	        float[] xDiff = new float[iconsCount];
	        float[] yDiff = new float[iconsCount];
	        float[] xSub = new float[iconsCount];
	        float[] ySub = new float[iconsCount];
	        for (int i = 0; i < iconsCount; ++i)
	        {
	            Icon icon = icons[i];
	            xMin[i] = icon.getMinU();
	            xMax[i] = icon.getMaxU();
	            yMin[i] = icon.getMinV();
	            yMax[i] = icon.getMaxV();
	            sheetWidth[i] = icon.getIconWidth();
	            sheetHeight[i] = icon.getIconHeight();
	            xDiff[i] = xMin[i] - xMax[i];
	            yDiff[i] = yMin[i] - yMax[i];
	            width[i] = sheetWidth[i] * xDiff[i];
	            height[i] = sheetHeight[i] * yDiff[i];
	            xSub[i] = 0.5f * (xMax[i] - xMin[i]) / sheetWidth[i];
	            ySub[i] = 0.5f * (yMax[i] - yMin[i]) / sheetHeight[i];
	        }
	        GL11.glPushMatrix();
	
	        if (type == ItemRenderType.INVENTORY)
	        {
	            GL11.glDisable(GL11.GL_LIGHTING);
	            tess.startDrawingQuads();
	            for (int i = 0; i < iconsCount; ++i)
	            {
	                tess.addVertexWithUV(0, 16, 0, xMin[i], yMax[i]);
	                tess.addVertexWithUV(16, 16, 0, xMax[i], yMax[i]);
	                tess.addVertexWithUV(16, 0, 0, xMax[i], yMin[i]);
	                tess.addVertexWithUV(0, 0, 0, xMin[i], yMin[i]);
	            }
	            tess.draw();
	            GL11.glEnable(GL11.GL_LIGHTING);
	        }
	        else
	        {
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	
	            switch (type)
	            {
	            case EQUIPPED_FIRST_PERSON:
	                break;
	            case EQUIPPED:
	                GL11.glTranslatef(0, -4 / 16f, 0);
	                break;
	            case ENTITY:
	                GL11.glTranslatef(0, 4 / 16f, 0);
	                break;
	            default:
	            }
	
	            tess.startDrawingQuads();
	            tess.setNormal(0, 0, 1);
	            for (int i = 0; i < iconsCount; ++i)
	            {
	                tess.addVertexWithUV(0, 0, 0, xMax[i], yMax[i]);
	                tess.addVertexWithUV(1, 0, 0, xMin[i], yMax[i]);
	                tess.addVertexWithUV(1, 1, 0, xMin[i], yMin[i]);
	                tess.addVertexWithUV(0, 1, 0, xMax[i], yMin[i]);
	            }
	            tess.draw();
	            tess.startDrawingQuads();
	            tess.setNormal(0, 0, -1);
	            for (int i = 0; i < iconsCount; ++i)
	            {
	                tess.addVertexWithUV(0, 1, -depth, xMax[i], yMin[i]);
	                tess.addVertexWithUV(1, 1, -depth, xMin[i], yMin[i]);
	                tess.addVertexWithUV(1, 0, -depth, xMin[i], yMax[i]);
	                tess.addVertexWithUV(0, 0, -depth, xMax[i], yMax[i]);
	            }
	            tess.draw();
	            tess.startDrawingQuads();
	            tess.setNormal(-1, 0, 0);
	            int k;
	            float pos;
	            float iconPos;
	
	            for (int i = 0; i < iconsCount; ++i)
	            {
	                for (k = 0; k < width[i]; ++k)
	                {
	                    pos = k / width[i];
	                    iconPos = xMax[i] + xDiff[i] * pos - xSub[i];
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
	
	            for (int i = 0; i < iconsCount; ++i)
	            {
	                for (k = 0; k < width[i]; ++k)
	                {
	                    pos = k / width[i];
	                    iconPos = xMax[i] + xDiff[i] * pos - xSub[i];
	                    posEnd = pos + 1 / width[i];
	                    tess.addVertexWithUV(posEnd, 1, -depth, iconPos, yMin[i]);
	                    tess.addVertexWithUV(posEnd, 1, 0, iconPos, yMin[i]);
	                    tess.addVertexWithUV(posEnd, 0, 0, iconPos, yMax[i]);
	                    tess.addVertexWithUV(posEnd, 0, -depth, iconPos, yMax[i]);
	                }
	            }
	
	            tess.draw();
	            tess.startDrawingQuads();
	            tess.setNormal(0, 1, 0);
	
	            for (int i = 0; i < iconsCount; ++i)
	            {
	                for (k = 0; k < height[i]; ++k)
	                {
	                    pos = k / height[i];
	                    iconPos = yMax[i] + yDiff[i] * pos - ySub[i];
	                    posEnd = pos + 1 / height[i];
	                    tess.addVertexWithUV(0, posEnd, 0, xMax[i], iconPos);
	                    tess.addVertexWithUV(1, posEnd, 0, xMin[i], iconPos);
	                    tess.addVertexWithUV(1, posEnd, -depth, xMin[i], iconPos);
	                    tess.addVertexWithUV(0, posEnd, -depth, xMax[i], iconPos);
	                }
	            }
	
	            tess.draw();
	            tess.startDrawingQuads();
	            tess.setNormal(0, -1, 0);
	
	            for (int i = 0; i < iconsCount; ++i)
	            {
	                for (k = 0; k < height[i]; ++k)
	                {
	                    pos = k / height[i];
	                    iconPos = yMax[i] + yDiff[i] * pos - ySub[i];
	                    tess.addVertexWithUV(1, pos, 0, xMin[i], iconPos);
	                    tess.addVertexWithUV(0, pos, 0, xMax[i], iconPos);
	                    tess.addVertexWithUV(0, pos, -depth, xMax[i], iconPos);
	                    tess.addVertexWithUV(1, pos, -depth, xMin[i], iconPos);
	                }
	            }
	
	            tess.draw();
	            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        }
	
	        GL11.glPopMatrix();
		}
	}

}
