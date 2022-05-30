package fr.vapeclone.vape.ui.clickgui.components;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.ui.clickgui.components.subs.Button;
import fr.vapeclone.vape.utils.ResUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class Frame extends Component{

	public ArrayList<Component> components;
	public Category category;
	public String name;
	private boolean open;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	public boolean visible = false;
	public Frame(Category cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		this.name = this.category.name().substring(0,1) + this.category.name().substring(1).toLowerCase();
		int tY = this.barHeight;
		for(Mod mod : Vape.getInstance().mm.getModsByCategory(category)) {
			Button modButton = new Button(mod, this, tY);
			this.components.add(modButton);
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
		if(!visible)return;
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, 0xFF1A1A1A);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		
		FontUtil.normal.drawStringWithShadow(name, (this.x + 2) * 2 + 20, (this.y + 2.5f) * 2 + 5, 0xFFFFFFFF);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("category/" + name.toLowerCase() + ".png"));
		int size = 13;
		Gui.drawModalRectWithCustomSizedTexture((this.x + 2) * 2 + 5, (int) ((this.y + 2.5f) * 2 + 2), 0, 0, size, size, size, size);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes(this.open ? "expand.png" : "retract.png"));
		
		int retX = (this.x + this.width - 10) * 2 + 5;
		int retY = (int)(this.y + 2.5f) * 2 + 5;
		fontRenderer.drawString("", 0, 0, 0xFF464445);
		Gui.drawModalRectWithCustomSizedTexture(retX, retY, 0, 0, 10, 10, 10, 10);
		//fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (this.x + this.width - 10) * 2 + 5, (this.y + 2.5f) * 2 + 5, -1);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.components.isEmpty()) {
				for(Component component : components) {
					component.renderComponent();
				}
			}
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
		if(!visible)return;
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(!visible)return false;
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}
	
	
}
