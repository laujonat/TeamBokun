package central;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;

import jsonParse.CarJsonParser;
import data.Car;
import data.ConstructFreeways;
import mySQL.MySQLConnect;

public class BokunCentral {
	public static MySQLConnect			SQLConnection;
	public static ConstructFreeways		freeways;
	public static CarJsonParser			jsonParser;
	
	private ArrayList<Car> allCars;
	private ArrayList<PlotInfo> carLatLongData;
	private PrintWriter writer;
	
	public BokunCentral() {
		try {
			SQLConnection = new MySQLConnect();
			freeways = new ConstructFreeways();
			jsonParser = new CarJsonParser();
			jsonParser.start();
			
			allCars = new ArrayList<Car>();
			carLatLongData = new ArrayList<PlotInfo>();
		}
		catch(Exception e) {
			System.err.println("System error: ");
			e.printStackTrace();
		}
		while(jsonParser.getCars().size() == 0)
		{
			System.out.print("");
		}
		allCars = jsonParser.getCars();
		carLatLongData = aggregateCarLatLong();
		
//		for(int i = 0; i < carLatLongData.size(); i++)
//		{
//			System.out.println(carLatLongData.get(i).getLatitude() + " " + carLatLongData.get(i).getLongitude()
//					+ " " + carLatLongData.get(i).getSpeed() + " " + carLatLongData.get(i).getFwy());
//		}
		
	}

	public static void main(String[] args) {
		BokunCentral bc = new BokunCentral();
		
//		for(int i = 0; i < jsonParser.getCars().size(); i++)
//			System.out.println(jsonParser.getCars().get(i).toString());
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
	
	public void exportDataToTxtFile()
	{
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
				}
			}


		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally 
		{
				writer.close();
		}
	}
	
}