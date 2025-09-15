package index.indexing;

import index.domain.Document;
import index.domain.Posting;
import index.domain.ScoredDocument;
import index.domain.Term;
import index.interfaces.IInvertedIndex;
import index.interfaces.IQueryProcessor;
import index.structures.MyHashTable;
import index.structures.MyVector;
import index.structures.MyVectorIterator;
import index.text.Tokenizer;

// Jugamos con un índice previamente sorted
public class QueryProcessor implements IQueryProcessor {
    private final IInvertedIndex index;
    private final Tokenizer tokenizer;

    public QueryProcessor(IInvertedIndex index, Tokenizer tokenizer) {
        this.index = index;
        this.tokenizer = tokenizer;
    }

    @Override
    public MyVector<ScoredDocument> search(String query, int topK) {
        MyVector<String> qTokens = tokenizer.tokenize(query);
        if (qTokens.isEmpty()) return new MyVector<>();

        int N = index.getDocumentCount();

        // --- 1) Construir TF de la consulta usando hash ---
        MyHashTable<String, Integer> qtf = new MyHashTable<>(Math.max(16, qTokens.size() * 2));
        MyVectorIterator<String> qit = qTokens.iterator();
        while (qit.hasNext()) {
            String t = qit.next();
            Integer prev = qtf.get(t);
            qtf.put(t, (prev == null) ? 1 : prev + 1);
        }

        // --- 2) Calcular dot products docId -> score ---
        MyHashTable<Integer, Double> docScores = new MyHashTable<>(Math.max(16, N * 2));
        double queryNormSum = 0.0;

        MyVector<MyHashTable.MyEntry<String, Integer>> qentries = qtf.entries();
        MyVectorIterator<MyHashTable.MyEntry<String, Integer>> qentIt = qentries.iterator();
        while (qentIt.hasNext()) {
            MyHashTable.MyEntry<String, Integer> e = qentIt.next();
            String token = e.key;
            int tf = e.value;

            Term term = binarySearchTerm(index.getAllTerms(), token);
            if (term == null) continue;
            int df = term.getDf();
            if (df <= 0) continue;

            double idf = Math.log10((double) N / df);
            double qWeight = tf * idf;
            queryNormSum += qWeight * qWeight;

            MyVectorIterator<Posting> pit = term.getPostings().iterator();
            while (pit.hasNext()) {
                Posting p = pit.next();
                int docId = p.getDocId();
                double dWeight = p.getTf() * idf;
                Double prev = docScores.get(docId);
                docScores.put(docId, (prev == null ? 0.0 : prev) + (qWeight * dWeight));
            }
        }

        double queryNorm = Math.sqrt(queryNormSum);
        if (queryNorm == 0.0) return new MyVector<>();

        // --- 3) Convertir a vector de resultados con cosine ---
        MyVector<ScoredDocument> results = new MyVector<>();
        MyVector<MyHashTable.MyEntry<Integer, Double>> entries = docScores.entries();
        MyVectorIterator<MyHashTable.MyEntry<Integer, Double>> entIt = entries.iterator();
        while (entIt.hasNext()) {
            MyHashTable.MyEntry<Integer, Double> e = entIt.next();
            int docId = e.key;
            double dot = e.value;
            Document md = index.getDocumentMetadata(docId);
            double docNorm = (md != null) ? md.getVectorNorm() : 0.0;
            if (docNorm == 0.0) continue;
            double cosine = dot / (docNorm * queryNorm);
            results.add(new ScoredDocument(docId, md.getDecodedPath(), cosine));
        }

        // --- 4) Mantener solo top-K con heap simple ---
        return topKResults(results, topK);
    }

    // --- Búsqueda binaria sobre el vector de términos ordenado ---
    private Term binarySearchTerm(MyVector<Term> sortedTerms, String token) {
        int low = 0, high = sortedTerms.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = sortedTerms.get(mid).getTerm().compareTo(token);
            if (cmp == 0) return sortedTerms.get(mid);
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    // --- Selección de top-K resultados usando heap simple ---
    private MyVector<ScoredDocument> topKResults(MyVector<ScoredDocument> allResults, int k) {
        MyVector<ScoredDocument> top = new MyVector<>();
        for (int i = 0; i < allResults.size(); i++) {
            ScoredDocument doc = allResults.get(i);
            if (top.size() < k) {
                top.add(doc);
            } else {
                // buscar el mínimo en top
                int minIndex = 0;
                double minScore = top.get(0).getScore();
                for (int j = 1; j < top.size(); j++) {
                    if (top.get(j).getScore() < minScore) {
                        minScore = top.get(j).getScore();
                        minIndex = j;
                    }
                }
                if (doc.getScore() > minScore) top.set(minIndex, doc);
            }
        }

        // ordenar top-K final descendente (insertion sort pequeño)
        for (int i = 1; i < top.size(); i++) {
            ScoredDocument key = top.get(i);
            int j = i - 1;
            while (j >= 0 && top.get(j).getScore() < key.getScore()) {
                top.set(j + 1, top.get(j));
                j--;
            }
            top.set(j + 1, key);
        }
        return top;
    }
}