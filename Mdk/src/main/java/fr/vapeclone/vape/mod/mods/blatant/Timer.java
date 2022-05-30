package fr.vapeclone.vape.mod.mods.blatant;

import java.lang.reflect.Field;

import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.utils.FieldUtil;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Timer extends Mod{

	public static Field mcTimerField = null;
	public static Field mcTimerSpeed = null;
	
	
	NumberSetting minRate = new NumberSetting("Min rate", 20, 1, 200, true);
	NumberSetting maxRate = new NumberSetting("Max rate", 25, 1, 200, true);
	
	
	public Timer() {
		super("Timer", "Change Minecraft tickspeed", Category.BLATANT);
		mcTimerField = FieldUtil.getField(Minecraft.class, "timer", "field_71428_T");
		mcTimerSpeed = FieldUtil.getField(net.minecraft.util.Timer.class, "timerSpeed", "field_74278_d");
		addSettings(minRate,maxRate);
		
		
	}
	
	
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		 if(mc.theWorld != null) {
			 double min = minRate.getValue();
			 double max = maxRate.getValue();
			 if(min >= max)
				 max = min + 1;
			 try {
				mcTimerSpeed.setFloat(mcTimerField.get(mc), (float)(ThreadLocalRandom.current().nextDouble(min,max) / 10));
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		 }
	}
	
	
		
}
