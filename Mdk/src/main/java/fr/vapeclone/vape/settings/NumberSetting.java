package fr.vapeclone.vape.settings;

public class NumberSetting extends Setting{
	public double value , minimum,maximum;
	public boolean onlyint;
	public NumberSetting(String name,double value, double minimum, double maxium, boolean onlyint) {
		this.name = name;
		this.value = value;
		this.minimum = minimum;
		this.maximum = maxium;
		this.onlyint = onlyint;
	}
	
	public double getValue() {
		return value;
		
	}

	public void setValue(double value) {
	
		if(this.onlyint){
			this.value = (int)value;
		}else {
			this.value = value;
		}
		
		
	}
	public double getMinimum() {
		return minimum;
	}

	public void setMinimum(double minimum) {
		this.minimum = minimum;
	}

	public double getMaximum() {
		return maximum;
	}

	public void setMaximum(double maxium) {
		this.maximum = maxium;
	}

	public boolean getOnlyInt() {
		return onlyint;
	}

	public void setOnlyInt(boolean onlyint) {
		this.onlyint = onlyint;
	}
	
}
