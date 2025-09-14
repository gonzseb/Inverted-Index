package index.structures;

public class MyVectorIterator<T> {
    private final MyVector<T> vector;
    private int index = 0;

    public MyVectorIterator(MyVector<T> vector) {
        this.vector = vector;
    }

    public boolean hasNext() { return index < vector.size(); }

    public T next() { return vector.get(index++); }
}