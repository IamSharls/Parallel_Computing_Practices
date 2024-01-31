//Clase que controla la entrada y salida de los procesos consumidores

public class controlConsumidor extends Thread {
    private String Nombre = null;
    private controlCaja controlC = null;

    public controlConsumidor(String nombre, controlCaja inventario) {
        this.Nombre = nombre;
        this.controlC = inventario;
    }

    public void setConsumerName(String consumerName) {
        this.Nombre = consumerName;
    }

    public String getConsumerName() {
        return Nombre;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Producto pro = controlC.pop();
                if (pro==null)
                    break;
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}