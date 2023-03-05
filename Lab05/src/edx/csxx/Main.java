package edu.cscc;

import java.sql.*;
import java.util.Scanner;

public class Main {

	// Database credentials
	static final String USER = "";
	static final String PASS = "";
	static final String PORT = "";
	static final String HOST = "";
	static final String DATABASE = "";
	
	// Scanner OBJ
	private static Scanner input = new Scanner(System.in);

	// Connection URL
	static final String connectionURL = "jdbc:sqlserver://"+HOST+":"+PORT+";databaseName="+DATABASE+";user="+USER+";password="+PASS+";encrypt=true;TrustServerCertificate=true";
	
	// Connects to DB, Gets user supplied country and prints related data rows from customers table
	// @author
	public static void main(String[] args) {
		
		System.out.println("Customer Search by Country");
		PreparedStatement stmt = null;
		
		// Open a connection and autoclose
		try (Connection conn = DriverManager.getConnection(connectionURL);) {
			
			// Get user country
			System.out.print("Enter Country Name: ");
			String usrCountry = input.nextLine();
			
			// Set the row format, the header and sql query
			String format = "%-40s %-50s %-30s %-20s %-15s %-15s";
			String header = String.format((format+"\n%s"), "Company Name", "Address", "City", "Region", "Postal", "Country","=".repeat(170));
			String sql = "SELECT COALESCE(CompanyName, 'n/a') AS CompanyName, COALESCE(Address, 'n/a') AS Address, COALESCE(City, 'n/a') AS City, COALESCE(Region, 'n/a') AS Region, COALESCE(PostalCode, 'n/a')AS PostalCode, COALESCE(Country , 'n/a') AS Country FROM Customers WHERE Country = ?;";
			
			// Sanitize the statement
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,usrCountry);
			
			// Execute the SQL and get the result set
			ResultSet rs = stmt.executeQuery();

			// Extract data and print data from result set row by row
			System.out.println("\n"+header);
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
			System.out.println("-".repeat(170));	
			
			// Close result set
			rs.close();
			
		// Handle DB errors
		} catch (SQLException se) {
			se.printStackTrace();
		} 
	}
}
