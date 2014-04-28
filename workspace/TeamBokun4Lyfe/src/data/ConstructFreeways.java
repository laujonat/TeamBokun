package data;

import graph.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConstructFreeways {

	public static Freeway N101;
	public static Freeway S101;
	
	public static Freeway N405;
	public static Freeway S405;
	
	public static Freeway E10;
	public static Freeway W10;
	
	public static Freeway E105;
	public static Freeway W105;
	
	public static Node MulhollandDrive;
	public static Node SantaMonica;
	public static Node LAX;
	public static Node LittleTokyo;
	
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
		N101 = new Freeway("N101");
		S101 = new Freeway("S101");
		N405 = new Freeway("N405");
		S405 = new Freeway("S405");
		E10 = new Freeway("E10");
		W10 = new Freeway("W10");
		E105 = new Freeway("E105");
		W105 = new Freeway("W105");
		
		ArrayList<Freeway> mdrEdges = new ArrayList<Freeway>();
		mdrEdges.add(N101);
		mdrEdges.add(S101);
		mdrEdges.add(N405);
		mdrEdges.add(S405);
		MulhollandDrive = new Node(34.16062, -118.46958, mdrEdges);
		
		ArrayList<Freeway> smEdges = new ArrayList<Freeway>();
		smEdges.add(N405);
		smEdges.add(S405);
		smEdges.add(E10);
		smEdges.add(W10);
		SantaMonica = new Node(34.03134, -118.43366, smEdges);
		
		ArrayList<Freeway> laxEdges = new ArrayList<Freeway>();
		laxEdges.add(E105);
		laxEdges.add(W105);
		laxEdges.add(N405);
		LAX = new Node(33.93002, -118.36843, laxEdges);
		
		ArrayList<Freeway> ltEdges = new ArrayList<Freeway>();
		ltEdges.add(N101);
		ltEdges.add(S101);
		ltEdges.add(E10);
		ltEdges.add(W10);
		LittleTokyo = new Node(34.05530, -118.21419, ltEdges);
	}
	
	public static void constructN101()
	{
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
			N101.addNodes(LittleTokyo);
			N101.addNodes(MulhollandDrive);
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
		int id = 0;
		for(int i = (N101.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = N101.getRoadSegAt(i);
			RoadSegment copy = new RoadSegment(rs);
			copy.setFreeway(S101);
			copy.setID(id++);
			S101.addRoadSeg(copy);
		}	
		S101.addNodes(MulhollandDrive);
		S101.addNodes(LittleTokyo);
	}
	
	public static void constructN405()
	{
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
			N405.addNodes(LAX);
			N405.addNodes(SantaMonica);
			N405.addNodes(MulhollandDrive);
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
		int id = 0;
		for(int i = (N405.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = N405.getRoadSegAt(i);
			RoadSegment copy = new RoadSegment(rs);
			copy.setFreeway(S405);
			copy.setID(id++);
			S405.addRoadSeg(copy);
		}
		S405.addNodes(MulhollandDrive);
		S405.addNodes(SantaMonica);
		S405.addNodes(LAX);
	}
	
	public static void constructE10()
	{
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
			E10.addNodes(LittleTokyo);
			E10.addNodes(SantaMonica);
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
		int id = 0;
		for(int i = (E10.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = E10.getRoadSegAt(i);
			RoadSegment copy = new RoadSegment(rs);
			copy.setFreeway(W10);
			copy.setID(id++);
			W10.addRoadSeg(copy);
		}
		W10.addNodes(SantaMonica);
		W10.addNodes(LittleTokyo);
	}

	public static void constructE105()
	{
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
			E105.addNodes(LAX);
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
		int id = 0;
		for(int i = (E105.getNumRoadSeg() - 1); i >= 0; i--)
		{
			RoadSegment rs = E105.getRoadSegAt(i);
			RoadSegment copy = new RoadSegment(rs);
			copy.setFreeway(W105);
			copy.setID(id++);
			W105.addRoadSeg(copy);
		}
		W105.addNodes(LAX);
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
	
	public static RoadSegment getRoadSegment(String freeway, String direction, String onOffKey)
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
	
	
	public static Freeway getFreeway(String freeway, String direction)
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
	
	// only need 1 direction per freeway
	public static RoadSegment getRoadSegmentAtKey(String key)
	{
		for(int i = 0; i < N101.getNumRoadSeg(); i++)
		{
			if(N101.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return N101.getRoadSegAt(i);
			}
		}
		for(int i = 0; i < E10.getNumRoadSeg(); i++)
		{
			if(E10.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return E10.getRoadSegAt(i);
			}
		}
		for(int i = 0; i < N405.getNumRoadSeg(); i++)
		{
			if(N405.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return N405.getRoadSegAt(i);
			}
		}
		for(int i = 0; i < E105.getNumRoadSeg(); i++)
		{
			if(E105.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return E105.getRoadSegAt(i);
			}
		}
		return null;
	}
	
	public static double getLatitudeAtKey(String key)
	{
		for(int i = 0; i < N101.getNumRoadSeg(); i++)
		{
			if(N101.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return N101.getRoadSegAt(i).getX();
			}
		}
		for(int i = 0; i < E10.getNumRoadSeg(); i++)
		{
			if(E10.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return E10.getRoadSegAt(i).getX();
			}
		}
		for(int i = 0; i < N405.getNumRoadSeg(); i++)
		{
			if(N405.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return N405.getRoadSegAt(i).getX();
			}
		}
		for(int i = 0; i < E105.getNumRoadSeg(); i++)
		{
			if(E105.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return E105.getRoadSegAt(i).getX();
			}
		}
		return 0;
	}
	
	public static double getLongitudeAtKey(String key)
	{
		for(int i = 0; i < N101.getNumRoadSeg(); i++)
		{
			if(N101.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return N101.getRoadSegAt(i).getY();
			}
		}
		for(int i = 0; i < E10.getNumRoadSeg(); i++)
		{
			if(E10.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return E10.getRoadSegAt(i).getY();
			}
		}
		for(int i = 0; i < N405.getNumRoadSeg(); i++)
		{
			if(N405.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return N405.getRoadSegAt(i).getY();
			}
		}
		for(int i = 0; i < E105.getNumRoadSeg(); i++)
		{
			if(E105.getRoadSegAt(i).getKey().equalsIgnoreCase(key))
			{
				return E105.getRoadSegAt(i).getY();
			}
		}
		return 0;
	}
	
}