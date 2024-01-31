import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;

public class Interfaz extends JFrame implements ActionListener{
    
    public int n = 0;
    JButton bt1, bt2, bt3;
    JTextArea log;
    JScrollPane scrollText;
    String numFilString;
    JLabel lb1;

    JPanel panelFilosofos = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    JPanel panel1, panelBotones;
    
    public int contFil = 0;
    public int contCu = 0;

    private static Chops chops = new Chops();
    private ArrayList<controlPhil> filosofos = new ArrayList<controlPhil>();
    private ArrayList<JLabel> labelsFilosofos = new ArrayList<JLabel>();
    public ArrayList<Integer> estadosFilosofos = new ArrayList<Integer>();

    ExecutorService executor = Executors.newFixedThreadPool(50);

    public Interfaz() {

        super("Filosofos");
        log = new JTextArea("");
        log.setBounds(10, 5, 500, 400);
        scrollText = new JScrollPane(log);
        add(log);

        panelBotones = new JPanel(new GridBagLayout());
        GridBagConstraints cBotones = new GridBagConstraints();

        bt1 = new JButton("Iniciar");
        bt1.setBounds(10, 410, 150, 40);
        bt1.setBackground(new Color(255, 113, 182));
        add(bt1);
        bt1.addActionListener(this);

        bt2 = new JButton("Agregar Filosofo");
        bt2.setBounds(185, 410, 150, 40);
        bt2.setBackground(new Color(92, 155, 247));
        add(bt2);
        bt2.setEnabled(false);
        bt2.addActionListener(this);

        bt3 = new JButton("Quitar Filosofo");
        bt3.setBounds(360, 410, 150, 40);
        bt3.setBackground(new Color(250, 72, 72));
        add(bt3);
        bt3.setEnabled(false);
        bt3.addActionListener(this);

        lb1 = new JLabel("");
        lb1.setBounds(200, 480, 250, 30);
        lb1.setFont(new Font("Arial", Font.BOLD, 15));
        add(lb1);

        getContentPane().add(scrollText, BorderLayout.EAST);
        getContentPane().add(panelFilosofos, BorderLayout.WEST);
        getContentPane().add(panelBotones, BorderLayout.CENTER);

        cBotones.fill = GridBagConstraints.VERTICAL;
        cBotones.gridx = 0;
        cBotones.gridy = 0;
        cBotones.gridx = 1;
    
        setTitle("Filosofos");
        setResizable(false);
		setSize(540,550);
		setVisible(true);
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void addPhil(){
        contFil++;
        
        log.setText("Un filosofo se sentó en la mesa" + "\n" + log.getText());
        
        JLabel label = new JLabel("", JLabel.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = contFil - 1;
        panelFilosofos.add(label, c);
        panelFilosofos.revalidate();
        chops.lista.add(true);
        contCu++;
        controlPhil filosofo = new controlPhil(chops, contFil, log);
        labelsFilosofos.add(label);
        filosofos.add(filosofo);
        executor.execute(filosofo);

    }

    public static void main(String[] args) {
        Interfaz inter = new Interfaz();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == bt1){
        addPhil();
        addPhil();
        addPhil();
        addPhil();
        addPhil();
        numFilString = String.valueOf(contFil);
        lb1.setText("Filosofos activos: " + numFilString);
        bt1.setEnabled(false);
        n++;
        if(n == 1){
            bt2.setEnabled(true);
            bt3.setEnabled(true);
        }
       }
       
       if(e.getSource() == bt2){
        
        contFil++;
        numFilString = String.valueOf(contFil);
        lb1.setText("Filosofos activos: " + numFilString);
        log.setText("Un filosofo y su tenedor SE SENTARON en la mesa" + "\n" + log.getText());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = contFil - 1;
        panelFilosofos.revalidate();
        chops.lista.add(true);
        contCu++;
        controlPhil filosofo = new controlPhil(chops, contFil, log);
        filosofos.add(filosofo);
        executor.execute(filosofo);

        }
        
        if(e.getSource() == bt3){
            if(contFil != 0){

                log.setText("Un filosofo y su tenedor SE RETIRARON de la mesa" + "\n" + log.getText());
                labelsFilosofos.get(contFil - 1).setVisible(false);
                labelsFilosofos.remove(contFil - 1);
                filosofos.get(contFil - 1).detener();
                filosofos.remove(contFil - 1);
                contFil--;
                numFilString = String.valueOf(contFil);
                lb1.setText("Filosofos activos: " + numFilString);
                chops.lista.remove(contCu - 1);
                contCu--;
                
            }
        }
        
    }

}

