package index.indexing;

import index.io.LocalFileReader;
import index.interfaces.IInvertedIndex;
import index.structures.MyVector;
import index.structures.MyVectorIterator;
import index.text.Tokenizer;

import java.io.File;
import java.io.IOException;

public class Indexer {
    private final IInvertedIndex index;
    private final Tokenizer tokenizer;
    private final double zipfPercentToRemove;

    public Indexer(IInvertedIndex index, Tokenizer tokenizer, double zipfPercentToRemove) {
        this.index = index;
        this.tokenizer = tokenizer;
        this.zipfPercentToRemove = zipfPercentToRemove;
    }

    public void indexDirectory(String rootPath) throws IOException {
        File root = new File(rootPath);

        LocalFileReader.readAllFilesRecursively(root, (file) -> {
            if (isTextFile(file)) indexFile(file);
        });

        ZipfFilter.apply(index, zipfPercentToRemove);

        index.computeDocumentVectorNorms(); // precomputar normas euclidianas
    }

    private boolean isTextFile(File f) {
        String name = f.getName().toLowerCase();
        return name.endsWith(".txt");
    }

    private void indexFile(File f) throws IOException {
        String content = LocalFileReader.readFileToString(f);

        int docId = index.registerDocument(f.getAbsolutePath());

        MyVector<String> tokens = tokenizer.tokenize(content);

        MyVectorIterator<String> it = tokens.iterator();

        while (it.hasNext()) index.insertToken(it.next(), docId);
    }
}