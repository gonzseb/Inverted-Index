package datastructures;

/**
 * Lista doblemente ligada circular minimalista sin java.util.
 * Incluye iterador propio (MyIterator).
 */
public class DoublyCircularLinkedList<T> implements MyIterable<T> {

    private static final class Node<T> {
        T data;
        Node<T> prev, next;
        Node(T d) { this.data = d; }
    }

    private Node<T> head, tail;
    private int size = 0;

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    /** Inserta al inicio de la lista */
    public void addFirst(T value) {
        Node<T> n = new Node<>(value);
        if (head == null) {
            head = tail = n;
            head.next = head.prev = head; // circular
        } else {
            n.next = head;
            n.prev = tail;
            head.prev = n;
            tail.next = n;
            head = n;
        }
        size++;
    }

    /** Inserta al final de la lista */
    public void addLast(T value) {
        Node<T> n = new Node<>(value);
        if (tail == null) {
            head = tail = n;
            head.next = head.prev = head; // circular
        } else {
            n.prev = tail;
            n.next = head;
            tail.next = n;
            head.prev = n;
            tail = n;
        }
        size++;
    }

    public T peekFirst() { return head == null ? null : head.data; }
    public T peekLast()  { return tail == null ? null : tail.data; }

    public T removeFirst() {
        if (head == null) return null;
        T val = head.data;
        if (head == tail) { // un solo elemento
            head = tail = null;
        } else {
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }
        size--;
        return val;
    }

    public T removeLast() {
        if (tail == null) return null;
        T val = tail.data;
        if (head == tail) { // un solo elemento
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = head;
            head.prev = tail;
        }
        size--;
        return val;
    }

    /** Elimina la primera aparición de value */
    public boolean remove(T value) {
        if (head == null) return false;
        Node<T> cur = head;
        for (int i = 0; i < size; i++) {
            if (cur.data == value || (cur.data != null && cur.data.equals(value))) {
                unlink(cur);
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    private void unlink(Node<T> x) {
        if (size == 1) { // solo un nodo
            head = tail = null;
        } else {
            Node<T> p = x.prev, n = x.next;
            p.next = n;
            n.prev = p;
            if (x == head) head = n;
            if (x == tail) tail = p;
        }
        size--;
    }

    /** Iterador hacia adelante (no infinito) */
    public MyIterator<T> iterator() {
        return new MyIterator<T>() {
            private Node<T> cur = head;
            private int iterated = 0;
            public boolean hasNext() { return iterated < size; }
            public T next() {
                T d = cur.data;
                cur = cur.next;
                iterated++;
                return d;
            }
        };
    }

    /** Iterador inverso (no infinito) */
    public MyIterator<T> reverseIterator() {
        return new MyIterator<T>() {
            private Node<T> cur = tail;
            private int iterated = 0;
            public boolean hasNext() { return iterated < size; }
            public T next() {
                T d = cur.data;
                cur = cur.prev;
                iterated++;
                return d;
            }
        };
    }
}
