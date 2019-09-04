package hashTables;

public class HashEntry<E> {

    public E data;
    public int state;

    public HashEntry() {
        this(null, FHhashQP.EMPTY);
    }

    public HashEntry(E data, int state) {
        this.data = data;
        this.state = state;
    }
}
