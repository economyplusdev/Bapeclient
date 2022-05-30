package fr.vapeclone.vape.mod.mods.blatant;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lwjgl.input.Keyboard;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.utils.AimUtil;
import fr.vapeclone.vape.utils.PlayerUtils;
import fr.vapeclone.vape.utils.TimerUtil;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class KillAura extends Mod{

	public TimerUtil timer = new TimerUtil();
	public NumberSetting range = new NumberSetting("Range", 4, 1, 6, false);
	public NumberSetting minAps = new NumberSetting("Min APS", 10, 1, 20, false);
	public NumberSetting maxAps = new NumberSetting("Max APS", 12, 1, 20, false);
	public BooleanSetting noSwing = new BooleanSetting("No Swing", false);
	public BooleanSetting fixLook = new BooleanSetting("Fix Look", true);
	
	
	public KillAura() {
		super("KillAura", "Attack entites arround you", Category.BLATANT);
		this.addSettings(range,minAps,maxAps,noSwing,fixLook);
		
	}
	@SubscribeEvent
	public void onTickUpdate(PlayerTickEvent e) {
		try {
			 Stream var10000 = this.mc.theWorld.loadedEntityList.stream();
		        List<EntityLivingBase> targets = (List)var10000.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
		        targets = (List)targets.stream().filter((entity) -> {
		           return (double)entity.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && entity != this.mc.thePlayer;
		        }).collect(Collectors.toList());
		        targets.sort(Comparator.comparingDouble((entity) -> {
		           return (double)entity.getDistanceToEntity(this.mc.thePlayer);
		        }));
		        if (!targets.isEmpty()) {
		        	EntityLivingBase target = (EntityLivingBase)targets.get(0);
		        	
		        	double min = minAps.getValue();
		        	double max = minAps.getValue();
		        	if(min >= max) {
		    			max = min + 1;
		    		}
		        	double aps = ThreadLocalRandom.current().nextDouble(min,max);
		        	
		        	if (this.timer.hasTimeElapsed((long)(1000.0D / aps), true)) {
		                if (this.noSwing.isEnabled()) {
		                   this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
		                } else {
		                   this.mc.thePlayer.swingItem();
		                }
		             }
		        	float yaw = mc.thePlayer.rotationYaw;
		        	float pitch = mc.thePlayer.rotationPitch;
		        	AimUtil.aimEntity(target);
		        	this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
		            if (fixLook.isEnabled()) {
		               mc.thePlayer.rotationYaw = yaw;
		               mc.thePlayer.rotationPitch = pitch;
		               
		            }
		        }
		} catch (Exception e2) {
			
		}
	}
	
	
	
	
	

	
	
}
