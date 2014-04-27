package graph;

import java.util.ArrayList;

import data.Freeway;
import data.RoadSegment;


public class Node 
{

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
	
	
	public ArrayList<Freeway> getOptions(Freeway fwy)
	{
		
		ArrayList<Freeway> options = new ArrayList<Freeway>();
		
		for(int i = 0; i < edges.size(); i++)
		{
			if(!((edges.get(i)).getName()).equalsIgnoreCase(fwy.getName()))
			{
				options.add(edges.get(i));
			}
		}
		
		return options;
		
	}
	
}
