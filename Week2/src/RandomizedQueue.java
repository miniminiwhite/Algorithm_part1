import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INI_SIZE = 8;
    private int size;
    private Item[] content;

    private class Itr implements Iterator<Item> {
        private int cur;
        private final Item[] myContent;

        Itr() {
            this.cur = 0;
            this.myContent = (Item[]) new Object[RandomizedQueue.this.size];
            for (int i = 0; i != RandomizedQueue.this.size; ++i) {
                this.myContent[i] = RandomizedQueue.this.content[i];
            }
            StdRandom.shuffle(this.myContent);
        }

        @Override
        public boolean hasNext() {
            return this.cur != this.myContent.length;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return this.myContent[this.cur++];
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public RandomizedQueue() {
        this.size = 0;
        resize(INI_SIZE);
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.content[this.size++] = item;
        if (this.size == this.content.length) {
            resize(this.size << 1);
        }
    }
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int outIdx = StdRandom.uniform(this.size);
        Item outItem = this.content[outIdx];
        this.content[outIdx] = this.content[--this.size];
        this.content[this.size] = null;
        if (this.size < this.content.length >> 2) {
            resize(this.content.length >> 1);
        }
        return outItem;
    }

    public Item sample() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return this.content[StdRandom.uniform(this.size)];
    }

    public Iterator<Item> iterator() {
        return new Itr();
    }

    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        if (this.content != null) {
            for (int i = 0; i != this.size; ++i) {
                newArray[i] = this.content[i];
                this.content[i] = null;
            }
        }
        this.content = newArray;
    }
}