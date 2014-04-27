package graph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import data.ConstructFreeways;
import data.Freeway;
import data.RoadSegment;

public class Traverse extends Thread {
	private RoadSegment		currSegment;			//	Current road segment 
	private Freeway			currFreeway;			//	Current freeway
	
	private boolean			endOfRoad;				//	True if can't continue any farther
	private boolean			reachedDestination;		//	True if this has reached the destination
	private double[]		destination;			//	Long/lat of destination
	
	private double			totalTravelTime;		//	Total travel time since the starting point
	private String			fullPath;				//	Description of the path to take
	
	public Traverse(RoadSegment start, double[] dest) {
		currSegment = start;
		currFreeway = currSegment.getFreewayObj();
		destination = dest;
		
		endOfRoad = false;
		reachedDestination = false;
		
		fullPath = "Start at " + start.getKey() + "\n";
		
		this.start();
		
		//	CALL FUNCTION TO GET MIRROR ROAD SEGMENT AND CLONE HERE AND SET THE CLONE RUNNING IN OPPOSITE DIRECTION
		RoadSegment mirrorSeg = ConstructFreeways.getPolarOppositeRoadSeg(currFreeway, currSegment);
		Traverse clone = new Traverse(this);
		clone.currSegment = mirrorSeg;
		clone.currFreeway = mirrorSeg.getFreewayObj();
		clone.start();
	}
	
	//	Copy constructor
	public Traverse(Traverse t) {
		currSegment = t.currSegment;
		currFreeway = t.currFreeway;
		endOfRoad = false;
		reachedDestination = false;
		destination = t.destination;
		totalTravelTime = t.totalTravelTime;
		fullPath = t.fullPath;
	}
	
	//	Traverse along the freeway
	@Override
	public void run() {
		//	Loop until a node is reached or end of road is reached
		while(!currSegment.isA_Node() && currFreeway.getNextRoadSeg(currSegment) != null) {
			try {
				RoadSegment nextSeg = currFreeway.getNextRoadSeg(currSegment);
				double distance = getDistanceToRoadSeg(nextSeg);
				double minSpeed = currSegment.getMinSpeed();
				
				//	Add the travel time for this road segment
				totalTravelTime += distance/minSpeed;
				
				currSegment = nextSeg;
				fullPath += "Go to " + currSegment.getKey() + "\n";
				
				//	Check if reached destination
				if(currSegment.getX() == destination[0] && currSegment.getY() == destination[1]) {
					reachedDestination = true;
					return;
				}
			}
			catch(Exception e) {
				System.err.println("Error:");
				e.printStackTrace();
			}
		}
		
		//	Get all options if reached a Node
		if(currSegment.isA_Node()) {
			Node node = currFreeway.getNodeAtSegment(currSegment);
			if(node == null) {
				System.err.println("Unknown error. Killing this traversal unit...");
				return;
			}
			
			//	Create clones and send them along all paths
			ArrayList<Freeway> freeways = node.getOptions(currFreeway);
			for(int i = 0; i < freeways.size(); i++) {
				
			}
		}
	}
	
	//	Gets distance from current road segment to next one
	private double getDistanceToRoadSeg(RoadSegment rs) throws Exception {
		String start = currSegment.getX() + "," + currSegment.getY();
		String end = rs.getX() + "," + rs.getY();
		
		BufferedReader br = null;
		try {
			URL                url; 
		    URLConnection      connection; 
		    DataInputStream    dataStreamer;
	
		    url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + start + "&destination=" + end + "&sensor=false");
		    connection = url.openConnection(); 
		    connection.setDoInput(true); 
		    connection.setUseCaches(false);
	
		    dataStreamer = new DataInputStream(connection.getInputStream()); 
		    br = new BufferedReader(new InputStreamReader(dataStreamer));
			
		    String line;
		    String feedback = "";
		    while((line = br.readLine()) != null)
		    	feedback += line;
		    feedback = feedback.replaceAll("\\s+", "");
		    
		    return parseManually(feedback);
		}
		catch(MalformedURLException e) { throw e; }
		catch(IOException e) { throw e; }
	}
	
	//	Manually parses JSON file for distance
	private double parseManually(String json) {
		for(int i = 0; i < json.length(); i++) {
			//	Search for distance
			if((i+19) < json.length() && json.substring(i, i+19).equals("distance\":{\"text\":\"")) {
				i = i+19;
				int j = i;
				while(json.substring(j, j+1).matches("[0-9]|\\.")) { j++; }
				return Double.parseDouble(json.substring(i, j));
			}
		}
		
		return 0;
	}

	
	//	GETTERS AND SETTERS
	public RoadSegment getCurrSegment() { return currSegment; }

	public void setCurrSegment(RoadSegment currSegment) { this.currSegment = currSegment; }
	

	public Freeway getCurrFreeway() { return currFreeway; }

	public void setCurrFreeway(Freeway currFreeway) { this.currFreeway = currFreeway; }
	

	public boolean isEndOfRoad() { return endOfRoad; }

	public void setEndOfRoad(boolean endOfRoad) { this.endOfRoad = endOfRoad; }
	

	public boolean isReachedDestination() { return reachedDestination; }

	public void setReachedDestination(boolean reachedDestination) { this.reachedDestination = reachedDestination; }
	

	public double[] getDestination() { return destination; }

	public void setDestination(double[] destination) { this.destination = destination; }
	

	public double getTotalTravelTime() { return totalTravelTime; }

	public void setTotalTravelTime(double totalTravelTime) { this.totalTravelTime = totalTravelTime; }
}
