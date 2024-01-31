//Clase para la interfaz del servidor 

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface serverInterfaz extends Remote {
		
	//Metodos remotos
	public Integer[] quickQuick(Integer[] vec, int inicio, int fin)throws RemoteException;

}