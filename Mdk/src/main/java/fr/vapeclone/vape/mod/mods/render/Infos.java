package fr.vapeclone.vape.mod.mods.render;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.utils.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Infos extends Mod{

	public Infos() {
		super("Infos", "Show infos on screen", Category.RENDER);
		
		
	}

	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent e) {
		ScaledResolution sr = new ScaledResolution(mc);
		if(!e.type.equals(e.type.CROSSHAIRS)) {
			return;
		}
		Gui.drawRect(10, 10, 180, 12, ColorUtil.getRainbow(4, 0.4, 1));
		Gui.drawRect(10, 12, 180, 20, 0xFF1A1A1A);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 1);
		
		String s = "Bape v4 | ";
		if(mc.isSingleplayer()) {
			s += "Singleplayer | ";
		}else {
			s += mc.getCurrentServerData().serverIP + " | ";
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		s += dtf.format(LocalDateTime.now());
		
		FontUtil.normal.drawStringWithShadow(s,25,20 + FontUtil.normal.getHeight() - 4,-1);
		GL11.glPopMatrix();
	}
}
