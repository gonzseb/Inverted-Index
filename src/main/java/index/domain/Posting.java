package index.domain;

public class Posting {
    private final int docId;
    private int tf;

    public Posting(int docId) {
        this.docId = docId;
        this.tf = 1;
    }

    public int getDocId() { return docId; }

    public int getTf() { return tf; }

    public void incrementTf() { tf++; }
}