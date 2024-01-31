//Clase para el diseño del chat

import java.awt.event.*;
import java.awt.*;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Vector;
import java.util.List;

import java.awt.Color;
import javax.swing.DefaultButtonModel;
;

public class clienteDiseño extends JFrame implements ActionListener {

	// Declaración de todos los elementos de la interface
	public static clienteInicio inicCliente;

	private static JButton bt1, bt2, bt3, bt4, bt5, bt6, bt8;
	public static JTextField tf1, tf2, tf3;
	public static JLabel jl1, jl2, jl3, jl4, jl5, jl6, jl7, jl8, jl9, jl10;
	public static JTextArea jt1;

	public static int elementosN;
	public static Integer[] b;
	public static Integer[] c;
	public static Integer[] d;
	public static Integer[] f;
	public static Integer[] g;

	public static long inicio = 0;
	public static long fin = 0;
	public static long tiempoFinal = 0;

	public static int banderaConectado = 0;

	public clienteDiseño() {
		// Todos los componentes del frame
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
		bt1.setBorder(null);
		bt1.setModel(new FixedStateButtonModel());

		bt2 = new JButton("Ordenar con Secuencial");
		bt2.setBounds(20, 250, 200, 40);
		bt2.setBackground(new Color(255, 87, 51));
		add(bt2);
		bt2.addActionListener(this);
		bt2.setBorder(null);
		bt2.setModel(new FixedStateButtonModel());

		bt3 = new JButton("Ordenar con Concurrencia - Fork/Join");
		bt3.setBounds(270, 250, 290, 40);
		bt3.setBackground(new Color(93, 149, 254));
		add(bt3);
		bt3.addActionListener(this);
		bt3.setBorder(null);
		bt3.setModel(new FixedStateButtonModel());

		bt4 = new JButton("Ordenar con Servidor");
		bt4.setBounds(200, 390, 200, 40);
		bt4.setBackground(new Color(93, 149, 254));
		add(bt4);
		bt4.addActionListener(this);
		bt4.setBorder(null);
		bt4.setModel(new FixedStateButtonModel());

		bt6 = new JButton("Ordenar con Servidor");
		bt6.setBounds(150, 390, 300, 40);
		bt6.setBackground(new Color(93, 149, 254));
		add(bt6);
		bt6.addActionListener(this);
		bt6.setModel(new FixedStateButtonModel());

		bt5 = new JButton("Conectar");
		bt5.setBounds(435, 520, 150, 40);
		bt5.setBackground(new Color(93, 149, 254));
		add(bt5);
		bt5.addActionListener(this);
		bt5.setVisible(true);
		bt5.setBorder(null);
		
		jl2 = new JLabel("Advertencias");
		jl2.setBounds(10, 530, 400, 30);
		add(jl2);

		jl3 = new JLabel("Tiempo de ejecución - Secuencial");
		jl3.setBounds(25, 300, 250, 30);
		add(jl3);

		jl4 = new JLabel("Tiempo de ejecución - Concurrente Fork/Join");
		jl4.setBounds(290, 300, 260, 30);
		add(jl4);

		jl9 = new JLabel("Tiempo de ejecución - Servidor");
		jl9.setBounds(210, 440, 260, 30);
		add(jl9);

		jl5 = new JLabel("Tiempo");
		jl5.setBounds(100, 340, 250, 30);
		add(jl5);

		jl6 = new JLabel("Tiempo");
		jl6.setBounds(395, 340, 250, 30);
		add(jl6);
	
		jl10 = new JLabel("Tiempo");
		jl10.setBounds(280, 480, 250, 30);
		add(jl10);
	}

	public class FixedStateButtonModel extends DefaultButtonModel    {

        @Override
        public boolean isPressed() {
            return false;
        }

        @Override
        public boolean isRollover() {
            return false;
        }

        @Override
        public void setRollover(boolean b) {
            //NOOP
        }

    }

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == bt5) {
				//Llamamos a la funcion para conectar al servidor
				conectar();
				banderaConectado++;
				//Y deshabilitamos el boton para no crear más conexiones despues
				bt5.setEnabled(false);
			}
			if (e.getSource() == bt8) {
				if (b == null) {
					jl2.setText("Debe delimitar el numero de elementos primero");
				} else {
					if(banderaConectado == 1){
					
					// Hacemos todo el primer bloque del algoritmo QuickSort para haga el proceso
					// de division del arreglo
					final int N = g.length;
					int inicioArreglo = 0;
					int finArreglo = N - 1;

					inicio = System.currentTimeMillis();

					int pivote = g[inicioArreglo];
					int elemIzq = inicioArreglo + 1;
					int elemDer = finArreglo;

					while (elemIzq <= elemDer) {
						while (elemIzq <= finArreglo && g[elemIzq] < pivote) {
							elemIzq++;
						}
						while (elemDer > inicioArreglo && g[elemDer] >= pivote) {
							elemDer--;
						}

						if (elemIzq < elemDer) {
							int temp = g[elemIzq];
							g[elemIzq] = g[elemDer];
							g[elemDer] = temp;
						}
					}

					if (elemDer > inicioArreglo) {
						int temp = g[inicioArreglo];
						g[inicioArreglo] = g[elemDer];
						g[elemDer] = temp;
					}

					// Enviamos la parte izquierda del arreglo (numeros menos al pivote) al servidor
					Integer[] o = inicCliente.interfazServer.quickQuick(g, inicioArreglo, elemDer- 1);

					// Enviamos la parte derecha del arreglo (numeros mayores al pivote) aqui mismo, al
					// cliente
					quickSortRMI(g, elemDer + 1, finArreglo);

					fin = System.currentTimeMillis();
					tiempoFinal = (fin - inicio);
					String time = Double.toString(tiempoFinal);
					jl2.setText("Elementos ordenados por Servidor");
					jl10.setText(time + " ms");

					/* 
					System.out.println("Parte de la izquierda del arreglo");
					for (int i = 0; i < elemDer; ++i) {
						System.out.print(o[i] + " ");
					}
					
					System.out.println("");

					System.out.println("Parte de la derecha del arreglo");
					for (int i = elemDer; i < g.length; ++i) {
						System.out.print(g[i] + " ");
					}
					
					System.out.println("");

					//Para poder juntarlos, imprimimos la parte izquierda desde el inicio del arreglo
					// hasta el pivote, y la parte derecha se imprime desde el pivote hasta el final
					// del arreglo
					System.out.println("Arreglos del servidor y el cliente combinados");
					for (int i = 0; i < elemDer; ++i) {
						System.out.print(o[i] + " ");
					}
					for (int i = elemDer; i < g.length; ++i) {
						System.out.print(g[i] + " ");
					}*/
				}else{
					jl2.setText("Conecte al servidor primero");
				}
				}
			}
		} catch (RemoteException remoteExc) {
			remoteExc.printStackTrace();
		}
		if (e.getSource() == bt1) {
			// Rellenamos el arreglo
			rellenarArreglo();

		}
		if (e.getSource() == bt2) {
			// Validamos que el arreglo no este vacio
			if (b == null) {
				jl2.setText("Debe delimitar el numero de elementos primero");
			} else {

				final int N = b.length;
				int finArreglo = N - 1;
				// Llamamos a la funcion y calculamos el tiempo que tarda en terminar
				inicio = System.currentTimeMillis();
				quickSort(b, 0, finArreglo);
				fin = System.currentTimeMillis();
				tiempoFinal = (fin - inicio);
				String time = Double.toString(tiempoFinal);
				jl2.setText("Elementos ordenados por secuencia");
				jl5.setText(time + " ms");

				//System.out.println("Arreglo ordenado normal");
				//imprimirArreglo(b);
				// Reseteamos el arreglo por si el usuario presiona denuevo el boton
				b = Arrays.copyOf(f, f.length);

			}
		}
		if (e.getSource() == bt3) {
			// Validamos que el arreglo no este vacio
			if (b == null) {
				jl2.setText("Debe delimitar el numero de elementos primero");
			} else {

				inicio = System.currentTimeMillis();
				// Llamamos a la funcion Fork creando un objeto y le enviamos el arreglo
				QuickFork qF = new QuickFork(c);
				// Creamos el objeto de la clase ForkJoinPool
				ForkJoinPool pool = new ForkJoinPool();
				// Iniciamos el objeto pool mandandole el objeto qF que contiene el arreglo
				pool.invoke(qF);
				// Una vez que termine el algoritmo apagamos el Fork
				pool.shutdown();
				fin = System.currentTimeMillis();
				tiempoFinal = (fin - inicio);
				String time = Double.toString(tiempoFinal);
				jl2.setText("Elementos ordenados por concurrencia - Fork/Join");
				jl6.setText(time + " ms");
				// System.out.println("Elementos ordenados por concurrencia - Fork/Join");
				// imprimirArreglo(c);

				// Reseteamos el arreglo por si el usuario presiona denuevo el boton
				c = Arrays.copyOf(f, f.length);
			}
		}
		if (e.getSource() == bt4) {
			if (b == null) {
				jl2.setText("Debe delimitar el numero de elementos primero");
			} else {
				if(banderaConectado == 1){
				// Creamos un pool de hilos con un objeto de la clase ExecutorService
				final ExecutorService executor = Executors.newFixedThreadPool(10);
				List futures = new Vector();
				inicio = System.currentTimeMillis();
				// Creamos un objeto de la clase en donde esta el algoritmo Executor, le
				// enviamos el pool
				// de hilos, la lista, el arreglo y el inicio y el final
				QuickExecutor laTarea = new QuickExecutor(executor, futures, d, 0, d.length - 1);
				// Añadimos el pool a la lista
				futures.add(executor.submit(laTarea));
				// Con la ayuda de un while estaremos comprobando si las subtareas ya enviaron
				// un resultado
				// con la funcion get()
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
				// System.out.println("Elementos ordenados por concurrencia - Executor");
				// imprimirArreglo(d);
				fin = System.currentTimeMillis();
				tiempoFinal = (fin - inicio);
				String time = Double.toString(tiempoFinal);
				jl2.setText("Elementos ordenados por Servidor");
				jl10.setText(time + " ms");
				// Al igual que en Fork/Join, una vez que termine el algoritmo apagamos el
				// Executor
				executor.shutdown();
				// Reseteamos el arreglo por si el usuario presiona denuevo el boton
				d = Arrays.copyOf(f, f.length);
			}else{
				jl2.setText("Conecte al servidor primero");
			}
			}
		}

		try {
			if (e.getSource() == bt6) {
				if (b == null) {
					jl2.setText("Debe delimitar el numero de elementos primero");
				} else {
					if(banderaConectado == 1){
					//Boton prueba
					final int N = g.length;
					int inicioArreglo = 0;
					int finArreglo = N - 1;

					inicio = System.currentTimeMillis();

					int pivote = g[inicioArreglo];
					int elemIzq = inicioArreglo + 1;
					int elemDer = finArreglo;

					while (elemIzq <= elemDer) {
						while (elemIzq <= finArreglo && g[elemIzq] < pivote) {
							elemIzq++;
						}
						while (elemDer > inicioArreglo && g[elemDer] >= pivote) {
							elemDer--;
						}

						if (elemIzq < elemDer) {
							int temp = g[elemIzq];
							g[elemIzq] = g[elemDer];
							g[elemDer] = temp;
						}
					}

					if (elemDer > inicioArreglo) {
						int temp = g[inicioArreglo];
						g[inicioArreglo] = g[elemDer];
						g[elemDer] = temp;
					}

					// Ordena parte izquierda
					Integer[] o = inicCliente.interfazServer.quickQuick(g, inicioArreglo, elemDer- 1);

					// Ordena parte derecha
					quickSortRMI(g, elemDer + 1, finArreglo);

					fin = System.currentTimeMillis();
					tiempoFinal = (fin - inicio);
					String time = Double.toString(tiempoFinal);
					jl2.setText("Elementos ordenados por Servidor");
					jl10.setText(time + " ms");

					System.out.println("Arreglo cliente");
					imprimirArreglo(g);

					System.out.println("Arreglo del servidor");
					imprimirArreglo(o);

					System.out.println("Parte de la izquierda del arreglo");
					for (int i = 0; i < elemDer; ++i) {
						System.out.print(o[i] + " ");
					}
					
					System.out.println("");

					System.out.println("Parte de la derecha del arreglo");
					for (int i = elemDer; i < g.length; ++i) {
						System.out.print(g[i] + " ");
					}
					
					System.out.println("");

					System.out.println("Arreglos del servidor y el cliente combinados");
					for (int i = 0; i < elemDer; ++i) {
						System.out.print(o[i] + " ");
					}
					for (int i = elemDer; i < g.length; ++i) {
						System.out.print(g[i] + " ");
					}
				}else{
					jl2.setText("Conecte al servidor primero");
				}
				}
			}
		} catch (RemoteException remoteExc) {
			remoteExc.printStackTrace();
		}
		

	}

	// Funcion del algoritmo QuickSort
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
		// Todo este bloque sirve para reordenar el arreglo de manera que los elementos
		// menores
		// al pivote queden del lado izquierdo y los mayores del lado derecho
		while (elemIzq <= elemDer) {
			while (elemIzq <= fin && vec[elemIzq] < pivote) {
				elemIzq++;
			}
			while (elemDer > inicio && vec[elemDer] >= pivote) {
				elemDer--;
			}

			// Estas dos validaciones sirven para cambiar dos elementos dependiendo si el de
			// la
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

		// Se hace recursividad para seguir dividiendo y ordenando tanto el lado
		// izquierdo
		// como el derecho
		quickSort(vec, inicio, elemDer - 1);
		quickSort(vec, elemDer + 1, fin);
	}

	// Funcion del algoritmo QuickSort
	public static void quickSortRMI(Integer vec[], int inicio, int fin) {
		
		// Validacion para ver si realmente el arreglo tiene más de un elementos
		if (inicio >= fin)
			return;

		// Elegimos el pivote que es el numero de referencia tomado del vector.
		int pivote = vec[inicio];
		// Variable que nos serviran para reacomodar el arreglo
		int elemIzq = inicio + 1;
		int elemDer = fin;

		// ----------------------------------------------------------------------------------//
		// Todo este bloque sirve para reordenar el arreglo de manera que los elementos
		// menores
		// al pivote queden del lado izquierdo y los mayores del lado derecho
		while (elemIzq <= elemDer) {
			while (elemIzq <= fin && vec[elemIzq] < pivote) {
				elemIzq++;
			}
			while (elemDer > inicio && vec[elemDer] >= pivote) {
				elemDer--;
			}

			// Estas dos validaciones sirven para cambiar dos elementos dependiendo si el de
			// la
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

		// Se hace recursividad para seguir dividiendo y ordenando tanto el lado
		// izquierdo
		// como el derecho
		quickSortRMI(vec, inicio, elemDer - 1);
		quickSortRMI(vec, elemDer + 1, fin);

	}

	// Funcion para rellenar el arreglo
	public void rellenarArreglo() {
		// Validaciones para el bloque de texto
		if (tf1.getText().length() >= 1) {

			elementosN = Integer.parseInt(tf1.getText());

			if (elementosN > 0) {

				// Llenamos el arreglo con la cantidad de elementos elegido por el usuario,
				// los elementos son aleatorios
				Integer[] a = new Integer[elementosN];

				for (int i = 0; i < a.length; i++) {
					a[i] = (int) (Math.random() * elementosN);
				}
				// Copiamos el array a multiples arrays para que cada metodo de ordenamiento
				// manipule cada array individualmente
				System.out.println("Tamaño del array: " + a.length);
				b = Arrays.copyOf(a, a.length);
				c = Arrays.copyOf(a, a.length);
				d = Arrays.copyOf(a, a.length);
				f = Arrays.copyOf(a, a.length);
				g = Arrays.copyOf(a, a.length);

				tf1.setText("");
				jl2.setText("");
				String res = " ";
				// Funcion para imprimir los elementos generados
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

	// Funcion para imprimir el arreglo
	static void imprimirArreglo(Integer arr[]) {
		int n = arr.length;
		for (int i = 0; i < n; ++i) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// Funcion para conectar al servidor
	private void conectar() throws RemoteException {
		try {
			// Enviamos el nick y la interfaz para iniciar la conexion
			inicCliente = new clienteInicio(this);
			inicCliente.clienteInicio();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		clienteDiseño hs = new clienteDiseño();
		// Proporciones del frame
		hs.setBounds(0, 0, 600, 600);
		// Frame visible
		hs.setVisible(true);
		// Inhabilitamos el poder cambiar el tamaño de la ventana
		hs.setResizable(false);
		// Funcion para hacer que el frame aparezca en medio de la pantalla
		hs.setLocationRelativeTo(null);
		// Funcion para poder cerrar por completo el programa con equis
		hs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//bt5.doClick();
	}

}
