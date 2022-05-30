package fr.vapeclone.vape.mod.mods.blatant;

import org.lwjgl.input.Keyboard;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoFall extends Mod{

	
	public NumberSetting maxFallDistance = new NumberSetting("Maximum fall distance", 2, 1, 4, true);
	
	public NoFall() {
		super("NoFall", "Remove fall damages", Category.BLATANT);
		this.addSettings(maxFallDistance);
		
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if(mc.thePlayer.fallDistance > maxFallDistance.getValue()) {
			 mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		 }
	}
	

	
	
}
