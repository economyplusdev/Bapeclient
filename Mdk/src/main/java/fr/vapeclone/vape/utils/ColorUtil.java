package fr.vapeclone.vape.utils;

import java.awt.Color;

public class ColorUtil {

	public static int getRainbow(double rainseconds,double rainsaturation,float brightness) {
		float hue = (System.currentTimeMillis() % (int)(rainseconds * 1000)) / (float)(rainseconds * 1000F);
		int color = Color.HSBtoRGB(hue, (float) rainsaturation, brightness);
		return color;
	}
	public static int getRainbow(double rainseconds,double rainsaturation,float brightness,long index) {
		float hue = ((System.currentTimeMillis() + index) % (int)(rainseconds * 1000)) / (float)(rainseconds * 1000F);
		int color = Color.HSBtoRGB(hue, (float) rainsaturation, brightness);
		return color;
	}
	public static int getRainbow(double rainseconds,double rainsaturation,float brightness,long index,boolean b) {
		float hue = ((System.currentTimeMillis() + (b ? index : (index *-1))) % (int)(rainseconds * 1000)) / (float)(rainseconds * 1000F);
		int color = Color.HSBtoRGB(hue, (float) rainsaturation, brightness);
		return color;
	}
}
