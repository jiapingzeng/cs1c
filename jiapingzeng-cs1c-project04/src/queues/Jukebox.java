package queues;

import cs1c.SongEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Reads text file and stores songs listed in the file in different playlists
 * @author Jiaping Zeng
 */

public class Jukebox {

    /**
     * favorite playlist
     */
    private Queue<SongEntry> favoritePL;

    /**
     * road trip playlist
     */
    private Queue<SongEntry> roadTripPL;

    /**
     * lounge playlist
     */
    private Queue<SongEntry> loungePL;

    /**
     * initialize playlists
     */
    Jukebox() {
        favoritePL = new Queue<>("favorites");
        roadTripPL = new Queue<>("road trip");
        loungePL = new Queue<>("lounge");
    }

    /**
     * reads song titles from file, locate them in music database and add to playlists
     * @param file list of song titles
     * @param songs music database
     */
    void fillPlaylists(String file, SongEntry[] songs) {
        HashMap<String, SongEntry> map = new HashMap<>();
        for (SongEntry song : songs) {
            map.put(song.getTitle(), song);
        }
        try {
            Scanner scanner = new Scanner(new File(file));
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (!map.containsKey(line[1])) {
                    System.out.println("Unable to find song \"" + line[1] + "\". Please make sure you have the correct song title");
                    continue;
                }
                switch (line[0]) {
                    case "favorites":
                        favoritePL.enqueue(map.get(line[1]));
                        break;
                    case "road trip":
                        roadTripPL.enqueue(map.get(line[1]));
                        break;
                    case "lounge":
                        loungePL.enqueue(map.get(line[1]));
                        break;
                    default:
                        System.out.println("Invalid playlist name \"" + line[0] + "\". Please check your playlist names.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + file + " not found. Please make sure you have the correct path.");
            System.exit(0);
        }
    }

    /**
     * accessor for favorite playlist
     * @return favorite playlist
     */
    public Queue<SongEntry> getFavoritePL() {
        return favoritePL;
    }

    /**
     * accessor for road trip playlist
     * @return road trip playlist
     */
    public Queue<SongEntry> getRoadTripPL() {
        return roadTripPL;
    }

    /**
     * accessor for lounge playlist
     * @return lounge playlist
     */
    public Queue<SongEntry> getLoungePL() {
        return loungePL;
    }
}
