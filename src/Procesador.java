import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.ArrayDeque;


public class Procesador {



  public int ciclosReloj;

  //private int[][] registros; //Estructura para manejar cada registro con su estado.

  public int contexto[][]; //Estructura para manejar el contexto de los hilillos. Cada fila representa un hilillo. Dentro de la fila pos 0 es el PC y 1 a 32 son los registros.
  //public CacheD[] cacheDatos = new CacheD[2];
  //public CacheI[] cacheInstrucciones = new CacheI[2];
  public IF hiloIF;
  public ID hiloID;
  public EX hiloEX;
  public Mem hiloMem;
  public WB hiloWB;


    public Procesador(){

        ciclosReloj = 0;

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
       


        procesador.hiloIF = new IF(pc);
        new Thread(new Runnable() {

        public void run() {
            procesador.hiloIF.run();
        }
        }).start();

  }

}
