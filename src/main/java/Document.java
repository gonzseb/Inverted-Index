class Document implements Comparable<Document> {
    private String id;
    private String filePath;

    public Document(String id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public String getId() { return id; }
    public String getFilePath() { return filePath; }

    @Override
    public int compareTo(Document o) { return this.id.compareTo(o.id); }

    @Override
    public String toString() { return id + " -> " + filePath; }
}