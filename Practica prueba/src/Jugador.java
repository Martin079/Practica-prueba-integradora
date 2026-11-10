

import java.util.ArrayList;
import torres.Torre;

public class Jugador {

	private int monedas;
	private ArrayList<Torre> torres;
	
	public Jugador(){
		 this.monedas=10;
		 this.torres= new ArrayList<>();
	}

	
	public int getMonedas() {
		return monedas;
	}

	public ArrayList<Torre> getTorres() {
		return torres;
	}
	
	
	public void agregarMonedas(int cantidad){
		this.monedas+=cantidad;		
	}
	
	public boolean restarMonedas(int cantidad){
		int monedasRestantes = this.monedas - cantidad;
	    
	    if (monedasRestantes < 0) {
	        return false; 
	    }
	    
	    this.monedas = monedasRestantes;
	    return true;	
	}
	
	public void agregarTorre(Torre torre){
		this.torres.add(torre);
	}
}
