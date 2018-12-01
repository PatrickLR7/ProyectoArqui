import java.util.concurrent.Semaphore;



public class AdminConcurrencia{
    private static AdminConcurrencia adminConcurrencia;
    public final int totalEtapas = 5; //El máximo de etapas que hay en el pipeline.
    public int etapasFinalizadas = 0; // Numero de etapas que han finalizado.
    Semaphore mutexEtapasFinalizadas; //Hay que inicializarlo en 1. Ya que semaforos en Java inicializados en 1 funcionan como Mutex.
    Semaphore barreraDeEtapas; //Para que las etapas esperen hasta que todas terminen.
    Semaphore barrera2DeEtapas; //Para que las etapas esperen hasta que hiloP realice sus operaciones.





public static AdminConcurrencia getInstancia() {
        if (adminConcurrencia == null) {
            adminConcurrencia = new AdminConcurrencia();
        }
        return adminConcurrencia;
    }

}