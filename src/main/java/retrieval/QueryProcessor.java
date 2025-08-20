package retrieval;

import datastructures.*;
import index.*;
import processing.*;

public class QueryProcessor {

    private final TFIDFCalculator calc;
    private final Tokenizer tokenizer;
    private final InvertedIndex idx;
    private static final int SCALE = 1_000_000; // factor de precisión

    public QueryProcessor(InvertedIndex idx, TFIDFCalculator calc, Tokenizer tok) {
        this.idx = idx;
        this.calc = calc;
        this.tokenizer = tok;
    }

    public void search(String query) {
        DoublyCircularLinkedList<String> qtoks = tokenizer.tokenize(query);
        Vector qvec = calc.buildQueryVector(qtoks);

        // similitud con cada documento
        double[] scores = new double[calc.numDocs()];
        int[] scaled = new int[calc.numDocs()];
        int[] docIds = new int[calc.numDocs()];

        for (int d = 0; d < calc.numDocs(); d++) {
            double s = CosineSimilarity.cosine(qvec, calc.getDocVector(d));
            scores[d] = s;
            scaled[d] = (int)(s * SCALE);
            docIds[d] = d;
        }

        // ordenar usando Radix Sort (scores en enteros)
        radixSortByScores(scaled, docIds);

        // mostrar resultados en orden descendente
        System.out.println("Resultados para: \"" + query + "\"");
        for (int i = scaled.length - 1; i >= 0; i--) {
            if (scores[docIds[i]] > 0) {
                Document doc = getDocById(docIds[i]);
                System.out.printf("Doc%d (%s) -> %.3f\n",
                        doc.getId(), doc.getName(), scores[docIds[i]]);
            }
        }
    }

    private void radixSortByScores(int[] scores, int[] ids) {
        // ordenamos solo scores, pero movemos ids en paralelo
        int n = scores.length;
        int max = 0;
        for (int v : scores) if (v > max) max = v;

        for (int exp = 1; max / exp > 0; exp *= 10) {
            int[] outputS = new int[n];
            int[] outputI = new int[n];
            int[] count = new int[10];

            for (int i = 0; i < n; i++) count[(scores[i] / exp) % 10]++;

            for (int i = 1; i < 10; i++) count[i] += count[i - 1];

            for (int i = n - 1; i >= 0; i--) {
                int digit = (scores[i] / exp) % 10;
                outputS[count[digit] - 1] = scores[i];
                outputI[count[digit] - 1] = ids[i];
                count[digit]--;
            }

            for (int i = 0; i < n; i++) {
                scores[i] = outputS[i];
                ids[i] = outputI[i];
            }
        }
    }

    private Document getDocById(int id) {
        MyIterator<Document> it = idx.getDocuments().iterator();
        while (it.hasNext()) {
            Document d = it.next();
            if (d.getId() == id) return d;
        }
        return null;
    }
}
