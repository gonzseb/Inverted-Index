package index.text;

import index.interfaces.NormalizerStrategy;

// Utiliza expresiones regulares - Gracias Loría
public class SpanishBasicNormalizer implements NormalizerStrategy {

    @Override
    public String normalize(String token) {
        if (token == null) return "";

        // 1. Minúsculas
        token = token.toLowerCase();

        // 2. Reemplazar acentos y caracteres especiales
        token = token
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("ñ", "n");

        // 3. Validar que solo queden letras a-z (ASCII)
        if (!token.matches("[a-z]+")) return "";

        // 4. Filtrar por longitud razonable
        if (token.length() < 3 || token.length() > 20) return "";

        return token;
    }
}