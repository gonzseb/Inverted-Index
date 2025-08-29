class MyVector<T extends Comparable<T>> implements Iterable<T> {
    private T[] elements;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public MyVector(int initialCapacity) {
        this.capacity = initialCapacity;
        this.elements = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    public MyVector() {
        this(10);
    }

    public void add(T element) {
        if (size >= capacity) resize();
        elements[size++] = element;
    }

    public void insertSorted(T element) {
        if (size == 0) { add(element); return; }
        int pos = 0;
        while (pos < size && elements[pos].compareTo(element) < 0) pos++;
        if (pos < size && elements[pos].compareTo(element) == 0) return; // ya existe
        insert(pos, element);
    }

    public void insert(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (size >= capacity) resize();
        for (int i = size; i > index; i--) elements[i] = elements[i - 1];
        elements[index] = element;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return elements[index];
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        elements[index] = element;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public int binarySearch(T target) {
        int left = 0, right = size - 1;
        while (left <= right) {
            int mid = left + (right - left)/2;
            int cmp = elements[mid].compareTo(target);
            if (cmp == 0) return mid;
            if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= 2;
        T[] newElements = (T[]) new Comparable[capacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    public void addAll(MyVector<T> other) {
        for (T e : other) add(e);
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private int current = 0;
            public boolean hasNext() { return current < size; }
            public T next() { return elements[current++]; }
        };
    }
}