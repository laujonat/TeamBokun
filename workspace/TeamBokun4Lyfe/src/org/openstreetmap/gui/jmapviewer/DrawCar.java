package org.openstreetmap.gui.jmapviewer;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import data.Car;

public class DrawCar extends Thread implements ActionListener{
	private Car car;
	private JMapViewer treeMap;
	private MapMarkerDot carMarker;
	private Timer timer;
	
	private double currX, currY;
	private double newX, newY;
	private int zoomLevel;
	private double slope;
	public static final int MAX_ZOOM = 17;
    public static final int MIN_ZOOM = 11;
    public static final double MAX_LAT = 85.05112877980659;
    public static final double MIN_LAT = -85.05112877980659;
    private static int TILE_SIZE = 256;
    int newYDestination;
	int newXDestination;
	int currentY;
	int currentX;
	

	
	protected Point center;
	
	public DrawCar(JMapViewer treeMap, double x, double y) {
		this.treeMap = treeMap;
		currX = x;
		currY = y;
		
		timer = new Timer(3, this);
	}
	
	public void destination(double newX, double newY, int zoomLevel) {
		this.newX = newX;
		this.newY = newY;
		this.zoomLevel = zoomLevel;
		
	}
	
	@Override
	public void run() {
		treeMap.addMapMarker(new MapMarkerDot(currX, currY));
		
		//	Get destination
//		double destX = car.getFreewayObj().getNextRoadSeg(car.getRoadSeg()).getX();
//		double destY = car.getFreewayObj().getNextRoadSeg(car.getRoadSeg()).getY();
		
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * OsmMercator - Scale class
		 * 
		 */
		if(newX == currX && newY == currY) {
			timer.stop();
		}
		
		
		
//		treeMap.removeMapMarker(treeMap);

		
		List markers = treeMap.getMapMarkerList();
		
		for(int k = 0; k < markers.size(); k++) {
			carMarker = (MapMarkerDot) markers.get(k);
			
			if((carMarker.getLat() == currX) && (carMarker.getLon() == currY)){                                 
                treeMap.removeMapMarker(carMarker);                       
                break;                                              
            }
		}
//		currX += 0.00001;
//		currY += 0.00001;
		
		if(newX != currX) {
			newYDestination = OsmMercator.LatToY(newY, zoomLevel);
			System.out.println(newYDestination + " Y DEST");
			newXDestination = OsmMercator.LonToX(newX, zoomLevel);
			System.out.println(newXDestination + " X DEST");
			currentY = OsmMercator.LatToY(currY, zoomLevel);
			System.out.println(currentY + " Y CURRENT");
			currentX = OsmMercator.LonToX(currX, zoomLevel);
			System.out.println(currentX + " X CURRENT");
			
//			System.out.println(currentY + currentX + " ASDASDSD " + newYDestination);
			slope = (newYDestination - currentY) / (newXDestination - currentX);
			System.out.println(slope + " THIS IS SLOPE");
			//	Moving to the left
			if(newXDestination < currentX) {
				currentX--;
				currentY -= slope;
			}
			
			//	Moving to the right
			else if(newXDestination > currentX) {
				currentX++;
				currentY += slope;
			}
		}
		else {
			if(currentY < newYDestination)
				currentY++;
			else
				currentY--;
		}
		
		
//		
//		newX  = OsmMercator.XToLon(currentX, zoomLevel);
//		newY = OsmMercator.YToLat(currentY, zoomLevel);
		
		
		treeMap.addMapMarker(new MapMarkerDot(currX, currY));
		
	}
	

	
	public static double XToLon(int aX, int aZoomlevel) {
        return ((360d * aX) / getMaxPixels(aZoomlevel)) - 180.0;
    }
	
	public static int falseEasting(int aZoomlevel) {
        return getMaxPixels(aZoomlevel) / 2;
    }
	
	public static int falseNorthing(int aZoomlevel) {
        return (-1 * getMaxPixels(aZoomlevel) / 2);
    }
	
	public static double radius(int aZoomlevel) {
	        return (TILE_SIZE * (1 << aZoomlevel)) / (2.0 * Math.PI);
	}
	
    public static double YToLat(int aY, int aZoomlevel) {
        aY += falseNorthing(aZoomlevel);
        double latitude = (Math.PI / 2) - (2 * Math.atan(Math.exp(-1.0 * aY / radius(aZoomlevel))));
        return -1 * Math.toDegrees(latitude);
    }
	
    public static int getMaxPixels(int aZoomlevel) {
        return TILE_SIZE * (1 << aZoomlevel);
    }
    
    public static int LonToX(double aLongitude, int aZoomlevel) {
        int mp = getMaxPixels(aZoomlevel);
        int x = (int) ((mp * (aLongitude + 180l)) / 360l);
        x = Math.min(x, mp - 1);
        return x;
    }
	
	  public static int LatToY(double aLat, int aZoomlevel) {
	        if (aLat < MIN_LAT)
	            aLat = MIN_LAT;
	        else if (aLat > MAX_LAT)
	            aLat = MAX_LAT;
	        double sinLat = Math.sin(Math.toRadians(aLat));
	        double log = Math.log((1.0 + sinLat) / (1.0 - sinLat));
	        int mp = getMaxPixels(aZoomlevel);
	        int y = (int) (mp * (0.5 - (log / (4.0 * Math.PI))));
	        y = Math.min(y, mp - 1);
	        return y;
	    }
	
	
	
	/**
	 * carMarker = new MapMarkerDot(x, y);
	 * treeMap.addMapMarker(carMarker);
	 */
}
