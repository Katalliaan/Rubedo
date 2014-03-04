package rubedo.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import rubedo.CommonProxy;
import rubedo.common.Content;

public class ClientProxy extends CommonProxy {
        
        @Override
        public void registerRenderers() {
        	//We might need this for some stuff
        	//ToolBaseRenderer renderer = new ToolBaseRenderer();
        }
        
}