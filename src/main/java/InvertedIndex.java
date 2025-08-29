class InvertedIndex {
    private MyVector<Term> terms;
    private MyVector<Document> documents;
    private TokenizerStrategy tokenizer;
    private int documentCounter;

    public InvertedIndex(TokenizerStrategy tokenizer) {
        terms = new MyVector<>(1000);
        documents = new MyVector<>();
        this.tokenizer = tokenizer;
        documentCounter = 0;
    }

    public String processDocument(String filePath) {
        documentCounter++;
        String docId = "doc" + documentCounter;
        documents.insertSorted(new Document(docId, filePath));

        MyVector<String> tokens = tokenizer.tokenize(filePath);
        for (String token : tokens) {
            Term term = new Term(token);
            int idx = terms.binarySearch(term);
            if (idx >= 0) terms.get(idx).addOccurrence(docId);
            else {
                term.addOccurrence(docId);
                terms.insertSorted(term);
            }
        }
        return docId;
    }

    public void processMultipleDocuments(String[] filePaths) {
        for (String fp : filePaths) processDocument(fp);
    }

    public Term searchTerm(String token) {
        int idx = terms.binarySearch(new Term(token.toLowerCase()));
        if (idx >= 0) return terms.get(idx);
        return null;
    }

    public String getDocumentPath(String docId) {
        int idx = documents.binarySearch(new Document(docId, ""));
        if (idx >= 0) return documents.get(idx).getFilePath();
        return null;
    }

    public void printStatistics() {
        System.out.println("\n=== ESTADÍSTICAS ===");
        System.out.println("Documentos: " + documents.size());
        System.out.println("Términos únicos: " + terms.size());
        System.out.println("Stopwords: " + TextProcessor.STOPWORDS.size());
    }

    public void printIndex(int maxTerms) {
        int limit = Math.min(maxTerms, terms.size());
        int count = 1;
        for (Term t : terms) {
            if (count > limit) break;
            System.out.println(count + ". " + t);
            count++;
        }
    }

    public void printIndex() { printIndex(terms.size()); }

    public void searchAndDisplay(String token) {
        Term term = searchTerm(token);
        if (term != null) System.out.println("Término encontrado: " + term + ", DF=" + term.getPostings().size());
        else System.out.println("Término '" + token + "' no encontrado");
    }
}