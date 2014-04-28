package graph;

import java.util.ArrayList;

import data.Freeway;
import data.RoadSegment;


public class Node 
{
//
	private ArrayList<Freeway> edges;
	private double latitude;
	private double longitude;
	
	public Node(double lat, double longi, ArrayList<Freeway> edges)
	{
		setEdges(edges);
		setLatitude(lat);
		setLongitude(longi);
	}
	
	
	
	public void setLatitude(double lat) { this.latitude = lat; }
	public double getLatitude() { return latitude; }
	
	public void setLongitude(double longi) { this.longitude = longi; }
	public double getLongitude() { return longitude; }
	
	public void setEdges(ArrayList<Freeway> edges) { this.edges = edges; }
	public ArrayList<Freeway> getEdges() { return edges; }
	
	//this might need editing hurrrr
	public ArrayList<Freeway> getOptions(Freeway fwy)
	{
		
		ArrayList<Freeway> options = new ArrayList<Freeway>();
		
		for(int i = 0; i < edges.size(); i++)
		{
			if(fwy.getName().equalsIgnoreCase("N101"))
			{
				if(!(((edges.get(i)).getName()).equalsIgnoreCase("S101"))) { options.add(edges.get(i)); }
			}
			else if (fwy.getName().equalsIgnoreCase("S101"))
			{
				if(!(((edges.get(i)).getName()).equalsIgnoreCase("N101"))) { options.add(edges.get(i)); }
			}
			else if (fwy.getName().equalsIgnoreCase("N405"))
			{
				if(!(((edges.get(i)).getName()).equalsIgnoreCase("S405"))) { options.add(edges.get(i)); }
			}
			else if (fwy.getName().equalsIgnoreCase("S405"))
			{
				if(!(((edges.get(i)).getName()).equalsIgnoreCase("N405"))) { options.add(edges.get(i)); }
			}
			else if (fwy.getName().equalsIgnoreCase("E10"))
			{
				if(!(((edges.get(i)).getName()).equalsIgnoreCase("W10"))) { options.add(edges.get(i)); }
			}
			else if (fwy.getName().equalsIgnoreCase("W10"))
			{
				if(!(((edges.get(i)).getName()).equalsIgnoreCase("E10"))) { options.add(edges.get(i)); }
			}
			else if (fwy.getName().equalsIgnoreCase("E105"))
			{
				if(!(((edges.get(i)).getName()).equalsIgnoreCase("W105"))) { options.add(edges.get(i)); }
			}
			else //if (fwy.getName().equalsIgnoreCase("W105"))
			{
				if(!(((edges.get(i)).getName()).equalsIgnoreCase("E105"))) { options.add(edges.get(i)); }
			}
		}
		
		return options;
		
	}
	
}
