import java.io.*;

class TextProcessor {
    private static final TextProcessor instance = new TextProcessor();
    public static TextProcessor getInstance() { return instance; }

    public static final MyVector<String> STOPWORDS = new MyVector<>(100);

    static {
        String[] stopwordArray = {
                "el","la","los","las","un","una","unos","unas","a","ante","bajo","con","de","del","por","para","y","o","pero","si",
                "soy","eres","es","somos","sois","son","estar","estoy","esta","estas","estamos","están","hacer",
                "a","an","the","i","me","my","mine","you","your","he","him","she","her","it","we","us","they"
        };
        for (String w : stopwordArray) STOPWORDS.insertSorted(w);
    }

    private TextProcessor() {} // privado para singleton
}