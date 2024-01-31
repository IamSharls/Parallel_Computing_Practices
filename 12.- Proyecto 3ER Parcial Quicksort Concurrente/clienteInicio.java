//Clase para conectar el cliente al servidor

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class clienteInicio extends UnicastRemoteObject{
	
	////Declaración de todos los elementos 
	clienteDiseño chatGUI;
	private String ipServer = "127.0.0.1"; //Aqui se pone la IP del servidor
	private String nombreServicio = "RMIChat"; //Nombre del servicio, puede ser cualquiera

	protected serverInterfaz interfazServer;
	protected boolean problemaConexion = false;

	public clienteInicio(clienteDiseño chatGUI) throws RemoteException {
		super();
		this.chatGUI = chatGUI;
	
	}

	public void clienteInicio() throws RemoteException {		

		try {
			//Hacemos la conexion al servidor
			interfazServer = (serverInterfaz)Naming.lookup("rmi://" + ipServer + "/" + nombreServicio);	
			System.out.println("Cliente iniciado");
		} 
		catch (ConnectException  e) {
			problemaConexion = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me){
			problemaConexion = true;
			me.printStackTrace();
		}

		
	}

}