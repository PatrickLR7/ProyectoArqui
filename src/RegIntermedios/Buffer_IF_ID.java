package RegIntermedios;

import java.util.ArrayList;

public class Buffer_IF_ID {
  private int npc;
  private ArrayList<Integer> ir;

  public Buffer_IF_ID(){
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
