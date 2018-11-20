import java.util.ArrayList;

public class RegistroIntermedio { 

    /*
    * Inicializar instancia de clase interna:
    * OuterClass.InnerClass innerObject = outerObject.new InnerClass();
    */

    public class IF_ID { //Estructura para registro intermedio IF_ID
        public int npc;
        public ArrayList<Integer> ir;

        public IF_ID(){
            npc = 0;
            ir = new ArrayList<Integer>(4);
          }
        
          public int getNpc(){
            return this.npc;
          }
        
          public void setNpc(int npcCopiado){
            this.npc = npcCopiado;
          }
        
          public ArrayList getIr(){
            return this.ir;
          }
        
          public void setIr(ArrayList irCopiado){
            this.ir = irCopiado;
          }
    }

    public class ID_EX { //Estructura para registros intermedios ID_EX
        public int npc;
        public ArrayList<Integer> ir;
        public int regA;
        public int regB;
        public int imm;

        public ID_EX(){
            npc = 0;
            ir = new ArrayList<Integer>(4);
            regA = 0;
            regB = 0;
            imm = 0;
          }
        
          public int getNpc(){
            return this.npc;
          }
        
          public void setNpc(int npcCopiado){
            this.npc = npcCopiado;
          }
        
          public ArrayList getIr(){
            return this.ir;
          }
        
          public void setIr(ArrayList irCopiado){
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
    }

    public class EX_MEM { //Estructura para registro intermedio EX_MEM
        public int aluOutput;
        public int regB;
        public ArrayList<Integer> ir;

        public EX_MEM(){
            aluOutput = 0;
            regB = 0;
            ir = new ArrayList<Integer>(4);
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
        
          public ArrayList getIr(){
            return this.ir;
          }
        
          public void setIr(ArrayList irCopiado){
            this.ir = irCopiado;
          }        
    }                

    public class MEM_WB { //Estructura para registro intermedio MEM_WB
        public int aluOutput;
        public int lmd;
        public ArrayList<Integer> ir;
        
        public MEM_WB() {
            aluOutput = 0;
            lmd = 0;
            ir = new ArrayList<Integer>(4);
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
        
          public ArrayList getIr(){
            return this.ir;
          }
        
          public void setIr(ArrayList irCopiado){
            this.ir = irCopiado;
          }        
    }       
}