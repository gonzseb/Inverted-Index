package index.io;

import java.io.*;

public class LocalFileReader {
    public static void readAllFilesRecursively(File root, FileHandler handler) throws IOException {
        if (root == null) return;
        if (!root.exists()) return;
        if (root.isFile()) {
            handler.handle(root);
            return;
        }
        File[] children = root.listFiles();
        if (children == null) return;
        for (File child : children) readAllFilesRecursively(child, handler);
    }

    public static String readFileToString(File f) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(f));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }

    public interface FileHandler {
        void handle(File f) throws IOException;
    }
}