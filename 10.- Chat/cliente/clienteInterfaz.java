package cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface clienteInterfaz extends Remote{

	public void mensajeDelServidor(String mensaje) throws RemoteException;

	public void actualizarListaUsuarios(String[] usuariosRegistrados) throws RemoteException;
	
}