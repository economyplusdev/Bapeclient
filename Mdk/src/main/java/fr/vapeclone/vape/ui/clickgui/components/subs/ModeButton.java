package fr.vapeclone.vape.ui.clickgui.components.subs;

import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.ui.clickgui.components.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModeButton extends Component{
	private boolean hovered;
	private Button parent;
	private ModeSetting set;
	private int offset;
	private int x;
	private int y;
	private Mod mod;

	private int modeIndex;
	
	public ModeButton(ModeSetting set, Button button, Mod mod, int offset) {
		this.set = set;
		this.parent = button;
		this.mod = mod;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.modeIndex = 0;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponent() {
		
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, this.hovered ? 0xFF26866A : 0xFF1A1A1A);
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF1A1A1A);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		FontUtil.normal.drawStringWithShadow(set.name + " : " + set.getMode(), (parent.parent.getX() + 7) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, -1);
		GL11.glPopMatrix();
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			set.cycle();
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
	
}
