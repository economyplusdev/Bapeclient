package fr.vapeclone.vape.ui.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.mods.render.ClickGUI;
import fr.vapeclone.vape.ui.clickgui.components.Component;
import fr.vapeclone.vape.ui.clickgui.components.Frame;
import fr.vapeclone.vape.ui.clickgui.components.FrameShower;
import fr.vapeclone.vape.utils.ColorUtil;
import fr.vapeclone.vape.utils.ResUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class ClickGui extends GuiScreen{
	
	public boolean blur = false;
	public static ArrayList<Frame> frames;
	public static FrameShower fs;
	public ClickGui() {
		this.frames = new ArrayList<Frame>();
		int frameX = 5;
		for(Category category : Category.values()) {
			//if(Pulsive.instance.modManager.getModsByCategory(category).size() == 0)continue;
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frames.add(frame);
			frameX += frame.getWidth() + 1;
		}
		
		fs = new FrameShower();
	}
	
	 public boolean doesGuiPauseGame() {
		 return false;
	 }
	    
	 public void onGuiClosed() {
		 if(blur) {
			 blur = false;
			 mc.entityRenderer.loadEntityShader(null);
		 }
		 
	 }
	 
	    
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		ClickGUI g = (ClickGUI)Vape.getInstance().mm.getMod("ClickGui");
		if(g.blur.isEnabled() && !blur) {
			blur = true;
			mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
		}
		
		if(!g.blur.isEnabled() && blur) {
			blur = false;
			mc.entityRenderer.loadEntityShader(null);
		}
		
		for(Frame frame : frames) {
			frame.renderFrame(this.fontRendererObj);
			frame.updatePosition(mouseX, mouseY);
			for(Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
		fs.renderFrame(this.fontRendererObj);
		fs.updatePosition(mouseX, mouseY);
		for(Component comp : fs.getComponents()) {
			comp.updateComponent(mouseX, mouseY);
		}
		
		ScaledResolution sr = new ScaledResolution(mc);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResUtil.getRes("bape_1.png"));
		mc.fontRendererObj.drawString("", 0, 0, -1);
		int size = 3;
		Gui.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() - 235 / size - 5, sr.getScaledHeight() - 48 / size - 10, 0, 0, 235 / size, 48 / size, 235 / size, 48 / size);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 1);
		FontUtil.normal.drawStringWithShadow("By Body Alhoha",((sr.getScaledWidth() - FontUtil.normal.getStringWidth("By Body Alhoha")) * 2) + 60,((sr.getScaledHeight() - FontUtil.normal.getHeight())*2) + 6,ColorUtil.getRainbow(4, 0.4, 1));
		GL11.glPopMatrix();
	}
	
    protected void mouseReleased(int mouseX, int mouseY, int state) {
    	
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
		fs.setDrag(false);
    	if(fs.isOpen()) {
			if(!fs.getComponents().isEmpty()) {
				for(Component component : fs.getComponents()) {
					component.mouseReleased(mouseX, mouseY, state);
				}
			}
		}
	}
	
	public void initGui() {
		
		ClickGUI g = (ClickGUI)Vape.getInstance().mm.getMod("ClickGui");
		if(g.blur.isEnabled()) {
			mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
			blur = true;
		}
		
		
	}
	protected void keyTyped(char typedChar, int keyCode) throws IOException{
		super.keyTyped(typedChar, keyCode);
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if(fs.isOpen() && keyCode != 1) {
			if(!fs.getComponents().isEmpty()) {
				for(Component component : fs.getComponents()) {
					component.keyTyped(typedChar, keyCode);
				}
			}
		}
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
		
		if(fs.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
			fs.setDrag(true);
			fs.dragX = mouseX - fs.getX();
			fs.dragY = mouseY - fs.getY();
		}
		if(fs.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
			fs.setOpen(!fs.isOpen());
		}
		int retX1 = (fs.getX() + fs.getWidth() - 10) * 2 + 5;
		int retY1 = (int)(fs.getY() + 2.5f) * 2 + 5;
		if(mouseX >= retX1 && mouseX <= retX1 + 10 && mouseY >= retY1 && mouseY <= retY1 + 10) {
			fs.setOpen(!fs.isOpen());
		}
		if(fs.isOpen()) {
			if(!fs.getComponents().isEmpty()) {
				for(Component component : fs.getComponents()) {
					component.mouseClicked(mouseX, mouseY, mouseButton);
				}
			}
		}
		for(Frame frame : frames) {
			
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			
			int retX = (frame.getX() + frame.getWidth() - 10) * 2 + 5;
			int retY = (int)(frame.getY() + 2.5f) * 2 + 5;
			if(mouseX >= retX && mouseX <= retX + 10 && mouseY >= retY && mouseY <= retY + 10) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
	}
	protected void actionPerformed(GuiButton button) throws IOException{
	    
	}
	    
}
