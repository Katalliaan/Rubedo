package rubedo.integration.atg;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import ttftcuts.atg.ATGChunkManager;
import ttftcuts.atg.config.configfiles.ATGMainConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RubedoWorldType extends WorldType {
	private int sealevel;

	public RubedoWorldType() {
		super("Rubedo");
		this.sealevel = ATGMainConfig.genModSeaLevel.getInt(63);
	}

	@Override
	public WorldChunkManager getChunkManager(World world) {
		return new ATGChunkManager(world);
	}

	@Override
	public IChunkProvider getChunkGenerator(World world, String flatOptions) {
		return new RubedoChunkProvider(world, world.getSeed(), world
				.getWorldInfo().isMapFeaturesEnabled());
	}

	@Override
	public String getTranslateName() {
		return "Rubedo - ATG+EB";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return 192.0F;
	}

	@Override
	public int getMinimumSpawnHeight(World world) {
		return this.sealevel + 1;
	}

	@Override
	public double getHorizon(World world) {
		return this.sealevel;
	}
}
