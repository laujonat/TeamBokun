package org.openstreetmap.gui.jmapviewer;

/**
 * 		Eric Dong
 * 		Michael(Bokun) Xu
 * 		Jonathan Lau
 * 		Christopher O'Brien
 * 
 * 		CS201 Final Project
 */

//License: GPL. Copyright 2008 by Jan Peter Stotz

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
//import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
//import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;
//import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import central.BokunCentral;
import central.PlotInfo;
import data.Car;
import data.ConstructFreeways;
import data.Freeway;
import data.RoadSegment;
import jsonParse.DirectionsJsonParser;

/**
 *
 * Demonstrates the usage of {@link JMapViewer}
 *
 * @author Jan Peter Stotz
 *
 */
public class Demo extends JFrame implements JMapViewerEventListener {

    private static final long serialVersionUID = 1L;

    private JMapViewerTree treeMap = null;

    private JLabel zoomLabel=null;
    private JLabel zoomValue=null;

    private JLabel mperpLabelName=null;
    private JLabel mperpLabelValue = null;
    private JPanel helpPanel;
    private JComboBox startHighwayList;
    private JComboBox endHighwayList;
    
    String[] highway = { "", "405", "105", "101", "10" };
    
    String[] the405Ramps = { "Manchester Boulevard, La Cienega Boulevard, Florence Avenue", "La Tijera Boulevard", "Howard Hughes Parkway, Sepulveda Boulevard", 
    		"Sepulveda Boulevard, Slauson Avenue (SR 90 east)", "Jefferson Boulevard", "SR 90 west (Marina Freeway)   Marina del Rey", "Culver Boulevard, Washington Boulevard   Culver City",
    		"Venice Boulevard (SR 187), Washington Boulevard", "National Boulevard", "I-10 (Santa Monica Freeway)   Santa Monica, Los Angeles", "Olympic Boulevard, Pico Boulevard",
    		"SR 2 (Santa Monica Boulevard)", "Wilshire Boulevard", "Montana Avenue", "Sunset Boulevard", "Moraga Drive", "Getty Center Drive",
    		"Mulholland Drive, Skirball Center Drive", "Ventura Boulevard, Sepulveda Boulevard, Valley Vista Boulevard",
    		"US 101 (Ventura Freeway)   Ventura, Los Angeles", "Burbank Boulevard", "Victory Boulevard   Van Nuys", "Sherman Way", "Roscoe Boulevard   Panorama City",
    		"Nordhoff Street", "Devonshire Street   Granada Hills", "SR 118 (Ronald Reagan Freeway)   Simi Valley", "San Fernando Mission Boulevard   San Fernando",
    		"Rinaldi Street   Mission Hills", "I-5 north (Golden State Freeway)   Sacramento", "I-105 (Century Freeway) El Segundo, Norwalk" };
    
    String[] the105Ramps = { "Imperial Highway west", "SR 1 (Sepulveda Boulevard) / Imperial Highway east LAX Airport", "Nash Street LAX Airport",
    		"La Cienega Boulevard, Aviation Boulevard", "I-405 (San Diego Freeway) Santa Monica, Long Beach", "Hawthorne Boulevard", 
    		"Prairie Avenue", "Crenshaw Boulevard", "Vermont Avenue", "I-110 (Harbor Freeway) Los Angeles, San Pedro", "Central Avenue", "Wilmington Avenue",
    		"Long Beach Boulevard", "I-710 (Long Beach Freeway) Pasadena, Long Beach", "Garfield Avenue", "Paramount Boulevard",
    		"SR 19 (Lakewood Boulevard)", "Bellflower Boulevard", "I-605 (San Gabriel River Freeway)", "Hoxie Avenue Norwalk Metro Station",
    		"Studebaker Road" };
   
    String[] the101Ramps = { "I-5 south (Santa Ana Freeway) Santa Ana", "Euclid Avenue", "SR 60 east (Pomona Freeway) / Soto Street Pomona", 
    		"Seventh Street", "Fourth Street", "First Street", "Cesar Chavez Avenue", "I-10 east (San Bernardino Freeway) San Bernardino", 
    		"Mission Road", "Vignes Street", "Alameda Street Union Station", "Los Angeles Street", "Spring Street", "Broadway", "Grand Avenue, Temple Street", 
    		"SR 110 north (Pasadena Freeway) / I-110 south (Harbor Freeway) Pasadena, San Pedro", "Glendale Boulevard, Echo Park Avenue, Union Avenue, Belmont Avenue", 
    		"SR 2 east (Alvarado Street)", "Rampart Boulevard, Benton Way", "Silver Lake Boulevard", "Vermont Avenue", "Melrose Avenue, Normandie Avenue", 
    		"SR 2 west (Santa Monica Boulevard) / Western Avenue", "Sunset Boulevard", "Hollywood Boulevard", "Gower Street", "Vine Street", 
    		"Cahuenga Boulevard Hollywood Bowl", "Highland Avenue (SR 170 south) Hollywood Bowl", "Barham Boulevard Burbank", "Universal Studios Boulevard", 
    		"Lankershim Boulevard Universal City", "Ventura Boulevard", "Vineland Avenue", "SR 134 east (Ventura Freeway) Pasadena", 
    		"SR 170 north (Hollywood Freeway) Sacramento", "Tujunga Avenue", "Laurel Canyon Boulevard Studio City", "Coldwater Canyon Avenue", 
    		"Woodman Avenue", "Van Nuys Boulevard", "Sepulveda Boulevard", "I-405 (San Diego Freeway) Santa Monica, Sacramento", 
    		"Haskell Avenue", "Hayvenhurst Avenue", "Balboa Boulevard Encino", "White Oak Avenue", "Reseda Boulevard", "Tampa Avenue", "Winnetka Avenue Woodland Hills", 
    		"De Soto Avenue, Serrania Avenue", "Canoga Avenue", "SR 27 (Topanga Canyon Boulevard) / Ventura Boulevard", "Shoup Avenue",
    		"Fallbrook Avenue", "Woodlake Avenue", "Mulholland Drive, Valley Circle Boulevard" };
    
    String[] the10Ramps = { "SR 1 north (Pacific Coast Highway) Oxnard", "4th Street, 5th Street", "SR 1 south (Lincoln Boulevard) to SR 2 east",
    		"20th Street", "Cloverfield Boulevard", "Centinela Avenue", "Bundy Drive", "I-405 (San Diego Freeway) Sacramento, LAX Airport, Long Beach", 
    		"National Boulevard, Overland Avenue", "National Boulevard", "Robertson Boulevard Culver City", "La Cienega Boulevard, Venice Boulevard (SR 187 west)",
    		"Fairfax Avenue, Washington Boulevard", "La Brea Avenue", "Crenshaw Boulevard", "Arlington Avenue", "Western Avenue, Normandie Avenue",
    		"Hoover Street, Vermont Avenue", "Hoover Street", "I-110 south (Harbor Freeway) / SR 110 north (Harbor Freeway) / Pico Boulevard San Pedro, Pasadena, Downtown Los Angeles",
    		"Grand Avenue", "Los Angeles Street Convention Center", "Maple Avenue", "San Pedro Street", "Central Avenue", "Alameda Street", 
    		"Mateo Street, Santa Fe Avenue", "I-5 south (Santa Ana Freeway) / SR 60 east (Pomona Freeway) Santa Ana, Pomona",
    		"Boyle Avenue", "Fourth Street", "Cesar Chavez Avenue", "I-5 north (Golden State Freeway) Sacramento", "US 101 north (Santa Ana Freeway via San Bernardino Freeway) Los Angeles, Hollywood", 
    		"State Street", "Soto Street", "City Terrace Drive", "Eastern Avenue"};
    
    private BokunCentral carData;
    private ArrayList<PlotInfo> plotInfo;
    private ArrayList<DrawCar> carList;
    private DrawCar car = null;
    private DrawCar car2 = null;
    private Car carObj;
    
    String startStr = "";
    String endStr = "";
    double[] tempStartCoord;
    double[] tempEndCoord;
    int[] tempCurrentSpeed;
    int[] tempSpeedLimit;
    double tempDistance;
    JTextArea jta;
    MapMarkerCircle end;
    MapMarkerCircle start;
    DirectionsJsonParser directions = new DirectionsJsonParser();
    //RoadSegment road;
    ConstructFreeways coordPoints = new ConstructFreeways();
    double x1, x2, y1, y2;

    /**
     * Constructs the {@code Demo}.
     */
    public Demo() {
        super("JMapViewer Demo");
        setSize(400, 400);
//        setBackground(new Color(10, 225, 215));
        treeMap = new JMapViewerTree("Zones");
//        carData = new BokunCentral();
        
        //	Wait until cars are populated
//        while(!BokunCentral.jsonParser.isUpdated()) { System.out.print(""); }

        map().addJMVListener(this);
        map().setDisplayPositionByLatLon(34.035, -118.238, 11);
        
        
//        ArrayList<Car> allCars = BokunCentral.jsonParser.getCars();
        ArrayList<DrawCar> carList = new ArrayList<DrawCar>();
        
        end = new MapMarkerCircle(0, 0, 0);
        start = new MapMarkerCircle(0, 0, 0);
        
        ConstructFreeways construct = new ConstructFreeways();
//        Car testCar = new Car(2, 34, construct.E10, construct.E10.getRoadSegAt(2));
        Car testCar = new Car(2, 60, construct.E10, construct.E10.getRoadSegAt(2));
        
        
        
        
//        for(int i = 0; i < 20; ++i) {
//        	double factor = 1e5;
////        	double startX = allCars.get(i).getLatitude();
////        	double startY = allCars.get(i).getLongitude();
//        	
//        	double roundEndX = Math.round((allCars.get(2).getLatitude()) * factor) / factor;
//        	double roundEndY = Math.round((allCars.get(2).getLongitude()) * factor) / factor;
//        	double roundEndX = Math.round((testCar.getLatitude()) * factor) / factor;
//        	double roundEndY = Math.round((testCar.getLongitude()) * factor) / factor;
        	double roundEndX = testCar.getLatitude();
        	double roundEndY = testCar.getLongitude();
//        	double roundEndXX = testCar2.getLatitude();
//        	double roundEndYY = testCar2.getLongitude();
        	
//        	car = new DrawCar(map(), roundEndX, roundEndY, allCars.get(2));
        	car = new DrawCar(map(), roundEndX, roundEndY, testCar);
//        	car2 = new DrawCar(map(), roundEndXX, roundEndYY, testCar2);
        	
////        	car.start();
//        	carList.add(car);   	
//        }
        
//        for(int i = 0; i < 20; ++i) {
////        	carList.get(i).start();
//        	double factor = 1e5;
//        	double endX = allCars.get(2).getFreewayObj().getNextRoadSeg(allCars.get(2).getRoadSeg()).getX();
//        	double endY = allCars.get(2).getFreewayObj().getNextRoadSeg(allCars.get(2).getRoadSeg()).getY();
        	double endX = testCar.getFreewayObj().getNextRoadSeg(testCar.getRoadSeg()).getX();
        	double endY = testCar.getFreewayObj().getNextRoadSeg(testCar.getRoadSeg()).getY();
//        	double endXX = testCar2.getFreewayObj().getNextRoadSeg(testCar2.getRoadSeg()).getX();
//        	double endYY = testCar2.getFreewayObj().getNextRoadSeg(testCar2.getRoadSeg()).getY();
        	try {
//        		Car c = allCars.get(2);
        		Car c = testCar;
//        		Car c2 = testCar2;
        		Freeway f = c.getFreewayObj();
//        		Freeway ff = c2.getFreewayObj();
        		RoadSegment rs = f.getNextRoadSeg(c.getRoadSeg());
//        		RoadSegment rs2 = f.getNextRoadSeg(c2.getRoadSeg());
        		if(rs == null) {
//        			continue;
        			System.out.println("ROADSEGMENT HIT NULL!");
        		}
//        		double roundEndX = Math.round(rs.getX() * factor) / factor;
//        		double roundEndY = Math.round(rs.getY() * factor) / factor;
//        		double roundEndX2 = Math.round((allCars.get(2).getFreewayObj().getNextRoadSeg(allCars.get(2).getRoadSeg()).getX()) * factor) / factor;
//	        	double roundEndY2 = Math.round((allCars.get(2).getFreewayObj().getNextRoadSeg(allCars.get(2).getRoadSeg()).getY()) * factor) / factor;
//        		double roundEndX2 = Math.round((testCar.getFreewayObj().getNextRoadSeg(testCar.getRoadSeg()).getX()) * factor) / factor;
//	        	double roundEndY2 = Math.round((testCar.getFreewayObj().getNextRoadSeg(testCar.getRoadSeg()).getY()) * factor) / factor;
        		double roundEndX2 = testCar.getFreewayObj().getNextRoadSeg(testCar.getRoadSeg()).getX();
        		double roundEndY2 = testCar.getFreewayObj().getNextRoadSeg(testCar.getRoadSeg()).getY();
//        		double roundEndXX2 = testCar2.getFreewayObj().getNextRoadSeg(testCar2.getRoadSeg()).getX();
//        		double roundEndYY2 = testCar2.getFreewayObj().getNextRoadSeg(testCar2.getRoadSeg()).getY();

//	        	carList.get(2).destination(roundEndX2, roundEndY2); // end of segment
//	        	carList.get(2).start();
	        	car.destination(roundEndX2, roundEndY2);
//	        	car2.destination(roundEndXX2, roundEndYY2);
	        	car.start();
//	        	car2.start();
	        	
        	}
        	catch(NullPointerException e) {
//        		System.err.println("What NULL: ");
        		e.printStackTrace();
        		System.exit(1);
        	}
//        }



        // final JMapViewer map = new JMapViewer(new MemoryTileCache(),4);
        // map.setTileLoader(new OsmFileCacheTileLoader(map));
        // new DefaultMapController(map);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        helpPanel = new JPanel();

        mperpLabelName=new JLabel("Meters/Pixels: ");
        mperpLabelValue=new JLabel(String.format("%s",map().getMeterPerPixel()));

        zoomLabel=new JLabel("Zoom: ");
        zoomValue=new JLabel(String.format("%s", map().getZoom()));
//        System.out.println("MAP SOOM" + map().getZoom());

        add(panel, BorderLayout.NORTH);
//        add(helpPanel, BorderLayout.WEST);
        panel.setLayout(new BorderLayout());
        panel.add(panelTop, BorderLayout.WEST);
        panel.add(panelBottom, BorderLayout.EAST);
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);
//        helpPanel.setVisible(false);
        panel.add(helpPanel, BorderLayout.SOUTH);
        JButton focusButton = new JButton("FOCUS");
        JButton resetButton = new JButton("RESET");
        focusButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                map().setDisplayToFitMapMarkers();
            	 map().setDisplayPositionByLatLon(34.035, -118.238, 11);
            }
        });
        JComboBox tileSourceSelector = new JComboBox(new TileSource[] { new OsmTileSource.Mapnik()});
        tileSourceSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map().setTileSource((TileSource) e.getItem());
            }
        });
        JComboBox tileLoaderSelector;
        try {
            tileLoaderSelector = new JComboBox(new TileLoader[] { new OsmFileCacheTileLoader(map()),
                    new OsmTileLoader(map()) });
        } catch (IOException e) {
            tileLoaderSelector = new JComboBox(new TileLoader[] { new OsmTileLoader(map()) });
        }
        tileLoaderSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map().setTileLoader((TileLoader) e.getItem());
            }
        });
        map().setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());
        
        JLabel startLabel = new JLabel("Starting Point:"); 
        JLabel destinationLabel = new JLabel("Destination:"); 
//        final JTextField startingPointTextField = new JTextField(); 
//        startingPointTextField.setColumns(30);
//        
//        final JTextField destinationTextField = new JTextField(); 
//        destinationTextField.setColumns(30); 
//        startingPointTextField.addActionListener(new ActionListener() { 
//        	public void actionPerformed(ActionEvent e) { 
//        		startStr = startingPointTextField.getText();
//        		System.out.println(startStr); 
//        		destinationTextField.requestFocus();
//        		} 
//        	});
//        JTextField startingPointTextField = new JTextField();
//        startingPointTextField.setText("Starting Point: ");
//        startingPointTextField.setColumns(30);
//       
//        final JTextField destinationTextField = new JTextField();
//        destinationTextField.setColumns(30);
        
        Border border = BorderFactory.createLineBorder(Color.BLACK);
    	final JTextArea jta = new JTextArea("Fastest Route: \n" + "\n" + "Time at Speed Limit: \n" + "\n" +  "Time at Current Speed (of traffic): \n" + "\n", 6, 10);
    	jta.setBorder(border);
    	jta.setEditable(false);
    	jta.setVisible(false);
        
//    	destinationTextField.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//            	jta.setVisible(true);
//            	helpPanel.setVisible(false);
//            }
//        }); 
    	
//    	destinationTextField.addActionListener(new ActionListener() {
//    		public void actionPerformed(ActionEvent e) { 
//    			endStr = destinationTextField.getText(); 
//    			System.out.println(endStr);
//    			directions.requestDirections(startStr, endStr); 
//    			//text area 
//    			tempDistance = directions.getDistance(); 
//    			tempStartCoord = directions.getOriginCoord(); 
//    			tempEndCoord = directions.getDestCoord();
//    			System.out.println("start coord: " + tempStartCoord[0] + ", " + tempStartCoord[1]); 
//    			System.out.println("end coord: " + tempEndCoord[0] + ", " + tempEndCoord[1]); 
//    			tempCurrentSpeed = directions.gettimeToDestAtCurrTraffic();
//    			tempSpeedLimit = directions.timeToDestAtSpeedLimit(); 
//    			System.out.println("time at current speed: " + tempCurrentSpeed[1] + " hours and " + tempCurrentSpeed[0] + " minutes");
//    			System.out.println("time at speed limit: " + tempSpeedLimit[1] + " hours and " + tempSpeedLimit[0] + " minutes");
//    			jta.setText("\n Distance: " + tempDistance + " miles \n" + "\n" + " Time at Highway Speed Limit: " + tempSpeedLimit[1] + " hours and " + tempSpeedLimit[0] + " minutes \n" + "\n" + " Time at Current Speed (of traffic): " + tempCurrentSpeed[1] + " hours and " + tempCurrentSpeed[0] + " minutes \n" + "\n"); 
//    			//Destination 
//    			Layer bokun = treeMap.addLayer("bokun");
//    			end = new MapMarkerCircle(bokun, "Destination", new Coordinate(tempEndCoord[0], tempEndCoord[1]), .005);
//    			end.setBackColor(Color.RED); 
//    			end.setColor(Color.RED); 
//    			//Starting Point 
//    			start = new MapMarkerCircle(bokun, "Starting Point", new Coordinate(tempStartCoord[0], tempStartCoord[1]), .005); 
//    			start.setBackColor(Color.RED); 
//    			start.setColor(Color.RED); 
//    			map().addMapMarker(end); 
//    			map().addMapMarker(start); 
//    			jta.setVisible(true); 
//    			helpPanel.setVisible(false);
//    			} 
//    		});
    	
    	startHighwayList = new JComboBox(highway);
    	
    	startHighwayList.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                    	startHighwayList = (JComboBox) e.getSource();
                        if ((String)startHighwayList.getSelectedItem() == "405") {
                        	startStr = (String) JOptionPane.showInputDialog(null, "Choose an on/off ramp",
                        			"On/Off Ramps", JOptionPane.QUESTION_MESSAGE, null, 
                        			the405Ramps, the405Ramps[0]);
                        	endHighwayList.requestFocus();
                        }
                        else if ((String)startHighwayList.getSelectedItem() == "105") {
                        	startStr = (String) JOptionPane.showInputDialog(null, "Choose an on/off ramp",
                        			"On/Off Ramps", JOptionPane.QUESTION_MESSAGE, null, 
                        			the105Ramps, the105Ramps[0]);       
                        	endHighwayList.requestFocus();
                        }
                        else if ((String)startHighwayList.getSelectedItem() == "101") {
                        	startStr = (String) JOptionPane.showInputDialog(null, "Choose an on/off ramp",
                        			"On/Off Ramps", JOptionPane.QUESTION_MESSAGE, null, 
                        			the101Ramps, the101Ramps[0]); 
                        	endHighwayList.requestFocus();
                        }
                        else if ((String)startHighwayList.getSelectedItem() == "10") {
                        	startStr = (String) JOptionPane.showInputDialog(null, "Choose an on/off ramp",
                        			"On/Off Ramps", JOptionPane.QUESTION_MESSAGE, null, 
                        			the10Ramps, the10Ramps[0]);     
                        	endHighwayList.requestFocus();
                        }
                    }
                }            
        );
    	
    	endHighwayList = new JComboBox(highway);
    	//highwayList.setSelectedIndex(4);
    	
    	endHighwayList.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                    	startHighwayList = (JComboBox) e.getSource();
                        if ((String)startHighwayList.getSelectedItem() == "405") {
                        	endStr = (String) JOptionPane.showInputDialog(null, "Choose an on/off ramp",
                        			"On/Off Ramps", JOptionPane.QUESTION_MESSAGE, null, 
                        			the405Ramps, the405Ramps[0]);
                        }
                        else if ((String)startHighwayList.getSelectedItem() == "105") {
                        	endStr = (String) JOptionPane.showInputDialog(null, "Choose an on/off ramp",
                        			"On/Off Ramps", JOptionPane.QUESTION_MESSAGE, null, 
                        			the105Ramps, the105Ramps[0]);       	
                        }
                        else if ((String)startHighwayList.getSelectedItem() == "101") {
                        	endStr = (String) JOptionPane.showInputDialog(null, "Choose an on/off ramp",
                        			"On/Off Ramps", JOptionPane.QUESTION_MESSAGE, null, 
                        			the101Ramps, the101Ramps[0]);       	
                        }
                        else if ((String)startHighwayList.getSelectedItem() == "10") {
                        	endStr = (String) JOptionPane.showInputDialog(null, "Choose an on/off ramp",
                        			"On/Off Ramps", JOptionPane.QUESTION_MESSAGE, null, 
                        			the10Ramps, the10Ramps[0]);       	
                        }
                        
                        directions.requestDirections(startStr, endStr);
                    	
                    	//text area
                    	tempDistance = directions.getDistance();
                    	
                    	tempStartCoord = directions.getOriginCoord();
                    	tempEndCoord = directions.getDestCoord();
//                    	System.out.println("start coord: " + tempStartCoord[0] + ", " + tempStartCoord[1]);
//                    	System.out.println("end coord: " + tempEndCoord[0] + ", " + tempEndCoord[1]);
                    	
                    	x1 = coordPoints.getLatitudeAtKey(startStr);
                    	y1 = coordPoints.getLongitudeAtKey(startStr);
                    	x2 = coordPoints.getLatitudeAtKey(endStr);
                    	y2 = coordPoints.getLongitudeAtKey(endStr);
                    	
                    	System.out.println("start coord: " + x1 + ", " + y1);
                    	System.out.println("start coord: " + x2 + ", " + y2);
                    	
                    	tempCurrentSpeed = directions.gettimeToDestAtCurrTraffic();
                        tempSpeedLimit = directions.timeToDestAtSpeedLimit();
                        System.out.println("time at current speed: " + tempCurrentSpeed[1] + " hours and " + tempCurrentSpeed[0] + " minutes");
                        System.out.println("time at speed limit: " + tempSpeedLimit[1] + " hours and " + tempSpeedLimit[0] + " minutes");
                    	
                    	jta.setText("\n  Distance:      " + tempDistance + " miles \n" + "\n" + "  Time at Highway Speed Limit:      " + tempSpeedLimit[1] + " hours and " 
                    	+ tempSpeedLimit[0] + " minutes \n" + "\n" +  "  Time at Current Speed (of traffic):      " + tempCurrentSpeed[1] + 
                    	" hours and " + tempCurrentSpeed[0] + " minutes \n" + "\n");

                        //Destination
                        Layer bokun = treeMap.addLayer("bokun");
//                        end = new MapMarkerCircle(bokun, "Destination", new Coordinate(tempEndCoord[0], tempEndCoord[1]), .005);
                        end = new MapMarkerCircle(bokun, "Destination", new Coordinate(x2, y2), .005);
                        end.setBackColor(Color.RED);
                        end.setColor(Color.RED);
                       
                        //Starting Point
                        //start = new MapMarkerCircle(bokun, "Starting Point", new Coordinate(tempStartCoord[0], tempStartCoord[1]), .005);
                        start = new MapMarkerCircle(bokun, "Starting Point", new Coordinate(x1, y1), .005);
                        start.setBackColor(Color.RED);
                        start.setColor(Color.RED);
                        map().addMapMarker(end);
                        map().addMapMarker(start);
                

                    	jta.setVisible(true);
                    	helpPanel.setVisible(false);
                    }
                }            
        );
    	
    	
    	panelBottom.add(startLabel); 
    	//panelBottom.add(startingPointTextField); 
    	panelBottom.add(startHighwayList);
    	panelBottom.add(destinationLabel);
    	//panelBottom.add(destinationTextField); 
    	panelBottom.add(endHighwayList);
    	panelBottom.add(focusButton); 
    	panelBottom.add(resetButton); 
    	panel.add(panelBottom, BorderLayout.NORTH);
   	
    	
//        panelBottom.add(startingPointTextField);
//        panelBottom.add(destinationTextField);
//        panelBottom.add(focusButton);
//        panelBottom.add(resetButton);
//        panel.add(panelBottom, BorderLayout.NORTH);
        
        
        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(map().getMapMarkersVisible());
        showMapMarker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setMapMarkerVisible(showMapMarker.isSelected());
            }
        });
        panelBottom.add(showMapMarker);
        ///
        final JCheckBox showTreeLayers = new JCheckBox("Tree Layers visible");
        showTreeLayers.setVisible(false);
        showTreeLayers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                treeMap.setTreeVisible(showTreeLayers.isSelected());
            }
        });
        panelBottom.add(showTreeLayers);
        ///
        final JCheckBox showToolTip = new JCheckBox("ToolTip visible");
        showToolTip.setSelected(true);
        showToolTip.setVisible(false);
        showToolTip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setToolTipText(null);
            }
        });
        panelBottom.add(showToolTip);
        ///
        final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
        showTileGrid.setSelected(map().isTileGridVisible());
        showTileGrid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setTileGridVisible(showTileGrid.isSelected());
            }
        });
        panelBottom.add(showTileGrid);
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(map().getZoomContolsVisible());
        showZoomControls.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setZoomContolsVisible(showZoomControls.isSelected());
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		jta.setVisible(false); 
//        		startingPointTextField.setText("");
//        		destinationTextField.setText("");
        		end.setVisible(false); 
        		start.setVisible(false); 
        		} 
        	});
        
//        JButton histData = new JButton("Historical Data");
//        histData.addActionListener(new ActionListener() {
// 
//            public void actionPerformed(ActionEvent e)
//            {
//                //Execute when button is pressed
//            	HistDataWindow hi = new HistDataWindow();
//            }
//        });
//        
//        JButton exportData = new JButton("Export Data");
//        exportData.addActionListener(new ActionListener()
//        {
//        	public void actionPerformed(ActionEvent e)
//        	{
//              	//carData.exportDataToTxtFile();
//        	}
//
//        });
//        panelTop.add(histData);
//        panelTop.add(exportData);

        
        add(treeMap, BorderLayout.CENTER);
        add(jta, BorderLayout.SOUTH);

        
        

        /*
         * for loop
         * loop through array list of draw cars
         * inside loop, add map marker
         */

        map().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map().getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });

        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
                if (cursorHand) {
                    map().setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                if(showToolTip.isSelected()) map().setToolTipText(map().getPosition(p).toString());
            }
        });
    }
    private JMapViewer map(){
        return treeMap.getViewer();
    }
    private static Coordinate c(double lat, double lon){
        return new Coordinate(lat, lon);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // java.util.Properties systemProperties = System.getProperties();
        // systemProperties.setProperty("http.proxyHost", "localhost");
        // systemProperties.setProperty("http.proxyPort", "8008");
//        new Demo().setVisible(true);
        try
		{
	        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    } 
	    
		catch (UnsupportedLookAndFeelException e)
	    {
	       // handle exception
	    }
	   
		catch (ClassNotFoundException e)
		{
	       // handle exception
	    }
	    
		catch (InstantiationException e)
		{
	       // handle exception
	    }
	    
		catch (IllegalAccessException e)
		{
	       // handle exception
	    }
    	
        new Demo().setVisible(true);
    }

    private void updateZoomParameters() {
        if (mperpLabelValue!=null)
            mperpLabelValue.setText(String.format("%s",map().getMeterPerPixel()));
        if (zoomValue!=null)
            zoomValue.setText(String.format("%s", map().getZoom()));
    }

    @Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
    }
    
//    public void exportAction()
//    {
//    	BokunCentral bCentral = new BokunCentral();
//    	bCentral.exportDataToTxtFile();
//    	System.out.println("PRINTED SHIT");
//    }

}
