package index.structures;

public class MyVector<T> {
    private Object[] data;
    private int size;

    public MyVector() { this(16); }

    public MyVector(int initialCapacity) {
        if (initialCapacity < 16) initialCapacity = 16;
        data = new Object[initialCapacity];
        size = 0;
    }

    // Añadir al final O(1) amortizado, si el arreglo se llena, puntualmente tomaría O(n)
    public void add(T element) {
        if (size == data.length) resize();
        data[size++] = element;
    }

    public void set(int index, T element) {
        checkIndex(index);
        data[index] = element;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    // O(n)
    private void resize() {
        int newCap = data.length * 2;
        Object[] nd = new Object[newCap];
        System.arraycopy(data, 0, nd, 0, data.length);
        data = nd;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    public MyVectorIterator<T> iterator() {
        return new MyVectorIterator<>(this);
    }
}