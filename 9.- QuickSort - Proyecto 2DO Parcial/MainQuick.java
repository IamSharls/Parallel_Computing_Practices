import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Vector;
import java.util.List;

public class MainQuick extends JFrame implements ActionListener {

  private static JButton bt1, bt2, bt3, bt4;
  public static JTextField tf1, tf2, tf3;
  public static JLabel jl1, jl2, jl3, jl4, jl5, jl6, jl7, jl8;
  public static JTextArea jt1;

  public static int elementosN;
  public static Integer[] b;
  public static Integer[] c;
  public static Integer[] d;
  public static Integer[] f;

  public static long inicio = 0;
  public static long fin = 0;
  public static long tiempoFinal = 0;

  //Constructor
  public MainQuick() {
    //Todos los componentes del frame
    setLayout(null);
    setTitle("Optimizar el ordenamiento QuickSort");

    jl1 = new JLabel("Ingresa el numero de elementos a ordenar");
    jl1.setBounds(180, 20, 250, 30);
    add(jl1);

    tf1 = new JTextField();
    tf1.setBounds(220, 80, 150, 30);
    add(tf1);

    bt1 = new JButton("Ingresar y generar arreglo");
    bt1.setBounds(195, 150, 200, 40);
    bt1.setBackground(new Color(107, 219, 34));
    add(bt1);
    bt1.addActionListener(this);

    bt2 = new JButton("Ordenar con Secuencial");
    bt2.setBounds(20, 300, 200, 40);
    bt2.setBackground(new Color(255, 87, 51));
    add(bt2);
    bt2.addActionListener(this);

    bt3 = new JButton("Ordenar con Concurrencia 1 - Fork/Join");
    bt3.setBounds(270, 250, 290, 40);
    bt3.setBackground(new Color(93, 149, 254));
    add(bt3);
    bt3.addActionListener(this);

    bt4 = new JButton("Ordenar con Concurrencia 2 - Executor");
    bt4.setBounds(270, 300, 290, 40);
    bt4.setBackground(new Color(93, 149, 254));
    add(bt4);
    bt4.addActionListener(this);

    jl2 = new JLabel("Advertencias");
    jl2.setBounds(10, 530, 400, 30);
    add(jl2);

    jl3 = new JLabel("Tiempo de ejecución - Secuencial");
    jl3.setBounds(25, 350, 250, 30);
    add(jl3);

    jl4 = new JLabel("Tiempo de ejecución - Concurrente Fork/Join");
    jl4.setBounds(290, 350, 260, 30);
    add(jl4);

    jl7 = new JLabel("Tiempo de ejecución - Concurrente Executor");
    jl7.setBounds(290, 430, 260, 30);
    add(jl7);

    jl5 = new JLabel("Tiempo");
    jl5.setBounds(100, 390, 250, 30);
    add(jl5);

    jl6 = new JLabel("Tiempo");
    jl6.setBounds(395, 390, 250, 30);
    add(jl6);

    jl8 = new JLabel("Tiempo");
    jl8.setBounds(395, 470, 250, 30);
    add(jl8);

  }

  //Funciones para activar los botones
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == bt1) {
      //Rellenamos el arreglo
      rellenarArreglo();
    }
    if (e.getSource() == bt2) {
      //Validamos que el arreglo no este vacio
      if (b == null) {
        jl2.setText("Debe delimitar el numero de elementos primero");
      } else {

        //Llamamos a la funcion y calculamos el tiempo que tarda en terminar
        inicio = System.currentTimeMillis();
        ordenacionRapida(b);
        fin = System.currentTimeMillis();
        tiempoFinal = (fin - inicio);
        String time = Double.toString(tiempoFinal);
        jl2.setText("Elementos ordenados por secuencia");
        jl5.setText(time + " ms");
        //System.out.println("Elementos ordenados por secuencia");
        //imprimirArreglo(b);

        //Reseteamos el arreglo por si el usuario  presiona denuevo el boton
        b = Arrays.copyOf(f, f.length);

      }
    }
    if (e.getSource() == bt3) {
      //Validamos que el arreglo no este vacio
      if (b == null) {
        jl2.setText("Debe delimitar el numero de elementos primero");
      } else {

        inicio = System.currentTimeMillis();
        //Llamamos a la funcion Fork creando un objeto y le enviamos el arreglo
        QuickFork qF = new QuickFork(c);
        //Creamos el objeto de la clase ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();
        //Iniciamos el objeto pool mandandole el objeto qF que contiene el arreglo
        pool.invoke(qF);
        //Una vez que termine el algoritmo apagamos el Fork
        pool.shutdown();
        fin = System.currentTimeMillis();
        tiempoFinal = (fin - inicio);
        String time = Double.toString(tiempoFinal);
        jl2.setText("Elementos ordenados por concurrencia - Fork/Join");
        jl6.setText(time + " ms");
        //System.out.println("Elementos ordenados por concurrencia - Fork/Join");
        //imprimirArreglo(c);

        //Reseteamos el arreglo por si el usuario  presiona denuevo el boton
        c = Arrays.copyOf(f, f.length);
      }
    }
    if (e.getSource() == bt4) {
      if (b == null) {
        jl2.setText("Debe delimitar el numero de elementos primero");
      } else {

        //Creamos un pool de hilos con un objeto de la clase ExecutorService
        final ExecutorService executor = Executors.newFixedThreadPool(10);
        List futures = new Vector();
        inicio = System.currentTimeMillis();
        //Creamos un objeto de la clase en donde esta el algoritmo Executor, le enviamos el pool
        //de hilos, la lista, el arreglo y  el inicio y el final
        QuickExecutor laTarea = new QuickExecutor(executor, futures, d, 0, d.length - 1);
        //Añadimos el pool a la lista
        futures.add(executor.submit(laTarea));
        //Con la ayuda de un while estaremos comprobando si las subtareas ya enviaron un resultado
        //con la funcion get()
        while (!futures.isEmpty()) {
          
          Future topFeature = (Future) futures.remove(0);
          try {
            if (topFeature != null)
              topFeature.get();
          } catch (InterruptedException ie) {
            ie.printStackTrace();
          } catch (ExecutionException ie) {
            ie.printStackTrace();
          }

        }
        //System.out.println("Elementos ordenados por concurrencia - Executor");
        //imprimirArreglo(d);
        fin = System.currentTimeMillis();
        tiempoFinal = (fin - inicio);
        String time = Double.toString(tiempoFinal);
        jl2.setText("Elementos ordenados por concurrencia - Executor ");
        jl8.setText(time + " ms");
        //Al igual que en Fork/Join, una vez que termine el algoritmo apagamos el Executor
        executor.shutdown();
        //Reseteamos el arreglo por si el usuario  presiona denuevo el boton
        d = Arrays.copyOf(f, f.length);
      }
    }
  }

  // Funcion para comenzar el QuickSort, enviando el vector y el inicio y el final
  // del mismo
  public static void ordenacionRapida(Integer vec[]) {
    final int N = vec.length;
    quickSort(vec, 0, N - 1);
  }

  //Funcion del algoritmo QuickSort
  public static void quickSort(Integer vec[], int inicio, int fin) {
    // Validacion para ver si realmente el arreglo tiene más de un elementos
    if (inicio >= fin)
      return;
    // Elegimos el pivote que es el numero de referencia tomado del vector.
    int pivote = vec[inicio];
    // Variable que nos serviran para reacomodar el arreglo
    int elemIzq = inicio + 1;
    int elemDer = fin;

    // ----------------------------------------------------------------------------------//
    // Todo este bloque sirve para reordenar el arreglo de manera que los elementos menores
    // al pivote queden del lado izquierdo y los mayores del lado derecho
    while (elemIzq <= elemDer) {
      while (elemIzq <= fin && vec[elemIzq] < pivote) {
        elemIzq++;
      }
      while (elemDer > inicio && vec[elemDer] >= pivote) {
        elemDer--;
      }

      // Estas  dos validaciones sirven para cambiar dos elementos dependiendo si el de la
      // derecha es mayor o menor que el de la izquierda o viceversa
      if (elemIzq < elemDer) {
        int temp = vec[elemIzq];
        vec[elemIzq] = vec[elemDer];
        vec[elemDer] = temp;
      }
    }
    
    if (elemDer > inicio) {
      int temp = vec[inicio];
      vec[inicio] = vec[elemDer];
      vec[elemDer] = temp;
    }
    // ----------------------------------------------------------------------------------//

    // Se hace recursividad para seguir dividiendo y ordenando tanto el lado izquierdo
    // como el derecho
    quickSort(vec, inicio, elemDer - 1);
    quickSort(vec, elemDer + 1, fin);
  }

  //Funcion para rellenar el arreglo
  public void rellenarArreglo() {
    //Validaciones para el bloque de texto
    if (tf1.getText().length() >= 1) {

      elementosN = Integer.parseInt(tf1.getText());

      if (elementosN > 0) {

        //Llenamos el arreglo con la cantidad de elementos elegido por el usuario,
        //los elementos son aleatorios
        Integer[] a = new Integer[elementosN];

        for (int i = 0; i < a.length; i++) {
          a[i] = (int) (Math.random() * elementosN);
        }
        //Copiamos el array a multiples arrays para que cada metodo de ordenamiento
        //manipule cada array individualmente
        System.out.println("Tamaño del array: " + a.length);
        b = Arrays.copyOf(a, a.length);
        c = Arrays.copyOf(a, a.length);
        d = Arrays.copyOf(a, a.length);
        f = Arrays.copyOf(a, a.length);

        tf1.setText("");
        jl2.setText("");
        String res = " ";
        //Funcion para imprimir los elementos generados
        /*
         * System.out.println("Elementos del arreglo desordenados");
         * for (int i = 0; i < a.length; ++i) {
         * res += a[i] + " ";
         * }
         */

        jl2.setText(elementosN + " elementos generados aleatoriamente de 0 a " + elementosN);
        System.out.println(res);

      } else {
        jl2.setText("El numero de elementos tiene que ser mayor a cero");
      }
    } else {
      jl2.setText("Ingrese la cantidad de elementos");
    }

  }

  //Funcion para imprimir el arreglo
  static void imprimirArreglo(Integer arr[]) {
    int n = arr.length;
    for (int i = 0; i < n; ++i) {
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }

  public static void main(String args[]) {
    MainQuick hs = new MainQuick();
    //Proporciones del frame
    hs.setBounds(0, 0, 600, 600);
    //Frame visible
    hs.setVisible(true);
    //Inhabilitamos el poder cambiar el tamaño de la ventana
    hs.setResizable(false);
    //Funcion para hacer que el frame aparezca en medio de la pantalla
    hs.setLocationRelativeTo(null);
    //Funcion para poder cerrar por completo el programa con equis
    hs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

}