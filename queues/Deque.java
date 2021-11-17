import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node now = head;
        @Override
        public boolean hasNext() {
            return now != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = now.item;
            now = now.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        private Item item;
        private Node prev;
        private Node next;

        private Node(Item item) {
            this.item = item;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null Pointer Item");
        }
        Node newNode = new Node(item);
        newNode.next = head;

        if (head == null) {
            tail = newNode;
        } else {
            head.prev = newNode;
        }
        head = newNode;
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null Pointer Item");
        }

        Node newNode = new Node(item);
        newNode.prev = tail;

        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty Dequeue");
        }
        Item item = head.item;

        head = head.next;

        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        --size;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty Dequeue");
        }
        Item item = tail.item;

        tail = tail.prev;

        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        --size;
        return item;
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
    }


}
