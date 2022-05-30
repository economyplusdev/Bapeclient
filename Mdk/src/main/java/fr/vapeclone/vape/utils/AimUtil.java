package fr.vapeclone.vape.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.util.MathHelper;

public class AimUtil {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static void aimEntity(Entity e) {
		float[] rotations = getRotations(e);
		mc.thePlayer.rotationYaw = rotations[0];
		mc.thePlayer.rotationPitch = rotations[1];
	}
	
	public static float[] getRotations(Entity e) {
		
		double d0 = e.posX - mc.thePlayer.posX;
        double d1 = e.posY - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() - e.getEyeHeight() + 0.2);
        double d2 = e.posZ - mc.thePlayer.posZ;
        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI));
        
        return new float[] {f,f1};
	
	
	}
}
