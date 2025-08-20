package datastructures;

/**
 * Vector denso de doubles con capacidad dinámica (sin java.util).
 * Incluye dot-product: dot(...) / times(...).
 */
public final class Vector {

    private double[] a;
    private int n; // cantidad de componentes usadas

    public Vector() {
        this.a = new double[4];
        this.n = 0;
    }

    public Vector(int size) {
        if (size < 0) size = 0;
        this.a = new double[size];
        this.n = size;
    }

    public int size() { return n; }

    public void ensureCapacity(int cap) {
        if (cap <= a.length) return;
        int newCap = a.length == 0 ? 4 : a.length;
        while (newCap < cap) newCap <<= 1;
        double[] b = new double[newCap];
        for (int i = 0; i < n; i++) b[i] = a[i];
        a = b;
    }

    /** Set by index; auto-extiende con ceros si es necesario. */
    public void set(int idx, double val) {
        if (idx < 0) throw new IllegalArgumentException("idx < 0");
        if (idx >= n) {
            ensureCapacity(idx + 1);
            // rellenar con 0.0 desde n hasta idx-1
            for (int i = n; i < idx; i++) a[i] = 0.0;
            n = idx + 1;
        }
        a[idx] = val;
    }

    public double get(int idx) {
        if (idx < 0 || idx >= n) return 0.0; // fuera de rango = 0 (útil en TF-IDF disperso)
        return a[idx];
    }

    /** Suma en idx (útil para contar TF). */
    public void add(int idx, double delta) {
        set(idx, get(idx) + delta);
    }

    /** Producto punto (dot product). */
    public double dot(Vector other) {
        int m = (this.n < other.n) ? this.n : other.n;
        double s = 0.0;
        for (int i = 0; i < m; i++) s += this.a[i] * other.a[i];
        return s;
    }

    /** Alias “times” para cumplir con el espíritu de usar * (no disponible en Java). */
    public double times(Vector other) { return dot(other); }

    /** Norma Euclídea. */
    public double norm() {
        double s = 0.0;
        for (int i = 0; i < n; i++) s += a[i] * a[i];
        return Math.sqrt(s);
    }

    /** Escalamiento por escalar (in-place). */
    public void scale(double alpha) {
        for (int i = 0; i < n; i++) a[i] *= alpha;
    }

    /** Clona contenido. */
    public Vector copy() {
        Vector v = new Vector(n);
        for (int i = 0; i < n; i++) v.a[i] = this.a[i];
        return v;
    }

    /** Para depuración rápida. */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(", ");
            sb.append(a[i]);
        }
        sb.append(']');
        return sb.toString();
    }
}
