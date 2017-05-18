package connectsix;

/**
 *@author Tom Wittal
 */

public class Connect6 extends core.GameModel{

	public String title;

	private int[][] brett;
	private int aktiverSpieler;
	private int spielerZüge;
	private final int reihe = 6;
	
	
	public Connect6(){
		title = "Connect Six";
		viewPanel = new StartPanel(this);
	}

	public void start(int g){
		if(g < 6) g = 6;
		if(g > 20) g = 20;

		brett = new int[g][g];
		for(int i = 0; i < g; i++) {
			for(int j = 0; j < g; j++) {
				brett[i][j] = 0;
			}
		}

		aktiverSpieler = 1;
		spielerZüge = 1;

		viewPanel = new GUI(g, this);
	}
	
	public int gibLänge(){
		return brett.length;
	}
	
	public int gibSpieler(){
		return aktiverSpieler;
	}
	

	public int gibFeldStatus(int x, int y){
		return brett[x][y];
	}
	
	public boolean prüfe(){
		//waagerecht
		for(int x = 0; x < gibLänge(); x++){
			for(int y = 0; y + reihe <= gibLänge(); y++){
				boolean gefunden = true;
				for(int i = 0; i < reihe; i++){
					if(brett[x][y+i] != aktiverSpieler) gefunden = false;
				}
				if(gefunden) return true;
			}
		}
		
		//senkrecht
		for(int x = 0; x + reihe <= gibLänge(); x++){
			for(int y = 0; y < gibLänge(); y++){
				boolean gefunden = true;
				for(int i = 0; i < reihe; i++){
					if(brett[x+i][y] != aktiverSpieler) gefunden = false;
				}
				if(gefunden) return true;
			}
		}
		
		//diagonal [\]
		for(int x = 0; x + reihe <= gibLänge(); x++){
			for(int y = 0; y  + reihe <= gibLänge(); y++){
				boolean gefunden = true;
				for(int i = 0; i < reihe; i++){
					if(brett[x+i][y+i] != aktiverSpieler) gefunden = false;
				}
				if(gefunden) return true;
			}
		}
		
		//diagonal [/]
		for(int x = 0; x + reihe <= gibLänge(); x++){
			for(int y = gibLänge() - 1; y  >= reihe; y--){
				boolean gefunden = true;
				for(int i = 0; i < reihe; i++){
					if(brett[x+i][y-i] != aktiverSpieler) gefunden = false;
				}
				if(gefunden) return true;
			}
		}
		
		
		return false;
	}
	
	public boolean prüfeSpielfeldVoll(){
		boolean voll = true;
		for(int x = 0; x < gibLänge(); x++){
			for(int y = 0; y < gibLänge(); y++){
				if(brett[x][y] == 0) voll = false;
			}
		}
		
		return voll;
	}
	
	
	public boolean setzeFeld(int x, int y){
		if(brett[x-1][y-1] == 0) {
			brett[x-1][y-1] = aktiverSpieler;
			return true;
		}
		return false;
	}
	
	public void spielerwechsel(){
		spielerZüge--;
		if(spielerZüge <= 0){
		
			if(aktiverSpieler == 1) {
				aktiverSpieler = 2;
				spielerZüge = 2;
			}
			else {
				aktiverSpieler = 1;
				spielerZüge = 2;
			}
			
		}
	}


	@Override
	public String getGameTitle() {
		return title;
	}
	
	
}




