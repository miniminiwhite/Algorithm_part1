import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    static int ini_size = 8;
    private int _size;
    private Item[] _content;

    private class Itr implements Iterator<Item> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
            return null;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public RandomizedQueue() {
        this._size = 0;
        resize(ini_size);
    }

    public boolean isEmpty() {
        return this._size == 0;
    }

    public int size() {
        return this._size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this._content[this._size++] = item;
        if (this._size == this._content.length) {
            resize(this._size << 1);
        }
    }
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int out_idx = StdRandom.uniform(this._size);
        Item out_item = this._content[out_idx];
        this._content[out_idx] = this._content[--this._size];
        if (this._size < this._content.length >> 2) {
            resize(this._content.length >> 1);
        }
        return out_item;
    }

    public Item sample() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return this._content[StdRandom.uniform(this._size)];
    }

    public Iterator<Item> iterator() {
        return new Itr();
    }

    private void resize(int size) {
        Item[] n_array = (Item[]) new Object[size];
        if (this._content != null) {
            int cur = 0;
            for (int i=0; i!= this._size; ++i) {
                n_array[i] = this._content[i];
                this._content[i] = null;
            }
        }
        this._content = n_array;
    }
}