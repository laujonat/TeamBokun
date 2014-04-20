package jsonParse;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

public class DirectionsJsonParser {
	int[] timeToDest;		//	timeToDest[0] = minutes		timeToDest[1] = hours

	public void requestDirections() {
		BufferedReader br = null;
		try {
			URL                url; 
		    URLConnection      connection; 
		    DataInputStream    dataStreamer;
	
//		    url = new URL("http://www-scf.usc.edu/~csci201/mahdi_project/project_data.json");
		    url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=3025+royal+street+los+angeles&destination=3131+mcclintock+street+los+angeles&sensor=false");
	//	   "text" : "5 hours 39 mins",
		    connection = url.openConnection(); 
		    connection.setDoInput(true); 
		    connection.setUseCaches(false);
	
		    dataStreamer = new DataInputStream(connection.getInputStream()); 
		    br = new BufferedReader(new InputStreamReader(dataStreamer));
			
		    String line;
		    String feedback = "";
		    while((line = br.readLine()) != null) {
		    	feedback += line;
		    	System.out.println(line);
		    }
		    feedback = feedback.replaceAll("\\s+", "");
		    
		    System.out.println(feedback);
		    
//			String jsonString = br.readLine();
//			if(jsonString == null) {
//				System.err.println("Invalid JSON file.");
//				System.exit(1);
//			}
//			
//			System.out.println(jsonString);
		}
		catch(MalformedURLException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		new DirectionsJsonParser().requestDirections();;
	}
}
