package data;

public class RoadSegment
{

	private int ID;
	private String key;
	private double[] startRampCoordinates;
	private double[] endRampCoordinates;
	private double minSpeed;
	private Freeway freeway;
	
	public RoadSegment(String key, int ID, Freeway freeway)
	{
		setKey(key);
		this.ID = ID;
		this.freeway = freeway;
		
		startRampCoordinates = new double[2];
		endRampCoordinates = new double[2];
		
		minSpeed = 0;
	}
	
	public RoadSegment(RoadSegment rs) {
		this.ID = rs.ID;
		this.key = rs.key;
		this.startRampCoordinates = rs.startRampCoordinates;
		this.endRampCoordinates = rs.endRampCoordinates;
		this.minSpeed = rs.minSpeed;
		this.freeway = rs.freeway;
	}
	
	public int getID() { return ID; }
	public void setID(int ID) { this.ID = ID; }
	
	public String getKey() { return key; }
	public void setKey(String key) { this.key = key; }
	
	public double getX1() { return startRampCoordinates[0]; }
	public void setX1(double x) { startRampCoordinates[0] = x; }
	
	public double getY1() { return startRampCoordinates[1]; }
	public void setY1(double y) { startRampCoordinates[1] = y; }
	
	public double getX2() { return endRampCoordinates[0]; }
	public void setX2(double x) { endRampCoordinates[0] = x; }
	
	public double getY2() { return endRampCoordinates[1]; }
	public void setY2(double y) { endRampCoordinates[1] = y; }
	
	public double getMinSpeed() { return minSpeed; }
	public void setMinSpeed(double s) { minSpeed = s; }
	
	public String getFreewayName() { return (freeway.getName()); }
	
	public void setFreeway(Freeway f)
	{
		freeway = f;
	}
	
	
	
}
