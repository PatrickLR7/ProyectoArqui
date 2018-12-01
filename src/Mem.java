import java.util.concurrent.Phaser;


public class Mem extends Etapa implements Runnable {

	private int[] IR;
	private int aluOutput;
	private int pc;

	
    public Mem() {
		aluOutput = -1;
	}
	
	public void run() {
		try{
			ejecutarEtapa();
		} catch (InterruptedException e){
			System.out.println("Interrupted Exception: " + e);
		}
	}

	@Override
	public void ejecutarEtapa() throws InterruptedException{
		IR = super.reg_EX_MEM.ir;
		pc = super.reg_EX_MEM.npc;
		int bloque, numPalabra;
		

		if(IR[0] == 19 || IR[0] == 71 || IR[0] == 83 || IR[0] == 72 || IR[0] == 56){ // op. arit.

			aluOutput = super.reg_EX_MEM.aluOutput;

		} else if(IR[0] == 111 || IR[0] == 103){ //jal y jalr
			

		} else if(IR[0] == 5 || IR[0] == 37){
			aluOutput = super.reg_EX_MEM.aluOutput;
			bloque = aluOutput / 16;
			numPalabra = (aluOutput - (16 * bloque)) / 4;

			//crear caché de datos en esta etapa para poder usarla con los lw y sw
		}


	
		super.barreraMEM();

		//copia a registroIntermedioWB
		super.reg_MEM_WB.ir = IR;
		super.reg_MEM_WB.npc = pc;
		super.reg_MEM_WB.aluOutput = aluOutput;
	
		super.releaseBarreraEX();

		super.manejarBarrera2();
	
	}

}
