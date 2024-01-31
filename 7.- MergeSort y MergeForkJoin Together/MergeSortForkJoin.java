import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;  

// Se manda a llamar a las librerias concurrent

// Este merge se realiza a partir de concurrencia
public class MergeSortForkJoin {
    // El constructor obtiene el arreglo
    public void sort(Integer[] a) {
        // Se crea un arreglo auxiliar (helper), del size de el arreglo que se mando
        Integer[] helper = new Integer[a.length];
        // Creamos un objeto ForkJoinPool de las librerias el cual nos ayudara a la concurrencia
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // Este objeto invoca un objeto de nuestra clase MergeSortTask que hereda de acciones recurvivas
        // Al invocar este objeto se manda tanto el arreglo original, la copia (auxiliar), indice de inicio, el size del arreglo orginal
        forkJoinPool.invoke(new MergeSortTask(a, helper, 0, a.length-1));
    }
    private class MergeSortTask extends RecursiveAction {
        // Se le asigna una version
        private static final long serialVersionUID = -749935388568367268L;
        // Se crean los atributos de la copia del arreglo, el arreglo original, el indice origen asi como el final
        private final Integer[] a;
        private final Integer[] helper;
        private final int lo;
        private final int hi;

        public MergeSortTask(Integer[] a, Integer[] helper, int lo, int hi) {
            // El constrcutor asigna estos datos a los atributos
            this.a = a;
            this.helper = helper;
            this.lo = lo;
            this.hi = hi;
        }

        // Se sobreescribe el metodo compute el cual es el que heredamos de RecursiveAction
        @Override
        protected void compute() {
            // Si es valor inicial es menor que el final (size del arreglo original)
            if(lo < hi) {
                // Se obtiene la mitad de ambos puntos y se le suma el indice inicial
                int mid = lo + (hi - lo) / 2;
                // Se crean 2 nuevas instnacias de la misma clase para generar la recursividad
                // Una de izquierda a en medio y otro de en medio a derecha 
                MergeSortTask left = new MergeSortTask(a, helper, lo, mid);
                MergeSortTask right = new MergeSortTask(a, helper, mid+1, hi);
                // Se invocan a las clases
                invokeAll(left, right);
                // Se realiza un merge, pasando en cada recursividad la variable mid, asi como el punto inicial y final
                merge(this.a, this.helper, this.lo, mid, this.hi);
            } else {
                // Si es que se termino el proceso porque el inicio es igual que el final
                return;
            }
        } 
        
        // Se toman los mismos datos, el arreglo original, la copia, el punto medio, inicio y final
        private void merge(Integer[] a, Integer[] helper, int lo, int mid, int hi) {
            // Se asigna que el punto medio menos uno es el limite de la izquierda
            int leftEnd = mid - 1;
            // La posicion temporal es la inical
            int tmpPos = lo;
            // Se obtiene el numero de elementos
            int numElements = hi - lo + 1;
            // Mientras la posicion inicial no llegue a el limite de la izquierda
            // Mientras la mitad no sea el punto inicial 
            while (lo <= leftEnd && mid <= hi) { 
                // Si el valor del inicio del arreglo inicial es menor que la mitad
                if (a[lo] < (a[mid])) {
                    // La posciion inicial se asignara al arreglo original en la posicion temporal
                    helper[tmpPos++] = a[lo++];
                } else {
                    // Si la posicion inicial ya llego (es igual) a la posicion de en medio
                    // La posciion del medio se asignara al arreglo original en la posicion temporal
                    helper[tmpPos++] = a[mid++];
                }
            }
            while (lo <= leftEnd) { 
                // Copia el resto de mitad de la izquierda
                helper[tmpPos++] = a[lo++];
            }
            while (mid <= hi) { 
                // Copia el resto de mitad de la derecha
                helper[tmpPos++] = a[mid++];
            }
            for (int i = 0; i < numElements; i++, hi--) { 
                // Los datos de la copia del array se asignana al arreglo original
                a[hi] = helper[hi];
            }
        }
    }
}