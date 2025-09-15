package index.structures;

public class MyHashTable<K, V> {
    private MyVector<MyEntry<K, V>> table;
    private int capacity;
    private int size;

    public MyHashTable(int initialCapacity) {
        this.capacity = nextPowerOfTwo(initialCapacity);
        this.table = new MyVector<>(capacity);
        for (int i = 0; i < capacity; i++) table.add(null);
        this.size = 0;
    }

    public void put(K key, V value) {
        int idx = hash(key);
        while (true) {
            MyEntry<K, V> entry = table.get(idx);
            if (entry == null) {
                table.set(idx, new MyEntry<>(key, value));
                size++;
                if (size > capacity * 0.7) resize();
                return;
            }
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            idx = (idx + 1) & (capacity - 1);
        }
    }

    public V get(K key) {
        int idx = hash(key);
        while (true) {
            MyEntry<K, V> entry = table.get(idx);
            if (entry == null) return null;
            if (entry.key.equals(key)) return entry.value;
            idx = (idx + 1) & (capacity - 1);
        }
    }

    public boolean containsKey(K key) { return get(key) != null; }
    public int size() { return size; }

    public MyVector<MyEntry<K, V>> entries() {
        MyVector<MyEntry<K, V>> all = new MyVector<>();
        for (int i = 0; i < capacity; i++) {
            MyEntry<K, V> entry = table.get(i);
            if (entry != null) all.add(entry);
        }
        return all;
    }

    public void remove(K key) {
        int idx = hash(key);
        while (true) {
            MyEntry<K, V> entry = table.get(idx);
            if (entry == null) return;
            if (entry.key.equals(key)) {
                table.set(idx, null);
                size--;
                idx = (idx + 1) & (capacity - 1);
                while (true) {
                    MyEntry<K, V> moved = table.get(idx);
                    if (moved == null) break;
                    table.set(idx, null);
                    size--;
                    put(moved.key, moved.value);
                    idx = (idx + 1) & (capacity - 1);
                }
                return;
            }
            idx = (idx + 1) & (capacity - 1);
        }
    }

    private int hash(K key) {
        int h = polynomialHash(key.toString());
        h = h & 0x7fffffff;
        return h & (capacity - 1);
    }

    private int polynomialHash(String s) {
        int h = 0, p = 31;
        for (int i = 0; i < s.length(); i++) {
            h = h * p + s.charAt(i);
        }
        return h;
    }

    private void resize() {
        MyHashTable<K, V> newTable = new MyHashTable<>(capacity * 2);
        for (int i = 0; i < capacity; i++) {
            MyEntry<K, V> entry = table.get(i);
            if (entry != null) newTable.put(entry.key, entry.value);
        }
        this.table = newTable.table;
        this.capacity = newTable.capacity;
        this.size = newTable.size;
    }

    private int nextPowerOfTwo(int n) {
        int p = 1;
        while (p < n) p <<= 1;
        return p;
    }

    public static class MyEntry<K, V> {
        public final K key;
        public V value;
        public MyEntry(K key, V value) { this.key = key; this.value = value; }
    }
}