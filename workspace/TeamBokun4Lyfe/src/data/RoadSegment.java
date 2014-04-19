package data;

public class RoadSegment
{

	private int ID;
	private String key;
	private int[] startRampCoordinates;
	private int[] endRampCoordinates;
	private double minSpeed;
	private Freeway freeway;
	
	public RoadSegment(String key)
	{
		
		setKey(key);
		
		startRampCoordinates = new int[2];
		endRampCoordinates = new int[2];
		
		
	}
	
	public int getID() { return ID; }
	public void setID(int ID) { this.ID = ID; }
	
	public String getKey() { return key; }
	public void setKey(String key) { this.key = key; }
	
	public int getX1() { return startRampCoordinates[0]; }
	public void setX1(int x) { startRampCoordinates[0] = x; }
	
	public int getY1() { return startRampCoordinates[1]; }
	public void setY1(int y) { startRampCoordinates[1] = y; }
	
	public int getX2() { return endRampCoordinates[0]; }
	public void setX2(int x) { endRampCoordinates[0] = x; }
	
	public int getY2() { return endRampCoordinates[1]; }
	public void setY2(int y) { endRampCoordinates[1] = y; }
	
	public double getMinSpeed() { return minSpeed; }
	public void setMinSpeed(double s) { minSpeed = s; }
	
	public String getFreewayName() { return (freeway.getName()); }
	
	public void setFreeway(Freeway f)
	{
		freeway = f;
	}
	
	
	
}
