//Clase para la interfaz del servidor del cliente

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface clienteInterfaz extends Remote{

	//Metodos remotos
	public void enviadorMensajes(String mensaje) throws RemoteException;
	public void updateUsuarios(String[] usuariosRegistrados) throws RemoteException;
	
}