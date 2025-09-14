package index.indexing;

import index.domain.Term;
import index.interfaces.IInvertedIndex;
import index.structures.MyVector;

// Filtro por percentil: elimina los términos que estén dentro del percentil superior por DF.
// percent (0..100) indica el percentil de términos a eliminar (ej: 5.0 elimina top 5% más frecuentes).
public class ZipfFilter {
    public static void apply(IInvertedIndex index, double percentToRemove) {
        if (percentToRemove <= 0.0) return;
        MyVector<Term> terms = index.getAllTerms();
        if (terms.isEmpty()) return;

        // Construir vector simple de DF
        int m = terms.size();
        MyVector<Integer> dfs = new MyVector<>(m);
        for (int i = 0; i < m; i++) dfs.add(terms.get(i).getDf());

        // Ordenar dfs descendente (insertion sort simple sobre pares termIndex)
        // Para mantener correspondencia, haremos un vector de pares (df, term)
        MyVector<PairIntTerm> pairs = new MyVector<>(m);
        for (int i = 0; i < m; i++) pairs.add(new PairIntTerm(terms.get(i).getDf(), terms.get(i)));

        // Ordenar pairs por df descendente (insertion sort)
        for (int i = 1; i < pairs.size(); i++) {
            PairIntTerm key = pairs.get(i);
            int j = i - 1;
            while (j >= 0 && pairs.get(j).df < key.df) {
                pairs.set(j + 1, pairs.get(j));
                j--;
            }
            pairs.set(j + 1, key);
        }

        int removeCount = (int) Math.floor((percentToRemove / 100.0) * pairs.size());
        for (int i = 0; i < removeCount; i++) {
            Term t = pairs.get(i).term;
            index.removeTerm(t.getTerm());
        }
    }

    private static class PairIntTerm {
        int df;
        Term term;
        PairIntTerm(int df, Term term) { this.df = df; this.term = term; }
    }
}
