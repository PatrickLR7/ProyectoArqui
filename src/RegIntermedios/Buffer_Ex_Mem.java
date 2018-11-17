package RegIntermedios;

import java.util.ArrayList;

public class Buffer_Ex_Mem {
  private int aluOutput;
  private int regB;
  private ArrayList<Integer> ir;

  public Buffer_Ex_Mem(){
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
