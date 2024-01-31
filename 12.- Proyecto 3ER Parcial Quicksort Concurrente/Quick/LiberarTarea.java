//Esta clase solamente revisa que las tareas del Executor esten disponibles
public abstract class LiberarTarea implements Runnable{
 
    public abstract boolean isReadyToProcess();
   
  }