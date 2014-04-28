package data;

import java.util.Calendar;
//

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
		//DOES THE FOLLOWING WORK?!?!? vvvvv
		rs.addCar(this);
		this.latitude = rs.getX();
		this.longitude = rs.getY();
		Calendar time = Calendar.getInstance();
		hour = time.get(Calendar.HOUR_OF_DAY);
		rs.checkMinSpeed(this.speed);
		freeway.checkTheHour(hour, speed);
		
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public double getSpeed() { return speed; }
	public void setSpeed(double speed)
	{ 
		this.speed = speed;
		roadSegment.determineMinSpeed();
	}
	
	public String getFreeway() { return freeway.getName(); }
	public Freeway getFreewayObj() { return freeway; }
	public void setFreeway(Freeway freeway) { this.freeway = freeway; }
	
	public RoadSegment getRoadSeg() { return roadSegment; }
	//check this method
	public void setRoadSeg(RoadSegment rs)
	{ 
		roadSegment.removeCar(this);
		if((this.speed == roadSegment.getMinSpeed()) && roadSegment.noDrivers())
		{
			roadSegment.resetMinSpeedTO_OVER_9000();
		}
		else if(this.speed == roadSegment.getMinSpeed()) //case that there are other drivers
		{
			roadSegment.determineMinSpeed();
		}
		
		roadSegment = rs; 
		roadSegment.checkMinSpeed(this.speed);
	}
	
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
