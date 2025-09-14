package index.text;

import index.interfaces.NormalizerStrategy;
import index.structures.MyVector;

public class Stopwords {
    public static MyVector<String> spanish() {
        MyVector<String> sw = new MyVector<>();
        String[] arr = {
                // Artículos y determinantes
                "algunos", "algunas", "muchos", "muchas", "pocos", "pocas", "varios", "varias",
                "todos", "todas", "otros", "otras", "mismo", "misma", "mismos", "mismas",

                // Preposiciones y conjunciones
                "sobre", "bajo", "entre", "hacia", "desde", "hasta", "durante", "mediante",
                "según", "contra", "tras", "ante", "mediante", "excepto", "salvo", "incluso",
                "además", "también", "tampoco", "sino", "aunque", "mientras", "después",
                "antes", "luego", "entonces", "ahora", "siempre", "nunca", "jamás",

                // Pronombres
                "este", "esta", "estos", "estas", "aquel", "aquella", "aquellos", "aquellas",
                "quien", "quién", "cual", "cuál", "cuales", "cuáles", "cuyo", "cuya",
                "cuyos", "cuyas", "algo", "nada", "todo", "cada", "tanto", "tanta",
                "tantos", "tantas", "mucho", "mucha", "poco", "poca", "bastante",

                // Adverbios comunes
                "aquí", "ahí", "allí", "donde", "cuando", "como", "así", "bien", "mal",
                "mejor", "peor", "muy", "tan", "más", "menos", "tanto", "cuanto",
                "demasiado", "bastante", "apenas", "casi", "solo", "sólo", "solamente",
                "únicamente", "principalmente", "especialmente", "particularmente",

                // Verbos auxiliares y modales
                "ser", "estar", "haber", "tener", "hacer", "poder", "querer", "deber",
                "saber", "ir", "venir", "llegar", "salir", "entrar", "subir", "bajar",
                "poner", "dar", "ver", "oír", "decir", "hablar", "pensar", "creer",
                "parecer", "resultar", "seguir", "continuar", "empezar", "comenzar",
                "terminar", "acabar", "pasar", "ocurrir", "suceder", "existir",

                // Sustantivos comunes
                "persona", "personas", "gente", "hombre", "mujer", "niño", "niña",
                "tiempo", "día", "noche", "mañana", "tarde", "hora", "momento",
                "lugar", "sitio", "casa", "hogar", "ciudad", "país", "mundo",
                "vida", "muerte", "trabajo", "escuela", "familia", "amigo", "amigos",
                "problema", "problemas", "cosa", "cosas", "parte", "partes",
                "forma", "manera", "modo", "tipo", "clase", "grupo", "equipo",
                "número", "cantidad", "dinero", "precio", "valor", "resultado",
                "ejemplo", "caso", "historia", "información", "datos", "noticia",

                // Adjetivos comunes
                "grande", "pequeño", "pequeña", "bueno", "buena", "buenos", "buenas",
                "malo", "mala", "malos", "malas", "nuevo", "nueva", "nuevos", "nuevas",
                "viejo", "vieja", "viejos", "viejas", "joven", "jóvenes", "mayor",
                "menor", "primero", "primera", "último", "última", "próximo", "próxima",
                "siguiente", "anterior", "diferente", "igual", "mismo", "otra", "otro",
                "importante", "necesario", "necesaria", "posible", "imposible",
                "fácil", "difícil", "simple", "complicado", "rápido", "rápida",
                "lento", "lenta", "alto", "alta", "bajo", "baja", "largo", "larga",
                "corto", "corta", "ancho", "ancha", "estrecho", "estrecha",

                // Palabras de transición y conectores
                "entonces", "después", "luego", "antes", "primero", "segundo", "tercero",
                "finalmente", "por último", "además", "también", "asimismo", "igualmente",
                "sin embargo", "no obstante", "aunque", "a pesar de", "por tanto",
                "por eso", "por lo tanto", "así que", "de modo que", "para que",
                "con el fin de", "a fin de", "debido a", "gracias a", "a causa de",

                // Expresiones temporales
                "ayer", "hoy", "mañana", "pasado", "presente", "futuro", "siempre",
                "nunca", "jamás", "a veces", "muchas veces", "pocas veces", "cada vez",
                "otra vez", "de nuevo", "todavía", "aún", "ya no", "desde que",
                "hasta que", "mientras que", "cuando", "antes de", "después de",

                // Palabras interrogativas y exclamativas
                "qué", "quién", "quiénes", "cuál", "cuáles", "cuándo", "dónde",
                "cómo", "por qué", "para qué", "cuánto", "cuánta", "cuántos", "cuántas",

                // Palabras de cantidad y medida
                "todo", "nada", "algo", "mucho", "poco", "bastante", "demasiado",
                "suficiente", "varios", "algunos", "ciertos", "cierta", "medio",
                "media", "completo", "completa", "entero", "entera",

                // Colores básicos
                "blanco", "negro", "rojo", "azul", "verde", "amarillo", "naranja",
                "rosa", "morado", "violeta", "marrón", "gris",

                // Días y meses (algunos)
                "lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo",
                "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio",
                "agosto", "septiembre", "octubre", "noviembre", "diciembre",

                // Números en palabras
                "cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete",
                "ocho", "nueve", "diez", "once", "doce", "trece", "catorce", "quince",
                "dieciséis", "diecisiete", "dieciocho", "diecinueve", "veinte",
                "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta",
                "noventa", "cien", "ciento", "mil", "millón",

                // Emociones y sentimientos
                "amor", "odio", "alegría", "tristeza", "miedo", "esperanza",
                "felicidad", "dolor", "placer", "sorpresa", "ira", "calma",
                "paz", "guerra", "éxito", "fracaso", "victoria", "derrota",

                // ============= ENGLISH WORDS =============

                // Articles and determiners
                "some", "many", "most", "much", "more", "less", "each", "every",
                "both", "either", "neither", "other", "another", "such", "same",
                "different", "certain", "various", "several", "enough", "little",

                // Prepositions and conjunctions
                "about", "above", "across", "after", "against", "along", "among",
                "around", "before", "behind", "below", "beneath", "beside", "between",
                "beyond", "during", "inside", "outside", "through", "throughout",
                "toward", "towards", "under", "until", "within", "without",
                "although", "because", "however", "therefore", "moreover", "furthermore",
                "nevertheless", "meanwhile", "otherwise", "besides", "instead",

                // Pronouns
                "this", "that", "these", "those", "what", "which", "when", "where",
                "whose", "whom", "someone", "anyone", "everyone", "nobody", "something",
                "anything", "everything", "nothing", "somewhere", "anywhere",
                "everywhere", "nowhere", "myself", "yourself", "himself", "herself",
                "itself", "ourselves", "yourselves", "themselves",

                // Common adverbs
                "here", "there", "where", "when", "then", "now", "soon", "later",
                "before", "after", "already", "still", "yet", "again", "once",
                "twice", "always", "never", "sometimes", "often", "usually",
                "rarely", "seldom", "hardly", "almost", "quite", "very", "too",
                "enough", "rather", "fairly", "pretty", "really", "truly",
                "certainly", "definitely", "probably", "perhaps", "maybe",
                "possibly", "likely", "unlikely", "obviously", "clearly",

                // Common verbs
                "have", "make", "take", "come", "give", "know", "think", "feel",
                "look", "seem", "want", "need", "like", "love", "hate", "hope",
                "wish", "help", "work", "play", "live", "stay", "move", "turn",
                "keep", "hold", "bring", "carry", "send", "find", "lose", "show",
                "tell", "ask", "answer", "speak", "talk", "hear", "listen",
                "read", "write", "learn", "teach", "understand", "remember",
                "forget", "believe", "doubt", "agree", "disagree", "decide",
                "choose", "try", "attempt", "succeed", "fail", "win", "lose",
                "start", "begin", "finish", "stop", "continue", "change",
                "become", "remain", "happen", "occur", "exist", "appear",
                "disappear", "arrive", "leave", "return", "visit", "meet",

                // Common nouns
                "time", "year", "month", "week", "hour", "minute", "moment",
                "today", "yesterday", "tomorrow", "morning", "afternoon",
                "evening", "night", "place", "home", "house", "room", "door",
                "window", "floor", "wall", "street", "city", "town", "country",
                "world", "life", "death", "birth", "family", "parent", "child",
                "children", "friend", "people", "person", "woman", "girl",
                "school", "college", "university", "student", "teacher",
                "work", "job", "business", "company", "office", "money",
                "price", "cost", "value", "number", "amount", "size", "length",
                "width", "height", "weight", "color", "shape", "form", "type",
                "kind", "sort", "part", "piece", "item", "thing", "object",
                "matter", "material", "substance", "food", "water", "air",
                "fire", "earth", "nature", "animal", "plant", "tree", "flower",
                "book", "page", "story", "word", "language", "letter", "name",
                "title", "idea", "thought", "mind", "heart", "body", "hand",
                "foot", "head", "face", "eye", "nose", "mouth", "ear",
                "hair", "skin", "blood", "bone", "muscle", "voice", "sound",
                "music", "song", "game", "sport", "picture", "photo", "image",
                "film", "movie", "show", "program", "news", "information",
                "message", "question", "answer", "problem", "solution",
                "reason", "cause", "effect", "result", "purpose", "goal",
                "plan", "method", "system", "process", "service", "product",

                // Common adjectives
                "good", "great", "best", "better", "excellent", "perfect",
                "wonderful", "amazing", "fantastic", "awesome", "incredible",
                "beautiful", "pretty", "lovely", "attractive", "ugly",
                "nice", "pleasant", "enjoyable", "comfortable", "uncomfortable",
                "easy", "difficult", "hard", "simple", "complex", "complicated",
                "important", "necessary", "useful", "useless", "helpful",
                "harmful", "dangerous", "safe", "secure", "risky", "careful",
                "careless", "serious", "funny", "interesting", "boring",
                "exciting", "surprised", "surprising", "confused", "confusing",
                "clear", "obvious", "certain", "sure", "possible", "impossible",
                "likely", "unlikely", "probable", "improbable", "true", "false",
                "real", "actual", "natural", "artificial", "normal", "strange",
                "unusual", "common", "rare", "special", "ordinary", "extraordinary",
                "public", "private", "personal", "professional", "social",
                "political", "economic", "financial", "legal", "illegal",
                "formal", "informal", "official", "unofficial", "local",
                "national", "international", "global", "worldwide", "universal",

                // Size and measurement
                "big", "large", "huge", "enormous", "giant", "massive", "tiny",
                "small", "little", "mini", "microscopic", "long", "short",
                "tall", "high", "low", "deep", "shallow", "wide", "narrow",
                "thick", "thin", "fat", "skinny", "heavy", "light", "strong",
                "weak", "powerful", "powerless", "fast", "quick", "rapid",
                "slow", "gradual", "sudden", "immediate", "instant", "delayed",

                // Colors
                "black", "white", "gray", "grey", "dark", "light", "bright",
                "pale", "vivid", "dull", "colorful", "colorless",

                // Age and time
                "young", "old", "ancient", "modern", "contemporary", "current",
                "recent", "past", "present", "future", "early", "late",
                "quick", "slow", "temporary", "permanent", "brief", "long",
                "eternal", "endless", "limited", "unlimited", "frequent",
                "occasional", "regular", "irregular", "constant", "variable",

                // Quantity and degree
                "full", "empty", "complete", "incomplete", "whole", "partial",
                "total", "final", "initial", "first", "second", "third",
                "last", "next", "previous", "following", "remaining", "extra",
                "additional", "spare", "single", "double", "triple", "multiple",
                "individual", "collective", "general", "specific", "particular",
                "detailed", "brief", "summary", "overall", "average", "typical",
                "standard", "regular", "irregular", "normal", "abnormal",

                // Emotions and feelings
                "happy", "sad", "angry", "calm", "excited", "bored", "interested",
                "surprised", "shocked", "amazed", "confused", "worried", "concerned",
                "relaxed", "stressed", "tired", "energetic", "lazy", "active",
                "proud", "ashamed", "guilty", "innocent", "brave", "scared",
                "afraid", "confident", "nervous", "comfortable", "uncomfortable",
                "satisfied", "disappointed", "hopeful", "hopeless", "optimistic",
                "pessimistic", "positive", "negative", "cheerful", "depressed",

                // Days of the week
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday",

                // Months
                "January", "February", "March", "April", "June", "July",
                "August", "September", "October", "November", "December",

                // Numbers in words
                "zero", "four", "five", "seven", "eight", "nine", "eleven",
                "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
                "seventeen", "eighteen", "nineteen", "twenty", "thirty",
                "forty", "fifty", "sixty", "seventy", "eighty", "ninety",
                "hundred", "thousand", "million", "billion", "trillion",

                // Question words
                "what", "when", "where", "which", "whose", "whom"
        };

        NormalizerStrategy norm = new SpanishBasicNormalizer();

        for (String s : arr) {
            String n = norm.normalize(s);
            if (!n.isEmpty()) sw.add(n);
        }
        return sw;
    }
}