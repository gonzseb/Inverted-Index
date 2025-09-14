package index.interfaces;

import index.domain.Document;
import index.domain.Term;
import index.structures.MyVector;

public interface IInvertedIndex {
    int registerDocument(String path);

    Document getDocumentMetadata(int docId);

    void insertToken(String token, int docId);

    Term findTerm(String token);

    MyVector<Term> getAllTerms();

    int getDocumentCount();

    void removeTerm(String token);

    void computeDocumentVectorNorms(); // calcular y guardar norma por documento (TF-IDF vector)
}