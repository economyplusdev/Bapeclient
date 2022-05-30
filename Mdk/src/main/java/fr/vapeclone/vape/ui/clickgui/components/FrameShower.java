package fr.vapeclone.vape.ui.clickgui.components;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.ui.clickgui.components.subs.Button;
import fr.vapeclone.vape.ui.clickgui.components.subs.FrameButton;
import fr.vapeclone.vape.utils.ResUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class FrameShower extends Component{

	public ArrayList<Component> components;
	private boolean open;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	public FrameShower() {
		this.components = new ArrayList<Component>();
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		int tY = this.barHeight;
		
		for(Frame f : Vape.getInstance().cg.frames) {
			FrameButton btn = new FrameButton(this, tY, f);
			this.components.add(btn);
			tY += 12;
		}
		
		
		
	}
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public void renderFrame(FontRenderer fontRenderer) {
		
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, 0xFF1A1A1A);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("bape_1.png"));
		Minecraft.getMinecraft().fontRendererObj.drawString("", 0, 0, -1);
		int size = 3;
		int x_ = (int)(this.x + 2) * 2 + 5;
		int y_ = (int) ((this.y + 2.5f) * 2 + 5);
		x_ += 76;
		y_ += barHeight + 10; 
		Gui.drawModalRectWithCustomSizedTexture(x_ - 235 / size - 5, y_ - 48 / size - 10, 0, 0, 235 / size, 48 / size, 235 / size, 48 / size);
		//FontUtil.normal.drawStringWithShadow("Bape v4", (this.x + 2) * 2 + 5, (this.y + 2.5f) * 2 + 5, 0xFFFFFFFF);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes((this.open ? "expand.png" : "retract.png")));
		
		int retX = (this.x + this.width - 10) * 2 + 5;
		int retY = (int)(this.y + 2.5f) * 2 + 5;
		fontRenderer.drawString("", 0, 0, 0xFF464445);
		//Gui.drawModalRectWithCustomSizedTexture(retX, retY, 0, 0, 10, 10, 10, 10);
		//fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (this.x + this.width - 10) * 2 + 5, (this.y + 2.5f) * 2 + 5, -1);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.components.isEmpty()) {
				for(Component component : components) {
					component.renderComponent();
				}
			}
			int baseY = this.y + ((this.components.size() + 1) * 12);
			
			Gui.drawRect(this.x,baseY , this.x + this.width, baseY + this.barHeight, 0xFF1A1A1A);
			fontRenderer.drawString("", 0, 0, 0xFF464445);
			size = 6;
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("favorite.png"));
			Gui.drawModalRectWithCustomSizedTexture(this.x + 2, baseY + 5, 0, 0, size, size, size, size);
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("rr.png"));
			Gui.drawModalRectWithCustomSizedTexture(this.x + this.width - 29, baseY + 5, 0, 0, size, size, size, size);
			
			size = 5;
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("rr2.png"));
			Gui.drawModalRectWithCustomSizedTexture(this.x + this.width - 9, baseY + 5, 0, 0, size, size, size, size);
			int tmp1 = this.x + this.width - 20;
			Gui.drawRect(tmp1, baseY + 5, tmp1 + 6, baseY + 6, 0xFF464445);
			Gui.drawRect(tmp1, baseY + 7, tmp1 + 4, baseY + 8, 0xFF464445);
			Gui.drawRect(tmp1, baseY + 9, tmp1 + 5, baseY + 10, 0xFF464445);
			
			
			
			
		}
		
		
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}
	
	
}
