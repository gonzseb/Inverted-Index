package index;

import datastructures.DoublyCircularLinkedList;

public class Document {
    private final int id;
    private final String name;
    private final DoublyCircularLinkedList<String> tokens;

    public Document(int id, String name, DoublyCircularLinkedList<String> tokens) {
        this.id = id;
        this.name = name;
        this.tokens = tokens;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public DoublyCircularLinkedList<String> getTokens() { return tokens; }
}
