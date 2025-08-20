package testing;

import index.*;
import processing.*;

public class DemoIndex {
    public static void main(String[] args) throws Exception {
        Tokenizer tok = new SpanishTokenizer();
        InvertedIndex idx = InvertedIndex.getInstance(tok);

        idx.buildFromPath("C:\\Users\\sbsgo\\Downloads\\Proyecto Datos\\P1\\docsPrueba");
        idx.printIndex();
    }
}
