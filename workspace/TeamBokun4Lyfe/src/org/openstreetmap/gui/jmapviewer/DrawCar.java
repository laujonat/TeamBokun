package org.openstreetmap.gui.jmapviewer;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import data.Car;
import data.RoadSegment;

public class DrawCar extends Thread implements ActionListener{
	private Car car;
	private JMapViewer treeMap;
	private MapMarkerDot carMarker;
	private Timer timer;
	
	private double currX, currY;
	private double newX, newY;
	private int zoomLevel;
	private double slope;
	private List markers = null;
    private static int TILE_SIZE = 256;
    public boolean flag;
    
    
    private double incrementX;
    private double incrementY;
    private double convert;
    
    double factor = 1e5;
  
	
	public DrawCar(JMapViewer treeMap, double x, double y, Car car) {
		this.treeMap = treeMap;
		double factor = 1e5;
		double roundEndX = Math.round(x * factor) / factor;
    	double roundEndY = Math.round(y * factor) / factor;
		currX = roundEndX;
		currY = roundEndY;
		this.car = car;
//		this.numSegments = car.getFreewayObj().getNumRoadSeg();
		markers = treeMap.getMapMarkerList();
		flag = false;
		timer = new Timer(1, this);
	}
	
	// This function converts decimal degrees to radians
	private double deg2rad(double deg) {
	      return (deg * Math.PI / 180.0);
	}
	// This function converts radians to decimal degrees
	private double rad2deg(double rad) {
	      return (rad * 180.0 / Math.PI);
	}
	// http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
	public double distance(double lat1, double lon1, double lat2, double lon2) {
	      double theta = lon1 - lon2;
	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	      return (dist);
	}
	
	public void destination(double newX, double newY) {
		this.newX = newX;
		this.newY = newY;	
	}
	
	@Override
	public void run() {
		timer.stop();
//		double factor = 1e5;

		double calcDistance = distance(currX, currY, newX, newY);
		
//		double roundDistance = Math.round(calcDistance * factor)/factor;
		double roundDistance = calcDistance;
//    	System.out.println("THIS IS DISTANCE " + roundDistance);

//    	double roundSpeed = Math.round(car.getSpeed() * factor)/factor;
		double roundSpeed = car.getSpeed();
    	
//    	double time = Math.round((roundDistance/roundSpeed) * factor)/factor; // minutes
		double time = roundDistance/roundSpeed;
    	
//    	double convertToMili =  Math.round((time * 60000) * factor)/factor; // milli
		double convertToMili = time*60000;
	
//    	convert = Math.round((convertToMili/5) * factor) / factor;
		convert = convertToMili/5;
//    	double yDistance = (Math.round((currY - newY) * factor) / factor);
//		double xDistance = (Math.round((currX - newX) * factor) / factor);
    	double yDistance = currY - newY;
    	double xDistance = currX - newX;
		
//		incrementY = (Math.round((yDistance/convert) * factor) / factor); 
//		incrementX = (Math.round((xDistance/convert) * factor) / factor); 
    	incrementY = yDistance/convert;
    	incrementX = xDistance/convert;
//    	System.out.println("THIS IS CONVERTED " + convert);
		timer.start();
	}
	
	public void setX(double x) {
		this.currX = x;
	}
	
	public void setY(double y) {
		this.currY = y;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int k = 0; k < markers.size(); k++) {
			carMarker = (MapMarkerDot) markers.get(k);
			treeMap.removeMapMarker(carMarker);
//			System.out.println("HERE");
		}
		
		double testCurrX = currX+0.01;
		double testCurrY = currY+0.01;
		double testCurrXM = currX-0.01;
		double testCurrYM = currY-0.01;
//		if(newX == testCurrX && newY == testCurrY) {
			
		if((newX >= testCurrXM && newX <= testCurrX) && (newY >= testCurrYM && newY <= testCurrY)) {
			
			System.out.println("HERE");
//				if(carMarker.getLat() == currX && carMarker.getLon() == currY) {
					  
//					setX(newX);
//					setY(newY);
					RoadSegment nextSeg = car.getFreewayObj().getNextRoadSeg(car.getRoadSeg());
					if(nextSeg != null) {
						car.setRoadSeg(nextSeg);
						destination(nextSeg.getX(), nextSeg.getY());
						timer.stop();
						run();
						return;
					} else {
						timer.stop();
					}
					
					
					
//					break;                                              
////	           }
//			}
//			timer.stop();
		} 
		
		
		
//		if(newX != currX) {
			currY -= incrementY;
			currX -= incrementX;
			

//		}
		
//		currY -= 0.00001;
//		currX -= 0.00001;
//		treeMap.removeAllMapMarkers();
//		currY -= incrementY;
//		currX -= incrementX;
//		double roundY = Math.round((currY) * factor) / factor;
//		double roundX = Math.round((currX) * factor) / factor;
		double roundY = currY;
		double roundX = currX;
		treeMap.addMapMarker(new MapMarkerDot(currX, currY));
		
	}
	
	/**
	 * carMarker = new MapMarkerDot(x, y);
	 * treeMap.addMapMarker(carMarker);
	 */
}
