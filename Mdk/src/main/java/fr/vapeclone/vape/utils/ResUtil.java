package fr.vapeclone.vape.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class ResUtil {

	
	public static String getFilePath(String name) {
		return System.getProperty("java.io.tmpdir") + "bape\\" + name;
	}
	
	public static DynamicTexture getTexture(String name) {
		String path = System.getProperty("java.io.tmpdir") + "bape\\" + name;
		File texture = new File(path);
	    BufferedImage image;
		try {
			image = ImageIO.read(texture);
			return new DynamicTexture(image);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	    
	}
	
	public static ResourceLocation getResourceLocation(DynamicTexture texture) {
		if(texture != null) {
			ResourceLocation location = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("vc", texture);
			return location;
		}
		return null;
		
	}
	public static ResourceLocation getRes(String name) {
		return getResourceLocation(getTexture(name));
	}
	public static ResourceLocation getRes(String unused,String name) {
		return getRes(name);
	}
	
}
