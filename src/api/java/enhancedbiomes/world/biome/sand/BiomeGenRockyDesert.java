package enhancedbiomes.world.biome.sand;

import java.util.Random;

import enhancedbiomes.EnhancedBiomesMod;
import enhancedbiomes.world.biome.base.BiomeGenSandBase;
import enhancedbiomes.world.gen.WorldGenMinableEnhancedBiomesDesert;
import enhancedbiomes.world.gen.WorldGenRockSpire;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDesertWells;
import net.minecraft.world.gen.feature.WorldGenLakes;

public class BiomeGenRockyDesert extends BiomeGenSandBase
{
    public BiomeGenRockyDesert(int par1)
    {
        super(par1);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        this.theBiomeDecorator.cactiPerChunk = 10;
    }

    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        super.decorate(par1World, par2Random, par3, par4);

        if (par2Random.nextInt(1000) == 0)
        {
            int var5 = par3 + par2Random.nextInt(16) + 8;
            int var6 = par4 + par2Random.nextInt(16) + 8;
            WorldGenDesertWells var7 = new WorldGenDesertWells();
            var7.generate(par1World, par2Random, var5, par1World.getHeightValue(var5, var6) + 1, var6);
        }

        for(int c = 25; c > 0; c--)
       	{
    		int j2 = par3 + par2Random.nextInt(16) + 8;
    		int l3 = 64 + par2Random.nextInt(64);
        	int j5 = par4 + par2Random.nextInt(16) + 8;
        	(new WorldGenMinableEnhancedBiomesDesert(EnhancedBiomesMod.getDominantStone(j2, j5, par1World), EnhancedBiomesMod.getDominantStoneMeta(j2, j5, par1World), 30, Blocks.sand)).generate(par1World, par2Random, j2, l3, j5);
    	}

        for(int c = 25; c > 0; c--)
       	{
    		int j2 = par3 + par2Random.nextInt(16) + 8;
    		int l3 = 64 + par2Random.nextInt(64);
        	int j5 = par4 + par2Random.nextInt(16) + 8;
        	(new WorldGenMinableEnhancedBiomesDesert(EnhancedBiomesMod.getCobbleFromStone(EnhancedBiomesMod.getDominantStone(j2, j5, par1World)), EnhancedBiomesMod.getDominantStoneMeta(j2, j5, par1World), 30, Blocks.sand)).generate(par1World, par2Random, j2, l3, j5);
    	}

        if (par2Random.nextInt(3) == 0)
        {
    		int j2 = par3 + par2Random.nextInt(16) + 8;
        	int j5 = par4 + par2Random.nextInt(16) + 8;

    		int l3 = par1World.getTopSolidOrLiquidBlock(j2, j5);
        	(new WorldGenRockSpire(new Block[]{EnhancedBiomesMod.getDominantStone(j2, j5, par1World), EnhancedBiomesMod.getCobbleFromStone(EnhancedBiomesMod.getDominantStone(j2, j5, par1World)), Blocks.sandstone}, 
					new byte[]{EnhancedBiomesMod.getDominantStoneMeta(j2, j5, par1World), EnhancedBiomesMod.getDominantStoneMeta(j2, j5, par1World), 0}, 10)).generate(par1World, par2Random, j2, l3, j5);
    	}
    }
}
