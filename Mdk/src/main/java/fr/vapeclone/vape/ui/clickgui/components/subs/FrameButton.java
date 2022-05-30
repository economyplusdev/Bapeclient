package fr.vapeclone.vape.ui.clickgui.components.subs;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.ui.clickgui.components.Component;
import fr.vapeclone.vape.ui.clickgui.components.Frame;
import fr.vapeclone.vape.ui.clickgui.components.FrameShower;
import fr.vapeclone.vape.utils.ResUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class FrameButton extends Component{
	public FrameShower parent;
	
	public Frame frame;
	public int offset;
	private boolean isHovered;
	private int height;
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			frame.visible = !frame.visible;
		}
		
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset,  this.isHovered ? 0xFF26866A : 0xFF1F1E1F);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		
		FontUtil.normal.drawStringWithShadow(frame.name, (parent.getX() + 13) * 2, (parent.getY() + offset + 2) * 2 + 4, frame.visible ? 0xFF06AC87 : -1);
		int size = 13;
		
		if(frame.visible) {
			
			FontUtil.normal.drawString("", 0, 0, 0xFF06AC87);
		}
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("category/" + frame.name.toLowerCase() + ".png"));
		
		
		Gui.drawModalRectWithCustomSizedTexture((parent.getX() + 4) * 2, (parent.getY() + offset + 2) * 2 + 4, 0, 0, size, size, size, size);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("settings1.png"));
		GL11.glPopMatrix();
		
	}
	
	public FrameButton(FrameShower parent, int offset,Frame frame) {
		this.parent = parent;
		this.offset = offset;
		this.frame = frame;
	}
	@Override
	public int getHeight() {
		return 12;
	}
}
