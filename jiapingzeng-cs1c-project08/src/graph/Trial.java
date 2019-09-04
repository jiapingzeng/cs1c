package graph;

/**
 * Stores the sorting time of a recursion limit / array size combination
 */
public class Trial {

    /**
     * recursion limit
     */
    private int limit;

    /**
     * sorting time
     */
    private long time;

    /**
     * stores recursion limit and sorting time
     * @param limit recursion limit used
     * @param time sorting time
     */
    public Trial(int limit, long time) {
        this.limit = limit;
        this.time = time;
    }

    /**
     * returns recursion limit
     * @return recursion limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * returns sorting time
     * @return sorting time
     */
    public long getTime() {
        return time;
    }
}
