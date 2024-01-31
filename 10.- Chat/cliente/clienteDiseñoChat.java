package cliente;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

public class clienteDiseñoChat extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;	
	private JPanel textPanel, inputPanel;
	private JTextField textField;
	private String nombre, mensaje;
	private Border blankBorder = BorderFactory.createEmptyBorder(10,10,20,10);
	private clienteInicio chatClient;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    
    protected JTextArea textArea, userArea;
    protected JFrame frame;
    protected JButton btnMensajePrivado, btnIniciar, btnEnviar;
    protected JPanel panelCliente, panelUsuario;

	public static void main(String args[]){
		try{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception e){
			}
		new clienteDiseñoChat();
		}
	
	public clienteDiseñoChat(){
			
		frame = new JFrame("Client Chat Console");	
	
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        
		    	if(chatClient != null){
			    	try {
			        	sendMessage("Bye all, I am leaving");
			        	chatClient.interfazServer.sacarCliente(nombre);
					} catch (RemoteException e) {
						e.printStackTrace();
					}		        	
		        }
		        System.exit(0);  
		    }   
		});
	
		Container c = getContentPane();
		JPanel outerPanel = new JPanel(new BorderLayout());
		
		outerPanel.add(getInputPanel(), BorderLayout.CENTER);
		outerPanel.add(getTextPanel(), BorderLayout.NORTH);
		
		c.setLayout(new BorderLayout());
		c.add(outerPanel, BorderLayout.CENTER);
		c.add(getUsersPanel(), BorderLayout.WEST);

		frame.add(c);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setLocation(150, 150);
		textField.requestFocus();
	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public JPanel getTextPanel(){
		String welcome = "Welcome enter your nombre and press Start to begin\n";
		textArea = new JTextArea(welcome, 14, 34);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel = new JPanel();
		textPanel.add(scrollPane);
	
		textPanel.setFont(new Font("Meiryo", Font.PLAIN, 14));
		return textPanel;
	}
	
	public JPanel getInputPanel(){
		inputPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		inputPanel.setBorder(blankBorder);	
		textField = new JTextField();
		inputPanel.add(textField);
		return inputPanel;
	}

	public JPanel getUsersPanel(){
		
		panelUsuario = new JPanel(new BorderLayout());
		String  userStr = " Current Users      ";
		
		JLabel userLabel = new JLabel(userStr, JLabel.CENTER);
		panelUsuario.add(userLabel, BorderLayout.NORTH);	
		userLabel.setFont(new Font("Meiryo", Font.PLAIN, 16));

		String[] noClientsYet = {"No other users"};
		setClientPanel(noClientsYet);

		panelUsuario.add(makeButtonPanel(), BorderLayout.SOUTH);		
		panelUsuario.setBorder(blankBorder);

		return panelUsuario;		
	}

    public void setClientPanel(String[] currClients) {  	
    	panelCliente = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<String>();
        
        for(String s : currClients){
        	listModel.addElement(s);
        }
        if(currClients.length > 1){
        	btnMensajePrivado.setEnabled(true);
        }
        
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(8);
        JScrollPane listScrollPane = new JScrollPane(list);

        panelCliente.add(listScrollPane, BorderLayout.CENTER);
        panelUsuario.add(panelCliente, BorderLayout.CENTER);
    }
	
	public JPanel makeButtonPanel() {		
		btnEnviar = new JButton("Send ");
		btnEnviar.addActionListener(this);
		btnEnviar.setEnabled(false);

        btnMensajePrivado = new JButton("Send PM");
        btnMensajePrivado.addActionListener(this);
        btnMensajePrivado.setEnabled(false);
		
		btnIniciar = new JButton("Start ");
		btnIniciar.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
		buttonPanel.add(btnMensajePrivado);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(btnIniciar);
		buttonPanel.add(btnEnviar);
		
		return buttonPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){

		try {
			if(e.getSource() == btnIniciar){
				nombre = textField.getText();				
				if(nombre.length() != 0){
					frame.setTitle(nombre + "'s console ");
					textField.setText("");
					textArea.append("username : " + nombre + " connecting to chat...\n");							
					getConnected(nombre);
					if(!chatClient.problemaConexion){
						btnIniciar.setEnabled(false);
						btnEnviar.setEnabled(true);
						}
				}
				else{
					JOptionPane.showMessageDialog(frame, "Enter your nombre to Start");
				}
			}

			if(e.getSource() == btnEnviar){
				mensaje = textField.getText();
				textField.setText("");
				sendMessage(mensaje);
				System.out.println("Sending mensaje : " + mensaje);
			}
			
			if(e.getSource() == btnMensajePrivado){
				int[] privateList = list.getSelectedIndices();
				
				for(int i=0; i<privateList.length; i++){
					System.out.println("selected index :" + privateList[i]);
				}
				mensaje = textField.getText();
				textField.setText("");
				sendPrivate(privateList);
			}
			
		}
		catch (RemoteException remoteExc) {			
			remoteExc.printStackTrace();	
		}
		
	}

	private void sendMessage(String chatMessage) throws RemoteException {
		chatClient.interfazServer.actualizarChat(nombre, chatMessage);
	}

	private void sendPrivate(int[] privateList) throws RemoteException {
		String mensajePrivado = "[PM from " + nombre + "] :" + mensaje + "\n";
		chatClient.interfazServer.enviarPrivado(privateList, mensajePrivado);
	}
	
	private void getConnected(String nombreUsuario) throws RemoteException{
		//remove whitespace and non word characters to avoid malformed url
		String cleanedUserName = nombreUsuario.replaceAll("\\s+","_");
		cleanedUserName = nombreUsuario.replaceAll("\\W+","_");
		try {		
			chatClient = new clienteInicio(this, cleanedUserName);
			chatClient.clienteInicio();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}










