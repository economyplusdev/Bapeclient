package fr.vapeclone.vape.mod.mods.blatant;

import org.lwjgl.input.Keyboard;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.ModeSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.utils.PlayerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BHop extends Mod{

	
	public NumberSetting speed = new NumberSetting("Speed", 2, 1, 15, true);
	
	public BHop() {
		super("Bhop", "Makes you bhop.", Category.BLATANT);
		this.addSettings(speed);
		
	}
	
	public static void bop(double s) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
           mc.thePlayer.motionX = 0.0D;
           mc.thePlayer.motionZ = 0.0D;
        } else {
           if (forward != 0.0D) {
              if (strafe > 0.0D) {
                 yaw += (float)(forward > 0.0D ? -45 : 45);
              } else if (strafe < 0.0D) {
                 yaw += (float)(forward > 0.0D ? 45 : -45);
              }

              strafe = 0.0D;
              if (forward > 0.0D) {
                 forward = 1.0D;
              } else if (forward < 0.0D) {
                 forward = -1.0D;
              }
           }

           double rad = Math.toRadians(yaw + 90.0F);
           double sin = Math.sin(rad);
           double cos = Math.cos(rad);
           mc.thePlayer.motionX = forward * s * cos + strafe * s * sin;
           mc.thePlayer.motionZ = forward * s * sin - strafe * s * cos;
        }

     }
	
	public void update() {
		if(PlayerUtils.playeriswalking() && !mc.thePlayer.isInWater() && !mc.thePlayer.capabilities.isFlying) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
			mc.thePlayer.noClip = true;
	         if (mc.thePlayer.onGround) {
	            mc.thePlayer.jump();
	         }
	         mc.thePlayer.setSprinting(true);
	         double spd = 0.0025D * speed.getValue();
	         double m = (float)(Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + spd);
	         bop(m);
		}
	}
	
	
	

	
	
}
