package cliente;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;
import server.serverInterfaz;

public class clienteInicio  extends UnicastRemoteObject implements clienteInterfaz {
	private static final long serialVersionUID = 7468891722773409712L;
	clienteDiseñoChat chatGUI;
	private String ipLocal = /*"192.168.100.103"*/ "127.0.0.1"; //Aqui se pone la IP del ordenador local
	private String ipServer = /*"192.168.100.169"*/ "127.0.0.1";
	private String nombreServicio = "ServicioChat";
	private String nombreServicioCliente;
	private String nombre;
	protected serverInterfaz interfazServer;
	protected boolean problemaConexion = false;

	public clienteInicio(clienteDiseñoChat chatGUI, String nombreUsuario) throws RemoteException {
		super();
		this.chatGUI = chatGUI;
		this.nombre = nombreUsuario;
		this.nombreServicioCliente = "ServicioMensajePrivado_" + nombreUsuario;
	}

	public void clienteInicio() throws RemoteException {		
		iniciarRMI();	
		String[] detalles = {nombre, ipLocal, nombreServicioCliente, ipServer};	

		try {
			Naming.rebind("rmi://" + ipLocal + "/" + nombreServicioCliente, this);
			interfazServer = (serverInterfaz)Naming.lookup("rmi://" + ipServer + "/" + nombreServicio);	
		} 
		catch (ConnectException  e) {
			JOptionPane.showMessageDialog(
					chatGUI.frame, "El servidor no está disponible\nIntente mas tarde.",
					"Error de conexión", JOptionPane.ERROR_MESSAGE);
			problemaConexion = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me){
			problemaConexion = true;
			me.printStackTrace();
		}
		if(!problemaConexion){
			registrarEnServidor(detalles);
		}	
		System.out.println("El Cliente de Chat RMI se ha iniciado.\n");
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

	public void registrarEnServidor(String[] detalles) {		
		try{
			interfazServer.pasarIdentidad(this.ref);
			interfazServer.avisoNuevoCliente(detalles);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void mensajeDelServidor(String mensaje) throws RemoteException {
		System.out.println(mensaje);
		chatGUI.textArea.append(mensaje);
		chatGUI.textArea.setCaretPosition(chatGUI.textArea.getDocument().getLength());
	}

	@Override
	public void actualizarListaUsuarios(String[] usuariosRegistrados) throws RemoteException {

		if(usuariosRegistrados.length < 2){
			chatGUI.btnMensajePrivado.setEnabled(false);
		}
		chatGUI.panelUsuario.remove(chatGUI.panelCliente);
		chatGUI.setClientPanel(usuariosRegistrados);
		chatGUI.panelCliente.repaint();
		chatGUI.panelCliente.revalidate();
	}
}