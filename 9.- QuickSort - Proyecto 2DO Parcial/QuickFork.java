import java.util.concurrent.RecursiveAction;

//Clase QuickSort con concurrencia
public class QuickFork extends RecursiveAction{
  private Integer[] info;
  private int inicio;
  private int fin;

  //Recibimos el arreglo y definimos las variables inicio y fin
  public QuickFork(Integer[] info) {
    this.info = info;
    inicio = 0;
    fin = info.length - 1;
  }

  //Esta funcion sera llamada por las subtareas
  public QuickFork(Integer[] info, int inicio, int fin) {
    this.info = info;
    this.inicio = inicio;
    this.fin = fin;
  }

  //La funcion compute proviene de la clase RecursiveAction y es la que nos permite
  //ejecutar el trabajo pesado de manera concurrente
  @Override
  protected void compute() {
    if(inicio < fin){
      //El pivote se crea con la clase partida, que sería la equivalente al bloque de codigo
      //que reordena el arreglo en el metodo secuencial
      int pivote = partida(info, inicio, fin);
      //Enviamos nuestras dos subtareas con el metodo invokeAll, las dos subtareas serian la parte
      //recursiva del metodo QuickSort para ordenar lado izquierdo y lado derecho
      invokeAll(new QuickFork(info,inicio,pivote), new QuickFork(info, pivote + 1, fin));
    }
  }

  //Funcion para reordenar el arreglo
  private int partida(Integer[] vec, int abajo, int arriba) {
    int pivote = vec[abajo];
    int inicio = abajo - 1;
    int fin  = arriba + 1;
    while (true){
      do {
        inicio++;
      }
      while (vec[inicio] < pivote);

      do {
        fin--;
      }
      while (vec[fin] > pivote);
      if (inicio >= fin)
        return fin;

      cambio(vec, inicio, fin);
    }
  }

  //Funcion para cambiar dos variables de posicion
  private void cambio(Integer[] vec, int inicio, int fin) {
    int temp = vec[inicio];
    vec[inicio] = vec[fin];
    vec[fin] = temp;
  }
}