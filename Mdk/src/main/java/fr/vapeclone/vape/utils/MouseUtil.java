package fr.vapeclone.vape.utils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.lwjgl.input.Mouse;

import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;

public class MouseUtil {
	
	
	private static Field mouseButton = null;
	private static Field mouseButtonState = null;
	private static Field mouseButtons = null;

	public MouseUtil() {
		try {
	         mouseButton = MouseEvent.class.getDeclaredField("button");
	         mouseButtonState = MouseEvent.class.getDeclaredField("buttonstate");
	         mouseButtons = Mouse.class.getDeclaredField("buttons");
	      } catch (Exception var2) {
	      }
	}
	
	public static void setMouseButtonState(int mouseButton, boolean held) {
	      if (MouseUtil.mouseButton != null && mouseButtonState != null && mouseButtons != null) {
	         MouseEvent m = new MouseEvent();

	         try {
	        	 MouseUtil.mouseButton.setAccessible(true);
	        	 MouseUtil.mouseButton.set(m, mouseButton);
	            mouseButtonState.setAccessible(true);
	            mouseButtonState.set(m, held);
	            MinecraftForge.EVENT_BUS.post(m);
	            mouseButtons.setAccessible(true);
	            ByteBuffer bf = (ByteBuffer) mouseButtons.get(null);
	            mouseButtons.setAccessible(false);
	            bf.put(mouseButton, (byte)(held ? 1 : 0));
	         } catch (IllegalAccessException var4) {
	         }

	      }
	   }
}
