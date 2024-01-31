import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.lang.Math;


public class controlPhil implements Runnable {

    public int id;
    public int comerTime = (int) ((Math.random() + 0.5) * 3000/2);
    public int pensarTime = (int) ((Math.random() + 0.5) * 8000/2);
    public boolean cIzq = false;
    public boolean cDer = false;


    private JTextArea log;
    private final Chops cucharas;

    public boolean again = false;
    public boolean ex = false;

    public controlPhil(Chops cucharas, int idFilosofo, JTextArea log) {
        
        this.log = log;
        this.cucharas = cucharas;
        id = idFilosofo;

    }

    @Override
    public void run() {
        try {
            while (true) {

                if(ex == true){
                    break;
                }

                log.setText("El filosofo numero " + id + " ESTA PENSANDO" + "\n" + log.getText());
                Thread.sleep(pensarTime);

                if(ex == true){
                    break;
                }
            
                again = false;

                
                synchronized (cucharas){

                    while(cIzq == false || cDer == false){

                        if (again == true){
                            try {
                            
                                if (cIzq == true) {
    
                                }
                                else if (cDer == true){
                                    
                                }
                                else{
                                    
                                }
                                
                                cucharas.wait();
                            } catch (InterruptedException error) {
                                error.printStackTrace();
                            }
                        }
                        
                        if(ex == true){
                            break;
                        }

                        if(cucharas.lista.get(id - 1) == true){
                            log.setText("El filosofo "+ id + " HA TOMADO LA CUCHARA: IZQ" + "\n" + log.getText());
                            cucharas.lista.set(id - 1, false);
                            cIzq = true;
                        }
                        
                        if(cucharas.lista.size() != id){
                            if(cucharas.lista.get(id) == true){
                                log.setText("El filosofo "+ id + " HA TOMADO LA CUCHARA: DER" + "\n" + log.getText());
                                cucharas.lista.set(id, false);
                                cDer = true;
                            }
                        }
                        else{
                            if(cucharas.lista.get(0) == true){

                                log.setText("El filosofo "+ id + " HA TOMADO LA CUCHARA: DER" + "\n" + log.getText());
                                cucharas.lista.set(0, false);
                                cDer = true;
                            }
                        }
                    
                        again = true;
                    }
                }

                if(ex == true){
                    synchronized (cucharas){
                        cucharas.lista.set(id - 1, true);
                        if(cucharas.lista.size() != id){
                            cucharas.lista.set(id, true);
                        }
                        else{
                            cucharas.lista.set(0, true);
                        }
                        cucharas.notify();
                    }

                    break;
                }

                log.setText("El filosofo "+ id + " ESTA COMIENDO" + "\n" + log.getText());
                Thread.sleep(comerTime);

                synchronized (cucharas){
                    cIzq = false;
                    cDer = false;
                    cucharas.lista.set(id - 1, true);
                    if(cucharas.lista.size() != id){
                        cucharas.lista.set(id, true);
                    }
                    else{
                       
                        cucharas.lista.set(0, true);
                    }
                    cucharas.notify();
                    log.setText("El filosofo "+ id + " HA TERMINADO DE COMER" + "\n" + log.getText());
                    log.setText("El filosofo "+ id + " ESTA PENSANDO" + "\n" + log.getText());
                    
                }

                if(ex == true){
                    break;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void detener(){
        ex = true;
    }
}