package index.text;

import index.domain.Document;
import index.domain.Posting;
import index.domain.ScoredDocument;
import index.domain.Term;
import index.interfaces.IInvertedIndex;
import index.interfaces.IQueryProcessor;
import index.structures.MyHashTable;
import index.structures.MyVector;
import index.structures.MyVectorIterator;

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

        // 1) Construir tf de la consulta
        MyVector<QueryTerm> qterms = new MyVector<>();
        MyVectorIterator<String> qit = qTokens.iterator();
        while (qit.hasNext()) {
            String t = qit.next();
            boolean found = false;
            MyVectorIterator<QueryTerm> qq = qterms.iterator();
            while (qq.hasNext()) {
                QueryTerm qt = qq.next();
                if (qt.term.equals(t)) { qt.tf++; found = true; break; }
            }
            if (!found) qterms.add(new QueryTerm(t));
        }

        // 2) Calcular dot products: docId -> dot, y usar norma precomputada del doc
        MyHashTable<Integer, Double> docScores = new MyHashTable<>(Math.max(16, N * 2));
        double queryNormSum = 0.0;

        MyVectorIterator<QueryTerm> qti = qterms.iterator();
        while (qti.hasNext()) {
            QueryTerm qt = qti.next();
            Term term = index.findTerm(qt.term);
            if (term == null) continue;
            int df = term.getDf();
            if (df <= 0) continue;
            double idf = Math.log10((double) N / (double) df);
            double qWeight = qt.tf * idf;
            queryNormSum += qWeight * qWeight;

            MyVectorIterator<Posting> pit = term.getPostings().iterator();
            while (pit.hasNext()) {
                Posting p = pit.next();
                int docId = p.getDocId();
                double dWeight = p.getTf() * idf;
                Double prev = docScores.get(docId);
                if (prev == null) prev = 0.0;
                prev = prev + (qWeight * dWeight);
                docScores.put(docId, prev);
            }
        }

        double queryNorm = Math.sqrt(queryNormSum);
        if (queryNorm == 0.0) return new MyVector<>();

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

        sortScoredDocumentsDesc(results);

        // Devolver topK
        MyVector<ScoredDocument> top = new MyVector<>();
        int limit = Math.min(topK, results.size());
        for (int i = 0; i < limit; i++) top.add(results.get(i));
        return top;
    }

    private void sortScoredDocumentsDesc(MyVector<ScoredDocument> arr) {
        for (int i = 1; i < arr.size(); i++) {
            ScoredDocument key = arr.get(i);
            int j = i - 1;
            while (j >= 0 && arr.get(j).getScore() < key.getScore()) {
                if (j + 1 < arr.size()) arr.set(j + 1, arr.get(j));
                j--;
            }
            arr.set(j + 1, key);
        }
    }

    private static class QueryTerm {
        String term;
        int tf = 1;
        QueryTerm(String t) { term = t; }
    }
}