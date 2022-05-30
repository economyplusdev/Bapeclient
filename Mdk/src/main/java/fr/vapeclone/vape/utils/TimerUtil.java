package fr.vapeclone.vape.utils;

public class TimerUtil {
public long lastMS;
	
	public TimerUtil() {
		lastMS = System.currentTimeMillis();
	}
	public void reset() {
		lastMS = System.currentTimeMillis();
	}
	public boolean hasTimeElapsed(long time,boolean reset) {
		if(System.currentTimeMillis()-lastMS > time) {
			if(reset) reset();
			return true;
		}

		
			
		
		
		return false;
	}
}
