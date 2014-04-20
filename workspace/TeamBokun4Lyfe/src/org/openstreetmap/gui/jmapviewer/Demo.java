package org.openstreetmap.gui.jmapviewer;

//License: GPL. Copyright 2008 by Jan Peter Stotz

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
    
    private BokunCentral carData;
    private ArrayList<PlotInfo> plotInfo;
    private ArrayList<DrawCar> carList;
    private DrawCar car = null;
    private Car carObj;

    /**
     * Constructs the {@code Demo}.
     */
    public Demo() {
        super("JMapViewer Demo");
        setSize(400, 400);
//        setBackground(new Color(10, 225, 215));
        treeMap = new JMapViewerTree("Zones");
        carData = new BokunCentral();
        
        //	Wait until cars are populated
        while(!BokunCentral.jsonParser.isUpdated()) { System.out.print(""); }

        map().addJMVListener(this);
        map().setDisplayPositionByLatLon(34.035, -118.238, 11);
        ArrayList<Car> allCars = BokunCentral.jsonParser.getCars();
        ArrayList<DrawCar> carList = new ArrayList<DrawCar>();
//        car = new DrawCar(map(), 33.97403, -118.38212);
//    	double endX = allCars.get(0).getFreewayObj().getNextRoadSeg(allCars.get(0).getRoadSeg()).getX();
//    	double endY = allCars.get(0).getFreewayObj().getNextRoadSeg(allCars.get(0).getRoadSeg()).getY();
//
//        car = new DrawCar(map(), 33.97403, -118.38212, allCars.get(0));
//        car.destination(endX, endY);
//        car.start();
        
//        System.out.println("HERE " + allCars.size());
        
        for(int i = 0; i < 20; ++i) {
        	double factor = 1e5;
//        	double startX = allCars.get(i).getLatitude();
//        	double startY = allCars.get(i).getLongitude();
        	
        	double roundEndX = Math.round((allCars.get(i).getLatitude()) * factor) / factor;
        	double roundEndY = Math.round((allCars.get(i).getLongitude()) * factor) / factor;
        	
        	car = new DrawCar(map(), roundEndX, roundEndY, allCars.get(i));
//        	car.start();
        	carList.add(car);   	
        }
        
        for(int i = 0; i < 20; ++i) {
//        	carList.get(i).start();
        	double factor = 1e5;
//        	double endX = allCars.get(i).getFreewayObj().getNextRoadSeg(allCars.get(i).getRoadSeg()).getX();
//        	double endY = allCars.get(i).getFreewayObj().getNextRoadSeg(allCars.get(i).getRoadSeg()).getY();
        	
        	double roundEndX = Math.round((allCars.get(i).getFreewayObj().getNextRoadSeg(allCars.get(i).getRoadSeg()).getX()) * factor) / factor;
        	double roundEndY = Math.round((allCars.get(i).getFreewayObj().getNextRoadSeg(allCars.get(i).getRoadSeg()).getY()) * factor) / factor;
        	
        	
        	carList.get(i).destination(roundEndX, roundEndY);
        	carList.get(i).start();

        }



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
        JButton button = new JButton("FOCUS");
        button.addActionListener(new ActionListener() {

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
        JTextField startingPointTextField = new JTextField();
        startingPointTextField.setText("Starting Point: ");
        startingPointTextField.setColumns(30);
        
        JTextField destinationTextField = new JTextField();
        destinationTextField.setText("Destination: ");
        destinationTextField.setColumns(30);
        
        Border border = BorderFactory.createLineBorder(Color.BLACK);
    	final JTextArea jta = new JTextArea("Fastest Route: \n" + "\n" + "Time at Speed Limit: \n" + "\n" +  "Time at Current Speed (of traffic): \n" + "\n", 6, 10);
    	jta.setBorder(border);
    	jta.setEditable(false);
    	jta.setVisible(false);
        
    	destinationTextField.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	jta.setVisible(true);
            	helpPanel.setVisible(false);
            }
        }); 
    	
        panelBottom.add(startingPointTextField);
        panelBottom.add(destinationTextField);
        panelBottom.add(button);
        panel.add(panelBottom, BorderLayout.NORTH);
        
        
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
        JButton histData = new JButton("Historical Data");
        histData.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	HistDataWindow hi = new HistDataWindow();
            }
        });
        
        JButton exportData = new JButton("Export Data");
        panelTop.add(histData);
        panelTop.add(exportData);

        
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

}
