package index.domain;

public class Document {
    private final int id;
    private final String decodedPath;
    private int length; // número de tokens del documento
    private double vectorNorm; // norma L2 (euclidiana) del vector TF-IDF del documento (precomputada)

    public Document(int id, String path) {
        this.id = id;
        this.decodedPath = path;
        this.length = 0;
        this.vectorNorm = 0.0;
    }

    public int getId() { return id; }

    public String getDecodedPath() { return decodedPath; }

    public int getLength() { return length; }

    public void incrementLength() { length++; }

    public double getVectorNorm() { return vectorNorm; }

    public void setVectorNorm(double v) { this.vectorNorm = v; }
}