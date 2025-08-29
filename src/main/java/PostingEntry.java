class PostingEntry implements Comparable<PostingEntry> {
    private String documentId;
    private int frequency;

    public PostingEntry(String documentId) {
        this.documentId = documentId;
        this.frequency = 1;
    }

    public void incrementFrequency() { frequency++; }
    public String getDocumentId() { return documentId; }
    public int getFrequency() { return frequency; }

    @Override
    public int compareTo(PostingEntry o) { return this.documentId.compareTo(o.documentId); }

    @Override
    public String toString() { return documentId + ":" + frequency; }
}