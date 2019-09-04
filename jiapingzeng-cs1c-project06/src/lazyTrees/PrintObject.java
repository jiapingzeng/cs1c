package lazyTrees;

/**
 * Print out every object
 * @param <E> Type to be processed
 */

public class PrintObject<E> implements Traverser<E> {
    /**
     * Print out object
     * @param x Type to be processed
     */
    @Override
    public void visit(E x) {
        System.out.print(x + " ");
    }
}