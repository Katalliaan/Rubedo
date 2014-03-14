package rubedo.common;

import net.minecraftforge.common.Configuration;

public interface IContent {
	void config(Configuration config);
	void register();
}
