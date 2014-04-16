package jsonParse;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import data.Car;

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
		
//		Car car = new Car(id, speed, direction, onOff, freeway);
//		return car;
		return null;
	}
}
