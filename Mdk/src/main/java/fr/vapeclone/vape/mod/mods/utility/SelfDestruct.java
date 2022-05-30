package fr.vapeclone.vape.mod.mods.utility;

import java.lang.reflect.Field;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SelfDestruct extends Mod{

	public SelfDestruct() {
		super("SelfDestruct", "Self destruct the client", Category.UTILITY);
		
	}
	@Override
	public void onEnable() {
		super.onEnable();
		this.toggle();
		Vape.getInstance().destruct();
	}
	
	
		
}
