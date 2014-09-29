package rubedo.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.common.ContentWorld;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockMetalOre extends BlockBase {
	private static Random random = new Random();

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
		int[] oreIDs = OreDictionary.getOreIDs(stack);

		if (oreIDs.length == 0)
			return;

		String ore = OreDictionary.getOreName(oreIDs[0]);
		if (ContentWorld.Config.dropCuprite
				&& ore.equals(ContentWorld.Config.oreCuprite)) {
			ItemStack cuprite = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_gem"));
			if (random.nextDouble() < ContentWorld.Config.dropCupriteChance)
				event.drops.add(cuprite);
		}
	}
}
