//Clase para iniciar el programa y registrar al usuario

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class inic extends JFrame implements ActionListener {

    //Declaracion de los elementos del frame
    private static JButton bt1;
    public static JTextField tf1;
    public static JLabel jl1;

    public inic() {
        //Titulo para el frame
        setTitle("Iniciando sesion...");
        setLayout(null);

        //Inicializacion de los componentes
        jl1 = new JLabel("Ingresa tu usuario");
        jl1.setBounds(95, 20, 250, 30);
        add(jl1);

        tf1 = new JTextField();
        tf1.setBounds(70, 80, 150, 30);
        add(tf1);

        bt1 = new JButton("Iniciar");
        bt1.setBounds(70, 150, 150, 40);
        bt1.setBackground(new Color(64, 130, 252));
        add(bt1);
        bt1.addActionListener(this);

    }

    public static void main(String[] args) {

        inic hs = new inic();

        //Proporciones del frame
        hs.setBounds(0, 0, 300, 300);
        //Frame visible
        hs.setVisible(true);
        // Inhabilitamos el poder cambiar el tamaño de la ventana
        hs.setResizable(false);
        // Funcion para hacer que el frame aparezca en medio de la pantalla
        hs.setLocationRelativeTo(null);
        // Funcion para poder cerrar por completo el programa con equis
        hs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        //Boton para registrar al usuario
        if (e.getSource() == bt1){

            //Creamos un objeto de la interfaz
            clienteDiseñoChat ab = new clienteDiseñoChat();
            //Cuando presione el boton, hacemos visible la interfaz del chat
            ab.setVisible(true);
            //Cuando presione el boton hacemos invisible el frame de registro
            this.setVisible(false);
            //Enviamos el nombre del usuario al textfield de mensajes del frame del chat para registrarlo
            ab.textField.setText(tf1.getText());
            //Autoclickeamos el boton para registrar
            clienteDiseñoChat.btnIniciar.doClick();

        }
    }

}
