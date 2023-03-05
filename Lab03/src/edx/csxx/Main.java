package edu.cscc;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;

/**
 * Reads a file.dat and finds improperly formatted lines
 * @author 
 * 
 */

public class Main {
	public static void main(String[] args) {
		File file = new File("phoneno.dat");
		try (Scanner scanner = new Scanner(file)) {
			int i = 0;
			while (scanner.hasNextLine()) {
				i++;
				String line = scanner.nextLine();
				if (Pattern.matches("^\\([2-9]\\d{2}\\) \\d{3}-\\d{4}$|^[2-9]\\d{2}-\\d{3}-\\d{4}$|^[2-9]\\d{9}$", line)) {
					continue;
				} else {
					System.out.println(String.format("Line %s: Invalid phone numder \"%s\"", Integer.toString(i), line));
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
