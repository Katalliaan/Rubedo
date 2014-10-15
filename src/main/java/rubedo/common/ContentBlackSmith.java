package rubedo.common;

import net.minecraftforge.common.config.Configuration;
import rubedo.blocks.BlockMagmaFurnace;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentBlackSmith extends Singleton<ContentBlackSmith> implements
		IContent {
	public static final BlockMagmaFurnace magma_furnace = new BlockMagmaFurnace();

	protected ContentBlackSmith() {
		super(ContentBlackSmith.class);
	}

	@Override
	public void config(Configuration config) {
	}

	@Override
	public void registerBase() {
		GameRegistry.registerBlock(magma_furnace, "magma_furnace");
	}

	@Override
	public void registerDerivatives() {
	}

	@Override
	public void tweak() {
	}

}
