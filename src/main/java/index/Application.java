package index;

import index.domain.ScoredDocument;
import index.indexing.Indexer;
import index.indexing.InvertedIndex;
import index.structures.MyVector;
import index.indexing.QueryProcessor;
import index.text.SpanishBasicNormalizer;
import index.text.Stopwords;
import index.text.Tokenizer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Pedir directorio de documentos
            System.out.print("Ingrese el path de la carpeta con documentos: ");
            String rootPathInput = sc.nextLine().trim();

            // Validación básica: no vacío
            if (rootPathInput.isEmpty()) {
                System.out.println("Path vacío. Abortando.");
                return;
            }

            Path rootPath = Paths.get(rootPathInput);

            // Intentar resolver ruta real
            try {
                rootPath = rootPath.toRealPath();
            } catch (Exception ignored) {
                // Si falla, seguimos con la ruta original: validaciones siguientes detectarán problemas.
            }

            // Validaciones del path: existencia, que sea directorio y lectura
            if (!Files.exists(rootPath)) {
                System.out.println("El path especificado no existe: " + rootPath);
                return;
            }
            if (!Files.isDirectory(rootPath)) {
                System.out.println("El path no es un directorio: " + rootPath);
                return;
            }
            if (!Files.isReadable(rootPath)) {
                System.out.println("No hay permisos de lectura sobre el directorio: " + rootPath);
                return;
            }

            // 2. Crear índice
            InvertedIndex index = new InvertedIndex(16);

            // 2.a Inicializar Tokenizer (singleton)
            Tokenizer.init(new SpanishBasicNormalizer(), Stopwords.spanish());

            // Recuperar la instancia para pasarla a Indexer / QueryProcessor
            Tokenizer tokenizer = Tokenizer.getInstance();

            // 3. Pedir porcentaje de términos Zipf
            System.out.print("Ingrese porcentaje de términos a eliminar por Ley de ZipF (0 para omitir, 0-100): ");
            double zipfPercent;
            try {
                zipfPercent = Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                zipfPercent = 0.0;
            }
            // Normalizar rango [0,100]
            if (zipfPercent < 0.0) {
                System.out.println("Porcentaje ZipF negativo, se usará 0.");
                zipfPercent = 0.0;
            } else if (zipfPercent > 100.0) {
                System.out.println("Porcentaje ZipF mayor a 100, se usará 100.");
                zipfPercent = 100.0;
            }

            // 4. Construir índice con Indexer
            Indexer service = new Indexer(index, tokenizer, zipfPercent);
            System.out.println("\nConstruyendo índice desde: " + rootPath);
            try {
                service.indexDirectory(rootPath.toString());
            } catch (Exception e) {
                System.out.println("Error al indexar el directorio: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            System.out.println("Índice construido con éxito!");
            System.out.println("Número de documentos: " + index.getDocumentCount());
            System.out.println("Número de términos únicos: " + index.getAllTerms().size());

            // 5. Mostrar info de los primeros 20 documentos indexados
            System.out.println("\n=== Documentos registrados (primeros 20) ===");
            int docLimit = Math.min(20, index.getDocumentCount());
            for (int i = 0; i < docLimit; i++) {
                var md = index.getDocumentMetadata(i);
                System.out.printf("DocID: %d | Path: %s | Tokens: %d | Norma: %.4f%n",
                        md.getId(), md.getDecodedPath(), md.getLength(), md.getVectorNorm());
            }

            // 6. Procesar queries
            QueryProcessor qp = new QueryProcessor(index, tokenizer);
            while (true) {
                System.out.print("\nIngrese consulta o digite 'salir' para salir del programa: ");
                String query = sc.nextLine().trim();
                if (query.equalsIgnoreCase("salir")) break;

                System.out.print("Ingrese topK (ej: 10): ");
                int topK;
                try {
                    topK = Integer.parseInt(sc.nextLine().trim());
                    if (topK <= 0) topK = 10;
                } catch (NumberFormatException e) {
                    topK = 10;
                }

                MyVector<ScoredDocument> results = qp.search(query, topK);
                if (results == null || results.isEmpty()) {
                    System.out.println("No se encontraron documentos relevantes.");
                } else {
                    System.out.println("\n=== Resultados ===");
                    for (int i = 0; i < results.size(); i++) {
                        var sd = results.get(i);
                        System.out.printf("%d) DocID: %d | Path: %s | Score: %.4f%n",
                                i + 1, sd.getDocId(), sd.getPath(), sd.getScore());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}