//Clase para conectar el cliente al servidor

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class clienteInicio extends UnicastRemoteObject implements clienteInterfaz {
	
	////Declaración de todos los elementos 
	clienteDiseñoChat chatGUI;
	private String ipLocal = "127.0.0.1"; //Aqui se pone la IP del ordenador local
	private String ipServer = "127.0.0.1"; //Aqui se pone la IP del servidor
	private String nombreServicio = "RMIChat"; //Nombre del servicio, puede ser cualquiera
	private String nombreServicioCliente;
	private String nombre;
	protected serverInterfaz interfazServer;
	protected boolean problemaConexion = false;

	public clienteInicio(clienteDiseñoChat chatGUI, String nombreUsuario) throws RemoteException {
		super();
		this.chatGUI = chatGUI;
		this.nombre = nombreUsuario;
		this.nombreServicioCliente = "MP_" + nombreUsuario;
	}

	public void clienteInicio() throws RemoteException {		
		//Llamamos a la función para hacer el rmiregistry
		registry();	
		//Ponemos todos los detalles dentro de un arreglo
		String[] detalles = {nombre, ipLocal, nombreServicioCliente, ipServer};	

		try {
			//Hacemos la conexion local
			Naming.rebind("rmi://" + ipLocal + "/" + nombreServicioCliente, this);
			//Hacemos la conexion al servidor
			interfazServer = (serverInterfaz)Naming.lookup("rmi://" + ipServer + "/" + nombreServicio);	
		} 
		catch (ConnectException  e) {
			problemaConexion = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me){
			problemaConexion = true;
			me.printStackTrace();
		}
		if(!problemaConexion){
			//Registramos los detallos de un nuevo usuario
			registrarNuevoUsuario(detalles);
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

	//Funcion para registrar un nuevo usuario
	public void registrarNuevoUsuario(String[] detalles) {		
		try{
			//Enviamos los detalles con la ayuda de las funciones remotas
			interfazServer.passID(this.ref);
			interfazServer.nuevoUser(detalles);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	//Funcion para enviar mensaje
	public void enviadorMensajes(String mensaje) throws RemoteException {
		chatGUI.textArea.append(mensaje);
		chatGUI.textArea.setCaretPosition(chatGUI.textArea.getDocument().getLength());
	}

	//Funcion para actualizar la lista de usuarios
	public void updateUsuarios(String[] usuariosRegistrados) throws RemoteException {

		//Si hay menos de dos usuarios deshabilitamos el boton para los mensajes privados
		if(usuariosRegistrados.length < 2){
			chatGUI.btnMensajePrivado.setEnabled(false);
		}
		//Removemos la lista
		chatGUI.panelUsuario.remove(chatGUI.panelCliente);
		//Volvemos a llenarla con los nuevos usuarios
		chatGUI.llenarLista(usuariosRegistrados);
		//La volvemos a imprimir
		chatGUI.panelCliente.repaint();
		chatGUI.panelCliente.revalidate();
	}
}