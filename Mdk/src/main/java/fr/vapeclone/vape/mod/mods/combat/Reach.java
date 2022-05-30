package fr.vapeclone.vape.mod.mods.combat;

import java.util.List;

import org.lwjgl.input.Mouse;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.BooleanSetting;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.utils.PlayerUtils;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Reach extends Mod{

	
	
	public NumberSetting minRange = new NumberSetting("Min Range", 4, 3, 6, false);
	public NumberSetting maxRange = new NumberSetting("Max Range", 4, 3, 6, false);
	public BooleanSetting weaponOnly = new BooleanSetting("Weapon only", false);
	public BooleanSetting movingOnly = new BooleanSetting("Moving Only", false);
	public BooleanSetting sprintingOnly = new BooleanSetting("Sprinting Only", false);
	public BooleanSetting hit_through_blocks = new BooleanSetting("Hit Through Blocks", false);
	
	
	public Reach() {
		super("Reach", "Boost your reach", Category.COMBAT);
		this.addSettings(minRange,maxRange,weaponOnly,movingOnly,sprintingOnly,hit_through_blocks);
		
		
	}
	@SubscribeEvent
	   public void onMouse(MouseEvent ev) {
			
	      // legit event
	      if(mc.thePlayer == null) return;
	      //if (Vape.getInstance().mm.getMod("AutoClicker").isToggled() && Mouse.isButtonDown(0)) return;
	      if (ev.button >= 0 && ev.buttonstate) {
	         call();
	      }
	   }
	
	@SubscribeEvent
	   public void onRenderTick(TickEvent.RenderTickEvent ev) {
		
	      // autoclick event
		if(mc.thePlayer == null) return;

	      
	      if (Vape.getInstance().mm.getMod("AutoClicker").isToggled() && Mouse.isButtonDown(0)){
	    	 
	         call();
	      }
	   }
	
	private static Object[] zz(double zzD, double zzE) {
	      if (!Vape.getInstance().mm.getMod("Reach").isToggled()) {
	         zzD = mc.playerController.extendedReach() ? 6.0D : 3.0D;
	      }

	      Entity entity1 = mc.getRenderViewEntity();
	      Entity entity = null;
	      if (entity1 == null) {
	         return null;
	      } else {
	         mc.mcProfiler.startSection("pick");
	         Vec3 eyes_positions = entity1.getPositionEyes(1.0F);
	         Vec3 look = entity1.getLook(1.0F);
	         Vec3 new_eyes_pos = eyes_positions.addVector(look.xCoord * zzD, look.yCoord * zzD, look.zCoord * zzD);
	         Vec3 zz6 = null;
	         List<Entity> zz8 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getEntityBoundingBox().addCoord(look.xCoord * zzD, look.yCoord * zzD, look.zCoord * zzD).expand(1.0D, 1.0D, 1.0D));
	         double zz9 = zzD;

	         for (Object o : zz8) {
	            Entity zz11 = (Entity) o;
	            if (zz11.canBeCollidedWith()) {
	               float ex = (float) ((double) zz11.getCollisionBorderSize() * 1.0D);
	               AxisAlignedBB zz13 = zz11.getEntityBoundingBox().expand(ex, ex, ex);
	               zz13 = zz13.expand(zzE, zzE, zzE);
	               MovingObjectPosition zz14 = zz13.calculateIntercept(eyes_positions, new_eyes_pos);
	               if (zz13.isVecInside(eyes_positions)) {
	                  if (0.0D < zz9 || zz9 == 0.0D) {
	                     entity = zz11;
	                     zz6 = zz14 == null ? eyes_positions : zz14.hitVec;
	                     zz9 = 0.0D;
	                  }
	               } else if (zz14 != null) {
	                  double zz15 = eyes_positions.distanceTo(zz14.hitVec);
	                  if (zz15 < zz9 || zz9 == 0.0D) {
	                     if (zz11 == entity1.ridingEntity) {
	                        if (zz9 == 0.0D) {
	                           entity = zz11;
	                           zz6 = zz14.hitVec;
	                        }
	                     } else {
	                        entity = zz11;
	                        zz6 = zz14.hitVec;
	                        zz9 = zz15;
	                     }
	                  }
	               }
	            }
	         }

	         if (zz9 < zzD && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
	            entity = null;
	         }

	         mc.mcProfiler.endSection();
	         if (entity != null && zz6 != null) {
	            return new Object[]{entity, zz6};
	         } else {
	            return null;
	         }
	      }
	   }
	
	public boolean call() {
		if(mc.thePlayer == null) return false;
		if(weaponOnly.isEnabled() && !PlayerUtils.isPlayerHoldingWeapon())return false;
		if (movingOnly.isEnabled() && (double)mc.thePlayer.moveForward == 0.0D && (double)mc.thePlayer.moveStrafing == 0.0D) return false;
		if (sprintingOnly.isEnabled() && !mc.thePlayer.isSprinting()) return false;
		if (!hit_through_blocks.isEnabled() && mc.objectMouseOver != null) {
			BlockPos p = mc.objectMouseOver.getBlockPos();
	         if (p != null && mc.theWorld.getBlockState(p).getBlock() != Blocks.air) {
	            return false;
	         }
		}
		double min = minRange.getValue();
		double max = maxRange.getValue();
		if(min >= max) {
			max = min + 0.1;
		}
		double r = ThreadLocalRandom.current().nextDouble(min,max);
		Object[] o = zz(r, 0.0D);
		if (o == null) {
	         return false;
	      } else {
	         Entity en = (Entity)o[0];
	         mc.objectMouseOver = new MovingObjectPosition(en, (Vec3)o[1]);
	         mc.pointedEntity = en;
	         
	         return true;
	      }
		
	}
	
	
	
}
