package fr.vapeclone.vape.ui.clickgui.components.subs;

import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.ui.clickgui.components.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Checkbox extends Component{
	private boolean hovered;
	private BooleanSetting op;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	
	public Checkbox(BooleanSetting option, Button button, int offset) {
		this.op = option;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, this.hovered ? 0xFF26866A : 0xFF1A1A1A);
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF1A1A1A);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		FontUtil.normal.drawStringWithShadow(this.op.name, (parent.parent.getX() + 10 + 4) * 2 + 5, (parent.parent.getY() + offset + 2) * 2 + 4, -1);
		GL11.glPopMatrix();
		Gui.drawRect(parent.parent.getX() + 3 + 4, parent.parent.getY() + offset + 3, parent.parent.getX() + 9 + 4, parent.parent.getY() + offset + 9, 0xFF999999);
		if(this.op.isEnabled())
			Gui.drawRect(parent.parent.getX() + 3 + 4, parent.parent.getY() + offset + 3, parent.parent.getX() + 9 + 4, parent.parent.getY() + offset + 9, 0xFF185644);
	}
	@Override
	public void setOff(int newOff) {
		offset = newOff;
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
			this.op.setEnabled(!op.isEnabled());
		}
	}
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
