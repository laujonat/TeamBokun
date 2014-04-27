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
	
	//public static Node 
	
	public static BufferedReader reader;
	
	public ConstructFreeways()
	{
		generateNodes();
		constructN101();
		constructS101();
		constructN405();
		constructS405();
		constructE10();
		constructW10();
		constructE105();
		constructW105();
	}
	
	public static void generateNodes()
	{
		//Node
	}
	
	public static void constructN101()
	{
		N101 = new Freeway("N101");
		try 
		{
			RoadSegment temp;
			String tempKey, line;
			int id = 0;
			reader = new BufferedReader(new FileReader("./src/The101.txt"));
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\\|");
				tempKey = split[0];
				temp = new RoadSegment(tempKey, id++, N101);
				N101.addRoadSeg(temp);
				
				//	Get coordinates
				String[] coords = split[1].split(",");
				
				temp.setX(Double.parseDouble(coords[0]));
				temp.setY(Double.parseDouble(coords[1]));
			}
			

			//populate nodes
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
		S101 = new Freeway("S101");
		int id = 0;
		for(int i = (N101.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = N101.getRoadSegAt(i);
			RoadSegment copy = new RoadSegment(rs);
			copy.setFreeway(S101);
			copy.setID(id++);
			S101.addRoadSeg(copy);
		}
	}
	
	public static void constructN405()
	{
		N405 = new Freeway("N405");
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
				
				temp.setX(Double.parseDouble(coords[0]));
				temp.setY(Double.parseDouble(coords[1]));
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
		S405 = new Freeway("S405");
		int id = 0;
		for(int i = (N405.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = N405.getRoadSegAt(i);
			RoadSegment copy = new RoadSegment(rs);
			copy.setFreeway(S405);
			copy.setID(id++);
			S405.addRoadSeg(copy);
		}
	}
	
	public static void constructE10()
	{
		E10 = new Freeway("E10");
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
				
				temp.setX(Double.parseDouble(coords[0]));
				temp.setY(Double.parseDouble(coords[1]));
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
		W10 = new Freeway("W10");
		int id = 0;
		for(int i = (E10.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = E10.getRoadSegAt(i);
			RoadSegment copy = new RoadSegment(rs);
			copy.setFreeway(W10);
			copy.setID(id++);
			W10.addRoadSeg(copy);
		}
	}

	public static void constructE105()
	{
		E105 = new Freeway("E105");
		try 
		{
			RoadSegment temp;
			String tempKey, line;
			int id = 0;
			reader = new BufferedReader(new FileReader("./src/The105.txt"));
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\\|");
				tempKey = split[0];
				temp = new RoadSegment(tempKey, id++, E105);
				E105.addRoadSeg(temp);
				
				//	Get coordinates
				String[] coords = split[1].split(",");
				
				temp.setX(Double.parseDouble(coords[0]));
				temp.setY(Double.parseDouble(coords[1]));
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
		W105 = new Freeway("W105");
		int id = 0;
		for(int i = (E105.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = E105.getRoadSegAt(i);
			RoadSegment copy = new RoadSegment(rs);
			copy.setFreeway(W105);
			copy.setID(id++);
			W105.addRoadSeg(copy);
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
	
	
	public static RoadSegment getPolarOppositeRoadSeg(Freeway fwy, RoadSegment rs)
	{
		
		RoadSegment polarOpposite;
		int polarOppositeIndex = 0;
		polarOppositeIndex = fwy.getNumRoadSeg() - (rs.getID() + 1);
		
		if(fwy.getName().equalsIgnoreCase("N101"))
		{
			polarOpposite = S101.getRoadSegAt(polarOppositeIndex);
		}
		else if(fwy.getName().equalsIgnoreCase("S101"))
		{
			polarOpposite = N101.getRoadSegAt(polarOppositeIndex);
		}
		else if(fwy.getName().equalsIgnoreCase("N405"))
		{
			polarOpposite = S405.getRoadSegAt(polarOppositeIndex);
		}
		else if(fwy.getName().equalsIgnoreCase("S405"))
		{
			polarOpposite = N405.getRoadSegAt(polarOppositeIndex);
		}
		else if(fwy.getName().equalsIgnoreCase("E10"))
		{
			polarOpposite = W10.getRoadSegAt(polarOppositeIndex);
		}
		else if(fwy.getName().equalsIgnoreCase("W10"))
		{
			polarOpposite = E10.getRoadSegAt(polarOppositeIndex);
		}
		else if(fwy.getName().equalsIgnoreCase("E105"))
		{
			polarOpposite = W105.getRoadSegAt(polarOppositeIndex);
		}
		else //if(getName().equalsIgnoreCase("W105"))
		{
			polarOpposite = E105.getRoadSegAt(polarOppositeIndex);
		}
		
		return polarOpposite;
		
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