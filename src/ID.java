import java.util.concurrent.Phaser;

public class ID extends Etapa implements Runnable {


	private int[] IR;
	private RegistrosIDWB regIDWB;
	private int reg1;
	private int reg2;
	private int inm;
	private int pc;
	private Phaser phaser1;
	private Phaser phaser2;
	private Phaser phaserIF_ID;
	private Phaser phaserID_EX;
	private int regA;
	private int regB;
	private int inmediato;
	

    public ID(int id, int[] contextoHilillo, Phaser phaser1, Phaser phaser2, Phaser phaserIF_ID, Phaser phaserID_EX) {

		IR = new int[4];
		regIDWB = super.registrosIDWB;
		
		
		for(int i = 0; i < 33; i++){

					regIDWB.registros[0][i] = contextoHilillo[i];
					regIDWB.registros[1][i] = 0;

		}
		
		regIDWB.registros[0][33] = -1;

		this.phaser1 = phaser1;
		this.phaser2 = phaser2;
		this.phaserIF_ID = phaserIF_ID;
		this.phaserID_EX = phaserID_EX;

		/*this.phaser1.register();
		this.phaser2.register();
		this.phaserIF_ID.register();
		this.phaserID_EX.register();
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

		IR = super.reg_IF_ID.ir;
		pc = super.reg_IF_ID.npc;
		reg1 = IR[2];
		if(IR[3] > 0){
		 reg2 = IR[3];
		}
		inm = IR[3];

			
		//Caso con inmediatos en pos 3 de IR 
		if(IR[0] == 19){ //Caso ADDI
			super.reg_ID_EX.imm = inm;
		
			
			if(regIDWB.registros[1][reg1] == 0){
				regIDWB.registros[1][reg1]++;
				//super.reg_ID_EX.regA = regIDWB.registros[0][reg1];
				//super.reg_ID_EX.regB = -1;
				regA  = regIDWB.registros[0][reg1];
				regB = -1;
				
			} else{
				//no puede avanzar.
			}

		//Caso sin inmediatos 
		} else if(IR[0] == 71 || IR[0] == 83 || IR[0] == 72 || IR[0] == 56){ //Caso ADD SUB MUL DIV
			
			reg2 = IR[3];
			
			if(regIDWB.registros[1][reg1] == 0 && regIDWB.registros[1][reg2] == 0){
				regIDWB.registros[1][reg1]++;
				regIDWB.registros[1][reg2]++;
				//super.reg_ID_EX.regA = regIDWB.registros[0][reg1];
				//super.reg_ID_EX.regB = regIDWB.registros[0][reg2];
				regA =  regIDWB.registros[0][reg1];
				regB  = regIDWB.registros[0][reg2];
				
			} else{
				//no puede avanzar.
			}
		//Caso lw
		} else if(IR[0] == 5){
			super.reg_ID_EX.imm = inm;
		
			
			if(regIDWB.registros[1][reg1] == 0){
				regIDWB.registros[1][reg1]++;
				//super.reg_ID_EX.regA = regIDWB.registros[0][reg1];
				//super.reg_ID_EX.regB = -1;
				regA  = regIDWB.registros[0][reg1];
				regB = -1;
				
			} else{
				//no puede avanzar.
			}
		//caso sw	
		} else if(IR[0] == 37){
			System.out.println("Reg1: " + reg1 + " Reg2: " +reg2);
			if(regIDWB.registros[1][reg1] == 0){
				regIDWB.registros[1][reg1]++;
				
				//super.reg_ID_EX.regA = regIDWB.registros[0][IR[1]];
				//super.reg_ID_EX.regB = regIDWB.registros[0][IR[2]];
				//super.reg_ID_EX.imm = inm;
				regA  = regIDWB.registros[0][IR[1]];
				regB =  regIDWB.registros[0][IR[2]];
				inmediato = inm;
				
				
			} else{
				//no puede avanzar.
			}
		
		//Caso  lr.	
		} else if(IR[0] == 51){
		

			if(regIDWB.registros[1][reg1] == 0){
				regIDWB.registros[1][reg1]++;
				//super.reg_ID_EX.regB = regIDWB.registros[0][reg1];
				//super.reg_ID_EX.regA = -1;
				//super.reg_ID_EX.imm = -1;
				regA  = regIDWB.registros[0][reg1];
				regB = -1;
				inmediato = -1;
				
				//copiar reg1 en RL
				regIDWB.registros[0][33]=reg1;

				
			} else{
				//no puede avanzar.
			}

		//Caso  sc	
		} else if(IR[0] == 52){

				//revisar si RL == reg1
			
			if (regIDWB.registros[1][reg1] == 0 && regIDWB.registros[1][reg2] == 0) {

				if (regIDWB.registros[0][33] == reg1) {

					regIDWB.registros[1][reg1]++;
					regIDWB.registros[1][reg2]++;
					//super.reg_ID_EX.regA = regIDWB.registros[0][IR[1]];
					//super.reg_ID_EX.regB = regIDWB.registros[0][IR[2]];
					//super.reg_ID_EX.imm = 0;
					regA  = regIDWB.registros[0][IR[1]];
					regB = regIDWB.registros[0][IR[2]];
					inmediato = 0;
					
					
				}

			} else {
				// no puede avanzar.
			}

		// caso Branches.
		} else if(IR[0] == 99 || IR[0] == 100){ 
	
			
			if(regIDWB.registros[1][reg1] == 0){
				regIDWB.registros[1][reg1]++;
				//regIDWB.registros[1][reg2]++;
				//super.reg_ID_EX.regA = regIDWB.registros[0][IR[1]];
				//super.reg_ID_EX.regB = regIDWB.registros[0][IR[2]];
				//super.reg_ID_EX.imm = inm;
				regA  = regIDWB.registros[0][IR[1]];
				regB  = regIDWB.registros[0][IR[2]];
				inmediato = inm;
				
				//super.reg_ID_EX.npc = super.reg_IF_ID.npc + (inm*4);
				 pc = super.reg_IF_ID.npc + (inm*4);

				
			} else{
				//no puede avanzar.
			}

		// caso jal.
		} else if(IR[0] == 111){

			//super.reg_ID_EX.imm = inm;
			//super.reg_ID_EX.regA = -1;
			//super.reg_ID_EX.regB = -1;

			//super.reg_ID_EX.npc = super.reg_IF_ID.npc + inm;
			
			inmediato = inm;
			regA = -1;
			regB = -1;
			pc =  super.reg_IF_ID.npc + inm;

		//caso jalr.
		} else if(IR[0] == 103){
			
			if(regIDWB.registros[1][reg1] == 0 ){
			    regIDWB.registros[1][reg1]++;
				//super.reg_ID_EX.regA = regIDWB.registros[0][reg1];
				//super.reg_ID_EX.regB = -1;
				//super.reg_ID_EX.imm = inm;
				
				//super.reg_ID_EX.npc =  regIDWB.registros[0][IR[2]] + inm;
				
			    regA  = regIDWB.registros[0][reg1];
			    regB = -1;
			    inmediato = inm;
			    pc =   regIDWB.registros[0][IR[2]] + inm;
				
				
			}else{
				//no puede avanzar.
			}

		}	else if(IR[0] == 999){
			
			//Finaliza

		}								

	
	
		phaser1.arriveAndAwaitAdvance();
	
		phaserID_EX.arriveAndAwaitAdvance();

		//copia a registroIntermedioEX

		super.reg_ID_EX.imm = inmediato;
		super.reg_ID_EX.regA = regA;
		super.reg_ID_EX.regB =  regB;

		super.reg_ID_EX.ir = IR;
		super.reg_ID_EX.npc = pc;

		phaserIF_ID.arriveAndAwaitAdvance();
		phaser2.arriveAndAwaitAdvance();

		phaser1.arriveAndDeregister();
		phaserID_EX.arriveAndDeregister();
		phaserIF_ID.arriveAndDeregister();
		phaser2.arriveAndDeregister();

	}

}
