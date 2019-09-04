package subsetsum;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Parses grocery prices CSV data files
 *
 * @author Jiaping Zeng
 */

public class GroceriesFileReader {

    /**
     * Reads data from file
     * @param path path to input file
     * @return parsed data
     */
    public ArrayList<Double> readFile(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            ArrayList<Double> prices = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    prices.add(Double.parseDouble(line.split(",")[1]));
                } catch (Exception e) {
                    System.out.println("Received invalid file format. Please check your file and try again.");
                    System.exit(0);
                }
            }
            return prices;
        } catch (FileNotFoundException e) {
            System.out.println("File " + path + " not found. Please make sure you have the correct path.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("An unknown error has occurred. Please try again.");
            System.exit(0);
        }
        // should've already either returned or exited program, return null here to make compiler happy
        return null;
    }
}
