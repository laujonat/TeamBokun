package data;

import java.util.ArrayList;
import java.util.Calendar;

public class Freeway
{

	ArrayList<RoadSegment> segments; 
	//int name;
	String name;
	int numRoadSeg;
	ArrayList<Double> speeds;
	int carHour;
	
	public Freeway(String name)
	{
		
		setName(name);
		segments = new ArrayList<RoadSegment>();
		speeds = new ArrayList<Double>();
		Calendar time = Calendar.getInstance();
		carHour = time.get(Calendar.HOUR_OF_DAY);
	}
	
	public String getName() { return name; }
	public void setName(String freeway) { this.name = freeway; }
	//{ this.name = Integer.parseInt(freeway); }
	
	public RoadSegment getRoadSegAt(int i) { return segments.get(i); }
	public void addRoadSeg(RoadSegment rs) { segments.add(rs); }
	
	
	public void checkTheHour(int hour, double speed)
	{
		if(carHour == hour)
		{
			addSpeed(speed);
		}
		else //NEW HOUR! YAY
		{
			updateDatabase(calculateAvgSpeed(), carHour);
			speeds.clear();
			if(carHour == 23) { carHour = 0; }
			else { carHour++; }
		}
	}
	
	public void addSpeed(Double speed)
	{
		speeds.add(speed);
	}
	
	public Double calculateAvgSpeed()
	{
		Double sum = (double) 0;
		Double avg = (double) 0;
		for(int i = 0; i < speeds.size(); i++)
		{
			sum += speeds.get(i);
		}
		avg = (sum / ((double) speeds.size()));
		return avg;
	}
	
	public void updateDatabase(double avg, int hour)
	{
		//CODE HERE
	}
	
	public int getNumRoadSeg() { return (segments.size()); }
	
	public RoadSegment getNextRoadSeg(RoadSegment rs) 
	{ 
		int currentID = rs.getID();
		if((getNumRoadSeg() - 1) == currentID)
		{
			System.out.println("Current Road Segment is the last one.");
			return getRoadSegAt((currentID));
		}
		else
		{
			return getRoadSegAt((currentID + 1));
		}
	}
	public RoadSegment getPrevRoadSeg(RoadSegment rs)
	{
		int currentID = rs.getID();
		
		if(getNumRoadSeg() == 0)
		{
			System.out.println("Current Road Segment is the first one.");
			return getRoadSegAt(currentID);
		}
		else
		{
			return getRoadSegAt((currentID - 1)); 
		}
	}
	
//	public RoadSegment assignRoadSegment(String key)
//	{
//		for(int i = 0; i < )
//	}
	
}
