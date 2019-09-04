package hashTables;

import cs1c.SongEntry;

/**
 * Stores a song comparable and hashable by ID
 */

public class SongCompInt implements Comparable<Integer> {

    /**
     * Song stored
     */
    private SongEntry song;

    /**
     * Stores song
     * @param song song to be stored
     */
    public SongCompInt(SongEntry song) {
        this.song = song;
    }

    /**
     * Compares ID of song to another ID
     * @param o ID to be compared to
     * @return different between the two IDs
     */
    @Override
    public int compareTo(Integer o) {
        return song.getID() - o;
    }

    /**
     * Checks if two songs are the same
     * @param obj Object to be compared
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SongCompInt && ((SongCompInt) obj).getSong().equals(song);
    }

    /**
     * Returns song ID as hash code
     * @return song ID
     */
    @Override
    public int hashCode() {
        return song.getID();
    }

    /**
     * Returns song as a string
     * @return song as a string
     */
    @Override
    public String toString() {
        return song.toString();
    }

    /**
     * Gets stored song
     * @return stored song
     */
    public SongEntry getSong() {
        return song;
    }
}