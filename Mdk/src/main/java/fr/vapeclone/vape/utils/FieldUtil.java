package fr.vapeclone.vape.utils;

import java.lang.reflect.Field;

public class FieldUtil {

	public static Field getField(Class cl,String name,String srgName) {
		Field r = null;
		try {
	         r = cl.getDeclaredField(srgName);
	      } catch (Exception var4) {
	         try {
	            r = cl.getDeclaredField(name);
	         } catch (Exception var3) {
	         }
	      }

	      if (r != null) {
	         r.setAccessible(true);
	      }
		return r;
	}
	
	
}
