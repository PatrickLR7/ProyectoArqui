public class WB extends Etapa implements Runnable {

	private int[] IR;
	private RegistrosIDWB regIDWB;
	private int regDestino;
	private int idHilillo;


    public WB( int id) {


		idHilillo = id;
		regIDWB = super.registrosIDWB;
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
		regDestino = IR[1];
		// Caso en el que es ADDI, ADD, SUB, MUL, DIV, JAL o JALR -> escribe a registro contenido de AluOutput.
		if(IR[0] == 19 || IR[0] == 71 || IR[0] == 83 || IR[0] == 72 || IR[0] == 56 || IR[0] == 111 || IR[0] == 103){
			regIDWB.registros[0][regDestino] = super.reg_MEM_WB.aluOutput;
			regIDWB.registros[1][regDestino]--;
		//Caso en el que es un LW o LR -> escribe a registro contenido del LMD.
		} else if(IR[0] == 5 || IR[0] == 51){ 
			regIDWB.registros[0][regDestino] = super.reg_MEM_WB.lmd;
			regIDWB.registros[1][regDestino]--;
		//Caso en el que es un SC.
		} else if(IR[0] == 52){
			if(registrosIDWB.registros[0][33] == -1){ //si no escribio
				regIDWB.registros[0][regDestino] = 0;
				regIDWB.registros[1][regDestino]--;
				regIDWB.registros[1][IR[2]]--;
			} else{ // si escribió
				regIDWB.registros[0][regDestino] = super.reg_MEM_WB.lmd;
				regIDWB.registros[1][regDestino]--;
				regIDWB.registros[1][IR[2]]--;
			}
		}

	

		super.releaseBarreraMEM();

		super.manejarBarrera2();


	}

	public int getIdHilillo(){
		return idHilillo;
	}

}
