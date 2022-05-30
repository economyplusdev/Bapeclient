package fr.vapeclone.vape.mod.mods.blatant;

import java.lang.reflect.Field;

import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import fr.vapeclone.vape.utils.FieldUtil;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiCobweb extends Mod{

	public static Field r = null;
	public AntiCobweb() {
		super("AntiCobweb", "Remove the slow of cobweb", Category.BLATANT);
		r = FieldUtil.getField(Entity.class, "isInWeb", "field_70134_J");
		
	}
	
	
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		try {
			r.setBoolean(mc.thePlayer, false);
		} catch (Exception e2) {
			
		}
	}
	
	
		
}
