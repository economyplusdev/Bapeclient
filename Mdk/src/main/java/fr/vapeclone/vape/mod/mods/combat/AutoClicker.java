package fr.vapeclone.vape.mod.mods.combat;

import org.lwjgl.input.Mouse;

import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.utils.MouseUtil;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoClicker extends Mod{

	
	public static boolean autoclicking = false;
	private long lastClick;
	private long hold;
	private double speed;
	private double holdLength;
	private double min;
	private double max;
	
	public NumberSetting minCPS = new NumberSetting("Minimum CPS", 8, 1, 20, true);
	public NumberSetting maxCPS = new NumberSetting("Maximum CPS", 12, 1, 20, true);
	public BooleanSetting breakBlocks = new BooleanSetting("Break blocks", false);
	
	public AutoClicker() {
		super("AutoClicker", "Automaticly clicks when you hold down left click", Category.COMBAT);
		this.addSettings(minCPS,maxCPS,breakBlocks);
		
		
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.RenderTickEvent e) {
		
		if(breakBlocks.isEnabled()) {
			if(mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK)return;
		}
		
		if(Mouse.isButtonDown(0)) {
			if(System.currentTimeMillis() - lastClick > speed * 1000) {
				lastClick = System.currentTimeMillis();
				if(hold < lastClick) {
					hold = lastClick;	
				}
				int key = mc.gameSettings.keyBindAttack.getKeyCode();
				KeyBinding.setKeyBindState(key, true);
				KeyBinding.onTick(key);
				MouseUtil.setMouseButtonState(0, true);
				autoclicking = true;
				this.updateVals();
							
			}else if (System.currentTimeMillis() - hold > holdLength * 1000) {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
				MouseUtil.setMouseButtonState(0, false);
				
				this.updateVals();
				
			}
		}else {
			autoclicking = false;
		}
	}
	
	private void updateVals() {
		min = minCPS.getValue();
		max = maxCPS.getValue();
		if(min >= max) {
			max = min + 1;
		}
		speed = 1.0 / ThreadLocalRandom.current().nextDouble(min - 0.2,max);
		holdLength = speed / ThreadLocalRandom.current().nextDouble(min,max);
		
	}
	
}
