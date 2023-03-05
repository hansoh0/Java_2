package edu.cscc;

import java.sql.*;

public class Main {

	// Database credentials
	static final String USER = "";
	static final String PASS = "";
	static final String PORT = "";
	static final String HOST = "";
	static final String DATABASE = "";

	// Connection URL
	static final String connectionURL = "jdbc:sqlserver://"+HOST+":"+PORT+";databaseName="+DATABASE+";user="+USER+";password="+PASS+";encrypt=true;TrustServerCertificate=true";
	
	// Connects to DB and prints data from customers table
	// @author 
	public static void main(String[] args) {
		System.out.println("Connection URL: "+connectionURL);
		System.out.println("Connecting to database...");
		System.out.println("Creating statement...");
		
		// Open a connection and autoclose
		try (Connection conn = DriverManager.getConnection(connectionURL); Statement stmt = conn.createStatement();) {
			
			// Set the row format, the header, and SQL query
			String format = "%-40s %-50s %-30s %-20s %-20s %-20s";
			String header = String.format((format+"\n%s"), "Company Name", "Address", "City", "Region", "Postal Code", "Country","-".repeat(180));
			String sql = "SELECT COALESCE(CompanyName, 'n/a') AS CompanyName, COALESCE(Address, 'n/a') AS Address, COALESCE(City, 'n/a') AS City, COALESCE(Region, 'n/a') AS Region, COALESCE(PostalCode, 'n/a')AS PostalCode, COALESCE(Country , 'n/a') AS Country FROM Customers;";
			
			// Get the result set
			System.out.println("Sending query...\n");
			ResultSet rs = stmt.executeQuery(sql);

			// Extract data and print data from result set row by row
			System.out.println(header);
			while (rs.next()) {
				String CompanyName = rs.getString("CompanyName");
				String Address = rs.getString("Address");
				String City = rs.getString("City");
				String Region = rs.getString("Region");
				String PostalCode = rs.getString("PostalCode");
				String Country = rs.getString("Country");
				String Row = String.format(format, CompanyName, Address, City, Region, PostalCode, Country);
		        System.out.println(Row);
			}
			
			// Close the result set
			rs.close();
			
		// Handle DB errors
		} catch (SQLException se) {
			se.printStackTrace();
		} 
		System.out.println("\nFinished.");
	}
}
