package rubedo.world;

import java.util.Random;

import rubedo.common.Content;
import rubedo.common.ContentWorld;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator {

	private final WorldGenMinable copper;
	
	public WorldGenerator() {
		copper = new WorldGenMinable(Content.oreBlocks.blockID, 0, 8, Block.stone.blockID);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.isHellWorld)
        {
            generateNether(random, chunkX * 16, chunkZ * 16, world);
        }
        else if (world.provider.terrainType != WorldType.FLAT)
        {
        	generateUnderground(random, chunkX * 16, chunkZ * 16, world);
        }
	}

	private void generateUnderground(Random random, int xChunk, int zChunk, World world) {
		int xPos, yPos, zPos;
		//if (config.generateCopper)
		{
			for (int q = 0; q <= ContentWorld.copperDensity; q++)
            {
                xPos = xChunk + random.nextInt(16);
                yPos = ContentWorld.copperMinY + random.nextInt(ContentWorld.copperMaxY - ContentWorld.copperMinY);
                zPos = zChunk + random.nextInt(16);
                copper.generate(world, random, xPos, yPos, zPos);
            }
		}
	}

	private void generateNether(Random random, int i, int j, World world) {
		// TODO Auto-generated method stub
	}

}
