package rubedo.integration.atg;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import ttftcuts.atg.ATGChunkProvider;

public class RubedoChunkProvider extends ATGChunkProvider {
	private World world;

	public RubedoChunkProvider(World world, long seedNum, boolean structures) {
		super(world, seedNum, structures);

		this.world = world;
	}

	@Override
	public void replaceBlocksForBiome(int par1, int par2, Block[] blocks,
			byte[] abyte, BiomeGenBase[] biomes) {
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(
				this, par1, par2, blocks, abyte, biomes, this.world);
		MinecraftForge.EVENT_BUS.post(event);
	}
}
