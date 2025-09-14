package index.text;

import index.interfaces.NormalizerStrategy;
import index.structures.MyVector;
import index.structures.MyVectorIterator;

public class Tokenizer {
    private final NormalizerStrategy normalizer;
    private final MyVector<String> stopwords;

    public Tokenizer(NormalizerStrategy normalizer, MyVector<String> stopwords) {
        this.normalizer = normalizer;
        this.stopwords = stopwords;
    }

    public MyVector<String> tokenize(String text) {
        MyVector<String> result = new MyVector<>();
        if (text == null || text.isEmpty()) return result;
        int n = text.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char c = text.charAt(i);
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '.' || c == ',' || c == ':' || c == ';' || c == '(' || c == ')' || c == '\"' || c == '¿' || c == '?' || c == '¡' || c == '!') {
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
        while (it.hasNext()) { if (it.next().equals(token)) return true; }
        return false;
    }
}