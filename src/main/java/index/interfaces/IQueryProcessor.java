package index.interfaces;

import index.domain.ScoredDocument;
import index.structures.MyVector;

public interface IQueryProcessor {
    MyVector<ScoredDocument> search(String query, int topK);
}