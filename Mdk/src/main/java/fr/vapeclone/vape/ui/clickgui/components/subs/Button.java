package fr.vapeclone.vape.ui.clickgui.components.subs;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.settings.Setting;
import fr.vapeclone.vape.ui.clickgui.components.Component;
import fr.vapeclone.vape.ui.clickgui.components.Frame;
import fr.vapeclone.vape.utils.ResUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class Button extends Component{

	public Mod mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	private int height;
	
	public boolean waitingKey = false;
	
	public Button(Mod mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		height = 12;
		int opY = offset + 12;
		
		for(Setting set : mod.settings) {
			if(set instanceof BooleanSetting) {
				this.subcomponents.add(new Checkbox((BooleanSetting)set, this, opY));
				opY += 12;
			}
			if(set instanceof ModeSetting) {
				this.subcomponents.add(new ModeButton((ModeSetting)set, this, mod, opY));
				opY += 12;
			}
			if(set instanceof NumberSetting) {
				this.subcomponents.add(new Slider((NumberSetting)set, this, opY));
				opY += 12;
			}
		}
		
		/*
		if(Pulsive.instance.settingsManager.getSettingsByMod(mod) != null) {
			for(Setting s : Pulsive.instance.settingsManager.getSettingsByMod(mod)){
				if(s.isCombo()){
					this.subcomponents.add(new ModeButton(s, this, mod, opY));
					opY += 12;
				}
				if(s.isSlider()){
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s.isCheck()){
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
		
		this.subcomponents.add(new Keybind(this, opY));
		this.subcomponents.add(new VisibleButton(this, mod, opY));
		*/
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset,  !this.isHovered ? (this.mod.isToggled() ? 0xFF26866A : 0xFF1A1A1A) : (this.mod.isToggled() ? 0xFF185644 : 0xFF26866A));
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		FontUtil.normal.drawStringWithShadow(this.mod.getName(), (parent.getX() + 2) * 2, (parent.getY() + offset + 2) * 2 + 4, -1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("settings1.png"));
		if(this.subcomponents.size() > 0) {
			int retX = (parent.getX() + parent.getWidth() - 10) * 2;
			int retY = (parent.getY() + offset + 2) * 2 + 4;
			Gui.drawModalRectWithCustomSizedTexture(retX, retY - 7, 0, 0, 24, 24, 24, 24);
		}
		int retX = (parent.getX() + parent.getWidth() - 10) * 2;
		int retY = (parent.getY() + offset + 2) * 2 + 4;
		if(isHovered) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("keybind.png"));
			Minecraft.getMinecraft().fontRendererObj.drawString("", 0, 0, (this.waitingKey ? -1 : 0xFF464445));
			Gui.drawModalRectWithCustomSizedTexture(retX - 10, retY - 3, 0, 0, 16, 16, 16, 16);
			if(!waitingKey) {
				String key = Keyboard.getKeyName(mod.getKey()).toUpperCase();
				if(mod.getKey() > 0) {
					if(key.length() == 1) {
						FontUtil.normal.drawString(key, retX - 5, retY, -1);
					}else {
						FontUtil.normal.drawString("?", retX - 5, retY, -1);
					}
					
				}
				
			}
		}
		
		
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
				Gui.drawRect(parent.getX() + 2, parent.getY() + this.offset + 12, parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), 0xFF1A1A1A);
			}
		}
	}
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		
		if(isMouseOnButton(mouseX, mouseY)) {
			if(isMouseOnKeybind(mouseX, mouseY)) {
				if(waitingKey) {
					mod.setKey(0);
					waitingKey = false;
				}else {
					waitingKey = true;
				}
				
				return;
			}
		}
		
		
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		
		if(waitingKey) {
			waitingKey = false;
			mod.setKey(key);
			return;
		}
		
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
	
	
	public boolean isMouseOnKeybind(int x,int y) {
		int retX = (parent.getX() + parent.getWidth() - 10) * 2;
		int retY = (parent.getY() + offset + 2) * 2 + 4;
		retX = retX - 10;
		retY = retY - 3;
		retX /= 2;
		retY /= 2;
		
		
		
		if(x >= retX && x <= (retX + 16) && y >= retY && y <= (retY + 16)) {
			return true;
		}
		return false;
	}
	
	
}
