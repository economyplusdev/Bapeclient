package fr.vapeclone.vape.mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.vapeclone.vape.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Mod {

	protected static Minecraft mc = Minecraft.getMinecraft();
	private String name,description;
	private int key;
	private Category category;
	public boolean visible = true;
	private boolean toggled;
	public List<Setting> settings = new ArrayList<Setting>();
	public Mod(String name, String description, Category category) {
		super();
		this.name = name;
		this.description = description;
		this.key = 0;
		this.category = category;
		this.toggled = false;
	}
	public void addSettings(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public boolean isToggled() {
		return toggled;
	}
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		if(this.toggled) this.onEnable(); else this.onDisable();
			
		
	}
	
	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
	
	public void update() {
		
	}
	
	
	public void toggle() {
		toggled = !toggled;
		if(toggled)onEnable(); else onDisable();
	}
	
	public String getName() {
		return this.name;
	}
	public Category getCategory() {
		return this.category;
	}
	
}
