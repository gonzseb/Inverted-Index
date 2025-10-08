package index.text;

import index.interfaces.NormalizerStrategy;
import index.structures.MyVector;
import index.structures.MyVectorIterator;

public class Tokenizer {
    private static Tokenizer INSTANCE = null;

    private final NormalizerStrategy normalizer;
    private final MyVector<String> stopwords;

    // Constructor privado
    private Tokenizer(NormalizerStrategy normalizer, MyVector<String> stopwords) {
        this.normalizer = normalizer;
        this.stopwords = stopwords;
    }

    // Inicializar el singleton
    public static synchronized void init(NormalizerStrategy normalizer, MyVector<String> stopwords) {
        if (INSTANCE != null) {
            throw new IllegalStateException("Tokenizer ya fue inicializado");
        }
        if (normalizer == null || stopwords == null) {
            throw new IllegalArgumentException("Normalizer y las Stopwords no pueden ser nulos");
        }
        INSTANCE = new Tokenizer(normalizer, stopwords);
    }

    // Obtener instancia
    public static Tokenizer getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Tokenizer no inicializado. Llama Tokenizer.init(...) antes de usarlo.");
        }
        return INSTANCE;
    }

    // Procesamiento...
    public MyVector<String> tokenize(String text) {
        MyVector<String> result = new MyVector<>();
        if (text == null || text.isEmpty()) return result;
        int n = text.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char c = text.charAt(i);
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '.' || c == ',' || c == ':' || c == ';' ||
                    c == '(' || c == ')' || c == '\"' || c == '¿' || c == '?' || c == '¡' || c == '!') {
                processToken(sb, result);
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        processToken(sb, result);
        return result;
    }

    private void processToken(StringBuilder sb, MyVector<String> out) {
        if (sb.isEmpty()) return;
        String raw = sb.toString();
        String norm = normalizer.normalize(raw);
        if (norm.isEmpty()) return;
        if (!isStopword(norm)) out.add(norm);
    }

    private boolean isStopword(String token) {
        MyVectorIterator<String> it = stopwords.iterator();
        while (it.hasNext()) {
            if (it.next().equals(token)) return true;
        }
        return false;
    }
}