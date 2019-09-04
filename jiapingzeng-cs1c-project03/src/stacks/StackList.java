package stacks;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterable, singly-linked stacking list implementation
 * @param <T> type of data to be stored
 */

public class StackList<T> implements Iterable<T> {

    /**
     * name of the list
     */
    private final String name;

    /**
     * size of the list
     */
    private int size;

    /**
     * last added (top) element of the list
     */
    private Node top;

    /**
     * create a new StackList with given name
     * @param name name of the list
     */
    public StackList(String name) {
        this.name = name;
        size = 0;
    }

    /**
     * add new entry to the list
     * @param data new entry to be added
     */
    void push(T data) {
        if (data == null) return;
        top = new Node(data, top);
        size++;
    }

    /**
     * extract and remove the last added (top) entry of the list
     * @return top entry of the list
     */
    T pop() {
        if (top != null) {
            T data = top.getData();
            top = top.getNext();
            size--;
            return data;
        }
        return null;
    }

    /**
     * extract the last added (top) entry without removing it
     * @return top entry of the list
     */
    T peek() {
        if (top != null) {
            return top.getData();
        }
        return null;
    }

    /**
     * reset the list
     */
    void clear() {
        top = null;
        size = 0;
    }

    /**
     * return the size of the list
     * @return size of the list
     */
    int size() {
        return size;
    }

    /**
     * check if the list is empty
     * @return true if list is empty, false otherwise
     */
    boolean isEmpty() {
        return size <= 0 || top == null;
    }

    /**
     * return a string that includes name, size and each element of the list
     * @return a string that includes name, size and each element of the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" (");
        sb.append(size);
        sb.append(" element");
        if (size > 1) sb.append("s");
        sb.append(") \n[ ");
        this.forEach(e -> {
            sb.append(e);
            sb.append(" ");
        });
        sb.append("]");
        return sb.toString();
    }

    /**
     * return an iterator
     * @return iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    /**
     * store the data of each entry of the list
     */
    class Node {

        /**
         * data stored in the node
         */
        private final T data;

        /**
         * next node on the list
         */
        private Node next;

        /**
         * create a new node
         * @param data data to be stored
         * @param next next entry
         */
        Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        /**
         * return data stored in current node
         * @return data stored
         */
        T getData() {
            return data;
        }

        /**
         * return the next node on the list
         * @return next node on the list
         */
        Node getNext() {
            return next;
        }

        /**
         * sets next node
         * @param next next node
         */
        void setNext(Node next) {
            this.next = next;
        }
    }

    /**
     * iterator for StackList
     */
    class StackIterator implements Iterator<T> {

        /**
         * current node
         */
        Node current;

        /**
         * create an iterator
         */
        StackIterator() {
            current = top;
        }

        /**
         * check if there is an element next on the list
         * @return true if there is a next element, false otherwise
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * return next element on the list
         * @return next element on the list
         */
        public T next() {
            if (current == null) throw new NoSuchElementException();
            T data = current.getData();
            current = current.getNext();
            return data;
        }
    }
}
