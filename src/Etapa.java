public class Etapa{
    public Etapa siguienteEtapa;
    public AdminConcurrencia adminConcurrencia;
    public RegistrosIDWB registrosIDWB = RegistrosIDWB.getInstancia();
    public RegistroIntermedio.IF_ID reg_IF_ID = RegistroIntermedio.IF_ID.getInstancia();
    public RegistroIntermedio.ID_EX reg_ID_EX = RegistroIntermedio.ID_EX.getInstancia();
    public RegistroIntermedio.EX_MEM reg_EX_MEM = RegistroIntermedio.EX_MEM.getInstancia();
    public RegistroIntermedio.MEM_WB reg_MEM_WB = RegistroIntermedio.MEM_WB.getInstancia();


    public Etapa(){
        siguienteEtapa = null;
        adminConcurrencia = new AdminConcurrencia();
    }

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