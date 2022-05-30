package fr.vapeclone.vape.mod.mods.combat;

import org.lwjgl.input.Mouse;

import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Velocity extends Mod{

	
	
	public NumberSetting horizontal = new NumberSetting("Horizontal", 90, 0, 100, true);
	public NumberSetting vertical = new NumberSetting("Vertical", 100, 0, 100, true);
	public NumberSetting chance = new NumberSetting("Chance", 100, 1, 100, true);
	
	
	public Velocity() {
		super("Velocity", "Reduce Knockback", Category.COMBAT);
		this.addSettings(horizontal,vertical,chance);
		
		
	}
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		
		
		
		
		
		
		
		
		if(mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
			
			if(chance.getValue() != 100) {
				int n = ThreadLocalRandom.current().nextInt(0,100);
				if(n < chance.getValue()) {
					return;
				}
			}
			float horizontal_ = (float) Math.round(horizontal.getValue());
			float vertical_ = (float) Math.round(vertical.getValue());
			mc.thePlayer.motionX *= (float)horizontal_ / 100;
			mc.thePlayer.motionZ *= (float)horizontal_ / 100;
			mc.thePlayer.motionY *= (float)vertical_ / 100;
			
		}
	}
	
	
	
}
