package processing;

/**
 * Normalizador de texto:
 * - minúsculas
 * - quitar acentos
 * - eliminar dígitos y signos de puntuación
 */
public class Normalizer {

    public static String normalize(String input) {
        if (input == null) return "";

        // a minúsculas
        String s = input.toLowerCase();

        // reemplazar vocales con tilde
        s = s.replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replace('ü', 'u')
                .replace('ñ', 'n');

        // quitar todo lo que no sea letra ni espacio
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 'a' && c <= 'z') || c == ' ') {
                sb.append(c);
            } else {
                sb.append(' ');
            }
        }

        return sb.toString().replaceAll("\\s+", " ").trim();
    }
}
