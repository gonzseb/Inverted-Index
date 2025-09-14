package index;

import index.domain.ScoredDocument;
import index.indexing.Indexer;
import index.indexing.InvertedIndex;
import index.structures.MyVector;
import index.text.QueryProcessor;
import index.text.SpanishBasicNormalizer;
import index.text.Stopwords;
import index.text.Tokenizer;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Pedir directorio de documentos
            System.out.print("Ingrese el path de la carpeta con documentos: ");
            String rootPath = sc.nextLine().trim();

            // 2. Crear índice y tokenizer
            InvertedIndex index = new InvertedIndex(16);
            Tokenizer tokenizer = new Tokenizer(new SpanishBasicNormalizer(), Stopwords.spanish());

            // 3. Pedir porcentaje de términos Zipf
            System.out.print("Ingrese porcentaje de términos a eliminar por Zipf (0 para omitir): ");
            double zipfPercent;
            try {
                zipfPercent = Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                zipfPercent = 0.0;
            }

            // 4. Construir índice con Indexer
            Indexer service = new Indexer(index, tokenizer, zipfPercent);
            System.out.println("\nConstruyendo índice...");
            service.indexDirectory(rootPath);

            System.out.println("Índice construido con éxito!");
            System.out.println("Número de documentos: " + index.getDocumentCount());
            System.out.println("Número de términos únicos: " + index.getAllTerms().size());

            // 5. Mostrar info de los primeros 15 documentos indexados
            System.out.println("\n=== Documentos registrados (primeros 15) ===");
            int docLimit = Math.min(15, index.getDocumentCount());
            for (int i = 0; i < docLimit; i++) {
                var md = index.getDocumentMetadata(i);
                System.out.printf("DocID: %d | Path: %s | Tokens: %d | Norma: %.4f%n",
                        md.getId(), md.getDecodedPath(), md.getLength(), md.getVectorNorm());
            }

            // 6. Procesar queries
            QueryProcessor qp = new QueryProcessor(index, tokenizer);
            while (true) {
                System.out.print("\nIngrese consulta (o 'salir' para terminar): ");
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
                if (results.isEmpty()) {
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