package index.domain;

public class ScoredDocument {
    private final int docId;
    private final String path;
    private final double score;

    public ScoredDocument(int docId, String path, double score) {
        this.docId = docId;
        this.path = path;
        this.score = score;
    }

    public int getDocId() { return docId; }

    public String getPath() { return path; }

    public double getScore() { return score; }
}