package fr.vapeclone.vape.ui.hardcoded;

import org.lwjgl.opengl.GL11;

import fr.vapeclone.vape.fontrenderer.FontUtil;
import fr.vapeclone.vape.notifications.Notification;
import fr.vapeclone.vape.notifications.NotificationManager;
import fr.vapeclone.vape.notifications.NotificationType;
import fr.vapeclone.vape.utils.ColorUtil;
import fr.vapeclone.vape.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HardcodedHUD {
	public TimerUtil timer;
	public boolean rShiftHelp = true;
	boolean renderedOneTime = false;
	public HardcodedHUD() {
		MinecraftForge.EVENT_BUS.register(this);
		
	}
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent e) {
		NotificationManager.update();
		NotificationManager.render();
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRendererObj;
		if(!e.type.equals(e.type.CROSSHAIRS)) {
			return;
		}
		if(!renderedOneTime && mc.thePlayer != null) {
			renderedOneTime = true;
			timer = new TimerUtil();
			NotificationManager.show(new Notification(NotificationType.INFO, "RShift", "Press RShift",5));
		}
		/*
		if(rShiftHelp && mc.thePlayer != null) {
			int m = 1000 / (88 * 2);
			Gui.drawRect(0, (int)(sr.getScaledHeight()/2f - fr.FONT_HEIGHT - 47), (int)(0 + FontUtil.normal.getStringWidth("Press RShift") + 20) / 2,(int)(sr.getScaledHeight()/2f - 39) , 0xff282828);
			GL11.glPushMatrix();
			GL11.glScalef(0.5f, 0.5f, 1);
			FontUtil.normal.drawStringWithShadow("Press RShift", 3 * 2, (sr.getScaledHeight()/2f - fr.FONT_HEIGHT - 45) * 2, ColorUtil.getRainbow(4, 0.4, 1));
			GL11.glPopMatrix();
			Gui.drawRect(m, (int)(sr.getScaledHeight()/2f - 40), (6000 / (88 * 2)), (int)(sr.getScaledHeight()/2f - 41) , 0xff181818);
			Gui.drawRect(m, (int)(sr.getScaledHeight()/2f - 40), m + ((int)((System.currentTimeMillis() - timer.lastMS) / (88 * 2))) , (int)(sr.getScaledHeight()/2f - 41) , -1);
			if(timer.hasTimeElapsed(5000, true)) {
				rShiftHelp = false;
			}
			fr.drawString("", 0, 0, -1);
		}
		*/
	}
	public void unload() {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
