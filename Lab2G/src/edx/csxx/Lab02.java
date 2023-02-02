package edx.csxx;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates an html page of natural arches in Ohio
 * @author 
 * 
 */
public class Lab02 {

	private static String pre = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<title>Natural Arches"+
			"</title>\n<meta charset=\"utf-8\">\n</head>\n<body>\n"+
			"<h1>Ohio Natural Arches</h1>\n<ul>\n";
	private static String post = "</ul>\n</body>\n</html>\n";
	private static final char DEGREES = '\u00B0';
	private static final String LI_START = "<li>";
	private static final String LI_END = "</li>\n";
	
	/**
	 * Main Method
	 * @param args not used
	 */
	public static void main(String[] args) {
		BufferedWriter bw = null;
		File file = new File("Lab2Data.txt");
		try ( BufferedReader input = new BufferedReader(new FileReader(file)); ) {
			bw = new BufferedWriter(new FileWriter(new File("arches.html")));
			bw.write(pre);
			while(input.ready()) {
				String str = input.readLine();
				if (str.equals("Name,County,Park,Geocoordinate")) {
					continue;
				} else {
					String anchorTag = getAnchorTag(str);
					String output = LI_START + anchorTag + LI_END;
					bw.write(output);
				}
			}
			bw.write(post);
		} catch(FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex2) {
			System.out.println(ex2.getMessage());
		}
		if (bw != null) {
			try { bw.close(); } catch (IOException e) { /* do nothing */ }
		}
		System.out.println("HTML file generated!");
	}
	
	/*
	 * Converts geographic coordinates to decimal
	 * @param geoCoordinates is the geographic coordinate pair assumed to be in form (degrees, minutes, seconds, direction, degrees, minutes, seconds, direction)
	 * @return a pair of doubles (decimal coordinates)
	 */
	public static double[] convertCoordinates(String geoCoordinates) {
		String[] pcs = geoCoordinates.split(" ");
		double[] decCoordinatePair = new double[2];
		for (int i = 0; i < decCoordinatePair.length; i++) {
			int h = i*4;
			double deg = Double.parseDouble(pcs[h].replace("�", ""));
			double min = Double.parseDouble(pcs[h+1].replace("'", ""));
			double sec = Double.parseDouble(pcs[h+2].replace("\"", ""));
			decCoordinatePair[i] = deg + (min/60) + (sec/3600);
			if ((pcs[h+3].toUpperCase().equals("S")) || (pcs[h+3].toUpperCase().equals("W"))) {
				decCoordinatePair[i] = decCoordinatePair[i] * -1;
				}
			}
		return decCoordinatePair;
	}
	
	/*
	 * Takes a line of text within a CSV and creates an anchor tag for use in an html page
	 * @param str is the line being read from the CSV file (String)
	 * @return the anchorTag as a string
	 */
	public static String getAnchorTag(String str) {
		String[] strList = str.split(",");
		String name = strList[0], county = strList[1], park = strList[2], geo = strList[3];
		double[] decCoordinatePair = convertCoordinates(geo);
		String linkURL = String.format("https://www.google.com/maps/search/?api=1&query=%.4f,%.4f", decCoordinatePair[0], decCoordinatePair[1]);
		String anchorTag = buildThisString(linkURL, name, county, park, decCoordinatePair);
		return anchorTag;
	}
	
	/*
	 * Builds the anchor tag using StringBuilder
	 * @param linkURL is the url to the arch (String)
	 * @param name is the arch name (String)
	 * @param county is the county where the arch is located (String)
	 * @param park is the park where the the arch is located (String)
	 * @param decCoordinatePair is the decimal coordinate pair (Double List)
	 * @return the anchorTag as a String
	 */
	public static String buildThisString(String linkURL, String name, String county, String park, double[] decCoordinatePair) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"").append(linkURL).append("\">").append(name).append("</a> at coordinates (").append(String.format("%.4f",decCoordinatePair[0])).append(",").append(String.format("%.4f",decCoordinatePair[1])).append(") is located at ").append(park).append(" in ").append(county).append(" County");
		String anchorTag = sb.toString();
		return anchorTag;
	}
		
}
