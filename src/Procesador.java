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
  public Mem hiloMem;
  public WB hiloWB;

  public Phaser ph;



    public Procesador(){

        ciclosReloj = 0;
        ciclosRelojHililloActual = 0;
        quantum = 10; //para pruebas

       
        contexto = new int[7][33];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 33; j++) {
                contexto[i][j] = 0;
            }
        }

        hiloIF = null;
        hiloID = null;
        hiloEX = null;
        hiloMem = null;
        hiloWB = null;
        
        ph = new Phaser(1);


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

    for (int i = 0; i < 7; i++) {

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
       
       



        int hililloActual;
       
        while (true) {


 
              if (!colaIDs.isEmpty() &&  procesador.hiloIF == null  &&  procesador.hiloID == null) {

                hililloActual = colaIDs.poll();
                procesador.ciclosRelojHililloActual = 0;
                procesador.hiloIF = new IF(hililloActual, procesador.contexto[hililloActual],procesador.ph);
                procesador.hiloID = new ID(hililloActual, procesador.contexto[hililloActual],procesador.ph); 
                procesador.hiloWB = new WB(hililloActual);

                System.out.println("hilos creados");

                procesador.hiloIF.start();
                procesador.hiloID.start();

                System.out.println("hilos Start");

              }else{

                procesador.hiloIF.run();
                procesador.hiloID.run();
                System.out.println("hilos Run");
              }

              

            

                    if(procesador.hiloIF.getLibereBarrera() == true){

                        procesador.ciclosReloj++;
                        procesador.ciclosRelojHililloActual++;
                        procesador.ciclosRelojH0++;
                        procesador.ciclosRelojH1++;
                        procesador.ciclosRelojH2++;
                        procesador.ciclosRelojH3++;
                        procesador.ciclosRelojH4++;
                        procesador.ciclosRelojH5++;
                        procesador.ciclosRelojH6++;
                         
                        

                        if (procesador.hiloIF != null && procesador.hiloID != null && procesador.ciclosRelojHililloActual > procesador.quantum) {
                            try{
                                procesador.hiloIF.join();
                                procesador.hiloID.join();
                                //procesador.hiloEX.join();
                                //procesador.hiloMem.join();
                                //procesador.hiloWB.join();

                                System.arraycopy(procesador.hiloWB.registrosIDWB , 0, procesador.contexto[procesador.hiloWB.getIdHilillo()], 0, 32);
                                procesador.contexto[procesador.hiloWB.getIdHilillo()][32] = procesador.hiloWB.reg_MEM_WB.npc;
                                colaIDs.add(procesador.hiloWB.getIdHilillo());
                                
                                procesador.hiloIF = null;
                                procesador.hiloID = null;
                                //procesador.hiloEX = null;
                                //procesador.hiloMem = null;
                                //procesador.hiloWB = null;

                            } catch (Exception e){

                            }


                            
                        }



                       // procesador.hiloIF.setLibereBarrera(false) ;

                        //adminConcurrencia.barrera2DeEtapas.release(4);
        
                    }

                    
                        



                       


                }

                 

               

                  



        }

}
