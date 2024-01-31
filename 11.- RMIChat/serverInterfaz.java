//Clase para la interfaz del servidor 

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;

public interface serverInterfaz extends Remote {
		
	//Metodos remotos
	public void updateChat(String nombreUsuario, String siguienteMensaje)throws RemoteException;
	public void passID(RemoteRef ref)throws RemoteException;
	public void nuevoUser(String[] detalles)throws RemoteException;
	public void dropUser(String nombreUsuario)throws RemoteException;
	public void enviarMP(int[] grupoPrivado, String mensajePrivado)throws RemoteException;

}