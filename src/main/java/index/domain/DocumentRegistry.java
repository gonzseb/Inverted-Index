package index.domain;

import index.structures.MyVector;
import index.structures.MyVectorIterator;

import java.io.File;
import java.util.Base64;

public class DocumentRegistry {
    private final MyVector<Document> docs = new MyVector<>();

    public int register(String path) {
        int id = docs.size();

        // Obtener solo el nombre del archivo
        String fileName = new File(path).getName();
        String base64Part = fileName.endsWith(".txt") ? fileName.substring(0, fileName.length() - 4) : fileName;

        String decodedPath;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Part);
            decodedPath = new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            decodedPath = path; // fallback si no es Base64 válido
        }

        docs.add(new Document(id, decodedPath));
        return id;
    }

    public Document getMetadata(int docId) {
        return docs.get(docId);
    }

    public int size() {
        return docs.size();
    }

    public MyVectorIterator<Document> iterator() {
        return docs.iterator();
    }
}