package jsonParse;

import java.awt.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import data.Car;
import data.RoadSegment;

import java.util.ArrayList;
import java.util.Iterator;

public class CarJsonParser extends Thread {
	private ArrayList<Car> cars = new ArrayList<Car>();
	
	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	//	Update data every 3 minutes
	@Override
	public void run() {
		int x = 1;
		while(true) {
			BufferedReader br = null;
			
			try {
			    URL                url; 
			    URLConnection      connection; 
			    DataInputStream    dataStreamer;
	
			    url = new URL("http://www-scf.usc.edu/~csci201/mahdi_project/project_data.json");
	
			    connection = url.openConnection(); 
			    connection.setDoInput(true); 
			    connection.setUseCaches(false);
	
			    dataStreamer = new DataInputStream(connection.getInputStream()); 
			    br = new BufferedReader(new InputStreamReader(dataStreamer));
				
				String jsonString = br.readLine();
				if(jsonString == null) {
					System.err.println("Invalid JSON file.");
					System.exit(1);
				}
					
				//	Get array of all json cars
				JsonElement json = new JsonParser().parse(jsonString);
				JsonArray array = json.getAsJsonArray();
				Iterator<JsonElement> it = array.iterator();
				
				//	Iterate through and save all cars
				while(it.hasNext()) {
					JsonElement jsonCar = (JsonElement)it.next();
					
					Car car = gson.fromJson(jsonCar, Car.class);
					
					//	Search through existing cars for a match
					boolean update = false;
					for(int i = 0; i < cars.size(); i++) {
						if(cars.get(i).getId() == car.getId()) {
							cars.set(i, car);
//							System.out.println("Car updated. Original:\n" + updateCar.toString());
							update = true;
//							System.out.println("Update:\n" + updateCar.toString() + "\n");
						}
					}
					
					if(!update)
						cars.add(car);
				}
			}
			catch(FileNotFoundException e) {
				System.err.println("Error parsing JSON file: " + e.getMessage());
				System.exit(1);
			}
			catch(IOException e) {
				System.err.println("Error parsing JSON file: " + e.getMessage());
				System.exit(1);
			}
			finally {
				try {
					br.close();
					System.out.println("Done with update " + x);
					x++;
					Thread.sleep(180000);
				}
				catch(IOException e) {System.err.println("Unknown error: "  + e.getMessage()); }
				catch(InterruptedException e) {System.err.println("Thread interrupted: "  + e.getMessage()); }
			}
		}
	}
	
	public CarJsonParser() {
		//		Configure Gson
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Car.class, new CarDeserializer());
		gson = gsonBuilder.create();
	}
	
	//	Get all cars
	public ArrayList<Car> getCars() { return cars; }
	
	//	Print all car information
	public void printCars() {
		for(int i = 0; i < cars.size(); i++)
			System.out.println(cars.get(i));
	}
}
