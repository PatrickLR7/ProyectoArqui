import java.util.concurrent.Phaser;

public class EX extends Etapa implements Runnable{

	private int[] IR;
	private int reg1;
	private int reg2;
	private int inm;
	private int dir;
	private int aluOutput;
	private int npc;
	private Phaser phaser1;
	private Phaser phaser2;
	private Phaser phaserID_EX;
	private Phaser phaserEX_MEM;

    
    public EX(int id, Phaser phaser1, Phaser phaser2, Phaser phaserID_EX, Phaser phaserEX_MEM) {
		int[] IR = new int[4];
		int reg1 = -1;
	 	int reg2 = -1;
		int inm = -1;
		int dir = -1;
		int aluOutput = -1;
		int npc = -1;

		this.phaser1 = phaser1;
		this.phaser2 = phaser2;
		this.phaserID_EX = phaserID_EX;
		this.phaserEX_MEM = phaserEX_MEM;

		/*this.phaser1.register();
		this.phaser2.register();
		this.phaserID_EX.register();
		this.phaserEX_MEM.register();
		*/

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

		IR = super.reg_ID_EX.ir;
		npc = super.reg_ID_EX.npc;
		reg1 = super.reg_ID_EX.regA;
		reg2 = super.reg_ID_EX.regB;
		inm = super.reg_ID_EX.imm;

		if(IR[0] == 19){ //ADDI, suma reg1 con inmediato
			aluOutput = reg1 + inm;
		} else if(IR[0] == 71 || IR[0] == 83 || IR[0] == 72 || IR[0] == 56){ //Caso ADD SUB MUL DIV, hace operacion correspondiente con reg1 y reg2.
			int op = IR[0];
			switch(op){
				case 71:  //Es un ADD.
					aluOutput = reg1 + reg2;
					break;
				case 83: //Es un SUB.
					aluOutput = reg1 - reg2;
					break;
				case 72: //Es un MUL.
					aluOutput = reg1 * reg2;
					break;
				case 56: //Es un DIV.
					aluOutput = reg1 / reg2;
					break;
			}
		} else if(IR[0] == 5 || IR[0] == 37 || IR[0] == 51 || IR[0] == 52){ // Caso LW, SW, LR, SC, calcula dir de memoria con reg1 e inmediato
			aluOutput = reg1 + inm;
		} else if(IR[0] == 99){ //Caso BEQ
			if(reg1 == reg2){
				//salte
			} else{
				//no salte
			}
		} else if(IR[0] == 100){ // Caso BNE
			if(reg1 != reg2){
				//salte
			} else{
				//no salte
			}
		} else if(IR[0] == 111){ //Caso JAL
			reg1 = npc;

		} else if(IR[0] == 103){ //Caso JALR
			reg2 = npc;
		}


		System.out.println("Pasó0EX");
		phaser1.arriveAndAwaitAdvance();
		System.out.println("Pasó1EX");
		phaserEX_MEM.arriveAndAwaitAdvance();

		//copia a registroIntermedioMEM
		super.reg_EX_MEM.npc = npc;
		super.reg_EX_MEM.aluOutput = aluOutput;
		super.reg_EX_MEM.ir = IR;
		super.reg_EX_MEM.regB = reg2;

		phaserID_EX.arriveAndAwaitAdvance();
		phaser2.arriveAndAwaitAdvance();

		phaser1.arriveAndDeregister();
		phaserEX_MEM.arriveAndDeregister();
		phaserID_EX.arriveAndDeregister();
		phaser2.arriveAndDeregister();
	}
}
