package rubedo.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import rubedo.common.ContentWorld;
import rubedo.common.ContentWorld.Metal;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator {	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int xPos, yPos, zPos;
        if (world.provider.terrainType != WorldType.FLAT)
        {
        	for (int i = 0; i < ContentWorld.metals.size(); i++)
    		{
        		Metal metal = ContentWorld.metals.get(i);

        		int idToReplace = Block.stone.blockID;
        		if (world.provider.isHellWorld)
        			idToReplace = Block.netherrack.blockID;
        		if (world.provider.dimensionId == 1)
        			idToReplace = Block.whiteStone.blockID;
        		
        		WorldGenMinable wgm = new WorldGenMinable(ContentWorld.oreBlocks.blockID, 0, 8, idToReplace);
        		
        		int oreDensity = MathHelper.floor_double(metal.oreDensity);
        		double chance = metal.oreDensity - oreDensity;

        		if (metal.dimensionExclusive == !metal.getDimensionList().contains(world.provider.dimensionId))
    			for (int q = 0; q <= oreDensity; q++)
                {
                    xPos = chunkX*16 + random.nextInt(16);
                    yPos = metal.oreMinY + random.nextInt(metal.oreMaxY - metal.oreMinY);
                    zPos = chunkZ*16 + random.nextInt(16);
                    wgm.generate(world, random, xPos, yPos, zPos);
                }
        		if (random.nextDouble() <= chance) {
        			xPos = chunkX*16 + random.nextInt(16);
                    yPos = metal.oreMinY + random.nextInt(metal.oreMaxY - metal.oreMinY);
                    zPos = chunkZ*16 + random.nextInt(16);
                    wgm.generate(world, random, xPos, yPos, zPos);
        		}
    		}
        }
	}
}
