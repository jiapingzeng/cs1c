package hashTables;

import cs1c.SongEntry;

import java.util.ArrayList;

/**
 * Stores data of the same genre
 */

public class SongsCompGenre implements Comparable<String> {

    /**
     * name of genre
     */
    private String name;

    /**
     * songs of genre
     */
    private ArrayList<SongEntry> data;

    /**
     * Initialize genre with name and empty ArrayList
     * @param name name of genre
     */
    public SongsCompGenre(String name) {
        this.name = name;
        this.data = new ArrayList<>();
    }

    /**
     * Adds song to genre
     * @param e song to be added
     */
    public void addSong(SongEntry e) {
        data.add(e);
    }

    /**
     * Compare two genres by name
     * @param o name of genre to be compared
     * @return difference between two names
     */
    @Override
    public int compareTo(String o) {
        return name.compareTo(o);
    }

    /**
     * Checks if two genres have the same name
     * @param obj Object to compare to
     * @return true of equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SongsCompGenre && ((SongsCompGenre) obj).getName().equals(name);
    }

    /**
     * Returns hashed name of genre
     * @return hashed name of genre
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Lists genre name and songs in the genre
     * @return String of genre name and songs in the genre
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(":\n");
        for (SongEntry song : data) {
            sb.append(song);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns name of genre
     * @return name of genre
     */
    public String getName() {
        return name;
    }

    /**
     * Returns songs of genre
     * @return songs of genre
     */
    public ArrayList<SongEntry> getData() {
        return data;
    }
}
