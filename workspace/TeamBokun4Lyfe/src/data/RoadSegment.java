package data;

import java.util.ArrayList;

public class RoadSegment
{
//
	private int ID;
	private String key;
	private double[] coordinates;
//	private double[] endRampCoordinates;
	private double minSpeed;
	private Freeway freeway;
	private boolean isA_Node;
	private ArrayList<Car> currentCars;
	
	public RoadSegment(String key, int ID, Freeway freeway)
	{
		setKey(key);
		this.ID = ID;
		this.freeway = freeway;
		coordinates = new double[2];
//		endRampCoordinates = new double[2];
		
		minSpeed = (double)9001;
		isA_Node = false;
		determineNodeStatus();
		currentCars = new ArrayList<Car>();
	}
	
	public RoadSegment(RoadSegment rs) {
		this.ID = rs.ID;
		this.key = rs.key;
		this.coordinates = rs.coordinates;
//		this.endRampCoordinates = rs.endRampCoordinates;
		this.minSpeed = rs.minSpeed;
		this.freeway = rs.freeway;
		//is the following right? vvvvv
		isA_Node = false;
		determineNodeStatus();
		currentCars = new ArrayList<Car>();
	}
	
	public int getID() { return ID; }
	public void setID(int ID) { this.ID = ID; }
	
	public String getKey() { return key; }
	public void setKey(String key) { this.key = key; }
	
	public double getX() { return coordinates[0]; }
	public void setX(double x) { coordinates[0] = x; }
	
	public double getY() { return coordinates[1]; }
	public void setY(double y) { coordinates[1] = y; }
	
//	public double getX2() { return endRampCoordinates[0]; }
//	public void setX2(double x) { endRampCoordinates[0] = x; }
//	
//	public double getY2() { return endRampCoordinates[1]; }
//	public void setY2(double y) { endRampCoordinates[1] = y; }
	
	public double getMinSpeed() { return minSpeed; }
	public void setMinSpeed(double s) { minSpeed = s; }
	
	public String getFreewayName() { return (freeway.getName()); }
	public Freeway getFreewayObj() { return freeway; }
	
	public void setFreeway(Freeway f)
	{
		freeway = f;
	}
	
	public void resetMinSpeedTO_OVER_9000()
	{
		setMinSpeed((double)9001);
	}
	
	public void checkMinSpeed(double speed)
	{
		if(speed < getMinSpeed())
		{
			setMinSpeed(speed);
		}
	}
	
	public void determineNodeStatus()
	{
		for(int i = 0; i < freeway.getNumNodes(); i++)
		{
			if(freeway.getNodes().get(i).getLatitude() == getX())
				if(freeway.getNodes().get(i).getLongitude() == getY())
					isA_Node = true;
		}
	}
	
	public boolean isA_Node()
	{
		return isA_Node;
	}
	
	public void addCar(Car c) { currentCars.add(c); }
	
	public void removeCar(Car c)
	{
		for(int i = 0 ; i < currentCars.size(); i++)
		{
			if(c.equals(currentCars.get(i)))
			{
				currentCars.remove(i);
				break;
			}
		}
	}
	
	public int numCarsCurrently() { return currentCars.size(); }
	
	public void determineMinSpeed()
	{
		if(currentCars.isEmpty())
			resetMinSpeedTO_OVER_9000();
		else
		{
			minSpeed = (currentCars.get(0)).getSpeed();
			for(int i = 1; i < currentCars.size(); i++)
			{
				if((currentCars.get(i)).getSpeed() < minSpeed)
				{
					minSpeed = (currentCars.get(i)).getSpeed();
				}
			}
		}
	}
	
	public boolean noDrivers()
	{
		if(currentCars.isEmpty()) { return true; }
		return false;
	}
	
	
	@Override
	public String toString() {
		String string = "";
		string += "ID: " + ID + "\n";
		string += "key: " + key + "\n";
		string += "Lat/Long: " + coordinates[0] + ", " + coordinates[1] + "\n";
//		string += "End Lat/Long: " + endRampCoordinates[0] + ", " + endRampCoordinates[1] + "\n";
		string += "minSpeed: " + minSpeed + "\n";
		string += "freeway: " + freeway.getName() + "\n";
		
		return string;
	}
	
}
