package index;

import datastructures.*;
import processing.*;
import java.io.*;

/**
 * InvertedIndex implementado como Singleton.
 */
public class InvertedIndex {

    private static InvertedIndex instance;  // Singleton
    private final HashTable<String, DoublyCircularLinkedList<Integer>> index;
    private final DoublyCircularLinkedList<Document> documents;
    private final Tokenizer tokenizer;
    private int docCounter = 0;

    private InvertedIndex(Tokenizer tok) {
        this.index = new HashTable<>();
        this.documents = new DoublyCircularLinkedList<>();
        this.tokenizer = tok;
    }

    public HashTable<String, DoublyCircularLinkedList<Integer>> getIndex() {
        return index;
    }

    public static InvertedIndex getInstance(Tokenizer tok) {
        if (instance == null) {
            instance = new InvertedIndex(tok);
        }
        return instance;
    }

    /** Construye el índice leyendo todos los .txt en un directorio */
    public void buildFromPath(String dirPath) throws IOException {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".txt")) {
                addDocument(f);
            }
        }
    }

    /** Preguntar si existe */
    private boolean contains(DoublyCircularLinkedList<Integer> list, int id) {
        MyIterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            if (it.next() == id) return true;
        }
        return false;
    }

    /** Agrega un solo documento al índice */
    public void addDocument(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line).append(" ");
        br.close();

        DoublyCircularLinkedList<String> tokens = tokenizer.tokenize(sb.toString());
        Document doc = new Document(docCounter++, f.getName(), tokens);
        documents.addLast(doc);

        // actualizar postings
        MyIterator<String> it = tokens.iterator();
        while (it.hasNext()) {
            String term = it.next();
            DoublyCircularLinkedList<Integer> postings = index.get(term);
            if (postings == null) {
                postings = new DoublyCircularLinkedList<>();
                index.put(term, postings);
            }
            if (!contains(postings, doc.getId())) { // Validar que no exista para evitar duplicados
                postings.addLast(doc.getId());
            }

        }
    }

    /** Actualizar el índice agregando documentos desde otra ruta */
    public void update(String dirPath) throws IOException {
        buildFromPath(dirPath);
    }

    /** Muestra el índice invertido */
    public void printIndex() {
        MyIterator<String> it = index.iterator();
        while (it.hasNext()) {
            String term = it.next();
            DoublyCircularLinkedList<Integer> postings = index.get(term);
            System.out.print(term + " -> ");
            MyIterator<Integer> pit = postings.iterator();
            while (pit.hasNext()) {
                System.out.print("D" + pit.next() + " ");
            }
            System.out.println();
        }
    }

    public DoublyCircularLinkedList<Document> getDocuments() {
        return documents;
    }
}
