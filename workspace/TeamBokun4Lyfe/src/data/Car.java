package data;

public class Car {
	private int			id;
	private double		speed;
	private String		direction;
	private String		onOffRamp;
	private String		freeway;
	
	public Car(int id, double speed, String direction, String onOff, String freeway) {
		this.id = id;
		this.speed = speed;
		this.direction = direction;
		this.onOffRamp = onOff;
		this.freeway = freeway;
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public double getSpeed() { return speed; }
	public void setSpeed(double speed) { this.speed = speed; }
	
	public String getDirection() { return direction; }
	public void setDirection(String direction) { this.direction = direction; }
	
	public String getOnOffRamp() { return onOffRamp; }
	public void setOnOffRamp(String onOffRamp) { this.onOffRamp = onOffRamp; }
	
	public String getFreeway() { return freeway; }
	public void setFreeway(String freeway) { this.freeway = freeway; }
	
	@Override
	public String toString() {
		String string = "";
		string += "ID: " + id + "\n";
		string += "Speed: " + speed + "\n";
		string += "Direction: " + direction + "\n";
		string += "On/Off Ramp: " + onOffRamp + "\n";
		string += "Freeway: " + freeway + "\n";
		
		return string;
	}
}
