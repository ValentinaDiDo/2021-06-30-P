package it.polito.tdp.genes.model;

public class Vicino {
	String localizzazione;
	int peso;
	
	public Vicino(String localizzazione, int peso) {
		super();
		this.localizzazione = localizzazione;
		this.peso = peso;
	}
	
	public String getLocalizzazione() {
		return localizzazione;
	}
	public int getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return this.localizzazione + " ("+this.peso+")";
	}
	
}
