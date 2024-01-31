//Clase que controla el ingreso de "productos"

import java.util.LinkedList;
import java.util.TreeMap;

public class controlCaja{

    private int contador = 0;
    public static int tope = 6;

    private static LinkedList<Producto> productosl;

    public controlCaja() {
        productosl = new LinkedList<Producto>();
    }

    private Producto[] productos = new Producto[10];

    public synchronized Producto push() {
        try {
            Grafico.agregarProductor(Grafico.productorPGrid);
            while (productosl.size() >= tope) {
                //System.out.println("El almacen esta lleno, Esperando...");
                Grafico.sleep(Grafico.productorPGrid, true);
                wait();
                Grafico.work(Grafico.productorPGrid, true);
            }
            contador++;
            productosl.add(new Producto(contador));

            notifyAll();
            return productosl.getLast();
        } catch (InterruptedException error) {
            return null;
        }
    }

    public synchronized Producto pop() {
        try {
            Producto producto = null;
            Grafico.quitarProductor(Grafico.productorPGrid);

            while (productosl.size() <= 0) {

                Grafico.sleep(Grafico.consumidorPGrid,false);
                wait();
                Grafico.work(Grafico.consumidorPGrid, false);
            }

            producto = productosl.getFirst();
            productosl.pop();
            notifyAll();
            return producto;

        } catch (InterruptedException error) {
            return null;
        }
    }
}