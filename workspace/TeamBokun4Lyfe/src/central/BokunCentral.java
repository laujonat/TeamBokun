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
	
	public BokunCentral() {
		try {
			SQLConnection = new MySQLConnect();
			freeways = new ConstructFreeways();
			jsonParser = new CarJsonParser("");
			
			allCars = new ArrayList<Car>();
		}
		catch(Exception e) { System.err.println("System error: " + e.getMessage()); }
	}

	public static void main(String[] args) {
		
	}
}
