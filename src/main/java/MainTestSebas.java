import java.io.File;

class MainTestSebas {
    public static void main(String[] args) {
        InvertedIndex index = new InvertedIndex(new BasicSpanishTokenizer());
        String directoryPath = "C:\\Users\\sbsgo\\Downloads\\InvertedIndex\\InvertedIndex\\documents";
        File dir = new File(directoryPath);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                String[] paths = new String[files.length];
                for (int i = 0; i < files.length; i++) paths[i] = files[i].getAbsolutePath();
                index.processMultipleDocuments(paths);
            }
        }

        index.printStatistics();
        index.printIndex(5);
        index.searchAndDisplay("editionyou");
        index.searchAndDisplay("constraseña");
        System.out.println(index.getDocumentPath("doc1"));

        // SECOND COMMIT LINE !!!


    }
}