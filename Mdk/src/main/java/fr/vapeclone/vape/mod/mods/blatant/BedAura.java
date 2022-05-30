package fr.vapeclone.vape.mod.mods.blatant;

import java.lang.reflect.Field;
import java.util.TimerTask;

import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.settings.NumberSetting;
import fr.vapeclone.vape.utils.FieldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BedAura extends Mod{

	public NumberSetting r = new NumberSetting("Range", 5.0D, 2.0D, 10.0D, true);
	private java.util.Timer t;
	private BlockPos m = null;
	private final long per = 600L;
	public BedAura() {
		super("BedAura", "Destroy beds around you", Category.BLATANT);
		this.addSettings(r);
		
		
	}
	
	private void mi(BlockPos p) {
	      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, p, EnumFacing.NORTH));
	      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, p, EnumFacing.NORTH));
	   }
	public TimerTask t() {
		return new TimerTask() {

			@Override
			public void run() {
				int ra = (int)r.getValue();
				for(int y = ra; y >= -ra; --y) {
		               for(int x = -ra; x <= ra; ++x) {
		                  for(int z = -ra; z <= ra; ++z) {
		                	  if(mc.thePlayer != null) {
		                		  BlockPos p = new BlockPos(mc.thePlayer.posX + (double) x, mc.thePlayer.posY + (double) y, mc.thePlayer.posZ + (double) z);
		                		  boolean bed = mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
		                		  if (m == p) {
		                              if (!bed) {
		                                 m = null;
		                              }
		                           } else if (bed) {
		                              mi(p);
		                              m = p;
		                              break;
		                           }
		                	  }
		                  }
		               }
				}
				
			}
		};
		
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		 (this.t = new java.util.Timer()).scheduleAtFixedRate(this.t(), 0L, 600L);
	}
	
	@Override
	 public void onDisable() {
		super.onDisable();
	      if (this.t != null) {
	         this.t.cancel();
	         this.t.purge();
	         this.t = null;
	      }

	      this.m = null;
	   }
	
	
		
}
