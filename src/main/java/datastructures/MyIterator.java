package datastructures;

/** Iterador propio para no depender de java.util.Iterator. */
public interface MyIterator<T> {
    boolean hasNext();
    T next();
}
