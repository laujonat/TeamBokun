package data;

import java.util.Calendar;


public class Car {
	
	private int			id;
	private double		speed;
	private Freeway		freeway;
	private RoadSegment roadSegment;
	private int 		hour;
	
	public Car(int id, double speed, Freeway freeway, RoadSegment rs) {
		this.id = id;
		this.speed = speed;
		this.freeway = freeway;
		this.roadSegment = rs;
		Calendar time = Calendar.getInstance();
		hour = time.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public double getSpeed() { return speed; }
	public void setSpeed(double speed) { this.speed = speed; }
	
	public String getFreeway() { return freeway.getName(); }
	public void setFreeway(Freeway freeway) { this.freeway = freeway; }
	
	public RoadSegment getRoadSeg() { return roadSegment; }
	public void setRoadSeg(RoadSegment rs) { roadSegment = rs; }
	
	public int getHour() { return hour; }
	// no setter for time as each car is only accounted for at 1 instance of time
	
	@Override
	public String toString() {
		String string = "";
		string += "ID: " + id + "\n";
		string += "Speed: " + speed + "\n";
//		string += "Direction: " + direction + "\n";
//		string += "On/Off Ramp: " + onOffRamp + "\n";
//		string += "Freeway: " + roadSegment.getFreewayName() + "\n";
		string += "Hour: " + hour + "\n";
		
		return string;
	}
}
