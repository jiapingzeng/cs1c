package lazyTrees;

/**
 * Provide interface for functor objects
 * @param <E> Type to be processed
 */

public interface Traverser<E> {

    /**
     * Perform action while traversing
     * @param x Type to be processed
     */
    void visit(E x);
}
