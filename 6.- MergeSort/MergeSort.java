// Clase para realizar la practica de manera secuencial
public class MergeSort {

    // Se toma como parametros un arreglo, un numero en donde empezar y el size del arreglo
    public static void mergeSort(Integer[] a, int i, int i1) {
        // Se crea un arreglo temporal con el size del arreglo mandado por parametros
        Integer[] tmpArray = new Integer[a.length];
        // Se manda el arreglo original, la copia y el size del arreglo original
        mergeSort(a, tmpArray, 0, a.length - 1);
    }

    
    // Se mandan los parametros enviados por el mergesort
    // Y se tiene el primer elemento asi como el ultimo (size del arreglo original)
    private static void mergeSort(
            Integer a[], Integer tmpArray[], int left, int right) {
        // Si el primer elemento es menor a la cantidad total de elementos
        if (left < right) {
            // Se obtiene el centro
            int center = (left + right) / 2;
            // Aqui es donde ocurre la magia de la recursividad
            // Se ira partiendo a la mitad como una busqueda binaria
            mergeSort(a, tmpArray, left, center);
            mergeSort(a, tmpArray, center + 1, right);
            // Se manda a llamar al metodo merge con los ultimos parametros 
            merge(a, tmpArray, left, center + 1, right);
        }
    }


    private static void merge(Integer a[], Integer tmpArray[], int leftPos, int rightPos, int rightEnd) {
        // El rightPosition (center + 1) se le resta 1
        // Asi, asignando el centro a el final del apartado izquierdo
        int leftEnd = rightPos - 1;
        // Asignamos el valor de la izquiera a una posicion temporal
        int tmpPos = leftPos;
        // Obtenemos el numero total de elementos
        int numElements = rightEnd - leftPos + 1;
        // Mientras que la posicion izquiera actual sea menor o igual a la posicion izquierda
        // Mientras que la posicion derecha actual sea menor o igual a la posicion derecha
        while (leftPos <= leftEnd && rightPos <= rightEnd) { // Main loop
            // Si la posicion actual izquierda es menor que la posicion derecha
            if (a[leftPos] < (a[rightPos])) {
                // La pisicion actual del arreglo temporal es igual a la ultima posicion actual de la izquierda
                tmpArray[tmpPos++] = a[leftPos++];
            } else {
                tmpArray[tmpPos++] = a[rightPos++];
            }
        }
        
        while (leftPos <= leftEnd) { 
            // Copia el resto de mitad de la izquierda
            tmpArray[tmpPos++] = a[leftPos++];
            
        }
        while (rightPos <= rightEnd) { 
            // Copia el resto de mitad de la derecha
            tmpArray[tmpPos++] = a[rightPos++];
        }
        for (int i = 0; i < numElements; i++, rightEnd--) { 
            // Los datos de la copia del array se asignana al arreglo original
            a[rightEnd] = tmpArray[rightEnd];
        }
    }
}

