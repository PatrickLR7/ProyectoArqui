import java.util.ArrayList;
public class IF extends Etapa implements Runnable{

  int pc;
  private ArrayList<ArrayList<Integer>> cacheInst;

	public IF(int pc) {
		this.pc = pc;
		cacheInst = new ArrayList<ArrayList<Integer>>();
	}
	
	public void run() {
		
	}

	@Override
	public void ejecutarEtapa(){
		//TODO hacer lo respectivo.
	}
}
