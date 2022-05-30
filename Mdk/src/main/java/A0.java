

import fr.vapeclone.vape.Vape;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = A0.MODID, version = A0.VERSION)
public class A0
{
    public static final String MODID = "9vW2ANH2xa";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			
			@Override
			public void run() {
				new Vape();
				
			}
		});
    	
        
    }
}
