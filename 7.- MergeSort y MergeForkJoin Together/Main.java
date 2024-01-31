import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Se cran 2 instancias de las diferentes clases, una con secuencial y el otro con 
        // Concurrencia
        MergeSortForkJoin mergeSortForkJoin = new MergeSortForkJoin();
        // Secuencial
        MergeSort mergeSort = new MergeSort();

        // Se crean 2 arreglos con capacidad de 1000 numeros
        Integer[] a = new Integer[1000000];
        Integer[] a2 = new Integer[1000000];

        // Se rellenan los arreglos con numeros random
        for (int x = 0; x < a.length; x++) {
            a[x] = (int) (Math.random() * 1000000) - 1000000;
            a2[x] = (int) (Math.random() * 1000000) - 1000000;
        }
        for (int i = 0; i < a.length; ++i)
            System.out.print(a[i] + " ");
        System.out.println();
        // Se define el inicio y el final
        long inicio = 0;
        long fin = 0;
        // Se obtiene el tiempo del sistema
        inicio = System.currentTimeMillis();
        // Se manda a llamar al metodo secuancial
        mergeSort.mergeSort(a, 0, a.length - 1);
        // Se toma datos del tiempo en el sistema
        fin = System.currentTimeMillis();
        // Tiempo final - tiempo inicial = Tiempo transcurrido
        System.out.println("Ordenamiento de manera secuencial:"+(fin-inicio) + " ms");

        // Realizamos lo mismo pero ahora con la concurrencia
        inicio = 0;
        fin = 0;
        inicio = System.currentTimeMillis();
        mergeSortForkJoin.sort(a2);
        fin = System.currentTimeMillis();
        System.out.println("Ordenamiento de manera concurrente:"+(fin-inicio) + " ms");
    }
}
