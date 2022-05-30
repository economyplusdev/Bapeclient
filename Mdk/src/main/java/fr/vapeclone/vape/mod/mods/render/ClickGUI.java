package fr.vapeclone.vape.mod.mods.render;

import org.lwjgl.input.Keyboard;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.settings.NumberSetting;

public class ClickGUI extends Mod{

	
	public BooleanSetting blur = new BooleanSetting("Blur background", true);
	
	public ClickGUI() {
		super("ClickGui", "Allows you to toggle modules.", Category.RENDER);
		this.setKey(Keyboard.KEY_RSHIFT);
		this.addSettings(blur);
		
		
	}

	
	public void onEnable() {
		mc.displayGuiScreen(Vape.getInstance().cg);
		setToggled(false);
	}
}
