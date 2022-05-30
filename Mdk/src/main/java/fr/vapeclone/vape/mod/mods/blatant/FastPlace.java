package fr.vapeclone.vape.mod.mods.blatant;

import java.lang.reflect.Field;

import org.lwjgl.input.Keyboard;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.utils.FieldUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FastPlace extends Mod{

	public static Field r = null;
	public NumberSetting delay = new NumberSetting("Delay", 2, 0, 4, true);
	public BooleanSetting onlyBlocks = new BooleanSetting("Only blocks", false);
	public FastPlace() {
		super("FastPlace", "Modify the right click cooldown.", Category.BLATANT);
		this.addSettings(delay,onlyBlocks);
		r = FieldUtil.getField(mc.getClass(), "rightClickDelayTimer", "field_71467_ac");
		
		
	}
	
	@SubscribeEvent
	public void a(PlayerTickEvent e) {
		if (e.phase == Phase.END) {
			if(mc.thePlayer != null && mc.inGameHasFocus && r != null) {
				if(onlyBlocks.isEnabled()) {
					ItemStack item = mc.thePlayer.getHeldItem();
		               if (item == null || !(item.getItem() instanceof ItemBlock)) {
		                  return;
		               }
				}
				try {
					int c = (int)delay.getValue();
					if (c == 0) {
		                  r.set(mc, 0);
					}else {
						if (c == 4) {
		                     return;
		                  }
						int d = r.getInt(mc);
		                  if (d == 4) {
		                     r.set(mc, c);
		                  }
					}
					
				} catch (Exception e2) {
					
				}
				
				
			}
		}
	}
	

	
	
}
