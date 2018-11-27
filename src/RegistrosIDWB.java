public class RegistrosIDWB{
    public int registros[][];
    private static RegistrosIDWB regIDWB;
    public RegistrosIDWB(){
        registros = new int[2][34]; // registros[32] es el PC y registros[33] es el RL; 
    }

    public int[][] getRegistros(){
        return registros;
    }

    /**
     * Metodo Singleton para controlar que solo se cree un objeto IF_ID.
     * Controla que solo se maneje una instancia de memoria en el programa.
     * @return Devuelve la memoria si esta ya existe.
     */
    public static RegistrosIDWB getInstancia() {
    if (regIDWB == null) {
        regIDWB = new RegistrosIDWB();
    }
        return regIDWB;
    }
}