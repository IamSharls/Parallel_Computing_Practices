package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;

public interface serverInterfaz extends Remote {
		
	public void actualizarChat(String nombreUsuario, String siguienteMensaje)throws RemoteException;
	
	public void pasarIdentidad(RemoteRef ref)throws RemoteException;
	
	public void avisoNuevoCliente(String[] detalles)throws RemoteException;
	
	public void sacarCliente(String nombreUsuario)throws RemoteException;
	
	public void enviarPrivado(int[] grupoPrivado, String mensajePrivado)throws RemoteException;
}