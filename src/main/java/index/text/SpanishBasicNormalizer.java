package index.text;

import index.interfaces.NormalizerStrategy;

public class SpanishBasicNormalizer implements NormalizerStrategy {
    @Override
    public String normalize(String token) {
        if (token == null) return "";
        token = token.toLowerCase();
        if (!allLettersOrAccented(token)) return "";
        token = stripAccents(token);
        if (!allAsciiLowerLetters(token)) return "";
        if (token.length() < 3 || token.length() > 20) return "";
        return token;
    }

    private boolean allLettersOrAccented(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!isSpanishLetter(c)) return false;
        }
        return true;
    }

    private boolean isSpanishLetter(char c) {
        if (c >= 'a' && c <= 'z') return true;
        return c == 'á' || c == 'é' || c == 'í' || c == 'ó' || c == 'ú' || c == 'ü' || c == 'ñ';
    }

    private String stripAccents(String s) {
        char[] out = new char[s.length()];
        for (int i = 0; i < s.length(); i++) out[i] = mapAccent(s.charAt(i));
        return new String(out);
    }

    private char mapAccent(char c) {
        return switch (c) {
            case 'á' -> 'a';
            case 'é' -> 'e';
            case 'í' -> 'i';
            case 'ó' -> 'o';
            case 'ú' -> 'u';
            case 'ü' -> 'u';
            case 'ñ' -> 'n';
            default -> c;
        };
    }

    private boolean allAsciiLowerLetters(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 'a' || c > 'z') return false;
        }
        return true;
    }
}