package processing;

import datastructures.DoublyCircularLinkedList;

/**
 * Strategy para tokenización.
 */
public interface Tokenizer {
    DoublyCircularLinkedList<String> tokenize(String text);
}
