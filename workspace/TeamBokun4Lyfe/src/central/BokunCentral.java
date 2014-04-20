package central;

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
//			System.out.println(allCars.get(i).toString());
			temp = new PlotInfo((((allCars.get(i)).getRoadSeg()).getX()), (((allCars.get(i)).getRoadSeg()).getY()), ((allCars.get(i)).getSpeed()), ((allCars.get(i)).getFreeway()));
			aggregatedData.add(temp);
//			System.out.println(allCars.get(i).getRoadSeg().toString());
//			System.out.println(allCars.get(i).getRoadSeg().getX1() + " " + allCars.get(i).getRoadSeg().getY1());
		}
		
		return aggregatedData;
	}
}