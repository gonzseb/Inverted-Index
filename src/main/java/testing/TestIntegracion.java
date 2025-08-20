package testing;

import datastructures.*;
import index.*;
import processing.*;

public class TestIntegracion {
    public static void main(String[] args) {
        InvertedIndex idx = InvertedIndex.getInstance(new SpanishTokenizer());
        Document d1 = new Document(0, "doc1", new DoublyCircularLinkedList<>());
        d1.getTokens().addLast("hola");
        d1.getTokens().addLast("mundo");
        idx.getDocuments().addLast(d1);
        idx.getIndex().put("hola", new DoublyCircularLinkedList<>());
        idx.getIndex().get("hola").addLast(0);

        // Recorrer para validar
        for (MyIterator<Document> it = idx.getDocuments().iterator(); it.hasNext();) {
            Document doc = it.next();
            System.out.println(doc.getName());
        }
        for (MyIterator<Integer> it = idx.getIndex().get("hola").iterator(); it.hasNext();) {
            System.out.println(it.next());
        }

    }
}
