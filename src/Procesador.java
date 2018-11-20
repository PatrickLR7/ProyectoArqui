import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Procesador {



  //private int[][] contexto; //Estructura para manejar el contexto de los hilillos. Cada fila representa un hilillo. Dentro de la fila pos 0 es el PC y 1 a 32 son los registros.
  //private int[][] registros; //Estructura para manejar cada registro con su estado.


    public Procesador(){





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




  public static void main(String[] args) {
    
    int pc;

    MemoriaPrincipal memoriaPrincipal = MemoriaPrincipal.getInstancia();



    for (int i = 0; i < 7; i++) {
        String rutaHilo = i + ".txt";
        String instruccionesLeidas = leerArchivo(rutaHilo);
        pc = memoriaPrincipal.agregarInst(instruccionesLeidas);

    }



    Procesador proce = new Procesador();
      proce.prueba();

    /*
        Thread thread = new Thread();

        new Thread(new Runnable() {

        public void run() {
            hiloBurbuja hiloB = new hiloBurbuja(inter);

            hiloB.run();

        }

        }).start();


        new Thread(new Runnable() {

                public void run() {
                    hiloQuick hiloQ = new hiloQuick(inter);

                    hiloQ.run();

                }
        }).start();
    */

  }

}
