class Term implements Comparable<Term> {
    private String token;
    private MyVector<PostingEntry> postings;

    public Term(String token) {
        this.token = token;
        this.postings = new MyVector<>();
    }

    public void addOccurrence(String documentId) {
        PostingEntry entry = new PostingEntry(documentId);
        int idx = postings.binarySearch(entry);
        if (idx >= 0) postings.get(idx).incrementFrequency();
        else postings.insertSorted(entry);
    }

    public String getToken() { return token; }
    public MyVector<PostingEntry> getPostings() { return postings; }

    @Override
    public int compareTo(Term o) { return this.token.compareTo(o.token); }

    @Override
    public String toString() { return token + " -> " + postingsToString(); }

    private String postingsToString() {
        StringBuilder sb = new StringBuilder("[");
        for (PostingEntry p : postings) {
            if (sb.length() > 1) sb.append(", ");
            sb.append(p.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}