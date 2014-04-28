package data;

import graph.Node;

import java.util.ArrayList;
import java.util.Calendar;

import central.BokunCentral;

public class Freeway
{
//
	private ArrayList<RoadSegment> segments; 
	//int name;
	private String name;
	private int numRoadSeg;
	private ArrayList<Double> speeds;
	private int carHour;
	private ArrayList<Node> nodes;
	
	public Freeway(String name)
	{
		
		setName(name);
		segments = new ArrayList<RoadSegment>();
		speeds = new ArrayList<Double>();
		Calendar time = Calendar.getInstance();
		carHour = time.get(Calendar.HOUR_OF_DAY);
		nodes = new ArrayList<Node>();
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
			updateDatabase(calculateAvgSpeed(), carHour);
		}
		else //NEW HOUR! YAY
		{
//			System.out.println(calculateAvgSpeed() + " hour: " + carHour);
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
	
	public double calculateAvgSpeed()
	{
		double sum = (double) 0;
		double avg = (double) 0;
		for(int i = 0; i < speeds.size(); i++)
		{
			sum += speeds.get(i);
		}
		avg = (sum / ((double) speeds.size()));
		return avg;
	}
	
	public void updateDatabase(double avg, int hour)
	{
		BokunCentral.updateDatabase(avg, hour, name);
	}
	
	public int getNumRoadSeg() { return (segments.size()); }
	
	public RoadSegment getNextRoadSeg(RoadSegment rs) 
	{ 
		int currentID = rs.getID();
		if((getNumRoadSeg() - 1) == currentID)
		{
//			System.out.println("Current Road Segment is the last one.");
			return null;
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
//			System.out.println("Current Road Segment is the first one.");
			return null;
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
	
	public void addNodes(double lat, double longi, ArrayList<Freeway> edges)
	{
		Node temp = new Node(lat, longi, edges);
		nodes.add(temp);
	}
	
	
	public void addNodes(Node addMe) { nodes.add(addMe); }
	
	
	public ArrayList<Node> getNodes(){ return nodes; }
	
	public int getNumNodes()
	{
		return nodes.size();
	}
	
//	Return the Node at a road segment, if there is one
	public Node getNodeAtSegment(RoadSegment rs) {
		for(int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			if(n.getLatitude() == rs.getX() && n.getLongitude() == rs.getY())
				return n;
		}
		
		return null;
	}
	
}
