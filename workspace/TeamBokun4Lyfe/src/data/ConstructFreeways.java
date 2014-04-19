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
			int j = 0;
			reader = new BufferedReader(new FileReader("/Users/christopherobrien/Documents/USC/Spring2014/cs201/chobrien_CS201_GroupProject/src/The101.txt"));
			while ((tempKey = reader.readLine()) != null) {
				temp = new RoadSegment(tempKey);
				N101.addRoadSeg(temp);
				N101.getRoadSegAt(j).setID(j);
				j++;
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
		for(int i = (N101.getNumRoadSeg() - 1); i >= 0; i--)
		{
			S101.addRoadSeg(N101.getRoadSegAt(i));
			S101.getRoadSegAt(i).setID(i);
		}
	}
	
	public static void constructN405()
	{
		N405 = new Freeway("405 North");
		try 
		{
			RoadSegment temp;
			String tempKey;
			int j = 0;
			reader = new BufferedReader(new FileReader("/Users/christopherobrien/Documents/USC/Spring2014/cs201/chobrien_CS201_GroupProject/src/The405.txt"));
			while ((tempKey = reader.readLine()) != null) {
				temp = new RoadSegment(tempKey);
				N405.addRoadSeg(temp);
				N405.getRoadSegAt(j).setID(j);
				j++;
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
		for(int i = (N405.getNumRoadSeg() - 1); i >= 0; i--)
		{
			S405.addRoadSeg(N405.getRoadSegAt(i));
			S405.getRoadSegAt(i).setID(i);
		}
	}
	
	public static void constructE10()
	{
		E10 = new Freeway("10 East");
		
		try 
		{
			RoadSegment temp;
			String tempKey;
			int j = 0;
			reader = new BufferedReader(new FileReader("/Users/christopherobrien/Documents/USC/Spring2014/cs201/chobrien_CS201_GroupProject/src/The10.txt"));
			while ((tempKey = reader.readLine()) != null) {
				temp = new RoadSegment(tempKey);
				E10.addRoadSeg(temp);
				E10.getRoadSegAt(j).setID(j);
				j++;
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
		for(int i = (E10.getNumRoadSeg() - 1); i >= 0; i--)
		{
			W10.addRoadSeg(E10.getRoadSegAt(i));
			W10.getRoadSegAt(i).setID(i);
		}
	}

	public static void constructE105()
	{
		E105 = new Freeway("105 East");
		try 
		{
			RoadSegment temp;
			String tempKey;
			int j = 0;
			reader = new BufferedReader(new FileReader("/Users/christopherobrien/Documents/USC/Spring2014/cs201/chobrien_CS201_GroupProject/src/The105.txt"));
			while ((tempKey = reader.readLine()) != null) {
				temp = new RoadSegment(tempKey);
				E105.addRoadSeg(temp);
				E105.getRoadSegAt(j).setID(j);
				j++;
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
			W105.getRoadSegAt(i).setID(i);
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
				int i = -1;
				do
				{
					i += 1;
				} while(!(N101.getRoadSegAt(i).getKey()).equalsIgnoreCase(onOffKey));
				
				return N101.getRoadSegAt(i);
				
			}
			else //if(freeway.equalsIgnoreCase("405"))
			{
				int i = -1;
				do
				{
					i += 1;
				} while(!(N405.getRoadSegAt(i).getKey()).equalsIgnoreCase(onOffKey));
				
				return N405.getRoadSegAt(i);
			}
		}
		else if(direction.equalsIgnoreCase("south"))
		{
			if(freeway.equalsIgnoreCase("101"))
			{
				int i = -1;
				do
				{
					i += 1;
				} while(!(S101.getRoadSegAt(i).getKey()).equalsIgnoreCase(onOffKey));
				
				return S101.getRoadSegAt(i);
				
			}
			else //if(freeway.equalsIgnoreCase("405"))
			{
				int i = -1;
				do
				{
					i += 1;
				} while(!(S405.getRoadSegAt(i).getKey()).equalsIgnoreCase(onOffKey));
				
				return S405.getRoadSegAt(i);
			}
		}
		else if(direction.equalsIgnoreCase("east"))
		{
			if(freeway.equalsIgnoreCase("10"))
			{
				int i = -1;
				do
				{
					i += 1;
				} while(!(E10.getRoadSegAt(i).getKey()).equalsIgnoreCase(onOffKey));
				
				return E10.getRoadSegAt(i);
				
			}
			else //if(freeway.equalsIgnoreCase("105"))
			{
				int i = -1;
				do
				{
					i += 1;
				} while(!(E105.getRoadSegAt(i).getKey()).equalsIgnoreCase(onOffKey));
				
				return E105.getRoadSegAt(i);
			}
		}
		else //if(direction.equalsIgnoreCase("west"))
		{
			if(freeway.equalsIgnoreCase("10"))
			{

				int i = -1;
				do
				{
					i += 1;
				} while(!(W10.getRoadSegAt(i).getKey()).equalsIgnoreCase(onOffKey));
				
				return W10.getRoadSegAt(i);
				
			}
			else //if(freeway.equalsIgnoreCase("105"))
			{

				int i = -1;
				do
				{
					i += 1;
				} while(!(W105.getRoadSegAt(i).getKey()).equalsIgnoreCase(onOffKey));
				
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