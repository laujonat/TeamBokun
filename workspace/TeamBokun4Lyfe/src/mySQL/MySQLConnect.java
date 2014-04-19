package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnect {
	private static final String USERNAME 		= "root";
	private static final String PASSWORD 		= "";
	private static final String DATABASE 		= "CS201";
	private static final String CAR_TABLE 		= "Cars";
	private static final String FREEWAY_TABLE 	= "Freeways";
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement prepStatement = null;
	private ResultSet resultSet = null;
	
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
	public void insertIntoTable(String[] headers, Object[] values, String table) throws SQLException {
		System.out.println("\nInserting into table " + table + "...");
		//	Small error check
		if(headers.length != values.length)
			throw new SQLException("Invalid parameters.");
		
		//	Build the query
		String query = "INSERT INTO " + table + " (";
		//	Add column headers
		for(int i = 0; i < headers.length; i++) {
			query += headers[i];
			if(i != headers.length - 1)
				query += ", ";
		}
		query += ") VALUES (";
		for(int i = 0; i < headers.length; i++) {
			query += "?";
			if(i != headers.length - 1)
				query += ",";
		}
		query += ")";
		
		//	Build the statement
		try {
			prepStatement = connect.prepareStatement(query);
			if(table.equals(CAR_TABLE)) {
				prepStatement.setInt(1, (Integer)values[0]);
				prepStatement.setString(2, (String)values[1]);
				prepStatement.setDouble(3, (Double)values[2]);
				prepStatement.setString(4, (String)values[3]);
				prepStatement.setInt(5, (Integer)values[4]);
				prepStatement.setInt(6, (Integer)values[5]);
			}
			else {
				//	Finish this later for freeway table
			}
			
			if(prepStatement.executeUpdate() == 0)
				System.out.println("Row insertion into " + table + " failed...");
			else
				System.out.println("Row insertion into " + table + " successful!");
		}
		catch(SQLException e) { throw e; }
		finally {
			if(prepStatement != null)
				prepStatement.close();
		}
	}
	
	//	Delete a record from a table by ID
	public void deleteFromTable(int ID, String table) throws SQLException {
		System.out.println("\nDeleting from table " + table + "...");
		
		//	Build the query
		String query = "DELETE FROM " + table + " WHERE ID = ?";
		
		//	Build the statement
		try {
			prepStatement = connect.prepareStatement(query);
			prepStatement.setInt(1, ID);
			
			if(prepStatement.executeUpdate() == 0)
				System.out.println("Deletion from table " + table + " failed...");
			else
				System.out.println("Deletion from table " + table + " successful!");
		}
		catch(SQLException e) { throw e; }
		finally {
			if(prepStatement != null)
				prepStatement.close();
		}
	}
	
//	Update String records in a table
	public void updateTable(int ID, String[] headers, Object[] values, String table) throws SQLException {
		System.out.println("\nUpdating table " + table + "...");
		
		//	Small error check
		if(headers.length != values.length)
			throw new SQLException("Invalid parameters.");
		
		//	Build query
		String query = "UPDATE " + table + " SET ";
		for(int i = 0; i < headers.length; i++) {
			query += headers[i] + " = ?";
			if(i != headers.length - 1)
				query += ", ";
		}
		query += " WHERE ID = ?";
		
		//	Build statement
		try {
			prepStatement = connect.prepareStatement(query);
			
			//	Set the values
			int i;
			for(i = 1; i <= headers.length; i++) {
				if(values[i-1] instanceof Integer)
					prepStatement.setInt(i, (Integer)values[i-1]);
				else if(values[i-1] instanceof Double)
					prepStatement.setDouble(i, (Double)values[i-1]);
				else
					prepStatement.setString(i, (String)values[i-1]);
				
			}
			
			//	Set the ID
			prepStatement.setInt(i, ID);
			
			if(prepStatement.executeUpdate() == 0)
				System.out.println("Update in table " + table + " failed...");
			else
				System.out.println("Update in table " + table + " succesful!");
		}
		catch(SQLException e) { throw e; }
		finally {
			if(prepStatement != null)
				prepStatement.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			MySQLConnect connection = new MySQLConnect();
			
//			connection.printRecordsFromTable("Cars");
//			connection.insertIntoTable(new String[] {"ID", "Direction", "Speed", "Freeway", "SegmentID", "Hour"},
//									   new Object[] {4, "East", 88.0, "105E", 15, 22},
//									   "Cars");
//			connection.deleteFromTable(4, "Cars");
//			connection.updateTable(3, new String[] {"SegmentID", "Direction"}, new Object[] {17, "North"}, "Cars");
		}
		catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
