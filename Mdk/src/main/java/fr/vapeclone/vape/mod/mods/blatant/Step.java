package fr.vapeclone.vape.mod.mods.blatant;

import org.lwjgl.input.Keyboard;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Step extends Mod{

	public NumberSetting height = new NumberSetting("Height", 1.5, 0.1, 5, false);
	public Step() {
		super("Step", "Allows you to step more than a slab.", Category.BLATANT);
		addSettings(height);
		
		
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		 mc.thePlayer.stepHeight = (float) height.getValue();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.stepHeight = 0.5f;
	}

	
	
}
