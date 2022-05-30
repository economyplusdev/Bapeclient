package fr.vapeclone.vape.mod;

import java.util.*;
import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.mods.blatant.*;
import fr.vapeclone.vape.mod.mods.blatant.Timer;
import fr.vapeclone.vape.mod.mods.combat.*;
import fr.vapeclone.vape.mod.mods.other.*;
import fr.vapeclone.vape.mod.mods.render.*;
import fr.vapeclone.vape.mod.mods.utility.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.TickEvent.*;

public class ModManager {

	
	public List<Mod> mods;
	public ModManager() {
		MinecraftForge.EVENT_BUS.register(this);
		(mods = new ArrayList<Mod>()).clear();
		mods.add(new ClickGUI());
		mods.add(new AutoClicker());
		mods.add(new Velocity());
		mods.add(new Reach());
		mods.add(new InvMove());
		mods.add(new Step());
		mods.add(new Infos());
		mods.add(new FastPlace());
		mods.add(new AntiCobweb());
		mods.add(new NoFall());
		mods.add(new BedAura());
		mods.add(new BHop());
		mods.add(new KillAura());
		mods.add(new Timer());
		
		
		
		mods.add(new SelfDestruct());
	}
	
	public Mod getMod(String name) {
		for(Mod m : this.mods) {
			if(m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	
	public List<Mod> getModList(){
		return this.mods;
	}
	public List<Mod> getModsByCategory(Category category){
		List<Mod> mods_ = new ArrayList<Mod>();
		for(Mod m : this.mods) {
			if(m.getCategory().name().equalsIgnoreCase(category.name())) {
				mods_.add(m);
			}
		}
		return mods_;
	}
	
	 @SubscribeEvent
	   public void onTick(ClientTickEvent e) {
	    	
	    	if (e.phase == Phase.END) {
	    		
	    		if(!Vape.getInstance().disabled && Minecraft.getMinecraft().thePlayer != null) {
	    			
	    			for(int i = 0; i < mods.size(); i++) {
	    				
	    				Mod mod = mods.get(i);
	    				//
	    				if(mod.isToggled()) {
	    					mod.update();
	    				}
	    				
	    			}
	    		}
	    	}
	    }
	
	
	public void unload() {
		MinecraftForge.EVENT_BUS.unregister(this);
		for(Mod m : mods) {
			if(m.isToggled())m.toggle();
			m.setKey(0);
		}
		mods.clear();
	}
	
}
