import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        if (size >= 0) System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // Resize
        if (size == items.length) {
            resize(2 * size);
        }

        if (size == 0) {
            items[size++] = item;
            return;
        }

        // Random index

        int randomIndex = StdRandom.uniform(size);
        Item randomItem = items[randomIndex];
        items[randomIndex] = item;
        items[size++] = randomItem;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // Resize
        if (size == items.length / 4) resize(items.length / 2);

        if (size == 1) {
            return items[--size];
        }
        int randomIndex = StdRandom.uniform(size);
        Item item = items[randomIndex];
        items[randomIndex] = items[--size];
        items[size] = null;
        return item;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        return items[randomIndex];
    }


    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int index = 0;
        private int[] randomIndex = new int[size];

        public ArrayIterator() {
            for (int i = 0; i < size; i++) {
                randomIndex[i] = i;
            }
            StdRandom.shuffle(randomIndex);
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new  NoSuchElementException();
            }

            return items[randomIndex[index++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello Github");
    }
}
