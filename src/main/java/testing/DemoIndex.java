package testing;

import index.*;
import processing.*;

public class DemoIndex {
    public static void main(String[] args) throws Exception {
        Tokenizer tok = new SpanishTokenizer();
        InvertedIndex idx = InvertedIndex.getInstance(tok);

        idx.buildFromPath("C:\\Users\\sbsgo\\OneDrive\\Documentos\\XuanZhi9\\a\\src\\main\\java\\documents");
        idx.printIndex();
    }
}
