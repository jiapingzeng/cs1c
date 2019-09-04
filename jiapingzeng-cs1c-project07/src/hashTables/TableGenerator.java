package hashTables;

import cs1c.SongEntry;

import java.util.ArrayList;

/**
 * Generates two hash tables of SongCompInt and SongsCompGenre
 */

public class TableGenerator {

    /**
     * hash table of SongCompInt
     */
    private FHhashQPwFind<Integer, SongCompInt> IDTable;

    /**
     * hash table of SongsCompGenre
     */
    private FHhashQPwFind<String, SongsCompGenre> genreTable;

    /**
     * names of genres
     */
    private ArrayList<String> genreNames;

    /**
     * Initializes IDTable, genreTable and genreNames
     */
    public TableGenerator() {
        IDTable = new FHhashQPwFind<>();
        genreTable = new FHhashQPwFind<>();
        genreNames = new ArrayList<>();
    }

    /**
     * populate hash table of SongCompInt
     * @param songs songs to be stored
     * @return table of SongCompInt
     */
    public FHhashQPwFind<Integer, SongCompInt> populateIDtable(SongEntry[] songs) {
        for (SongEntry song : songs) {
            IDTable.insert(new SongCompInt(song));
        }
        return IDTable;
    }

    /**
     * populate hash table of SongsCompGenre
     * @param songs songs to be stored
     * @return table of SongsCompGenre
     */
    public FHhashQPwFind<String, SongsCompGenre> populateGenreTable(SongEntry[] songs) {
        for (SongEntry song : songs) {
            String genre = song.getGenre();
            if (!genreTable.contains(new SongsCompGenre(genre))) {
                SongsCompGenre scg = new SongsCompGenre(genre);
                scg.addSong(song);
                if (genreTable.insert(scg)) {
                    genreNames.add(genre);
                }
            } else {
                genreTable.find(genre).addSong(song);
            }
        }
        return genreTable;
    }

    /**
     * Returns list of genre names
     * @return list of genre names
     */
    public ArrayList<String> getGenreNames() {
        return genreNames;
    }
}
