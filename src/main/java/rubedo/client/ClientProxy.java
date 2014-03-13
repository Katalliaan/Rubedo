package rubedo.client;

import rubedo.CommonProxy;
import rubedo.items.spells.EntitySpellProjectile;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
        
        @Override
        public void registerRenderers() {
        	//We might need this for some stuff
        	//ToolBaseRenderer renderer = new ToolBaseRenderer();
        	
        	RenderingRegistry.registerEntityRenderingHandler(EntitySpellProjectile.class, new RenderSpellProjectile());
        }
        
}