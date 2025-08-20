package processing;

/**
 * Conjunto de stopwords en español e inglés.
 * Implementado como búsqueda lineal en array (simple).
 */
public class Stopwords {

    private static final String[] SPANISH = {
            "el", "la", "los", "las", "un", "una", "unos", "unas", "y", "o", "pero", "porque", "de", "del",
            "que", "a", "en", "con", "por", "para", "como", "su", "sus", "al", "lo", "la", "le", "lo",
            "les", "mi", "ti", "su", "nuestro", "nuestra", "nuestros", "nuestras", "este", "esta", "estos",
            "estas", "ese", "esa", "esos", "esas", "quien", "quienes", "algo", "alguien", "nada", "nadie",
            "todos", "todas", "algún", "ningún", "ninguna", "si", "ya", "nosotros", "nosotras", "vosotros",
            "vosotras", "ellos", "ellas", "ustedes", "mismo", "misma", "mismos", "mismas", "cada", "cual",
            "mucho", "mucha", "muchos", "muchas", "poco", "poca", "pocos", "pocas"
    };

    private static final String[] ENGLISH = {
            "the", "a", "an", "and", "or", "but", "because", "of", "to", "in", "on", "at", "with", "for",
            "by", "as", "is", "are", "was", "were", "have", "has", "had", "having", "be", "been", "being",
            "this", "that", "these", "those", "I", "you", "he", "she", "it", "we", "they", "them", "us",
            "my", "your", "his", "her", "its", "our", "their", "mine", "yours", "hers", "ours", "theirs",
            "each", "every", "all", "some", "any", "none", "one", "two", "three", "four", "who", "whom",
            "whose", "which", "what", "where", "when", "why", "how", "that", "those", "such", "only", "even",
            "more", "less", "much", "many", "few", "fewer", "most", "least", "several", "few", "both", "either",
            "neither", "just", "so", "than", "too", "very", "quite", "rather", "slightly", "almost"
    };

    public static boolean isStopword(String word, String lang) {
        if (word == null || word.length() == 0) return true;
        String[] arr = lang.equals("es") ? SPANISH : ENGLISH;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(word)) return true;
        }
        return false;
    }
}
