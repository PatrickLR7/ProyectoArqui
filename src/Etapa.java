public class Etapa{
    Etapa siguientEtapa = null;
    AdminConcurrencia adminConcurrencia = null;

    public void ejecutarEtapa() throws InterruptedException {
        /*
            if(ir != null){  Debemos averiguar de donde sacar el IR

            }
        */

        manejarBarrera();
    }

    public Etapa getSiguienteEtapa(){
        return siguientEtapa;
    }

    public void setSiguienteEtapa(Etapa siguienteEtapa){
        this.siguientEtapa = siguienteEtapa;
    }

    public void manejarBarrera() throws InterruptedException{
        adminConcurrencia.mutexEtapasFinalizadas.acquire();
        if(adminConcurrencia.etapasFinalizadas == 4){
            //TODO aquí hay que tomar en cuenta manejar lógica de liberar de derecha a izquierda.

            adminConcurrencia.barreraDeEtapas.release(4);
            adminConcurrencia.mutexEtapasFinalizadas.release();
        } else {
            adminConcurrencia.etapasFinalizadas++;
            adminConcurrencia.mutexEtapasFinalizadas.release();
            adminConcurrencia.barreraDeEtapas.acquire();
        }
    }

}