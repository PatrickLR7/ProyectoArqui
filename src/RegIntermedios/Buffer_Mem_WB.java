package RegIntermedios;

import java.util.ArrayList;

public class Buffer_Mem_WB {
  private int aluOutput;
  private int lmd;
  private ArrayList<Integer> ir;

  public Buffer_Mem_WB() {
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
