package fr.vapeclone.vape;

import org.lwjgl.input.Keyboard;

import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.mod.ModManager;
import fr.vapeclone.vape.ui.clickgui.ClickGui;
import fr.vapeclone.vape.ui.hardcoded.HardcodedHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Vape {

	private static Vape instance;
	
	public int guiColor = -1;
	public boolean disabled = false;
	public ClickGui cg;
	public ModManager mm;
	public HardcodedHUD hh;
	
	public Vape() {
		instance = this;
		init();
	}
	
	public static Vape getInstance() {
		return instance;
	}
	public void init() {
		
		mm = new ModManager();
		cg = new ClickGui();
		hh = new HardcodedHUD();
		MinecraftForge.EVENT_BUS.register(this);
		FontUtil.bootstrap();
		
	}
	
	public void destruct() {
		if(Minecraft.getMinecraft().currentScreen instanceof ClickGui) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
		cg = null;
		disabled = true;
		MinecraftForge.EVENT_BUS.unregister(this);
		mm.unload();
		hh.unload();
		cg = null;
		mm = null;
		hh = null;
	}
	
	@SubscribeEvent
    public void key(KeyInputEvent e) {
    	if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
    		return; 
    	try {
    		if (Keyboard.isCreated()) {
    			if (Keyboard.getEventKeyState()) {
    				int keyCode = Keyboard.getEventKey();
                    if (keyCode <= 0)
                   	 return;
                    for (Mod m : mm.getModList()) {
                   	 
                   	 if (m.getKey() == keyCode && keyCode > 0) {
                   		 m.toggle();
                   	 }
                    }
    			}
    		}
    	}catch(Exception q) {q.printStackTrace();}
	}
	
	
}
