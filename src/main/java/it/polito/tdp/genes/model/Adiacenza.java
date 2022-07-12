package it.polito.tdp.genes.model;

public class Adiacenza {
	private String l1;
	private String l2;
	private int peso;
	public Adiacenza(String l1, String l2, int peso) {
		super();
		this.l1 = l1;
		this.l2 = l2;
		this.peso = peso;
	}
	public String getL1() {
		return l1;
	}
	public String getL2() {
		return l2;
	}
	public int getPeso() {
		return peso;
	}
	
	
	
}