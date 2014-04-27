package graph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import data.Freeway;
import data.RoadSegment;

public class Traverse extends Thread {
	RoadSegment		currSegment;			//	Current road segment 
	Freeway			currFreeway;			//	Current freeway
	
	boolean			endOfRoad;				//	True if can't continue any farther
	boolean			reachedDestination;		//	True if this has reached the destination
	double[]		destination;			//	Long/lat of destination
	
	double			totalTravelTime;		//	Total travel time since the starting point
	
	public Traverse(RoadSegment start, double[] dest) {
		currSegment = start;
		currFreeway = currSegment.getFreewayObj();
		destination = dest;
		
		endOfRoad = false;
		reachedDestination = false;
	}
	
	//	Gets distance from current road segment to next one
	public double getDistanceRoadSeg(RoadSegment rs) {
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
		    
		    parseManually(feedback);
		}
		catch(MalformedURLException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
	}
	
//	Manually parses JSON file for necessary information
	private void parseManually(String json) {
		boolean distanceFound = false;
		
		for(int i = 0; i < json.length(); i++) {
			//	Search for distance
			if((i+19) < json.length() && json.substring(i, i+19).equals("distance\":{\"text\":\"") && !distanceFound) {
				distanceFound = true;
				i = i+19;
				int j = i;
				while(json.substring(j, j+1).matches("[0-9]|\\.")) { j++; }
				distance = Double.parseDouble(json.substring(i, j));
			}
		}
		
		//	Set timeToDestAtSpeedLimit
		double time = distance/65;	//	Assume speed limit is 65 mph
		time *= 60;	//	Convert to minutes
		if(time > 60) {
			timeToDestAtSpeedLimit[0] = (int) (time % 60);
			timeToDestAtSpeedLimit[1] = (int)Math.round(time/60);
		}
	}
}
