package central;

public class PlotInfo {
	public double latitude;
	public double longitude;
	public double speed;
	public String fwy;
	
	public PlotInfo(double latitude, double longitude, double speed, String fwy)
	{
		setLatitude(latitude);
		setLongitude(longitude);
		setSpeed(speed);
		setFwy(fwy);
	}
	
	public void setLatitude(double latitude) { this.latitude = latitude; }
	public double getLatitude() { return latitude; }
	
	public void setLongitude(double longitude) { this.longitude = longitude; }
	public double getLongitude() { return longitude; }
	
	public void setSpeed(double speed) { this.speed = speed; }
	public double getSpeed() { return speed; }
	
	public void setFwy(String fwy) { this.fwy = fwy; }
	public String getFwy() { return fwy; }
}
