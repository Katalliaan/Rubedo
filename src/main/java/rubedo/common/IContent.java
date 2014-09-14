package rubedo.common;

import net.minecraftforge.common.config.Configuration;

public interface IContent {
	void config(Configuration config);
	void register();
}
