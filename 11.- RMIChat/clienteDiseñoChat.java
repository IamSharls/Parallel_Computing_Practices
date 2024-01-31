//Clase para el diseño del chat

import java.awt.event.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.rmi.RemoteException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

public class clienteDiseñoChat extends JFrame implements ActionListener{

		
	//Declaración de todos los elementos de la interface
	private JPanel textPanel, inputPanel;
	public static JTextField textField, textField2;
	public static String nombre, mensaje;
	public static clienteInicio chatClient;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    protected JTextArea textArea, userArea;
    public static JFrame cont;
    public static JButton btnMensajePrivado, btnIniciar, btnEnviar, btnEntrar;
    protected JPanel panelCliente, panelUsuario;
	private Border blankBorder = BorderFactory.createEmptyBorder(10,20,20,20);

	public static void main(String args[]){

		//Iniciamos la interfaz	
		new clienteDiseñoChat();

		}
	
	public clienteDiseñoChat(){
			
		//Titulo del panel
		cont = new JFrame("Chat");	
	
		//Declaración e invocacion de los paneles
		Container c = getContentPane();
		JPanel outerPanel = new JPanel(new BorderLayout());
		
		outerPanel.add(panelInputs(), BorderLayout.CENTER);
		outerPanel.add(panelChat(), BorderLayout.NORTH);
		
		c.setLayout(new BorderLayout());
		c.add(outerPanel, BorderLayout.CENTER);
		c.add(panelUsuarios(), BorderLayout.EAST);

		//Añadimos el panel
		cont.add(c);
		cont.pack();
		cont.setAlwaysOnTop(true);
		//Definimos donde aparece el panel
		cont.setLocation(390, 200);
		//Deshabilitamos el poder hacer mas grande o pequeño el panel
		cont.setResizable(false);
		//Para poder cerrar la aplicacion con la equis
		cont.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Hacemo visible el panel
		cont.setVisible(true);

	}
	
	public JPanel panelChat(){
		
		//Inicializamos el textArea para el chat
		textArea = new JTextArea(8, 30);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel = new JPanel();
		textPanel.add(scrollPane);
		textArea.setVisible(true);
		textPanel.setFont(new Font("Meiryo", Font.PLAIN, 14));

		return textPanel;
	}
	
	public JPanel panelInputs(){

		//Inicializamos los componentes para los input de los mensajes
		inputPanel = new JPanel(new GridLayout(2, 1, 5, 10));
		inputPanel.setBorder(blankBorder);	
		textField = new JTextField();
		textField2 = new JTextField();
		inputPanel.add(textField);
		inputPanel.add(textField2);
		inputPanel.setVisible(true);

		return inputPanel;
	}


	public JPanel panelUsuarios(){

		//Inicializamos el panel para ver los usuarios conectados
		panelUsuario = new JPanel(new BorderLayout());
		String[] noClientsYet = {""};
		llenarLista(noClientsYet);
		//Añadimos el panel de botones al panel de usuarios
		panelUsuario.add(makeButtonPanel(), BorderLayout.NORTH);
		//Ponerle el borde
		panelUsuario.setBorder(blankBorder);
		//Hacer invisible el panel de los usuarios activos
		panelUsuario.setVisible(true);

		return panelUsuario;		
	}

    public void llenarLista(String[] currClients) {  

		//Creamos un panel para los usuarios conectados
    	panelCliente = new JPanel(new BorderLayout());
		//Creamos una lista donde se añaden los usuarios conectados
        listModel = new DefaultListModel<String>();
        
		//Llenamos la lista con los usuarios actuales
        for(String s : currClients){
        	listModel.addElement(s);
        }
		//Si hay mas de 1 usuarios conectado habilitamos el boton de mensaje privado
        if(currClients.length > 1){
        	btnMensajePrivado.setEnabled(true);
        }
        
		//Inicializamos la lista y la añadimos al panel
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(8);
        JScrollPane listScrollPane = new JScrollPane(list);
		list.setEnabled(false);
        panelCliente.add(listScrollPane, BorderLayout.CENTER);
        panelUsuario.add(panelCliente, BorderLayout.CENTER);
		
    }
	
	public JPanel makeButtonPanel() {	

		//Inicilizacion de todos los botones y sus caracteristicas	
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(this);
		btnEnviar.setEnabled(false);
		btnEnviar.setBackground(new Color(64, 130, 252));
	
        btnMensajePrivado = new JButton("Privado");
        btnMensajePrivado.addActionListener(this);
        btnMensajePrivado.setEnabled(false);
		btnMensajePrivado.setBackground(new Color(83, 247, 123));
		
		btnIniciar = new JButton("Entrar");
		btnIniciar.addActionListener(this);
		
		//Añadimos los botones al panel
		JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
		buttonPanel.add(btnMensajePrivado);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(btnEnviar);
		
		return buttonPanel;
	}
	
	public void actionPerformed(ActionEvent e){

		try {
			//Boton para iniciar la conexion
			if(e.getSource() == btnIniciar){
				//Obtenemos el nombre del textfield que se llena desde la clase inic
				nombre = textField.getText();
				//Comprobamos que si exista un nombre
				if(nombre.length() != 0){
					//Cambiamos el titulo del frame al del usuario registrado
					cont.setTitle("Usuario en linea: " + nombre);
					//Ponemos en blanco el textfield
					textField.setText("");
					//Enviamos el nombre a la funcion para conectar al servidor							
					conectar(nombre);
					if(!chatClient.problemaConexion){
						//Habilitamos el boton para enviar mensaje
						btnEnviar.setEnabled(true);
						}
				}else{

				}
				
			}
			//Boton para enviar un mensaje
			if(e.getSource() == btnEnviar){
				//Obtenemos el mensaje
				mensaje = textField.getText();
				textField.setText("");
				//Lo enviamos con la funcion de enviar mensaje
				enviarSMS(mensaje);
			}
			//Boton para enviar un mensaje privado
			if(e.getSource() == btnMensajePrivado){

				//Obtenemos el indice de la persona a la que queremos enviar MP
				int ind  = Integer.parseInt(textField2.getText());
				//Ingresamos ese indice a un arreglo
				int[] privateList = {ind};

				mensaje = textField.getText();
				textField.setText("");
				textField2.setText("");
				//Le enviamos el indice que contiene el nombre de la persona destino
				enviarMP(privateList);
			}
			
		}
		catch (RemoteException remoteExc) {			
			remoteExc.printStackTrace();	
		}
		
	}

	
	//Funcion para enviar un mensaje global
	private void enviarSMS(String chatMessage) throws RemoteException {
		//Simplemente enviamos el nombre de la persona que lo envio y el mensaje a traves de la funcion
		//remota
		chatClient.interfazServer.updateChat(nombre, chatMessage);
	}

	//Funcion para enviar un mensaje privado
	private void enviarMP(int[] privateList) throws RemoteException {
		//Construimos el mensaje que le llegara al destinatario
		String mensajePrivado = "[Privado de " + nombre + "] :" + mensaje + "\n";
		//Le pasamos el indice de la persona destino y el mensaje a traves de la funcion remota
		chatClient.interfazServer.enviarMP(privateList, mensajePrivado);
	}
	
	//Funcion para conectar al servidor
	private void conectar(String nombreUsuario) throws RemoteException{
		//Quitamos simbolos raros del nick
		String cleanedUserName = nombreUsuario.replaceAll("\\s+","_");
		cleanedUserName = nombreUsuario.replaceAll("\\W+","_");
		try {		
			//Enviamos el nick y la interfaz para iniciar la conexion
			chatClient = new clienteInicio(this, cleanedUserName);
			chatClient.clienteInicio();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	

}










