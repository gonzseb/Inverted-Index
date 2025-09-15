package index.indexing;

import index.domain.Document;
import index.domain.DocumentRegistry;
import index.domain.Posting;
import index.domain.Term;
import index.interfaces.IInvertedIndex;
import index.structures.MyHashTable;
import index.structures.MyVector;
import index.structures.MyVectorIterator;

public class InvertedIndex implements IInvertedIndex {
    private final MyHashTable<String, Term> table;
    private final DocumentRegistry documentRegistry;

    private MyVector<Term> sortedTerms;
    private boolean isSorted = false;

    public InvertedIndex(int hashCapacity) {
        this.table = new MyHashTable<>(hashCapacity);
        this.documentRegistry = new DocumentRegistry();
    }

    @Override
    public int registerDocument(String path) {
        return documentRegistry.register(path);
    }

    @Override
    public Document getDocumentMetadata(int docId) {
        return documentRegistry.getMetadata(docId);
    }

    @Override
    public void insertToken(String token, int docId) {
        Term t = table.get(token);
        if (t == null) {
            t = new Term(token);
            table.put(token, t);
            isSorted = false; // nueva inserción invalida orden
        }
        t.addOccurrence(docId);
        documentRegistry.getMetadata(docId).incrementLength();
    }

    @Override
    public void removeTerm(String token) {
        table.remove(token);
        isSorted = false; // eliminación invalida orden
    }

    @Override
    public Term findTerm(String token) { return table.get(token); }

    @Override
    public MyVector<Term> getAllTerms() {
        if (!isSorted) {
            MyVector<MyHashTable.MyEntry<String, Term>> entries = table.entries();
            sortedTerms = new MyVector<>();
            MyVectorIterator<MyHashTable.MyEntry<String, Term>> it = entries.iterator();
            while (it.hasNext()) sortedTerms.add(it.next().value);
            lsdRadixSort(sortedTerms);
            isSorted = true;
        }
        return sortedTerms;
    }

    @Override
    public int getDocumentCount() { return documentRegistry.size(); }

    @Override
    public void computeDocumentVectorNorms() {
        int N = getDocumentCount();
        // iniciar acumuladores
        MyHashTable<Integer, Double> docSumSquares = new MyHashTable<>(Math.max(16, N * 2));

        MyVector<Term> allTerms = getAllTerms();
        MyVectorIterator<Term> it = allTerms.iterator();
        while (it.hasNext()) {
            Term t = it.next();
            int df = t.getDf();
            if (df <= 0) continue;
            double idf = Math.log10((double) N / (double) df);
            MyVectorIterator<Posting> pit = t.getPostings().iterator();
            while (pit.hasNext()) {
                Posting p = pit.next();
                int docId = p.getDocId();
                double weight = p.getTf() * idf;
                Double prev = docSumSquares.get(docId);
                if (prev == null) prev = 0.0;
                prev = prev + weight * weight;
                docSumSquares.put(docId, prev);
            }
        }

        // guardar norma en metadata del documento
        MyVector<MyHashTable.MyEntry<Integer, Double>> entries = docSumSquares.entries();
        MyVectorIterator<MyHashTable.MyEntry<Integer, Double>> entIt = entries.iterator();
        while (entIt.hasNext()) {
            MyHashTable.MyEntry<Integer, Double> e = entIt.next();
            int docId = e.key;
            double norm = Math.sqrt(e.value);
            Document md = getDocumentMetadata(docId);
            if (md != null) md.setVectorNorm(norm);
        }
    }

    // Implementación RadixSort LSD interna
    private void lsdRadixSort(MyVector<Term> arr) {
        if (arr.isEmpty()) return;
        int N = arr.size();
        int R = 27; // 26 letras + vacío
        int maxLen = 15;

        MyVector<Term> aux = new MyVector<>(N);
        for (int i = 0; i < N; i++) aux.add(null);

        // recorrer de derecha a izquierda
        for (int d = maxLen - 1; d >= 0; d--) {
            int[] count = new int[R + 1];

            // 1. contar
            for (int i = 0; i < N; i++) {
                int c = charAt(arr.get(i).getTerm(), d);
                count[c + 1]++;
            }

            // 2. acumular
            for (int r = 0; r < R; r++) count[r + 1] += count[r];

            // 3. distribuir
            for (int i = 0; i < N; i++) {
                int c = charAt(arr.get(i).getTerm(), d);
                aux.set(count[c], arr.get(i));
                count[c]++;
            }

            // 4. copiar de vuelta
            for (int i = 0; i < N; i++) arr.set(i, aux.get(i));
        }
    }

    private int charAt(String s, int d) {
        if (d < 0 || d >= s.length()) return 0; // vacío
        return (s.charAt(d) - 'a') + 1; // 'a'->1 ... 'z'->26
    }
}