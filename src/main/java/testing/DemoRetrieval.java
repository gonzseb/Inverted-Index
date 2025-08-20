package testing;

import index.*;
import processing.*;
import retrieval.*;

public class DemoRetrieval {
    public static void main(String[] args) throws Exception {
        Tokenizer tok = new SpanishTokenizer();
        InvertedIndex idx = InvertedIndex.getInstance(tok);

        // construir índice desde carpeta docs/
        idx.buildFromPath("C:\\Users\\sbsgo\\Downloads\\Proyecto Datos\\P1\\docsTodos\\Documentos");

        // construir matriz TF-IDF
        TFIDFCalculator calc = new TFIDFCalculator(idx);

        // procesar consulta
        QueryProcessor qp = new QueryProcessor(idx, calc, tok);
        qp.search("costa rica");
    }
}
