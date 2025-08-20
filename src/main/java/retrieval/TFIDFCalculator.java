package retrieval;

import datastructures.*;
import index.*;
import processing.*;

/**
 * Construye matriz TF-IDF a partir de un InvertedIndex.
 */
public class TFIDFCalculator {

    private final InvertedIndex idx;
    private final HashTable<String,Integer> termToId; // mapeo termino -> índice de columna
    private final String[] idToTerm;
    private final Vector[] docVectors;
    private final int N; // número de documentos
    private final int T; // número de términos

    public TFIDFCalculator(InvertedIndex index) {
        this.idx = index;
        this.N = index.getDocuments().size();

        // --- indexar términos ---
        int count = 0;
        termToId = new HashTable<>();
        DoublyCircularLinkedList<String> terms = new DoublyCircularLinkedList<>();

        MyIterator<String> it = idx.getIndex().iterator(); // Falta getIndex()
        while (it.hasNext()) {
            String term = it.next();
            termToId.put(term, count++);
            terms.addLast(term);
        }

        this.T = count;
        idToTerm = new String[T];
        MyIterator<String> it2 = terms.iterator();
        int pos = 0;
        while (it2.hasNext()) idToTerm[pos++] = it2.next();

        // --- construir vectores de documentos ---
        docVectors = new Vector[N];
        MyIterator<Document> dit = idx.getDocuments().iterator();
        while (dit.hasNext()) {
            Document d = dit.next();
            Vector v = new Vector(T);
            MyIterator<String> tok = d.getTokens().iterator();
            while (tok.hasNext()) {
                String w = tok.next();
                Integer col = termToId.get(w);
                if (col != null) v.add(col, 1.0); // TF bruto
            }
            docVectors[d.getId()] = v;
        }

        // --- aplicar IDF ---
        applyIDF();
    }

    private void applyIDF() {
        for (int t = 0; t < T; t++) {
            String term = idToTerm[t];
            DoublyCircularLinkedList<Integer> postings = idx.getIndex().get(term);
            int df = postings.size();
            double idf = Math.log10((double) N / df);

            // multiplicar TF de cada documento por idf
            for (int d = 0; d < N; d++) {
                double tf = docVectors[d].get(t);
                if (tf > 0) {
                    docVectors[d].set(t, tf * idf);
                }
            }
        }
    }

    /** Devuelve el vector TF-IDF de un documento */
    public Vector getDocVector(int docId) {
        return docVectors[docId];
    }

    /** Devuelve vector TF-IDF de la query */
    public Vector buildQueryVector(DoublyCircularLinkedList<String> tokens) {
        Vector v = new Vector(T);
        MyIterator<String> it = tokens.iterator();
        while (it.hasNext()) {
            String w = it.next();
            Integer col = termToId.get(w);
            if (col != null) v.add(col, 1.0);
        }

        // multiplicar por IDF
        for (int t = 0; t < T; t++) {
            double tf = v.get(t);
            if (tf > 0) {
                String term = idToTerm[t];
                int df = idx.getIndex().get(term).size();
                double idf = Math.log10((double) N / df);
                v.set(t, tf * idf);
            }
        }
        return v;
    }

    public int numDocs() { return N; }
    public int numTerms() { return T; }
}
