package central;

import graph.Traverse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;

import jsonParse.CarJsonParser;
import data.Car;
import data.ConstructFreeways;
import data.RoadSegment;
import mySQL.MySQLConnect;

public class BokunCentral {
	public static MySQLConnect			SQLConnection;
	public static ConstructFreeways		freeways;
	public static CarJsonParser			jsonParser;
	public static ArrayList<Traverse> 	traverseUnits;
	
	private ArrayList<Car> 				allCars;
	private ArrayList<PlotInfo> 		carLatLongData;
	
//	private PrintWriter writer;
	
	public static void main(String[] args) {
		new BokunCentral();
		String start = ConstructFreeways.S101.getRoadSegAt(6).getKey();
		String end = ConstructFreeways.W10.getRoadSegAt(1).getKey();
		
		System.out.println("Start from " + start + "\nGo to " + end + "\n");
		
		BokunCentral.getFastestPath(start, end);
	}
	
	public BokunCentral() {
		try {
			SQLConnection = new MySQLConnect();
			freeways = new ConstructFreeways();
			jsonParser = new CarJsonParser();
			jsonParser.start();
			
			traverseUnits = new ArrayList<Traverse>();
			allCars = new ArrayList<Car>();
			carLatLongData = new ArrayList<PlotInfo>();
		}
		catch(Exception e) {
			System.err.println("System error: ");
			e.printStackTrace();
		}
		while(jsonParser.getCars().size() == 0) { System.out.print(""); }
		
		allCars = jsonParser.getCars();
		carLatLongData = aggregateCarLatLong();
		
//		for(int i = 0; i < carLatLongData.size(); i++)
//		{
//			System.out.println(carLatLongData.get(i).getLatitude() + " " + carLatLongData.get(i).getLongitude()
//					+ " " + carLatLongData.get(i).getSpeed() + " " + carLatLongData.get(i).getFwy());
//		}
	}
	
	//	Get the fastest path from source to destination
	public static void getFastestPath(String startKey, String endKey) {
		//	Find the road segments corresponding to the start and end keys
		RoadSegment start = ConstructFreeways.getRoadSegmentAtKey(startKey);
		RoadSegment end = ConstructFreeways.getRoadSegmentAtKey(endKey);
		
		//	Send out the Traverse units
		Traverse t = new Traverse(start, new double[] {end.getX(), end.getY()});
		traverseUnits.add(t);
		
		try {
			//	Check every second for new traverse units
			while(true) {
				int size = traverseUnits.size();
				Thread.sleep(1000);
				
				//	If no updates after 1 seconds assume no more units will be added
				if(size == traverseUnits.size()) {
					//	Wait until all units are done
					for(int i = 0; i < traverseUnits.size(); i++)
						traverseUnits.get(i).join();
					break;
				}
			}
			
			//	Parse through all the units
			double leastTravelTime = traverseUnits.get(0).getTotalTravelTime();
			Traverse finalTraverse = traverseUnits.get(0);
			for(int i = 1; i < traverseUnits.size(); i++) {
				Traverse temp = traverseUnits.get(i);
				
				if(leastTravelTime > temp.getTotalTravelTime()) {
					leastTravelTime = temp.getTotalTravelTime();
					finalTraverse = temp;
				}
			}
			
//			System.out.println(finalTraverse);
			
		}
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	//	Update database
	public static void updateDatabase(double avg, int hour, String freeway) {
		try {
			SQLConnection.updateTable(hour, new String[] {freeway + "Speed"}, new double[] {avg});
		}
		catch(SQLException e) {
			System.out.println("SQL error: ");
			e.printStackTrace();
		}
	}

	public ArrayList<PlotInfo> aggregateCarLatLong()
	{
		ArrayList<PlotInfo> aggregatedData = new ArrayList<PlotInfo>();
		PlotInfo temp;
		for(int i = 0; i < allCars.size(); i++)
		{
			temp = new PlotInfo((((allCars.get(i)).getRoadSeg()).getX()), (((allCars.get(i)).getRoadSeg()).getY()), ((allCars.get(i)).getSpeed()), ((allCars.get(i)).getFreeway()));
			aggregatedData.add(temp);
		}
		
		return aggregatedData;
	}
	
	public static void exportDataToTxtFile()
	{
		PrintWriter writer = null;
		try
		{
			
			double[] E10Speeds = null;
			double[] W10Speeds = null;
			double[] E105Speeds = null;
			double[] W105Speeds = null;
			double[] S101Speeds = null;
			double[] N101Speeds = null;
			double[] S405Speeds = null;
			double[] N405Speeds = null;
			try {
				E10Speeds = SQLConnection.getAverageSpeedsOf("E10");
				W10Speeds = SQLConnection.getAverageSpeedsOf("W10");
				E105Speeds = SQLConnection.getAverageSpeedsOf("E105");
				W105Speeds = SQLConnection.getAverageSpeedsOf("W105");
				S101Speeds = SQLConnection.getAverageSpeedsOf("S101");
				N101Speeds = SQLConnection.getAverageSpeedsOf("N101");
				S405Speeds = SQLConnection.getAverageSpeedsOf("S405");
				N405Speeds = SQLConnection.getAverageSpeedsOf("N405");
			}
			catch(SQLException e) 
			{ 
				e.printStackTrace(); 
			}
			
			writer = new PrintWriter("AggregatedData.txt", "UTF-8");
			
			int theHour = 0;
			double avgSpd_N101 = 0; 
			double avgSpd_S101 = 0;
			double avgSpd_N405 = 0;
			double avgSpd_S405 = 0;
			double avgSpd_E10 = 0;
			double avgSpd_W10 = 0;
			double avgSpd_E105 = 0;
			double avgSpd_W105 = 0;
			
			for(int i = 0; i < 25; i++)
			{
				if(i == 0)
				{
					writer.println("Hours,N101,S101,N405,S405,E10,W10,E105,W105");
				}
				else
				{
					theHour = (i - 1);
					avgSpd_N101 = N101Speeds[theHour];
					avgSpd_S101 = S101Speeds[theHour];
					avgSpd_N405 = N405Speeds[theHour];
					avgSpd_S405 = S405Speeds[theHour];
					avgSpd_E10 = E10Speeds[theHour];
					avgSpd_W10 = W10Speeds[theHour];
					avgSpd_E105 = E105Speeds[theHour];
					avgSpd_W105 = W105Speeds[theHour];
					writer.println(theHour + "," + avgSpd_N101 + "," + avgSpd_S101 + "," + avgSpd_N405 + "," + avgSpd_S405 + "," + avgSpd_E10 + "," + avgSpd_W10 + "," + avgSpd_E105 + "," + avgSpd_W105);
					writer.flush();
					//System.out.println(theHour + "," + avgSpd_N101 + "," + avgSpd_S101 + "," + avgSpd_N405 + "," + avgSpd_S405 + "," + avgSpd_E10 + "," + avgSpd_W10 + "," + avgSpd_E105 + "," + avgSpd_W105);
				}
			}


		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally 
		{
			if(writer != null)
				writer.close();
		}
	}
	
}