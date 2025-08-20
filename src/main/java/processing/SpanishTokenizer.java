package processing;

import datastructures.DoublyCircularLinkedList;

/**
 * Tokenizer español: normaliza, separa en palabras y elimina stopwords.
 */
public class SpanishTokenizer implements Tokenizer {

    public DoublyCircularLinkedList<String> tokenize(String text) {
        DoublyCircularLinkedList<String> tokens = new DoublyCircularLinkedList<>();
        String norm = Normalizer.normalize(text);
        String[] parts = norm.split(" ");
        for (int i = 0; i < parts.length; i++) {
            String w = parts[i].trim();
            if (w.length() > 0 && !Stopwords.isStopword(w, "es")) {
                tokens.addLast(w);
            }
        }
        return tokens;
    }
}
