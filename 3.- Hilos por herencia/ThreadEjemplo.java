public class ThreadEjemplo extends Thread {

    public ThreadEjemplo(String str) {

    super(str);

    }

    public void run() {

    for(int i = 0; i < 10 ; i++)
    System.out.println(i + " " + getName());
    System.out.println("Terminó el hilo de " + getName());
    
    }

    public static void main(String [] args) {

    new ThreadEjemplo("Pepe").start();
    new ThreadEjemplo("Juan").start();
    System.out.println("Se iniciaron los dos hilos");

        }

    }