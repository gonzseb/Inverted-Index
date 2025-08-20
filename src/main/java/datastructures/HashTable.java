package datastructures;

/**
 * HashTable con direccionamiento abierto (linear probing).
 * Sin java.util.*.
 */
public class HashTable<K, V> implements MyIterable<K> {

    private static final Object DELETED = new Object();

    private Object[] keys;
    private Object[] values;
    private int n; // elementos efectivos
    private int capacity;
    private int used; // incluye DELETED

    public HashTable() {
        this(8);
    }

    public HashTable(int cap) {
        capacity = cap < 8 ? 8 : cap;
        keys = new Object[capacity];
        values = new Object[capacity];
        n = 0;
        used = 0;
    }

    private int hash(Object key) {
        int h = key.hashCode();
        if (h < 0) h = -h;
        return h % capacity;
    }

    public int size() { return n; }
    public boolean isEmpty() { return n == 0; }

    public void put(K key, V value) {
        if ((double) used / capacity >= 0.7) resize(capacity * 2);
        insert(key, value);
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        int idx = hash(key);
        for (int i = 0; i < capacity; i++) {
            int j = (idx + i) % capacity;
            Object k = keys[j];
            if (k == null) return null;
            if (k != DELETED && k.equals(key)) return (V) values[j];
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public boolean remove(K key) {
        int idx = hash(key);
        for (int i = 0; i < capacity; i++) {
            int j = (idx + i) % capacity;
            Object k = keys[j];
            if (k == null) return false;
            if (k != DELETED && k.equals(key)) {
                keys[j] = DELETED;
                values[j] = null;
                n--;
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void insert(K key, V value) {
        int idx = hash(key);
        int firstDeleted = -1;
        for (int i = 0; i < capacity; i++) {
            int j = (idx + i) % capacity;
            Object k = keys[j];
            if (k == null) {
                if (firstDeleted != -1) j = firstDeleted;
                keys[j] = key;
                values[j] = value;
                n++;
                used++;
                return;
            } else if (k == DELETED) {
                if (firstDeleted == -1) firstDeleted = j;
            } else if (k.equals(key)) {
                values[j] = value; // update
                return;
            }
        }
    }

    private void resize(int newCap) {
        Object[] oldKeys = keys;
        Object[] oldVals = values;
        int oldCap = capacity;

        capacity = newCap;
        keys = new Object[capacity];
        values = new Object[capacity];
        n = 0;
        used = 0;

        for (int i = 0; i < oldCap; i++) {
            Object k = oldKeys[i];
            if (k != null && k != DELETED) {
                @SuppressWarnings("unchecked")
                K kk = (K) k;
                @SuppressWarnings("unchecked")
                V vv = (V) oldVals[i];
                insert(kk, vv);
            }
        }
    }

    // Iterador sobre las keys
    public MyIterator<K> iterator() {
        return new MyIterator<K>() {
            int i = 0;
            public boolean hasNext() {
                while (i < capacity) {
                    if (keys[i] != null && keys[i] != DELETED) return true;
                    i++;
                }
                return false;
            }
            @SuppressWarnings("unchecked")
            public K next() {
                return (K) keys[i++];
            }
        };
    }
}
