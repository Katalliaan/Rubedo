package rubedo.common;

import net.minecraftforge.common.config.Configuration;

public interface IContent {
	void config(Configuration config);

	void registerBase();

	void registerDerivatives();

	// TODO find a better name
	void tweak();
}
