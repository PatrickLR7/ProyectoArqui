public class Etapa{
    Etapa siguienteEtapa = null;
    AdminConcurrencia adminConcurrencia = null;

    public void ejecutarEtapa() throws InterruptedException {
        /*
            Esto solo es la firma del método, cada clase especializada le hace Override.
            Deben hacer algo así:

            if(ir != null){  Debemos averiguar de donde sacar el IR en cada una de las etapas.

            }

            manejarBarrera();

        */
    }

    public Etapa getSiguienteEtapa(){
        return siguienteEtapa;
    }

    public void setSiguienteEtapa(Etapa siguienteEtapa){
        this.siguienteEtapa = siguienteEtapa;
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