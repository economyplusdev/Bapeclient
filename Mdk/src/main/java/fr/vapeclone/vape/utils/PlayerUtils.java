package fr.vapeclone.vape.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PlayerUtils {
	public static Minecraft mc = Minecraft.getMinecraft();
	  public static boolean playeriswalking() {
	    if (mc.gameSettings.keyBindForward.isKeyDown())
	      return true; 
	    if (mc.gameSettings.keyBindBack.isKeyDown())
	      return true;
	    if (mc.gameSettings.keyBindLeft.isKeyDown())
	      return true; 
	    if (mc.gameSettings.keyBindRight.isKeyDown())
	      return true; 
	    return false;
	  }
	  
	  public static boolean isPlayerHoldingWeapon() {
	      if (mc.thePlayer.getCurrentEquippedItem() == null) {
	         return false;
	      } else {
	         Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
	         return item instanceof ItemSword || item instanceof ItemAxe;
	      }
	   }
	  
	  public static boolean playeriswalkingforward() {
	    if (mc.gameSettings.keyBindBack.isKeyDown())
	      return false; 
	    if (mc.gameSettings.keyBindLeft.isKeyDown())
	      return false; 
	    if (mc.gameSettings.keyBindRight.isKeyDown())
	      return false; 
	    if (mc.gameSettings.keyBindForward.isKeyDown())
	      return true; 
	    return false;
	  }
	  
	  public static void sendPosition(double x, double y, double z, boolean onGround) {
	    mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, onGround));
	  }
	  
	  public static void sendGround(boolean onGround) {
	    mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(onGround));
	  }
}
