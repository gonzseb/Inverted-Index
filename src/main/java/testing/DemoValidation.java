package testing;

import index.*;
import processing.*;
import datastructures.*;

public class DemoValidation {
    public static void main(String[] args) throws Exception {
        // --- Inicialización ---
        Tokenizer tok = new SpanishTokenizer();
        InvertedIndex idx = InvertedIndex.getInstance(tok);

        // Construir índice desde tu carpeta de documentos
        String path = "C:\\Users\\sbsgo\\OneDrive\\Documentos\\XuanZhi9\\a\\src\\main\\java\\documents";
        idx.buildFromPath(path);

        System.out.println("=== Lista de documentos ===");
        MyIterator<Document> docIt = idx.getDocuments().iterator();
        while (docIt.hasNext()) {
            Document d = docIt.next();
            System.out.println("Doc" + d.getId() + ": " + d.getName() +
                    " (tokens: " + d.getTokens().size() + ")");
        }

        System.out.println("\n=== Índice invertido (primeros 5 postings por término) ===");
        MyIterator<String> termIt = idx.getIndex().iterator();
        while (termIt.hasNext()) {
            String term = termIt.next();
            DoublyCircularLinkedList<Integer> postings = idx.getIndex().get(term);

            System.out.print(term + " -> ");
            MyIterator<Integer> postIt = postings.iterator();
            int count = 0;
            while (postIt.hasNext() && count < 5) {
                System.out.print("D" + postIt.next() + " ");
                count++;
            }
            if (postings.size() > 5) System.out.print("...");
            System.out.println();
        }

        System.out.println("\n=== Validación completa de listas circulares OK ===");
    }
}
