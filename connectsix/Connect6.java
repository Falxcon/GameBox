package connectsix;

import javax.swing.JPanel;

/**
 *@author Tom Wittal
 */

public class Connect6 extends core.GameControl{
	
	private int[][] brett;
	private int aktiverSpieler;
	private int spielerZ�ge;
	private final int reihe = 6;
	
	
	public Connect6(int g){
		if(g < 6) g = 6;
		if(g > 20) g = 20;
		
		brett = new int[g][g];
		for(int i = 0; i < g; i++) {
			for(int j = 0; j < g; j++) {
				brett[i][j] = 0;
			}
		}
		
		aktiverSpieler = 1;
		spielerZ�ge = 1;
	}
	
	public int gibL�nge(){
		return brett.length;
	}
	
	public int gibSpieler(){
		return aktiverSpieler;
	}
	

	public int gibFeldStatus(int x, int y){
		return brett[x][y];
	}
	
	public boolean pr�fe(){
		//waagerecht
		for(int x = 0; x < gibL�nge(); x++){
			for(int y = 0; y + reihe <= gibL�nge(); y++){
				boolean gefunden = true;
				for(int i = 0; i < reihe; i++){
					if(brett[x][y+i] != aktiverSpieler) gefunden = false;
				}
				if(gefunden) return true;
			}
		}
		
		//senkrecht
		for(int x = 0; x + reihe <= gibL�nge(); x++){
			for(int y = 0; y < gibL�nge(); y++){
				boolean gefunden = true;
				for(int i = 0; i < reihe; i++){
					if(brett[x+i][y] != aktiverSpieler) gefunden = false;
				}
				if(gefunden) return true;
			}
		}
		
		//diagonal [\]
		for(int x = 0; x + reihe <= gibL�nge(); x++){
			for(int y = 0; y  + reihe <= gibL�nge(); y++){
				boolean gefunden = true;
				for(int i = 0; i < reihe; i++){
					if(brett[x+i][y+i] != aktiverSpieler) gefunden = false;
				}
				if(gefunden) return true;
			}
		}
		
		//diagonal [/]
		for(int x = 0; x + reihe <= gibL�nge(); x++){
			for(int y = gibL�nge() - 1; y  >= reihe; y--){
				boolean gefunden = true;
				for(int i = 0; i < reihe; i++){
					if(brett[x+i][y-i] != aktiverSpieler) gefunden = false;
				}
				if(gefunden) return true;
			}
		}
		
		
		return false;
	}
	
	public boolean pr�feSpielfeldVoll(){
		boolean voll = true;
		for(int x = 0; x < gibL�nge(); x++){
			for(int y = 0; y < gibL�nge(); y++){
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
		spielerZ�ge--;
		if(spielerZ�ge <= 0){
		
			if(aktiverSpieler == 1) {
				aktiverSpieler = 2;
				spielerZ�ge = 2;
			}
			else {
				aktiverSpieler = 1;
				spielerZ�ge = 2;
			}
			
		}
	}

	
	
	
	@Override
	public JPanel getPanel() {
		JPanel panel = new JPanel();
		
		
		return panel;
	}

	@Override
	public int getUpdateInterval() {
		return 1000;
	}
	
	
}
