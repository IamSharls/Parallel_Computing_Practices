//Clase que controla la entrada y salida de los procesos productores

import java.util.TreeMap;

public class controlProductor implements Runnable {

    private String Nombre;
    private controlCaja controlC;

    public controlProductor(String nombre, controlCaja controlC) {
        this.Nombre = nombre;
        this.controlC = controlC;
    }


    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            Producto pro = controlC.push();
            if (pro==null)
                break;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }
}
