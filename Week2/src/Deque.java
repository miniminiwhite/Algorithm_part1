import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int _size;
    private Node _head;
    private Node _tail;

    private class Node {
        private Item _item;
        private Node _prev;
        private Node _next;

        Node(Item item) {
            this._item = item;
            this._prev = null;
            this._next = null;
        }

        void set_prev(Node prev) {
            this._prev = prev;
        }

        void set_next(Node next) {
            this._next = next;
        }
    }

    private class Itr implements Iterator<Item> {
        private int _cur;
        private Node _cur_node;

        Itr() {
            this._cur = 0;
            this._cur_node = Deque.this._head;
        }

        @Override
        public boolean hasNext() {
            return _cur != Deque.this.size();
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            if (this._cur++ != 0) {
                this._cur_node = this._cur_node._next;
            }
            return this._cur_node._item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public Deque() {
        this._size = 0;
        this._head = null;
        this._tail = null;
    }

    public boolean isEmpty() {
        return this._size== 0;
    }

    public int size() {
        return this._size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node n_head = new Node(item);
        n_head.set_next(this._head);
        if (this._head != null) {
            this._head.set_prev(n_head);
        } else {
            this._tail = n_head;
        }
        this._size++;
        this._head = n_head;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node n_tail = new Node(item);
        n_tail.set_prev(this._tail);
        if (this._tail != null) {
            this._tail.set_next(n_tail);
        } else {
             this._head = n_tail;
        }
        this._size++;
        this._tail = n_tail;
    }

    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node cur_head = this._head;
        this._head = cur_head._next;
        if (this._head != null) {
            this._head.set_prev(null);
        }
        cur_head.set_next(null);
        this._size--;
        return cur_head._item;
    }

    public Item removeLast() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node cur_tail = this._tail;
        this._tail = cur_tail._prev;
        if (this._tail != null) {
            this._tail.set_next(null);
        }
        cur_tail.set_prev(null);
        this._size--;
        return cur_tail._item;
    }

    public Iterator<Item> iterator() {
        return new Itr();
    }

    public static void main(String[] args) {
        Deque deque = new Deque();
        for (int i=0; i!=10; ++i) {
            if ((i&1) == 0) {
                deque.addFirst(i);
            } else {
                deque.addLast(i);
            }
        }
        Iterator iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next()+" "+iterator.hasNext());
        }
        while (!deque.isEmpty()) {
            System.out.println(deque.removeFirst());
            if (!deque.isEmpty()) {
                System.out.println(deque.removeLast());
            }
        }
    }
}