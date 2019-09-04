package hashTables;

import java.util.NoSuchElementException;

/**
 * Provides find functionality for FHhashQP
 * @param <KeyType> type of key
 * @param <E> type of data
 */

public class FHhashQPwFind<KeyType, E extends Comparable<KeyType>> extends FHhashQP<E> {

    /**
     * finds item by key
     * @param key key of item
     * @return item found
     */
    E find(KeyType key) {
        int pos = findPosKey(key);
        if (mArray[pos].state == FHhashQP.ACTIVE) {
            return mArray[pos].data;
        } else throw new NoSuchElementException();
    }

    /**
     * Finds hash of key
     * @param key key to be hashed
     * @return hash of key
     */
    private int myHashKey(KeyType key) {
        int hashVal;
        hashVal = key.hashCode() % mTableSize;
        if (hashVal < 0) hashVal += mTableSize;
        return hashVal;
    }

    /**
     * Finds next available index
     * @param key key of item
     * @return index available
     */
    private int findPosKey(KeyType key) {
        int kthOddNum = 1;
        int index = myHashKey(key);
        while (mArray[index].state != EMPTY && mArray[index].data.equals(key)) {
            index += kthOddNum;
            kthOddNum += 2;
            if (index >= mTableSize) index -= mTableSize;
        }
        return index;
    }
}
