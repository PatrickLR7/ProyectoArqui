import java.util.concurrent.Phaser;


public class Mem extends Etapa implements Runnable {

	private int[] IR;
	private int aluOutput;
	private int lmd;
	private int pc;
	private int cacheDatos[][];
	private int regB;
	private Phaser phaser1;
	private Phaser phaser2;
	private Phaser phaserEX_MEM;
	private Phaser phaserMEM_WB;

	
    public Mem(int id, Phaser phaser1, Phaser phaser2, Phaser phaserEX_MEM, Phaser phaserMEM_WB) {
		aluOutput = -1;
		lmd = -1;

		//columna 16 de cacheDatos = Etiqueta
		//columna 17 de cacheDatos = Estado
		cacheDatos = new int[4][18];
		IR = new int[4];

		for (int i = 0; i < 4; i++) {   //llena la cache de datos con 1s
            for (int j = 0; j < 17; j++) {
                cacheDatos[i][j] = 1;
			}
		}
		for (int i = 0; i < 4; i++) {   //llena los estados de la cache de datos con 0s
            for (int j = 17; j < 18; j++) {
                cacheDatos[i][j] = 0;
			}
		}


		this.phaser1 = phaser1;
		this.phaser2 = phaser2;
		this.phaserEX_MEM = phaserEX_MEM;
		this.phaserMEM_WB = phaserMEM_WB;

		//this.phaser1.register();
		//this.phaser2.register();
		//this.phaserEX_MEM.register();
		//this.phaserMEM_WB.register();



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
		int bloque, numPalabra, posCacheD;
		regB =  super.reg_EX_MEM.regB;

		MemoriaPrincipal memoriaPrincipal = MemoriaPrincipal.getInstancia();

		

		if(IR[0] == 19 || IR[0] == 71 || IR[0] == 83 || IR[0] == 72 || IR[0] == 56){ // op. arit.

			aluOutput = super.reg_EX_MEM.aluOutput;

		} else if(IR[0] == 111 || IR[0] == 103){ //jal y jalr
			

		} else if(IR[0] == 5 || IR[0] == 37){
			aluOutput = super.reg_EX_MEM.aluOutput;
			bloque = aluOutput / 16;
			numPalabra = (aluOutput - (16 * bloque)) / 4;
			posCacheD = bloque % 4;
			int etiqueta = cacheDatos[posCacheD][16];

			//crear caché de datos en esta etapa para poder usarla con los lw y sw 
			
			if(bloque != cacheDatos[posCacheD][16]){

				//cacheDatos estado = 1: Modificado, estado = 2: Compartido.


				if(cacheDatos[posCacheD][17] != 1){
				
					System.arraycopy(memoriaPrincipal.memDatos[numPalabra].palabra, 0, cacheDatos[posCacheD], 0, 4);
				}
				else{
					System.arraycopy(cacheDatos[posCacheD], 0, memoriaPrincipal.memDatos[etiqueta].palabra , 0, 4);
					
					System.arraycopy(memoriaPrincipal.memDatos[numPalabra].palabra, 0, cacheDatos[posCacheD], 0, 4);

				}

				if(IR[0] == 37){ //Lw
					cacheDatos[posCacheD][17] = 2;
					lmd = cacheDatos[posCacheD][numPalabra];				
				}else if(IR[0] == 5) { //Sw
					cacheDatos[posCacheD][17] = 1;
					cacheDatos[posCacheD][numPalabra] = regB;
				}


				


			}



		}


		
		phaser1.arriveAndAwaitAdvance();
	
		phaserMEM_WB.arriveAndAwaitAdvance();

		//copia a registroIntermedioWB
		super.reg_MEM_WB.ir = IR;
		super.reg_MEM_WB.npc = pc;
		super.reg_MEM_WB.aluOutput = aluOutput;
		super.reg_MEM_WB.lmd = lmd;
	
		phaserEX_MEM.arriveAndAwaitAdvance();
		phaser2.arriveAndAwaitAdvance();

		imprimirCacheMem();

		phaser1.arriveAndDeregister();
		phaserMEM_WB.arriveAndDeregister();
		phaserEX_MEM.arriveAndDeregister();
		phaser2.arriveAndDeregister();
	
	}

	/** Metodo que imprime los valores que hay en la cache. */
	public void imprimirCacheMem() {
		System.out.println("CacheDatosMem");
		StringBuilder cd = new StringBuilder();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 18; j++) {
				cd.append(cacheDatos[i][j]).append("   ");
			}
			cd.append("\n");
		}
		System.out.println(cd);
	}

}
