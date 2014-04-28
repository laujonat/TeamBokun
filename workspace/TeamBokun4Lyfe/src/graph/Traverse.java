package graph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import central.BokunCentral;
import data.ConstructFreeways;
import data.Freeway;
import data.RoadSegment;

public class Traverse extends Thread {
	private RoadSegment		currSegment;			//	Current road segment 
	private Freeway			currFreeway;			//	Current freeway
	
	private boolean			reachedDestination;		//	True if this has reached the destination
	private double[]		destination;			//	Long/lat of destination
	
	private double			totalTravelTime;		//	Total travel time since the starting point
	private double			totalDistance;			//	Total distance travelled
	private String			fullPath;				//	Description of the path to take
	
	public Traverse(RoadSegment start, double[] dest) {
		currSegment = start;
		currFreeway = currSegment.getFreewayObj();
		destination = dest;
		
		reachedDestination = false;
		totalTravelTime = 0;
		totalDistance = 0;
		
		fullPath = "Start at " + start.getKey() + " and travel down the " + currFreeway.getName() + "\n";
		
		this.start();
		
		//	CALL FUNCTION TO GET MIRROR ROAD SEGMENT AND CLONE HERE AND SET THE CLONE RUNNING IN OPPOSITE DIRECTION
		RoadSegment mirrorSeg = ConstructFreeways.getPolarOppositeRoadSeg(currFreeway, currSegment);
		Traverse clone = new Traverse(this);
		clone.currSegment = mirrorSeg;
		clone.currFreeway = mirrorSeg.getFreewayObj();
		clone.start();
		//	Add to the list of traverse units
		BokunCentral.traverseUnits.add(clone);
	}
	
	//	Copy constructor
	public Traverse(Traverse t) {
		currSegment = t.currSegment;
		currFreeway = t.currFreeway;
		reachedDestination = false;
		totalDistance = t.totalDistance;
		destination = t.destination;
		totalTravelTime = t.totalTravelTime;
		fullPath = t.fullPath;
	}
	
	//	Traverse along the freeway
	@Override
	public void run() {
//		synchronized(BokunCentral.jsonParser) {
//			System.out.println("\nNodes on freeway " + currFreeway.getName());
//			for(int i = 0; i < currFreeway.getNodes().size(); i++) {
//				Node n = currFreeway.getNodes().get(i);
//				System.out.print("(" + n.getLatitude() + ", " + n.getLongitude() + ")\t");
//				
//				RoadSegment rs = currFreeway.getSegmentAtNode(n);
//				if(rs.isA_Node())
//					System.out.println(rs.getKey() + ": (" + rs.getX() + ", " + rs.getY() + ")");
//			}
//		}
		
		//	Loop until a node is reached or end of road is reached
		while(!currSegment.isA_Node() && currFreeway.getNextRoadSeg(currSegment) != null) {
			try {
				RoadSegment nextSeg = currFreeway.getNextRoadSeg(currSegment);
				double distance = getDistanceToRoadSeg(nextSeg);
				double minSpeed = currSegment.getMinSpeed();
				
				//	Add the travel time for this road segment
				totalTravelTime += distance/minSpeed;
				totalDistance += distance;
				
				currSegment = nextSeg;
				
				//	Check if reached destination
				if(currSegment.getX() == destination[0] && currSegment.getY() == destination[1]) {
					fullPath += "Arrive at " + currSegment.getKey();
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
				if(!BokunCentral.traverseUnits.remove(this))
					System.err.println("Something went wrong with traverse unit removal");
				return;
			}
			
//			System.out.println("On Freeway " + currFreeway.getName() + " with options:");
//			for(int i = 0; i < node.getEdges().size(); i++)
//				System.out.println(node.getEdges().get(i).getName());
			
			//	Create clones and send them along all paths
			ArrayList<Freeway> freeways = node.getOptions(currFreeway);
//			System.out.println("And options: ");
			for(int i = 0; i < freeways.size(); i++) {
				Freeway f = freeways.get(i);
//				System.out.println(f.getName());
				
				Traverse t = new Traverse(this);
				t.currSegment = f.getSegmentAtNode(node);
				t.currFreeway = f;
				t.fullPath += "Turn onto the " + f.getName() + "\n";
				
				//	Advance to next segment before proceeding with run function
				try {
					RoadSegment nextSeg = t.currFreeway.getNextRoadSeg(t.currSegment);
					double distance = getDistanceToRoadSeg(nextSeg);
					double minSpeed = t.currSegment.getMinSpeed();
					
					//	Add the travel time for this road segment
					t.totalTravelTime += distance/minSpeed;
					t.totalDistance += distance;
					
					t.currSegment = nextSeg;
					
					//	Check if reached destination
					if(t.currSegment.getX() == t.destination[0] && t.currSegment.getY() == t.destination[1]) {
						t.fullPath += "Arrive at " + t.currSegment.getKey();
						t.reachedDestination = true;
						return;
					}
				}
				catch(Exception e) {
					System.err.println("Error:");
					e.printStackTrace();
				}
				
				t.start();
				BokunCentral.traverseUnits.add(t);
			}
		}
		
		//	Remove this unit
		if(!BokunCentral.traverseUnits.remove(this))
			System.err.println("Something went wrong with traverse unit removal");
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

	@Override
	public String toString() {
		String line = "Travelled " + totalDistance + " miles in " + totalTravelTime + " hours\n";
		line += "Directions:\n" + fullPath;
		return line;
	}
	
	//	GETTERS AND SETTERS
	public RoadSegment getCurrSegment() { return currSegment; }
	public void setCurrSegment(RoadSegment currSegment) { this.currSegment = currSegment; }

	public Freeway getCurrFreeway() { return currFreeway; }
	public void setCurrFreeway(Freeway currFreeway) { this.currFreeway = currFreeway; }

	public boolean isReachedDestination() { return reachedDestination; }
	public void setReachedDestination(boolean reachedDestination) { this.reachedDestination = reachedDestination; }
	
	public double[] getDestination() { return destination; }
	public void setDestination(double[] destination) { this.destination = destination; }
	
	public double getTotalTravelTime() { return totalTravelTime; }
	public void setTotalTravelTime(double totalTravelTime) { this.totalTravelTime = totalTravelTime; }
}
