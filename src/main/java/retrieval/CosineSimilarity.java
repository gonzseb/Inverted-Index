package retrieval;

import datastructures.Vector;

/** Calcula similitud coseno entre vectores. */
public class CosineSimilarity {
    public static double cosine(Vector a, Vector b) {
        double dot = a.dot(b);
        double na = a.norm();
        double nb = b.norm();
        if (na == 0 || nb == 0) return 0.0;
        return dot / (na * nb);
    }
}
