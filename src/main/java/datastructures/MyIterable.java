package datastructures;

/** Análogo a java.lang.Iterable pero local para nuestro proyecto. */
public interface MyIterable<T> {
    MyIterator<T> iterator();
}
