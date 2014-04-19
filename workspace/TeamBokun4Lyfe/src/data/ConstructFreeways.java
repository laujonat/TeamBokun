package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConstructFreeways {

	public static Freeway N101;
	public static Freeway S101;
	
	public static Freeway N405;
	public static Freeway S405;
	
	public static Freeway E10;
	public static Freeway W10;
	
	public static Freeway E105;
	public static Freeway W105;
	
	public static BufferedReader reader;
	
	public ConstructFreeways()
	{
		constructN101();
		constructS101();
		constructN405();
		constructS405();
		constructE10();
		constructW10();
		constructE105();
		constructW105();
	}
	
	public static void constructN101()
	{
		N101 = new Freeway("101 North");
		
		try 
		{
			RoadSegment temp;
			String tempKey;
			reader = new BufferedReader(new FileReader("./src/The101.txt"));
			int id = 0;
			while ((tempKey = reader.readLine()) != null) {
				temp = new RoadSegment(tempKey, id++, N101);
				N101.addRoadSeg(temp);
			}
 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (reader != null) { reader.close(); }
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void constructS101()
	{
		S101 = new Freeway("101 South");
		int id = 0;
		for(int i = (N101.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = N101.getRoadSegAt(i);
			rs.setFreeway(S101);
			rs.setID(id++);
			S101.addRoadSeg(rs);
		}
	}
	
	public static void constructN405()
	{
		N405 = new Freeway("405 North");
		try 
		{
			RoadSegment temp;
			String tempKey, line;
			int id = 0;
			reader = new BufferedReader(new FileReader("./src/The405.txt"));
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\\|");
				tempKey = split[0];
				temp = new RoadSegment(tempKey, id++, N405);
				N405.addRoadSeg(temp);
				
				//	Get coordinates
				String[] coords = split[1].split(",");
				
				//	First seg will start and stop at same point
				if(id == 1) {
					temp.setX1(Double.parseDouble(coords[0]));
					temp.setY1(Double.parseDouble(coords[1]));
					temp.setX2(Double.parseDouble(coords[0]));
					temp.setY2(Double.parseDouble(coords[1]));
				}
				else {
					temp.setX2(Double.parseDouble(coords[0]));
					temp.setY2(Double.parseDouble(coords[1]));
					
					//	Find end coordinates of previous segment
					RoadSegment prev = N405.getPrevRoadSeg(temp);
					temp.setX1(prev.getX2());
					temp.setY1(prev.getY2());
				}
			}
 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (reader != null) { reader.close(); }
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void constructS405()
	{
		S405 = new Freeway("405 South");
		int id = 0;
		for(int i = (N405.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = N405.getRoadSegAt(i);
			rs.setFreeway(S405);
			rs.setID(id++);
			S405.addRoadSeg(rs);
		}
	}
	
	public static void constructE10()
	{
		E10 = new Freeway("10 East");
		try 
		{
			RoadSegment temp;
			String tempKey, line;
			int id = 0;
			reader = new BufferedReader(new FileReader("./src/The10.txt"));
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\\|");
				tempKey = split[0];
				temp = new RoadSegment(tempKey, id++, E10);
				E10.addRoadSeg(temp);
				
				//	Get coordinates
				String[] coords = split[1].split(",");
				
				//	First seg will start and stop at same point
				if(id == 1) {
					temp.setX1(Double.parseDouble(coords[0]));
					temp.setY1(Double.parseDouble(coords[1]));
					temp.setX2(Double.parseDouble(coords[0]));
					temp.setY2(Double.parseDouble(coords[1]));
				}
				else {
					temp.setX2(Double.parseDouble(coords[0]));
					temp.setY2(Double.parseDouble(coords[1]));
					
					//	Find end coordinates of previous segment
					RoadSegment prev = E10.getPrevRoadSeg(temp);
					temp.setX1(prev.getX2());
					temp.setY1(prev.getY2());
				}
				System.out.println(temp.getX1() + ", " + temp.getY1());
				System.out.println(temp.getX2() + ", " + temp.getY2() + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (reader != null) { reader.close(); }
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

	}
	
	public static void constructW10()
	{
		W10 = new Freeway("10 West");
		int id = 0;
		for(int i = (E10.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = E10.getRoadSegAt(i);
			rs.setFreeway(W10);
			rs.setID(id++);
			W10.addRoadSeg(rs);
		}
	}

	public static void constructE105()
	{
		E105 = new Freeway("105 East");
		try 
		{
			RoadSegment temp;
			String tempKey;
			reader = new BufferedReader(new FileReader("./src/The105.txt"));
			while ((tempKey = reader.readLine()) != null) {
//				temp = new RoadSegment(tempKey);
//				E105.addRoadSeg(temp);
			}
 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (reader != null) { reader.close(); }
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void constructW105()
	{
		W105 = new Freeway("105 West");
		W105 = new Freeway("105 West");
		for(int i = (E105.getNumRoadSeg() - 1); i >= 0; i--)
		{
			W105.addRoadSeg(E105.getRoadSegAt(i));
		}
	}
	
	public static void displayFreewayExits()
	{
		System.out.println("-------N101---------");
		for(int i = 0; i < N101.getNumRoadSeg(); i++)
		{
			System.out.println(N101.getRoadSegAt(i).getKey());
		}
		System.out.println("---------------");
		System.out.println("-------S101---------");
		for(int i = 0; i < S101.getNumRoadSeg(); i++)
		{
			System.out.println(S101.getRoadSegAt(i).getKey());
		}
		System.out.println("---------------");
		System.out.println("-------N405---------");
		for(int i = 0; i < N405.getNumRoadSeg(); i++)
		{
			System.out.println(N405.getRoadSegAt(i).getKey());
		}
		System.out.println("---------------");
		System.out.println("-------S405---------");
		for(int i = 0; i < S405.getNumRoadSeg(); i++)
		{
			System.out.println(S405.getRoadSegAt(i).getKey());
		}
		System.out.println("---------------");
		System.out.println("-------E10---------");
		for(int i = 0; i < E10.getNumRoadSeg(); i++)
		{
			System.out.println(E10.getRoadSegAt(i).getKey());
		}
		System.out.println("-------W10---------");
		for(int i = 0; i < W10.getNumRoadSeg(); i++)
		{
			System.out.println(W10.getRoadSegAt(i).getKey());
		}
		System.out.println("---------------");
		System.out.println("-------E105---------");
		for(int i = 0; i < E105.getNumRoadSeg(); i++)
		{
			System.out.println(E105.getRoadSegAt(i).getKey());
		}
		System.out.println("-------W105---------");
		for(int i = 0; i < W105.getNumRoadSeg(); i++)
		{
			System.out.println(W105.getRoadSegAt(i).getKey());
		}
		System.out.println("---------------");
	}
	
	public RoadSegment getRoadSegment(String freeway, String direction, String onOffKey)
	{
		
		//change this method if time permits (this method was written before the getFreeway method
		if(direction.equalsIgnoreCase("north"))
		{
			if(freeway.equalsIgnoreCase("101"))
			{
				boolean found = false;
				int i = -1;
				do
				{
					i += 1;
				} while(N101.getRoadSegAt(i).getKey() != onOffKey);
				
				return N101.getRoadSegAt(i);
				
			}
			else //if(freeway.equalsIgnoreCase("405"))
			{
				boolean found = false;
				int i = -1;
				do
				{
					i += 1;
				} while(N405.getRoadSegAt(i).getKey() != onOffKey);
				
				return N405.getRoadSegAt(i);
			}
		}
		else if(direction.equalsIgnoreCase("south"))
		{
			if(freeway.equalsIgnoreCase("101"))
			{
				boolean found = false;
				int i = -1;
				do
				{
					i += 1;
				} while(S101.getRoadSegAt(i).getKey() != onOffKey);
				
				return S101.getRoadSegAt(i);
				
			}
			else //if(freeway.equalsIgnoreCase("405"))
			{
				boolean found = false;
				int i = -1;
				do
				{
					i += 1;
				} while(S405.getRoadSegAt(i).getKey() != onOffKey);
				
				return S405.getRoadSegAt(i);
			}
		}
		else if(direction.equalsIgnoreCase("east"))
		{
			if(freeway.equalsIgnoreCase("10"))
			{
				boolean found = false;
				int i = -1;
				do
				{
					i += 1;
				} while(E10.getRoadSegAt(i).getKey() != onOffKey);
				
				return E10.getRoadSegAt(i);
				
			}
			else //if(freeway.equalsIgnoreCase("105"))
			{
				boolean found = false;
				int i = -1;
				do
				{
					i += 1;
				} while(E105.getRoadSegAt(i).getKey() != onOffKey);
				
				return E105.getRoadSegAt(i);
			}
		}
		else //if(direction.equalsIgnoreCase("west"))
		{
			if(freeway.equalsIgnoreCase("10"))
			{
				boolean found = false;
				int i = -1;
				do
				{
					i += 1;
				} while(W10.getRoadSegAt(i).getKey() != onOffKey);
				
				return W10.getRoadSegAt(i);
				
			}
			else //if(freeway.equalsIgnoreCase("105"))
			{
				boolean found = false;
				int i = -1;
				do
				{
					i += 1;
				} while(W105.getRoadSegAt(i).getKey() != onOffKey);
				
				return W105.getRoadSegAt(i);
			}

		}
	
	}
	
	public Freeway getFreeway(String freeway, String direction)
	{
		if(direction.equalsIgnoreCase("north"))
		{
			if(freeway.equalsIgnoreCase("101"))
			{
				return N101;
			}
			else //if(freeway.equalsIgnoreCase("405"))
			{
				return N405;
			}
		}
		else if(direction.equalsIgnoreCase("south"))
		{
			if(freeway.equalsIgnoreCase("101"))
			{
				return S101;
			}
			else //if(freeway.equalsIgnoreCase("405"))
			{
				return S405;
			}
		}
		else if(direction.equalsIgnoreCase("east"))
		{
			if(freeway.equalsIgnoreCase("10"))
			{
				return E10;
			}
			else //if(freeway.equalsIgnoreCase("105"))
			{
				return E105;
			}
		}
		else //if(direction.equalsIgnoreCase("west"))
		{
			if(freeway.equalsIgnoreCase("10"))
			{
				return W10;
			}
			else //if(freeway.equalsIgnoreCase("105"))
			{
				return W105;
			}

		}
	}
}