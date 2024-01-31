//Clase para crear la interfaz

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Grafico extends JFrame implements ActionListener{


    //Rutas de los iconos
    public static final Icon i1 = new ImageIcon("Imagenes/consu.jpg");
    public static final Icon i2 = new ImageIcon("Imagenes/sleep.png");
    public static final Icon i4 = new ImageIcon("Imagenes/caja.jpg");
    public static final Icon i5 = new ImageIcon("Imagenes/pro.jpg");
    public static final Icon i3 = new ImageIcon("");

    static LinkedList<JLabel> jProductor = new LinkedList<JLabel>();
    static LinkedList<JLabel> jConsummer = new LinkedList<JLabel>();
    static LinkedList<JLabel> jInventory = new LinkedList<JLabel>();
   

    static LinkedList<Thread> productoresCorriendo = new LinkedList<Thread>();
    static LinkedList<Thread> consumidoresCorriendo = new LinkedList<Thread>();

    //Variable del control de cajas
    static controlCaja controlC;

    //Variables de botones y labels
    public JButton bt1, bt2, bt3, bt4;
    public JLabel jl1, jl2, jl3, jl4;

    //Contadores de productores y consumidores
    private int contadorProductores = 0, contadorProductoresTotales = 0, contadorConsumidores = 0, contadorConsumidoresTotales = 0, contadorInventory = 0;

    //Paneles para las imagenes
    public static JPanel productorPGrid = new JPanel();
    public static JPanel cajaPGrid = new JPanel();
    public static JPanel consumidorPGrid = new JPanel();

    public Grafico() {

        super("Problema Productor-Consumidor");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 520);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        controlC = new controlCaja();

        jl1 = new JLabel("Controles Productores");
        jl1.setBounds(110, 400, 250, 30);
        add(jl1);

        jl2 = new JLabel("Productores generados");
        jl2.setBounds(110, 150, 250, 30);
        add(jl2);

        bt1 = new JButton("Agregar Productor");
        bt1.setBounds(20, 430, 150, 40);
        bt1.setBackground(new Color(107, 219, 34));
        add(bt1);
        bt1.addActionListener(this);

        bt2 = new JButton("Quitar Productor");
        bt2.setBounds(180, 430, 140, 40);
        bt2.setBackground(new Color(229, 51, 51));
        add(bt2);
        bt2.addActionListener(this);

        jl3 = new JLabel("Controles Consumidores");
        jl3.setBounds(540, 400, 250, 30);
        add(jl3);

        jl4 = new JLabel("Consumidores generados");
        jl4.setBounds(540, 150, 250, 30);
        add(jl4);

        bt3 = new JButton("Agregar Consumidor");
        bt3.setBounds(440, 430, 170, 40);
        bt3.setBackground(new Color(107, 219, 34));
        add(bt3);
        bt3.addActionListener(this);

        bt4 = new JButton("Quitar Consumidor");
        bt4.setBounds(625, 430, 160, 40);
        bt4.setBackground(new Color(229, 51, 51));
        add(bt4);
        bt4.addActionListener(this);
        
        
        JPanel panelP = new JPanel();
        JPanel panelIn = new JPanel();
        JPanel panelC = new JPanel();

        productorPGrid.setLayout(new GridLayout(2, 3, 5, 5));
        cajaPGrid.setLayout(new GridLayout(3, 2, 5, 5));
        consumidorPGrid.setLayout(new GridLayout(2, 3, 5, 5));

        //Si no hay productores o consumidores, desactivamos los controles de reduccion
        if((contadorProductores == 0) && (contadorConsumidores == 0)){
            bt2.setEnabled(false);
            bt4.setEnabled(false);
        }

        //FOR para agregar 6 espacios al control de cajas
        for (int i = 0; i < controlCaja.tope; i++) {
            agregarCaja(jInventory, cajaPGrid, i3);
            contadorInventory++;
        }

        //Agregar los GridLayout de las imagenes
        panelP.add(productorPGrid);
        panelIn.add(cajaPGrid);
        panelC.add(consumidorPGrid);

        //Agregar los GridLayout de las imagenes
        add(panelC, BorderLayout.EAST);
        add(panelIn, BorderLayout.CENTER);
        add(panelP, BorderLayout.WEST);

    }

    //Acciones para cada botón
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == bt1){
        if(contadorProductores < 6){
            agregarCaja(jProductor, productorPGrid, i5);
            contadorProductores++;
            contadorProductoresTotales++;
            var productor = new controlProductor(contadorProductoresTotales + " ", controlC);
            productoresCorriendo.add(new Thread(productor));
            productoresCorriendo.getLast().start();

            }else{
                System.out.println("Productores maximos");
            }
            if (contadorProductores == 6){
                bt1.setEnabled(false);
            }
            if(contadorProductores >= 1){
                bt2.setEnabled(true);
            }
        }

        if(e.getSource() == bt2){
            quitarCaja(jProductor, productorPGrid);
            contadorProductores--;
            var threadP = productoresCorriendo.pop();
            threadP.interrupt();
            if (contadorProductores < 1)
                bt2.setEnabled(false);
            if (contadorProductores < 6)
                bt1.setEnabled(true);
            repaint();
        }

        if(e.getSource() == bt3){
            if(contadorConsumidores < 6){
                agregarCaja(jConsummer, consumidorPGrid, i1);
                contadorConsumidores++;
                contadorConsumidoresTotales++;
                var consumidor = new controlConsumidor(contadorConsumidoresTotales + "", controlC);
                consumidoresCorriendo.add(new Thread(consumidor));
                consumidoresCorriendo.getLast().start();

                }else{
                    System.out.println("Consumidores maximos");
                }
                if (contadorConsumidores == 6){
                    bt3.setEnabled(false);
                }
                if(contadorConsumidores >= 1){
                    bt4.setEnabled(true);
                }
        }

        if(e.getSource() ==  bt4){
            quitarCaja(jConsummer, consumidorPGrid);
            contadorConsumidores--;
            var threadC = consumidoresCorriendo.pop();
            threadC.interrupt();
            if (contadorConsumidores < 1)
                bt4.setEnabled(false);
            if (contadorConsumidores < 6)
                bt3.setEnabled(true);
            repaint();
        }
        
    }

    //Funcion para agregar una caja
    public static void agregarCaja(LinkedList<JLabel> type, Container container, Icon icon) {
        JLabel label = new JLabel();
        type.add(label);
        label.setIcon(icon);
        container.add(label);
        container.revalidate();
    }

    //Funcion para quitar una caja
    public void quitarCaja(LinkedList<JLabel> type, Container container) {
        JLabel targetLabel = type.get(type.size() - 1);
        container.remove(targetLabel);
        type.remove(targetLabel);
    }

    //Funcion para agregar un productor
    public static void agregarProductor(Container container) {
        for (var item : jInventory) {
            if (item.getIcon() == i3) {
                item.setIcon(i4);
                break;
            }
        }
        container.revalidate();
    }

    //Funcion para quitar un productor
    public static void quitarProductor(Container container) {
        for (var item : jInventory) {
            if (item.getIcon() == i4) {
                item.setIcon(i3);
                break;
            }
        }
        container.revalidate();
    }

    //Funcion para dormir los procesos
    public static void sleep(Container container, boolean pc) {
        if (pc) {
            for (var item : jProductor) {
                item.setIcon(i2);
            }
        } else {
            for (var item : jConsummer) {
                item.setIcon(i2);
            }
        }
        container.revalidate();
    }

    //Funcion para despertar los procesos
    public static void work(Container container, boolean pc) {
        if (pc) {
            for (var item : jProductor) {
                item.setIcon(i5);
            }
        } else {
            for (var item : jConsummer) {
                item.setIcon(i1);
            }
        }
        container.revalidate();
    }

}
