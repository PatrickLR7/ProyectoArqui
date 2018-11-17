import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Procesador {

private ArrayList<Integer> memoriaDatos;// Estructura de memoria para datos, dirección de memoria 0 hasta la 380
private ArrayList<ArrayList<Integer>> memoriaIntrucciones;// Estructura de memoria para instrucciones, dirección 384 de memoria hasta la 1020


private int[][] contexto; //Estructura para manejar el contexto de los hilillos. Cada fila representa un hilillo. Dentro de la fila pos 0 es el PC y 1 a 32 son los registros.
private int[][] registros; //Estructura para manejar cada registro con su estado.


  public Procesador(){

    System.out.print("Hola");

    memoriaIntrucciones = new ArrayList<ArrayList<Integer>>();
    memoriaDatos = new ArrayList<Integer>();


    for (int i=1; i<=24; i++){ // Se llena la memoria de datos con 1s
            memoriaDatos.add(1);
    }


  }



  /**
 * Metodo que lee y guarda las instrucciones de los archivos de los hilillos.
 * @param rutaArchivo Ruta del archivo que contiene las instrucciones.
 * @return String con instrucciones del hilillo.
 */

private static String leerArchivo(String rutaArchivo) {
    StringBuilder instrucciones = new StringBuilder();
    File archivo;
    FileReader fr = null;
    BufferedReader br;
    ArrayList<Integer> arrayInstruccion; //array de la instruccion leida

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

    for (int i = 0; i < 7; i++) {
        String rutaHilo = i + ".txt";
        leerArchivo(rutaHilo);

    }



  ArrayList<Integer> arrayInstruccion; //array de la instruccion leida
  String instruccion; //Hilera de la instruccion
  String[] splited;
  String path = "/Users/pankaj/Downloads/";
  String hilillo;



          try {

             for(int i = 0; i == 6; i++ ){
                Scanner scanner = new Scanner(new File(path)); // Path de hilillos

                while (scanner.hasNextLine()) { //lee el archivo

            			instruccion = (scanner.nextLine()); //Línea leida
                  splited = instruccion.split("\\s+"); //Vector con ints separados de la instruccion

                  arrayInstruccion.add(splited[0]); //se agrega cada int de la instruccion al arrayInstruccion
                  arrayInstruccion.add(splited[1]);
                  arrayInstruccion.add(splited[2]);
                  arrayInstruccion.add(splited[3]);

                }

                memoriaIntrucciones.add(arrayInstruccion);
                arrayInstruccion.clear();
          			scanner.close();

                path = "/Users/pankaj/Downloads/hilillo0.txt";
            }

          }
          catch (FileNotFoundException e) {
          			e.printStackTrace();
          }




    Procesador proce = new Procesador();
      proce.prueba();

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


  }

}
