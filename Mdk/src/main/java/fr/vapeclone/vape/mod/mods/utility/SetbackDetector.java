package fr.vapeclone.vape.mod.mods.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import fr.vapeclone.vape.Vape;
import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SetbackDetector extends Mod{

	
	private List<Vec3> lastLocations = new ArrayList<>();
    private List<Long> lastSetBacks = new ArrayList<>();
	
	public SetbackDetector() {
		super("SetbackDetector", "Detect anticheat's flags", Category.UTILITY);
		
		
	}
	
	
	
		
}
