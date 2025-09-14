package index.domain;

import index.structures.MyVector;
import index.structures.MyVectorIterator;

public class Term {
    private final String term;
    private final MyVector<Posting> postings;
    private int df;

    public Term(String term) {
        this.term = term;
        this.postings = new MyVector<>();
        this.df = 0;
    }

    public String getTerm() { return term; }

    public int getDf() { return df; }

    public MyVector<Posting> getPostings() { return postings; }

    // Añade ocurrencia o incrementa tf si ya existe
    public void addOccurrence(int docId) {
        MyVectorIterator<Posting> it = postings.iterator();
        while (it.hasNext()) {
            Posting p = it.next();
            if (p.getDocId() == docId) {
                p.incrementTf();
                return;
            }
        }
        postings.add(new Posting(docId));
        df++;
    }
}