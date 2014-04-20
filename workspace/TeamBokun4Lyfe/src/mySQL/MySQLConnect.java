package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnect {
	private final String USERNAME 			= "root";
	private final String PASSWORD 			= "";
	private final String DATABASE 			= "CS201";
	private final String TABLE 				= "AverageSpeeds";
	
	private Connection connect				= null;
	private Statement statement				= null;
	private PreparedStatement prepStatement = null;
	private ResultSet resultSet				= null;
	
	public MySQLConnect() throws Exception {
		try {
			System.out.println("Connecting to the database " + DATABASE + "...");
			//	Load the MySQL driver for the database
			Class.forName("com.mysql.jdbc.Driver");
			
			//	Setup the connection with the database
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE, USERNAME, PASSWORD);
			
			if(connect != null)
				System.out.println("Connection successful!");
		}
		catch(Exception e) { throw e; }
	}
	
	//	Returns all average speeds of a freeway
	public double[] getAverageSpeedsOf(String freeway) throws SQLException {
		String query = "SELECT " + freeway + "Speed FROM " + TABLE;
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			
			double[] speeds = new double[24];
			
			int i = 0;
			while(resultSet.next())
				speeds[i++] = resultSet.getDouble(1);
			
			return speeds;
		}
		catch(SQLException e) { throw e; }
		finally {
			if(statement != null)
				statement.close();
		}
	}
	
	//	Return speed at hour of freewayy
	
	//	Print all records from a table
	public void printRecordsFromTable(String table) throws SQLException {
		System.out.println("\nPrinting all records from table " + table + "...");
		String query = "SELECT * FROM " + table;
		
		try {
			statement = connect.createStatement();
			
			resultSet = statement.executeQuery(query);
			
			//	Retrieve column headers
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String[] columnHeaders = new String[rsmd.getColumnCount()];
			for(int i = 1; i <= rsmd.getColumnCount(); i++)
				columnHeaders[i-1] = rsmd.getColumnName(i);
			
			while(resultSet.next()) {
				//	Print results
				for(int i = 0; i < columnHeaders.length; i++)
					System.out.format("%-20s", columnHeaders[i] + ": " + resultSet.getObject(i+1).toString());
				System.out.println();
			}
		}
		catch(SQLException e) { throw e; }
		finally {
			if(statement != null)
				statement.close();
		}
	}
	
	//	Insert into a table
//	public void insertIntoTable(String[] headers, Object[] values, String table) throws SQLException {
//		System.out.println("\nInserting into table " + table + "...");
//		//	Small error check
//		if(headers.length != values.length)
//			throw new SQLException("Invalid parameters.");
//		
//		//	Build the query
//		String query = "INSERT INTO " + table + " (";
//		//	Add column headers
//		for(int i = 0; i < headers.length; i++) {
//			query += headers[i];
//			if(i != headers.length - 1)
//				query += ", ";
//		}
//		query += ") VALUES (";
//		for(int i = 0; i < headers.length; i++) {
//			query += "?";
//			if(i != headers.length - 1)
//				query += ",";
//		}
//		query += ")";
//		
//		//	Build the statement
//		try {
//			prepStatement = connect.prepareStatement(query);
//			
//			
//			if(table.equals(TABLE)) {
//				if(values.length != 9)
//					throw new SQLException("Invalid arguments to insertIntoTable()");
//				
//				prepStatement.setInt(1, (Integer)values[0]);
//				prepStatement.setDouble(2, (Double)values[1]);
//				prepStatement.setDouble(3, (Double)values[2]);
//				prepStatement.setDouble(4, (Double)values[3]);
//				prepStatement.setDouble(5, (Double)values[4]);
//				prepStatement.setDouble(6, (Double)values[5]);
//				prepStatement.setDouble(7, (Double)values[6]);
//				prepStatement.setDouble(8, (Double)values[7]);
//				prepStatement.setDouble(9, (Double)values[8]);
//			}
////			else {
////				if(values.length != 8)
////					throw new SQLException("Invalid arguments to insertIntoTable()");
////				
////				prepStatement.setInt(1, (Integer)values[0]);
////				prepStatement.setString(2, (String)values[1]);
////				prepStatement.setString(3, (String)values[2]);
////				prepStatement.setDouble(4, (Double)values[3]);
////				prepStatement.setDouble(5, (Double)values[4]);
////				prepStatement.setDouble(6, (Double)values[5]);
////				prepStatement.setDouble(7, (Double)values[6]);
////				prepStatement.setDouble(8, (Double)values[7]);
////			}
//			
//			if(prepStatement.executeUpdate() == 0)
//				System.out.println("Row insertion into " + table + " failed...");
//			else
//				System.out.println("Row insertion into " + table + " successful!");
//		}
//		catch(SQLException e) { throw e; }
//		finally {
//			if(prepStatement != null)
//				prepStatement.close();
//		}
//	}
	
	//	Delete a record from Cars by ID
//	public void deleteCars(int ID) throws SQLException {
//		System.out.println("\nDeleting from table Cars...");
//		
//		//	Build the query
//		String query = "DELETE FROM Cars WHERE ID = ?";
//		
//		//	Build the statement
//		try {
//			prepStatement = connect.prepareStatement(query);
//			prepStatement.setInt(1, ID);
//			
//			if(prepStatement.executeUpdate() == 0)
//				System.out.println("Deletion from table Cars failed...");
//			else
//				System.out.println("Deletion from table Cars successful!");
//		}
//		catch(SQLException e) { throw e; }
//		finally {
//			if(prepStatement != null)
//				prepStatement.close();
//		}
//	}
//	
//	//	Delete a record from RoadSegments by ID/freeway
//	public void deleteSegments(int ID, String freeway) throws SQLException {
//		System.out.println("\nDeleting from table RoadSegments...");
//		
//		//	Build the query
//		String query = "DELETE FROM RoadSegments WHERE ID = ? AND Freeway = ?";
//		
//		//	Build the statement
//		try {
//			prepStatement = connect.prepareStatement(query);
//			prepStatement.setInt(1, ID);
//			prepStatement.setString(2, freeway);
//			
//			if(prepStatement.executeUpdate() == 0)
//				System.out.println("Deletion from table Cars failed...");
//			else
//				System.out.println("Deletion from table Cars successful!");
//		}
//		catch(SQLException e) { throw e; }
//		finally {
//			if(prepStatement != null)
//				prepStatement.close();
//		}
//	}
	
	//	Update records in table
	public void updateTable(int hour, String[] freeways, double[] speeds) throws SQLException {
		System.out.println("\nUpdating table " + TABLE + "...");
		
		//	Small error check
		if(freeways.length != speeds.length)
			throw new SQLException("Invalid parameters.");
		
		//	Build query
		String query = "UPDATE " + TABLE + " SET ";
		for(int i = 0; i < freeways.length; i++) {
			query += freeways[i] + " = ?";
			if(i != freeways.length - 1)
				query += ", ";
		}
		query += " WHERE Hour = ?";
		
		//	Build statement
		try {
			prepStatement = connect.prepareStatement(query);
			
			//	Set the values
			int i;
			for(i = 1; i <= speeds.length; i++) {
				prepStatement.setDouble(i, speeds[i-1]);
			}
			prepStatement.setInt(i, hour);
			
			if(prepStatement.executeUpdate() == 0)
				System.out.println("Update in table " + TABLE + " failed...");
			else
				System.out.println("Update in table " + TABLE + " succesful!");
		}
		catch(SQLException e) { throw e; }
		finally {
			if(prepStatement != null)
				prepStatement.close();
		}
	}
	
	//	Update records in table RoadSegments
//	public void updateSegments(int ID, String freeway, String[] headers, Object[] values) throws SQLException {
//		System.out.println("\nUpdating table " + SEGMENTS_TABLE + "...");
//		
//		//	Small error check
//		if(headers.length != values.length)
//			throw new SQLException("Invalid parameters.");
//		
//		//	Build query
//		String query = "UPDATE " + SEGMENTS_TABLE + " SET ";
//		for(int i = 0; i < headers.length; i++) {
//			query += headers[i] + " = ?";
//			if(i != headers.length - 1)
//				query += ", ";
//		}
//		query += " WHERE ID = ? AND Freeway = ?";
//		
//		//	Build statement
//		try {
//			prepStatement = connect.prepareStatement(query);
//			
//			//	Set the values
//			int i;
//			for(i = 1; i <= headers.length; i++) {
//				if(values[i-1] instanceof Integer)
//					prepStatement.setInt(i, (Integer)values[i-1]);
//				else if(values[i-1] instanceof Double)
//					prepStatement.setDouble(i, (Double)values[i-1]);
//				else
//					prepStatement.setString(i, (String)values[i-1]);
//				
//			}
//			
//			//	Set the ID and freeway
//			prepStatement.setInt(i++, ID);
//			prepStatement.setString(i, freeway);
//			
//			if(prepStatement.executeUpdate() == 0)
//				System.out.println("Update in table " + SEGMENTS_TABLE + " failed...");
//			else
//				System.out.println("Update in table " + SEGMENTS_TABLE + " succesful!");
//		}
//		catch(SQLException e) { throw e; }
//		finally {
//			if(prepStatement != null)
//				prepStatement.close();
//		}
//	}
	
	public static void main(String[] args) {
		try {
			MySQLConnect connection = new MySQLConnect();
			
//			connection.printRecordsFromTable(connection.TABLE);
//			connection.updateTable(14, new String[] {"S101Speed", "N405Speed"}, new double[] {3, 5});
//			connection.printRecordsFromTable(connection.TABLE);
//			connection.insertIntoTable(new String[] {"ID", "Freeway", "OnOffRamp", "Long1", "Lat1", "Long2", "Lat2", "MinSpeed"},
//									   new Object[] {3, "N101", "Exit 12", 1.0, 2.0, 3.0, 4.0, 45.0},
//									   SEGMENTS_TABLE);
//			connection.printRecordsFromTable(SEGMENTS_TABLE);
////			connection.deleteFromTable(4, "Cars");
//			connection.deleteSegments(3, "N101");
//			connection.printRecordsFromTable(SEGMENTS_TABLE);
//			connection.updateSegments(1, "E10", new String[] {"Freeway"}, new Object[] {"Balls"});
//			connection.printRecordsFromTable(SEGMENTS_TABLE);
		}
		catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
