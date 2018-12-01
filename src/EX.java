import java.util.concurrent.Phaser;

public class EX extends Etapa implements Runnable{

	private int[] IR;
	private int reg1;
	private int reg2;
	private int inm;
	private int dir;
	private int aluOutput;
	private int npc;

    
    public EX(int id) {
		int[] IR = new int[4];
		int reg1 = -1;
	 	int reg2 = -1;
		int inm = -1;
		int dir = -1;
		int aluOutput = -1;
		int npc = -1;
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


	
		super.barreraEX();

		//copia a registroIntermedioMEM
		super.reg_EX_MEM.npc = npc;
		super.reg_EX_MEM.aluOutput = aluOutput;
		super.reg_EX_MEM.ir = IR;
		super.reg_EX_MEM.regB = reg2;

		super.releaseBarreraID();



		super.manejarBarrera2();
	}
}
