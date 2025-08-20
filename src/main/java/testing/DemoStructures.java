package testing;

import datastructures.*;

public class DemoStructures {
    public static void main(String[] args) {
        // --- DoublyCircularLinkedList test ---
        DoublyCircularLinkedList<String> list = new DoublyCircularLinkedList<>();

        // Agregar elementos
        list.addLast("gato");
        list.addLast("come");
        list.addLast("pescado");
        list.addFirst("perro");

        // Estado inicial
        System.out.println("Lista inicial:");
        printList(list);

        // Probar peek
        System.out.println("peekFirst(): " + list.peekFirst()); // perro
        System.out.println("peekLast(): " + list.peekLast());   // pescado

        // Eliminar primero y último
        System.out.println("\nremoveFirst(): " + list.removeFirst()); // perro
        System.out.println("removeLast(): " + list.removeLast());   // pescado
        printList(list); // Solo debería quedar "gato come"

        // Eliminar por valor
        list.remove("come");
        System.out.println("\nTras remove(\"come\"):");
        printList(list); // Solo debería quedar "gato"

        // Agregar más para probar circularidad
        list.addFirst("pez");
        list.addLast("pollo");
        System.out.println("\nLista tras agregar 'pez' y 'pollo':");
        printList(list);

        // Comprobar circularidad
        System.out.println("\nComprobación circularidad:");
        System.out.println("head.prev = " + list.peekFirst()); // debería apuntar a tail
        System.out.println("tail.next = " + list.peekLast()); // debería apuntar a head
        System.out.println("size = " + list.size());

        // Recorrido inverso
        System.out.println("\nIterador inverso:");
        for (MyIterator<String> it = list.reverseIterator(); it.hasNext(); ) {
            System.out.print(it.next() + " ");
        }
        System.out.println("\n");

        // --- Vector test ---
        Vector a = new Vector();
        a.set(0, 1); a.set(1, 2); a.set(2, 3);

        Vector b = new Vector();
        b.set(0, 4); b.set(1, 5); b.set(2, 6);

        System.out.println("Vector a = " + a);
        System.out.println("Vector b = " + b);
        System.out.println("dot(a,b) = " + a.dot(b));
        System.out.println("||a|| = " + a.norm());
        System.out.println();

        // --- HashTable test ---
        HashTable<String, String> ht = new HashTable<>();
        ht.put("gato", "document1");
        ht.put("perro", "document2");
        ht.put("gato", "document1,document3"); // update

        System.out.println("HashTable:");
        MyIterator<String> it = ht.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String val = ht.get(key);
            System.out.println("  " + key + " -> " + val);
        }

        // remove test
        ht.remove("perro");
        System.out.println("\nTras eliminar 'perro':");
        it = ht.iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println("  " + key + " -> " + ht.get(key));
        }
    }

    /** Función auxiliar para imprimir la lista */
    private static void printList(DoublyCircularLinkedList<String> list) {
        for (MyIterator<String> it = list.iterator(); it.hasNext(); ) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }
}
