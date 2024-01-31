import java.util.List;
import java.util.concurrent.ExecutorService;

public class QuickExecutor extends LiberarTarea {
    private final int ST = 100000;
    private Integer arr[];
    private Integer inicio = 0;
    private Integer fin = 0;
    ExecutorService hilosPool;
    List list;

    //Recibimos todos los datos que vienen del main, el pool de hilos(10), la lista, el array y como
    //siempre el indice de inicio y final caracteristico de QuickSort
    public QuickExecutor(ExecutorService hilosPool, List list, Integer[] array, int start, int end) {
        this.arr = array;
        this.inicio = start;
        this.fin = end;
        this.hilosPool = hilosPool;
        this.list = list;
    }

    //Comprobamos que las subtareas esten libres para su ejecución
    @Override
    public boolean isReadyToProcess() {
        return true;
    }

    //El metodo run seria el equivalente al metodo compute, aqui es donde se va a ejecutar el trabajo
    //pesado, sin embargo aqui no se hace la division de tareas
    public void run() {
        //Llamamos a la funcion sort que es la que contiene todo el algoritmo QuickSort, enviandole
        //el arreglo y el inicio y el final de este
        sort(arr, inicio, fin);
    }

    private void sort(final Integer array[], int inicioSort, int finSort) {

        //Asignacion de variables y valores para manipular el metodo QuickSort
        int pivote = array[inicioSort]; 
        int puntoIzq = inicioSort;
        int puntoDer = finSort;
        final int LEFT = 1;
        final int RIGHT = -1;
        int pointerSide = RIGHT; 

        // ----------------------------------------------------------------------------------//
        // Todo este bloque sirve para reordenar el arreglo de manera que los elementos menores
        // al pivote queden del lado izquierdo y los mayores del lado derecho
        while (puntoIzq != puntoDer) {
            if (pointerSide == RIGHT) {
                if (array[puntoDer] < pivote) {
                    array[puntoIzq] = array[puntoDer];
                    puntoIzq++;
                    pointerSide = LEFT;
                } else {
                    puntoDer--;
                }
            } else if (pointerSide == LEFT) {
                if (array[puntoIzq] > pivote) {
                    array[puntoDer] = array[puntoIzq];
                    puntoDer--;
                    pointerSide = RIGHT;
                } else {
                    puntoIzq++;
                }
            }
        }
        
        array[puntoIzq] = pivote;
        //----------------------------------------------------------------------------------//
        //Aqui es donde la recursividad se convierte crear la concurrencia, añadimos a la lista
        //con el metodo submit tanto el ordenamiento del lado izquierdo como el del derecho.
        if ((puntoIzq - inicioSort) > 1) {
            if ((puntoIzq - inicioSort) > ST) {
                list.add(
                        hilosPool.submit(new QuickExecutor(hilosPool, list, array, inicioSort, puntoIzq - 1)));
            } else {
                sort(array, inicioSort, puntoIzq - 1);
            }
        }

        if ((finSort - puntoIzq) > 1) {
            if ((finSort - puntoIzq) > ST) {
                list.add(
                        hilosPool.submit(new QuickExecutor(hilosPool, list, array, puntoIzq + 1, finSort)));
            } else {
                sort(array, puntoIzq + 1, finSort);
            }
        }

    }

}