import java.io.*;

class BasicSpanishTokenizer implements TokenizerStrategy {
    @Override
    public MyVector<String> tokenize(String filePath) {
        MyVector<String> tokens = new MyVector<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : line.split("\\s+")) {
                    String norm = normalizeToken(word.trim());
                    if (norm != null) tokens.add(norm);
                }
            }
        } catch(IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
        return tokens;
    }

    private String normalizeToken(String token) {
        token = token.toLowerCase();
        StringBuilder clean = new StringBuilder();
        for (char c : token.toCharArray()) {
            char norm = normalizeChar(c);
            if (norm == 0) return null;
            clean.append(norm);
        }
        String t = clean.toString();
        if (t.length() > 1 && t.length() < 20 && TextProcessor.STOPWORDS.binarySearch(t) < 0) return t;
        return null;
    }

    private char normalizeChar(char c) {
        switch(c){
            case 'á': case 'à': case 'ä': case 'â': return 'a';
            case 'é': case 'è': case 'ë': case 'ê': return 'e';
            case 'í': case 'ì': case 'ï': case 'î': return 'i';
            case 'ó': case 'ò': case 'ö': case 'ô': return 'o';
            case 'ú': case 'ù': case 'ü': case 'û': return 'u';
            case 'ñ': return 'n';
        }
        if (c >= 'a' && c <= 'z') return c;
        return 0;
    }
}
