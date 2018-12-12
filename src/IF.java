import java.util.ArrayList;
import java.util.concurrent.Phaser;

public class IF extends Etapa implements Runnable{


private int estadoHilillo;
private int[] IR;

private int[] registro;
private int pc; 
private int idHilillo;

Phaser phaser1;
Phaser phaser2;
Phaser phaserIF_ID;



private int cacheInst[][];

	public IF( int id, int[] contextoHilillo, Phaser phaser1, Phaser phaser2, Phaser phaserIF_ID) {
		
		//columna 16 de caccheInst = Etiqueta
		//  0 = invalido; 1 = compartido; 2 = modificado.
		
		cacheInst = new int[4][17];
		IR = new int[4];
		pc = contextoHilillo[32];
		idHilillo = id;

		for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 17; j++) {
                cacheInst[i][j] = -1;
			}
		}

		this.phaser1 = phaser1;
		this.phaser2 = phaser2;
		this.phaserIF_ID = phaserIF_ID;

		/*this.phaser1.register();
		this.phaser2.register();
		this.phaserIF_ID.register();
		//System.out.println( phaser1.getRegisteredParties() );
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
		//TODO hacer lo respectivo.
		
		int bloque, posCacheI, numPalabra;

		MemoriaPrincipal memoriaPrincipal = MemoriaPrincipal.getInstancia();

		
		bloque = pc  / 16;
		numPalabra = pc % 16;
		posCacheI = bloque % 4;



	
		//Revisar si el bloque está en la CacheI y si no está traerlo
		if(bloque != cacheInst[posCacheI][16]){


			int bloqueMem = pc / 16;
			int bloqueEnMemoria = bloqueMem - 24;

			
			System.out.println("bloqueEnMemoria: " + bloqueEnMemoria);
			System.out.println("posCacheI: " + posCacheI);
			System.arraycopy(memoriaPrincipal.memInstrucciones[numPalabra].palabra, 0, cacheInst[posCacheI], 0, 16);

			

			for (int x = 0; x < 4; x++) { //Cada instruccion la coloca en el IR y la ejecuta con el metodo ALU.
                IR[x] = cacheInst[posCacheI][numPalabra];
                pc++;
                numPalabra++;
			}
			
			
			

			//super.barreraIF();
		
			phaser1.arriveAndAwaitAdvance();
		
			phaserIF_ID.arriveAndAwaitAdvance();
			
			super.reg_IF_ID.ir = IR;
			super.reg_IF_ID.npc = pc;

			
			phaser2.arriveAndAwaitAdvance();
		
	
		
			imprimirCache();


			phaser1.arriveAndDeregister();
			phaserIF_ID.arriveAndDeregister();
			phaser2.arriveAndDeregister();
		}


		
	}



	/** Metodo que imprime los valores que hay en la cache. */
	public void imprimirCache() {
		StringBuilder cd = new StringBuilder();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 17; j++) {
				cd.append(cacheInst[i][j]).append("   ");
			}
			cd.append("\n");
		}
		System.out.println(cd);
	}


}
