//Clase para iniciar el servidor

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class serverIniciar extends UnicastRemoteObject implements serverInterfaz{

	//Vector de clientes
	private Vector<serverClientesNames> listaClientes;

	public serverIniciar() throws RemoteException {
		super();
		listaClientes = new Vector<serverClientesNames>(10, 1);
	}
	
	public static void main(String[] args) {

		//Llamamos a la funcion para iniciar el rmiregistry
		registry();	

		String ipLocal = "127.0.0.1"; //Aqui se pone la IP del ordenador local,
									//el que va a hacer el servidor
		String nombreServicio = "RMIChat"; //Nombre del servicio, puede ser el que sea
		
		//Tambien prodriamos enviar las IP's a traves de argumentos
		if(args.length == 2){
			ipLocal = args[0];
			nombreServicio = args[1];
		}
		
		//Levantamos el servidor
		try{
			serverInterfaz interfazServer = new serverIniciar();
			Naming.rebind("rmi://" + ipLocal + "/" + nombreServicio, interfazServer);
			System.out.println("Server iniciado");
		}
		catch(Exception e){
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
	
	//Funcion remota para actualizar los mensajes del chat
	public void updateChat(String nombre, String siguienteMensaje) throws RemoteException {
		String mensaje =  nombre + " : " + siguienteMensaje + "\n";
		mensajeGlobal(mensaje);
	}
	
	//Funcion remota para identificar al usuario
	public void passID(RemoteRef ref) throws RemoteException {	
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//Funcion remota para ingresar un nuevo cliente
	public void nuevoUser(String[] detalles) throws RemoteException {	
		regUser(detalles);
	}

	//Funcion remota para agregar clientes a la lista, iniciar su conexion y notificarlo en el chat
	private void regUser(String[] detalles){		
		try{
			clienteInterfaz nuevoCliente = (clienteInterfaz)Naming.lookup("rmi://" + detalles[1] + "/" + detalles[2]);
			
			listaClientes.addElement(new serverClientesNames(detalles[0], nuevoCliente));
			
			mensajeGlobal("-" + detalles[0] + "-" + " ha entrado al chat.\n");
			
			updateUsers();		
		}
		catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
		}
	}
	
	//Funcion para actulizar los usuarios activos
	private void updateUsers() {
		String[] usuariosRegistrados = obtenerUsers();	

		for(serverClientesNames c : listaClientes){
			try {
				c.getClient().updateUsuarios(usuariosRegistrados);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	
	//Funcion para obtener los clientes de la lista
	private String[] obtenerUsers(){
		String[] usuarios = new String[listaClientes.size()];

		for(int i = 0; i< usuarios.length; i++){
			usuarios[i] = listaClientes.elementAt(i).getNombre();
		}

		return usuarios;
	}

	//Funcion para enviar el mensaje al chat
	public void mensajeGlobal(String nuevoMensaje){	
		for(serverClientesNames c : listaClientes){
			try {
				c.getClient().enviadorMensajes(nuevoMensaje);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}

	//Funcion remota para remover un usuario de la lista cuando se cierre su chat
	public void dropUser(String nombreUsuario) throws RemoteException{
		for(serverClientesNames c : listaClientes){
			if(c.getNombre().equals(nombreUsuario)){
				listaClientes.remove(c);
				break;
			}
		}		
		if(!listaClientes.isEmpty()){
			updateUsers();
		}			
	}
	
	//Funcion remota para enviar mensaje privado
	public void enviarMP(int[] grupoPrivado, String mensajePrivado) throws RemoteException{
		serverClientesNames cliente;
		//Se busca el usuario dependiendo del indice recibido
		for(int i : grupoPrivado){
			//Se le envia el mensaje al usuario en especifico
			cliente = listaClientes.elementAt(i);
			cliente.getClient().enviadorMensajes(mensajePrivado);
		}
	}
	
}