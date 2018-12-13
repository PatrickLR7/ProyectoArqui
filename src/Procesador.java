import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.concurrent.Phaser;

public class Procesador {

  public int ciclosReloj;
  public int ciclosRelojHililloActual;
  public int ciclosRelojH0;
  public int ciclosRelojH1;
  public int ciclosRelojH2;
  public int ciclosRelojH3;
  public int ciclosRelojH4;
  public int ciclosRelojH5;
  public int ciclosRelojH6;
  public int quantum;
  
  //private int[][] registros; //Estructura para manejar cada registro con su estado.

  public int contexto[][]; //Estructura para manejar el contexto de los hilillos. Cada fila representa un hilillo. Dentro de la fila pos 32 es el PC y 0 a 31 son los registros.
  //public CacheD[] cacheDatos = new CacheD[2];
  //public CacheI[] cacheInstrucciones = new CacheI[2];
  public IF hiloIF;
  public ID hiloID;
  public EX hiloEX;
  public Mem hiloMEM;
  public WB hiloWB;

  public Phaser phaser1;
  public Phaser phaser2;
  public Phaser phaserIF_ID;
  public Phaser phaserID_EX;
  public Phaser phaserEX_MEM;
  public Phaser phaserMEM_WB;


 
    public Procesador(){

        ciclosReloj = 0;
        ciclosRelojHililloActual = 0;
        quantum = 5; //para pruebas

       
        contexto = new int[7][33];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 33; j++) {
                contexto[i][j] = 0;
            }
        }

        hiloIF = null;
        hiloID = null;
        hiloEX = null;
        hiloMEM = null;
        hiloWB = null;
        

        phaser1 = new Phaser(6); //Aquí esperan los 5 procesos de las etapas + proceso principal (Procesador), total = 6 procesos.
        phaser2 = new Phaser(6); //Aquí esperan los 5 procesos de las etapas + proceso principal (Procesador), total = 6 procesos.
        phaserIF_ID = new Phaser(2); //Aquí esperan etapas IF e ID. Para controlar liberado de reg intermedios. total = 2 procesos.
        phaserID_EX = new Phaser(2); //Aquí esperan etapas ID e EX. Para controlar liberado de reg intermedios. total = 2 procesos.
        phaserEX_MEM = new Phaser(2); //Aquí esperan etapas EX e MEM. Para controlar liberado de reg intermedios. total = 2 procesos.
        phaserMEM_WB = new Phaser(2); //Aquí esperan etapas MEM e WB. Para controlar liberado de reg intermedios. total = 2 procesos.

        //phaser2.register();
        //phaser1.register();
        //System.out.println( phaser1.getRegisteredParties() );


    }

     /**
     * Metodo que lee y guarda las instrucciones de los archivos de los hilillos.
     * @param rutaArchivo Ruta del archivo que contiene las instrucciones.
     */
    private static String leerArchivo(String rutaArchivo) {
        StringBuilder instrucciones = new StringBuilder();
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        try {
            archivo = new File (rutaArchivo);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            while((linea = br.readLine()) != null) {
                instrucciones.append(linea).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return instrucciones.toString();

    }

     /**
     * Metodo que llena el PC del hilillo en su contexto.
     * @param fila Numero del hilillo.
     * @param pc PC del hilillo.
     */
    public void llenarContextopc(int fila, int pc) {
        contexto[fila][32] = pc;
    }

    /** Metodo que imprime los valores del contexto*/
    public void imprimirContexto() {
        System.out.println("CONTEXTO");
        String md = "";
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 33; j++) {
                md += contexto[i][j] + " ";
            }
            md += "\n";
        }
        System.out.println(md);
    }

  public static void main(String[] args) {
    
    Procesador procesador = new Procesador();

       int  pc=0;

    MemoriaPrincipal memoriaPrincipal = MemoriaPrincipal.getInstancia();
    AdminConcurrencia adminConcurrencia = AdminConcurrencia.getInstancia();

    Queue<Integer> colaIDs = new ArrayDeque<>();

    for (int i = 0; i < 6; i++) {

        String rutaHilo = i + ".txt";
        String instruccionesLeidas = leerArchivo(rutaHilo);
        pc = memoriaPrincipal.agregarInst(instruccionesLeidas);
        procesador.llenarContextopc(i, pc);
        colaIDs.add(i);

    }

        //IMPRIMIR MEMORIA DATOS
        System.out.println("MEMORIA DE DATOS");
        memoriaPrincipal.imprimirMemoria();

        //IMPRIMIR MEMORIA INSTRUCCIONES
        System.out.println("MEMORIA DE INSTRUCCIONES");
        memoriaPrincipal.imprimirMemoriaInst();

        //IMPRIMIR CONTEXTO
        System.out.println("CONTEXTO");
        procesador.imprimirContexto(); 
       

        int hililloActual = 0;
       
        while (colaIDs.isEmpty() == false) {
            //procesador.phaser1.bulkRegister(6); 
            //procesador.phaser2.bulkRegister(6); 
            //procesador.phaserIF_ID.bulkRegister(2); 
            //procesador.phaserID_EX.bulkRegister(2); 
            //procesador.phaserEX_MEM.bulkRegister(2);
            //procesador.phaserMEM_WB.bulkRegister(2);

            
 
            
              if (!colaIDs.isEmpty() &&  procesador.hiloIF == null  &&  procesador.hiloID == null) {

               
                hililloActual = colaIDs.poll();
                procesador.ciclosRelojHililloActual = 0;
                procesador.hiloIF = new IF(hililloActual, procesador.contexto[hililloActual],procesador.phaser1, procesador.phaser2, procesador.phaserIF_ID );
                procesador.hiloID = new ID(hililloActual, procesador.contexto[hililloActual],procesador.phaser1, procesador.phaser2, procesador.phaserIF_ID, procesador.phaserID_EX); 
                procesador.hiloEX = new EX(hililloActual, procesador.phaser1, procesador.phaser2, procesador.phaserID_EX, procesador.phaserEX_MEM );
                procesador.hiloMEM= new Mem(hililloActual, procesador.phaser1, procesador.phaser2, procesador.phaserEX_MEM, procesador.phaserMEM_WB);
                procesador.hiloWB = new WB(hililloActual, procesador.contexto[hililloActual],procesador.phaser1, procesador.phaser2, procesador.phaserMEM_WB);

                System.out.println("hilos creados");

                procesador.hiloIF.start();
                procesador.hiloID.start();
                procesador.hiloEX.start();
                procesador.hiloMEM.start();
                procesador.hiloWB.start();

                System.out.println("hilos Start");

              }else{

                procesador.hiloIF.run();
                procesador.hiloID.run();
                procesador.hiloEX.run();
                procesador.hiloMEM.run();
                procesador.hiloWB.run();

                System.out.println("hilos Run");
              }

                    procesador.phaser1.arriveAndAwaitAdvance();

                        procesador.ciclosReloj++;
                        procesador.ciclosRelojHililloActual++;
 

                    if(hililloActual == 0){
                        procesador.ciclosRelojH0++;
                    }else if(hililloActual == 1){
                        procesador.ciclosRelojH1++;
                    }else if(hililloActual == 2){
                        procesador.ciclosRelojH2++;
                    }else if(hililloActual == 3){
                        procesador.ciclosRelojH3++;
                    }else if(hililloActual == 4){
                        procesador.ciclosRelojH4++;
                    }else if(hililloActual == 5){
                        procesador.ciclosRelojH5++;
                    }
                    /*
                    else if(hililloActual == 6){
                        procesador.ciclosRelojH6++;
                    } 
                    */

                        System.out.println("CiclosRelojH0: " + procesador.ciclosRelojH0);
                        System.out.println("CiclosRelojH1: " + procesador.ciclosRelojH1);
                        System.out.println("CiclosRelojH2: " + procesador.ciclosRelojH2);
                        System.out.println("CiclosRelojH3: " + procesador.ciclosRelojH3);
                        System.out.println("CiclosRelojH4: " + procesador.ciclosRelojH4);
                        System.out.println("CiclosRelojH5: " + procesador.ciclosRelojH5);
                        //System.out.println("CiclosRelojH6: " + procesador.ciclosRelojH6);
                        System.out.println("CiclosReloj: " + procesador.ciclosReloj);

                     if( procesador.hiloID.getFinalice() == false ){

                        if (procesador.hiloIF != null && procesador.hiloID != null && procesador.ciclosRelojHililloActual > procesador.quantum) {
                      
                          
                           try{
                               
                                procesador.hiloIF.join();
                                procesador.hiloID.join();
                                procesador.hiloEX.join();
                                procesador.hiloMEM.join();
                                procesador.hiloWB.join();
                                
                                System.arraycopy(procesador.hiloWB.registrosIDWB.registros[0], 0, procesador.contexto[procesador.hiloWB.getIdHilillo()], 0, 32);
                                procesador.contexto[procesador.hiloWB.getIdHilillo()][32] = procesador.hiloWB.reg_MEM_WB.npc;
                                colaIDs.add(procesador.hiloWB.getIdHilillo());
                                
                                procesador.hiloIF = null;
                                procesador.hiloID = null;
                                procesador.hiloEX = null;
                                procesador.hiloMEM = null;
                                procesador.hiloWB = null;
                               
                               
                           } catch (InterruptedException e){
                               System.out.println(e);
                           }
                            
                        }

                    } else{

                        try{
                            procesador.hiloIF.join();
                            procesador.hiloID.join();
                            procesador.hiloEX.join();
                            procesador.hiloMEM.join();
                            procesador.hiloWB.join();
                            procesador.hiloIF = null;
                            procesador.hiloID = null;
                            procesador.hiloEX = null;
                            procesador.hiloMEM = null;
                            procesador.hiloWB = null;
                        } catch (InterruptedException e){
                            System.out.println(e);
                        }

                    }


                    procesador.phaser2.arriveAndAwaitAdvance();
    

                  

                }

                procesador.imprimirContexto();
                System.out.println("Termino!");


        }
}
