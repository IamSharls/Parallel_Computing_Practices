//Clase para iniciar el servidor

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class serverIniciar extends UnicastRemoteObject implements serverInterfaz{

	public serverIniciar() throws RemoteException {
		
		
	}
	
	public static void main(String[] args) {

		//Llamamos a la funcion para iniciar el rmiregistry
		registry();	

		String ipLocal = "127.0.0.1"; //Aqui se pone la IP del ordenador local,
									//el que va a hacer el servidor
		String nombreServicio = "RMIChat"; //Nombre del servicio, puede ser el que sea
		
		//Levantamos el servidor
		try{
			serverInterfaz interfazServer = new serverIniciar();
			Naming.rebind("rmi://" + ipLocal + "/" + nombreServicio, interfazServer);
			System.out.println("Server iniciado");
		}
		catch(Exception e){
			System.out.println("No se inició el server");
		}	
	}

	//Funcion para hacer el rmiregistry automatico
	public static void registry() {
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}
	}
	
	//Funcion remota quicksort
	
	public Integer[] quickQuick(Integer[] vec, int inicio, int fin) throws RemoteException{

		if (inicio >= fin){
			return vec;
		}
		
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
		
		quickQuick(vec, inicio, elemDer - 1);
		quickQuick(vec, elemDer + 1, fin);

		return vec;

	}
	
	
}