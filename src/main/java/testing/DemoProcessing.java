package testing;

import processing.*;
import datastructures.*;

public class DemoProcessing {
    public static void main(String[] args) {
        Tokenizer tok = new SpanishTokenizer();

        String doc1 = "EL GATO4 come! pescado";
        String doc2 = "el perro come carne7";
        String doc3 = "el gato... duerme";

        DoublyCircularLinkedList<String> t1 = tok.tokenize(doc1);
        DoublyCircularLinkedList<String> t2 = tok.tokenize(doc2);
        DoublyCircularLinkedList<String> t3 = tok.tokenize(doc3);

        System.out.println("Doc1 tokens:");
        MyIterator<String> it1 = t1.iterator();
        while (it1.hasNext()) System.out.print(it1.next() + " ");
        System.out.println("\n");

        System.out.println("Doc2 tokens:");
        MyIterator<String> it2 = t2.iterator();
        while (it2.hasNext()) System.out.print(it2.next() + " ");
        System.out.println("\n");

        System.out.println("Doc3 tokens:");
        MyIterator<String> it3 = t3.iterator();
        while (it3.hasNext()) System.out.print(it3.next() + " ");
        System.out.println();
    }
}
