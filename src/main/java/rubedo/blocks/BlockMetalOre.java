package rubedo.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.common.ContentWorld;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockMetalOre extends BlockBase {
	public BlockMetalOre() {
		super(Material.rock, new String[ContentWorld.metals.size()]);

		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setStepSound(Block.soundTypeStone);

		for (int i = 0; i < ContentWorld.metals.size(); i++) {
			if (ContentWorld.metals.get(i).isGenerated == true)
				this.textures[i] = ContentWorld.metals.get(i).name + "_ore";
		}

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void CupriteEvent(HarvestDropsEvent event) {
		ItemStack stack = new ItemStack(event.block);
		String ore = OreDictionary
				.getOreName(OreDictionary.getOreIDs(stack)[0]);
		if (ore.equals("oreCopper")) {
			ItemStack cuprite = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_gem"));
			event.drops.add(cuprite);
		}
	}
}
