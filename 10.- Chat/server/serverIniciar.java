package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;

import cliente.clienteInterfaz;

public class serverIniciar extends UnicastRemoteObject implements serverInterfaz {
	private static final long serialVersionUID = 1L;
	private Vector<serverClientesNames> listaClientes;

	public serverIniciar() throws RemoteException {
		super();
		listaClientes = new Vector<serverClientesNames>(10, 1);
	}
	
	public static void main(String[] args) {
		iniciarRMI();	
		String ipLocal = /*"192.168.100.103"*/"127.0.0.1"; //Aqui se pone la IP del ordenador local
		String nombreServicio = "ServicioChat";
		
		if(args.length == 2){
			ipLocal = args[0];
			nombreServicio = args[1];
		}
		
		try{
			serverInterfaz interfazServer = new serverIniciar();
			Naming.rebind("rmi://" + ipLocal + "/" + nombreServicio, interfazServer);
			System.out.println("El Servidor de Chat RMI se ha iniciado.");
		}
		catch(Exception e){
			System.out.println("ERROR: El servidor tuvo problemas para iniciar");
		}	
	}

	public static void iniciarRMI() {
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI Registry en puerto 1099");
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarChat(String nombre, String siguienteMensaje) throws RemoteException {
		String mensaje =  nombre + " : " + siguienteMensaje + "\n";
		enviarATodos(mensaje);
	}
	
	@Override
	public void pasarIdentidad(RemoteRef ref) throws RemoteException {	
		try{
			System.out.println("---------------------------------------------\n" + ref.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void avisoNuevoCliente(String[] detalles) throws RemoteException {	
		System.out.println(new Date(System.currentTimeMillis()));
		System.out.println(detalles[0] + " ha entrado al chat.");
		System.out.println("Dirección IP: " + detalles[1]);
		System.out.println("Servicio RMI: " + detalles[2]);

		registrarCliente(detalles);
	}

	private void registrarCliente(String[] detalles){		
		try{
			clienteInterfaz nuevoCliente = (clienteInterfaz)Naming.lookup("rmi://" + detalles[1] + "/" + detalles[2]);
			
			listaClientes.addElement(new serverClientesNames(detalles[0], nuevoCliente));
			
			nuevoCliente.mensajeDelServidor("[Servidor] Hola " + detalles[0] + ", bienvenido al chat :)\n");
			
			enviarATodos("[Servidor] " + detalles[0] + " ha entrado al grupo.\n");
			
			actualizarListaUsuarios();		
		}
		catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
		}
	}
	
	private void actualizarListaUsuarios() {
		String[] usuariosRegistrados = getListaClientes();	

		for(serverClientesNames c : listaClientes){
			try {
				c.getClient().actualizarListaUsuarios(usuariosRegistrados);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private String[] getListaClientes(){
		String[] usuarios = new String[listaClientes.size()];

		for(int i = 0; i< usuarios.length; i++){
			usuarios[i] = listaClientes.elementAt(i).getNombre();
		}

		return usuarios;
	}
	
	public void enviarATodos(String nuevoMensaje){	
		for(serverClientesNames c : listaClientes){
			try {
				c.getClient().mensajeDelServidor(nuevoMensaje);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public void sacarCliente(String nombreUsuario) throws RemoteException{
		for(serverClientesNames c : listaClientes){
			if(c.getNombre().equals(nombreUsuario)){
				System.out.println("---------------------------------------------\n" + nombreUsuario + " left the chat session");
				System.out.println(new Date(System.currentTimeMillis()));
				listaClientes.remove(c);
				break;
			}
		}		
		if(!listaClientes.isEmpty()){
			actualizarListaUsuarios();
		}			
	}
	
	@Override
	public void enviarPrivado(int[] grupoPrivado, String mensajePrivado) throws RemoteException{
		serverClientesNames cliente;
		for(int i : grupoPrivado){
			cliente = listaClientes.elementAt(i);
			cliente.getClient().mensajeDelServidor(mensajePrivado);
		}
	}
	
}