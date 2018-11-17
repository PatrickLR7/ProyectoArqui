package RegIntermedios;

import java.util.ArrayList;

public class Buffer_ID_EX {
  private int npc;
  private ArrayList<Integer> ir;
  private int regA;
  private int regB;
  private int imm;

  public Buffer_ID_EX(){
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
