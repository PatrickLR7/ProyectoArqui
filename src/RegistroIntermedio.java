import java.util.ArrayList;

public class RegistroIntermedio { 

    /*
    * Inicializar instancia de clase interna:
    * OuterClass.InnerClass innerObject = outerObject.new InnerClass();
    */




    public static class IF_ID { //Estructura para registro intermedio IF_ID
        public int npc;
        public int[] ir;
        public boolean libre;
        private static IF_ID if_id;
        private boolean banderaIFNoSiga; // ID le indica a IF que no siga.
        private boolean banderaIDNoSiga; //IF le indica a ID que no le va a llegar nada porque IF está en fallo o porque ya termino el quantum.

        public IF_ID(){
            npc = 0;
            ir = new int[4];
            libre = true;
          }
        
          public int getNpc(){
            return this.npc;
          }
        
          public void setNpc(int npcCopiado){
            this.npc = npcCopiado;
          }
        
          public int[] getIr(){
            return this.ir;
          }
        
          public void setIr(int[] irCopiado){
            this.ir = irCopiado;
          }


          public boolean getlibre(){
            return this.libre;
          }
        
          public void setlibre(boolean libreCopiado){
            this.libre = libreCopiado;
          }

          /**
           * Metodo Singleton para controlar que solo se cree un objeto IF_ID.
           * Controla que solo se maneje una instancia de memoria en el programa.
           * @return Devuelve la memoria si esta ya existe.
           */
          public static IF_ID getInstancia() {
            if (if_id == null) {
                if_id = new IF_ID();
            }
              return if_id;
            }


          
    }

    public static class ID_EX { //Estructura para registros intermedios ID_EX
        public int npc;
        public int[] ir;
        public int regA;
        public int regB;
        public int imm;
        public boolean libre;  
        private static ID_EX id_ex;
        private boolean banderaIDNoSiga; //EX le indica a ID que no siga.
        private boolean banderaEXNoSiga; //ID le indica a EX que no le va a llegar nada.      

        public ID_EX(){
            npc = 0;
            ir = new int[4];
            regA = 0;
            regB = 0;
            imm = 0;
            libre = true;                                  
          }
        
          public int getNpc(){
            return this.npc;
          }
        
          public void setNpc(int npcCopiado){
            this.npc = npcCopiado;
          }
        
          public int[] getIr(){
            return this.ir;
          }
        
          public void setIr(int[] irCopiado){
            this.ir = irCopiado;
          }
        
          public int getRegA(){
            return this.regA;
          }
         
          public void setRegA(int nRegA){
            this.regA = nRegA;
          }
        
          public int getRegB(){
            return this.regB;
          }
        
          public void setRegB(int nRegB){
            this.regB = nRegB;
          }

          public boolean getlibre(){
            return this.libre;
          }
        
          public void setlibre(boolean libreCopiado){
            this.libre = libreCopiado;
          }

          /**
           * Metodo Singleton para controlar que solo se cree un objeto IF_ID.
           * Controla que solo se maneje una instancia de memoria en el programa.
           * @return Devuelve la memoria si esta ya existe.
           */
          public static ID_EX getInstancia() {
            if (id_ex == null) {
                id_ex = new ID_EX();
            }
              return id_ex;
            }

                    
    }

    public static class EX_MEM { //Estructura para registro intermedio EX_MEM
        public int aluOutput;
        public int regB;
        public int[] ir;
        public boolean libre;
        private static EX_MEM ex_mem;        
        private boolean banderaEXSiga; //MEM le dice a EX que no siga ya que MEM está en fallo.
        private boolean banderaMEMNoSiga; //EX le dice a MEM que no le va a llegar nada.

        public EX_MEM(){
            aluOutput = 0;
            regB = 0;
            ir = new int[4];
            libre = true;
          }
        
          public int getaluOutput(){
            return this.aluOutput;
          }
        
          public void setaluOutput(int aluOutputCopiado){
            this.aluOutput = aluOutputCopiado;
          }
        
          public int getRegB(){
            return this.regB;
          }
        
          public void setRegB(int nRegB){
            this.regB = nRegB;
          }
        
          public int[] getIr(){
            return this.ir;
          }
        
          public void setIr(int[] irCopiado){
            this.ir = irCopiado;
          }
             
          public boolean getlibre(){
            return this.libre;
          }
        
          public void setlibre(boolean libreCopiado){
            this.libre = libreCopiado;
          }   

          public static EX_MEM getInstancia() {
            if (ex_mem == null) {
              ex_mem = new EX_MEM();
            }
              return ex_mem;
            }
                    
    }                

    public static class MEM_WB { //Estructura para registro intermedio MEM_WB
        public int aluOutput;
        public int lmd;
        public int[] ir;
        public boolean libre;
        private static MEM_WB mem_wb;
        private boolean banderaMEMSiga; //WB le dice a MEM que no siga.
        private boolean banderaWBNoSiga; //MEM le dice a WB que no le va a llegar nada.       
        
        public MEM_WB() {
            aluOutput = 0;
            lmd = 0;
            ir = new int[4];
            libre = true;            
          }
        
          public int getaluOutput(){
            return this.aluOutput;
          }
        
          public void setaluOutput(int aluOutputCopiado){
            this.aluOutput = aluOutputCopiado;
          }
        
          public int getlmd(){
            return this.lmd;
          }
        
          public void setlmd(int lmdCopiado){
            this.lmd = lmdCopiado;
          }
        
          public int[] getIr(){
            return this.ir;
          }
        
          public void setIr(int[] irCopiado){
            this.ir = irCopiado;
          }       
          
          public boolean getlibre(){
            return this.libre;
          }
        
          public void setlibre(boolean libreCopiado){
            this.libre = libreCopiado;
          }

          public static MEM_WB getInstancia() {
            if (mem_wb == null) {
                mem_wb = new MEM_WB();
            }
              return mem_wb;
            }
    }       
}