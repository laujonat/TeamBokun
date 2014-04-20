package org.openstreetmap.gui.jmapviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import data.Car;

public class DrawCar extends Thread implements ActionListener{
	private Car car;
	private JMapViewer treeMap;
//	private MapMarkerDot carMarker;
	private Timer timer;
	
	private double currX, currY;
	private double newX, newY;
	private double slope;
	
	public DrawCar(JMapViewer treeMap, double x, double y) {
		this.treeMap = treeMap;
		currX = x;
		currY = y;
		
		timer = new Timer(3, this);
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
		treeMap.removeAllMapMarkers();
		currX += 0.00001;
		currY += 0.00001;
		treeMap.addMapMarker(new MapMarkerDot(currX, currY));
		

		
	}
	
	
	
	/**
	 * carMarker = new MapMarkerDot(x, y);
	 * treeMap.addMapMarker(carMarker);
	 */
}
