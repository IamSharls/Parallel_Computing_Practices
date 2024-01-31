//Clase para iniciar el programa

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Inic{

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

        Runnable initFrame = new Runnable() {
            @Override
            public void run() {
                new Grafico();
            }
        };

        SwingUtilities.invokeAndWait(initFrame);

    }
}
