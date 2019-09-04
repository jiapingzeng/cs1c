### Project folder:
jiapingzeng-cs1c-project02/

The approach used in ShoppingBag problem was not efficient enough to handle the large amount of data required by FoothillTunesStore (I believe it was mostly due to the cloning of ArrayList when generating and storing a new subset). In order to optimize the algorithm, I used TreeMap<Integer, SongEntry> to keep track of subsets, where in each pair the key is the total duration of the subset and the value is the last song in the subset. According to java documentation, TreeMap is sorted in ascending order by default which is helpful because it allows me to eliminate some subsets that are already over the target.

The reason I chose this approach was to eliminate the need to clone an existing subset when creating a new one. The map only stores the total duration of the subset and the last song in the subset. When adding a song to an existing subset, it would create a new entry with the new total duration and the new song that was added to the old subset. And when a solution is found, a helper method (getSolution) is called and gets the full set of songs backwards (until the duration of the song matches the key, which means there are no more items in the subset).

### Submitted Files:
##### src/subsetsum/SubsetSum.java: 
Finds optimal subset for ShoppingBag and FoothillTunesStore
##### src/subsetsum/GroceriesFileReader.java
Parses grocery CSV data files
##### resources/baddata.txt
Invalid data used to make sure GroceriesFileReader handles errors properly
##### resources/music_small.json
Smaller version of music_genre_subset.json used to test findSubsetOfSongs method
##### resources/RUN.txt
Output of ShoppingBag.java and FoothillTunesStore.java
##### README.md
Description of submitted files