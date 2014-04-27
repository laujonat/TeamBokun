package jsonParse;
import java.lang.reflect.Type;

import central.BokunCentral;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import data.Car;
import data.ConstructFreeways;
import data.Freeway;
import data.RoadSegment;

//	Custom deserialization for cars
public class CarDeserializer implements JsonDeserializer<Car>{
	@Override
	public Car deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		
		int id = jsonObject.get("id").getAsInt();
		double speed = jsonObject.get("speed").getAsDouble();
		String direction = jsonObject.get("direction").getAsString();
		String onOff = jsonObject.get("on/off ramp").getAsString();
		String freeway = jsonObject.get("freeway").getAsString();
		
		Freeway fwy = BokunCentral.freeways.getFreeway(freeway, direction);
		RoadSegment rs = BokunCentral.freeways.getRoadSegment(freeway, direction, onOff);
		
		// check if car exists already exists
		Car car = new Car(id, speed, fwy, rs);
		return car;
	}
}
