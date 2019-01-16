import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    static int ini_size = 8;
    private int _size;
    private Item[] _content;

    private class Itr implements Iterator<Item> {
        private int _cur;
        private Item[] _my_content;

        Itr() {
            int cnt = 0;

            this._cur = 0;
            this._my_content = (Item[]) new Object[RandomizedQueue.this._size];
            for (Item i: RandomizedQueue.this._content) {
                if (i != null) {
                    this._my_content[cnt++] = i;
                }
            }
            StdRandom.shuffle(this._my_content);
        }

        @Override
        public boolean hasNext() {
            return this._cur != this._my_content.length;
        }

        @Override
        public Item next() {
            return this._my_content[this._cur++];
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

    public static void main(String[] args) {
        RandomizedQueue randomizedQueue = new RandomizedQueue();
        for (int i=0; i!= 10; ++i) {
            randomizedQueue.enqueue(i);
        }
        Iterator iterator1 = randomizedQueue.iterator();
        Iterator iterator2 = randomizedQueue.iterator();
        while (true) {
            if (!iterator1.hasNext() && !iterator2.hasNext()) {
                break;
            }
            if (iterator1.hasNext()) {
                System.out.print(iterator1.next()+" ");
            } else {
                System.out.print("X ");
            }
            if (iterator2.hasNext()) {
                System.out.print(iterator2.next()+" ");
            } else {
                System.out.print("X");
            }
            System.out.println();
        }
        for (int i=0; i!=10; ++i) {
            System.out.print(randomizedQueue.sample()+" ");
        }
        System.out.println();

        while (!randomizedQueue.isEmpty()) {
            System.out.print(randomizedQueue.dequeue()+" ");
        }
        System.out.println();
    }
}