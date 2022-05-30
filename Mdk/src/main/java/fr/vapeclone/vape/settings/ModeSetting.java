package fr.vapeclone.vape.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting{
	public int index = 0;
	public List<String> modes;
	public ModeSetting(String name,String defaultmode,String...modes) {
		this.name = name;
		this.modes = Arrays.asList(modes);
		index = this.modes.indexOf(defaultmode);
	
	}
	public String getMode() {
		
		return modes.get(index);
	}
		
		
	
	public boolean is(String mode) {
		return index == modes.indexOf(mode);
	}
	public void cycle() {
		if(index < modes.size() -1) {
			index++;
		}else {
			index = 0;
		}
	}
}
