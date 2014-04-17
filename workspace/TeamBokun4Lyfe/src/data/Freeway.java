package data;

import java.util.ArrayList;

public class Freeway
{

	ArrayList<RoadSegment> segments; 
	int name;
	int numRoadSeg;
	
	public Freeway(String name)
	{
		
		setName(name);
		segments = new ArrayList<RoadSegment>();
		
	}
	
	public int getName() { return name; }
	public void setName(String freeway) { name = Integer.parseInt(freeway); }
	
	public RoadSegment getRoadSegAt(int i) { return segments.get(i); }
	public void addRoadSeg(RoadSegment rs) { segments.add(rs); }
	
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
	
}
