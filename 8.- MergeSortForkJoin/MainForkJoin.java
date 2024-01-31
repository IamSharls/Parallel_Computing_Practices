import java.util.Scanner;

public class MainForkJoin {
    
    public static void main(String[] args) {

        // Se cran 2 instancias de las diferentes clases, una con secuencial y el otro con 
        // Concurrencia
        MergeSortForkJoin mergeSortForkJoin = new MergeSortForkJoin();
        // Secuencial
    
        // Se crean 2 arreglos con capacidad de 1000 numeros
        Integer[] a2 = new Integer[1000000];

        // Se rellenan los arreglos con numeros random
        for (int x = 0; x < a2.length; x++) {
           
            a2[x] = (int) (Math.random() * 100000) - 100000;
        }
        // Se define el inicio y el final
        long inicio = 0;
        long fin = 0;
        

        // Realizamos lo mismo pero ahora con la concurrencia
        inicio = 0;
        fin = 0;
        inicio = System.currentTimeMillis();
        mergeSortForkJoin.sort(a2);
        fin = System.currentTimeMillis();
        System.out.println("Ordenamiento de manera concurrente:"+(fin-inicio) + " ms");
    }

}
