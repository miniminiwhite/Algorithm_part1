import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node head;
    private Node tail;

    private class Node {
        final private Item item;
        private Node prev;
        private Node next;

        Node(Item item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }

        void setPrev(Node prev) {
            this.prev = prev;
        }

        void setNext(Node next) {
            this.next = next;
        }
    }

    private class Itr implements Iterator<Item> {
        private int cur;
        private Node curNode;

        Itr() {
            this.cur = 0;
            this.curNode = Deque.this.head;
        }

        @Override
        public boolean hasNext() {
            return cur != Deque.this.size();
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            if (this.cur != 0) {
                this.curNode = this.curNode.next;
            }
            this.cur++;
            return this.curNode.item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public Deque() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node newHead = new Node(item);
        newHead.setNext(this.head);
        if (this.head != null) {
            this.head.setPrev(newHead);
        } else {
            this.tail = newHead;
        }
        this.size++;
        this.head = newHead;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node newTail = new Node(item);
        newTail.setPrev(this.tail);
        if (this.tail != null) {
            this.tail.setNext(newTail);
        } else {
             this.head = newTail;
        }
        this.size++;
        this.tail = newTail;
    }

    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node curHead = this.head;
        this.head = curHead.next;
        if (this.head != null) {
            this.head.setPrev(null);
        } else {
            this.tail = null;
        }
        curHead.setNext(null);
        this.size--;
        return curHead.item;
    }

    public Item removeLast() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node curTail = this.tail;
        this.tail = curTail.prev;
        if (this.tail != null) {
            this.tail.setNext(null);
        } else {
            this.head = null;
        }
        curTail.setPrev(null);
        this.size--;
        return curTail.item;
    }

    public Iterator<Item> iterator() {
        return new Itr();
    }
}