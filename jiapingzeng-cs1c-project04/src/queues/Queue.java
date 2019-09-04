package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of a singly-linked queue data structure
 * @param <T> type of data to be stored
 */

public class Queue<T> implements Iterable<T> {

    /**
     * name of the list
     */
    private final String name;

    /**
     * front of the list
     */
    private Node head;

    /**
     * rear of the list
     */
    private Node tail;

    /**
     * size of the list
     */
    private int size;

    /**
     * initialize list
     * @param name name of the list
     */
    Queue(String name) {
        this.name = name;
        size = 0;
    }

    /**
     * add a new item to the rear end of the list
     * @param item item to be added
     */
    void enqueue(T item) {
        Node node = new Node(item);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        size++;
    }

    /**
     * remove an item from the front of the list
     * @return item at the front of the list
     */
    T dequeue() {
        if (size <= 0 || head == null) {
            throw new NoSuchElementException();
        }
        Node node = head;
        head = head.getNext();
        if (head == null) {
            tail = null;
        }
        size--;
        return node.getData();
    }

    /**
     * return an item from the front of the list without removing it
     * @return item at the front of the list
     */
    T peek() {
        if (size <= 0 || head == null) return null;
        return head.getData();
    }

    /**
     * check if the list is empty
     * @return true if list is empty, false otherwise
     */
    boolean isEmpty() {
        return size <= 0;
    }

    /**
     * return the size of the list
     * @return size of the list
     */
    int size() {
        return size;
    }

    /**
     * return a string that includes name and each element of the list
     * @return a string that includes name and each element of the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n[");
        if (head != null) {
            Node current = head;
            sb.append(current.getData());
            while (current.getNext() != null) {
                sb.append("\n");
                current = current.getNext();
                sb.append(current.getData());
            }
        } else sb.append(" ");
        sb.append("]");
        return sb.toString();
    }

    /**
     * return the name of the list
     * @return name of the list
     */
    public String getName() {
        return name;
    }

    /**
     * return an iterator
     * @return iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
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
         */
        Node(T data) {
            this.data = data;
            this.next = null;
        }

        /**
         * return data stored in current node
         * @return data stored
         */
        public T getData() {
            return data;
        }

        /**
         * return the next node on the list
         * @return next node on the list
         */
        public Node getNext() {
            return next;
        }

        /**
         * sets next node
         * @param next next node
         */
        public void setNext(Node next) {
            this.next = next;
        }
    }

    /**
     * iterator for Queue
     */
    class QueueIterator implements Iterator<T> {

        /**
         * current node
         */
        Node current;

        /**
         * create an iterator
         */
        QueueIterator() {
            current = head;
        }

        /**
         * check if there is an element next on the list
         * @return true if there is a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * return next element on the list
         * @return next element on the list
         */
        @Override
        public T next() {
            if (current == null) throw new NoSuchElementException();
            T data = current.getData();
            current = current.getNext();
            return data;
        }
    }
}
