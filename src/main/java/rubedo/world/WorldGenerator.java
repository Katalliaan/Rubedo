package rubedo.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import rubedo.common.ContentWorld;
import rubedo.common.ContentWorld.Metal;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator {

	private final List<WorldGenMinable> metals = new ArrayList<WorldGenMinable>();
	
	public WorldGenerator() {
		for (int i = 0; i < ContentWorld.metals.size(); i++)
			metals.add(new WorldGenMinable(ContentWorld.oreBlocks.blockID, 0, 8, Block.stone.blockID));
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int xPos, yPos, zPos;
        if (world.provider.terrainType != WorldType.FLAT)
        {
        	for (int i = 0; i < metals.size(); i++)
    		{
        		Metal metal = ContentWorld.metals.get(i);

        		if (metal.dimensionExclusive == !metal.getDimensionList().contains(world.provider.dimensionId))
    			for (int q = 0; q <= metal.oreDensity; q++)
                {
                    xPos = chunkX*16 + random.nextInt(16);
                    yPos = metal.oreMinY + random.nextInt(metal.oreMaxY - metal.oreMinY);
                    zPos = chunkZ*16 + random.nextInt(16);
                    metals.get(i).generate(world, random, xPos, yPos, zPos);
                }
    		}
        }
	}
}
