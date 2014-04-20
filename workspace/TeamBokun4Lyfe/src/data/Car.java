package data;

import java.util.Calendar;


public class Car {
	
	private int			id;
	private double		speed;
	private Freeway		freeway;
	private RoadSegment roadSegment;
	private int 		hour;
	private double latitude;
	private double longitude;
	
	public Car(int id, double speed, Freeway freeway, RoadSegment rs) {
		this.id = id;
		this.speed = speed;
		this.freeway = freeway;
		this.roadSegment = rs;
		this.latitude = rs.getX();
		this.longitude = rs.getY();
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
	
	public double getLatitude() { return latitude; }
	public void setLatitude(double latitude) { this.latitude = latitude; }
	
	public double getLongitude() { return longitude; }
	public void setLongitude(double longitude) { this.longitude = longitude; }
	
	@Override
	public String toString() {
		String string = "";
		string += "ID: " + id + "\n";
		string += "Speed: " + speed + "\n";
		string += "On/Off Ramp: " + roadSegment.getKey() + "\n";
		string += "Freeway: " + roadSegment.getFreewayName() + "\n";
		string += "Hour: " + hour + "\n";
		
		return string;
	}
}
