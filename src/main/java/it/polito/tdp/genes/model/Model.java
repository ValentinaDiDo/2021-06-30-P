package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	GenesDao dao;
	Graph<String, DefaultWeightedEdge> grafo;
	List<String> localizations ;
	List<Adiacenza> adiacenze;
	
	List<String>best;
	int pesoBest;
	
 	public Model() {
 		this.dao = new GenesDao();
 	}
 	
 	public void creaGrafo() {
 		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
 		
 		this.localizations = this.dao.getLocalizations();
 		
 		Graphs.addAllVertices(this.grafo, this.localizations);
 		
 		this.adiacenze = this.dao.getAdiacenze();
 		
 		for(Adiacenza a : adiacenze) {
 			Graphs.addEdge(this.grafo, a.getL1(), a.getL2(), a.getPeso());
 		}
 	}

	public Graph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<String> getLocalizations() {
		return localizations;
	}
 	
	public void cercaPercorso(String partenza) {
		List<String> daVisitare = new ArrayList<>(Graphs.neighborListOf(this.grafo, partenza));
		
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		this.best = new ArrayList<>();
		this.pesoBest = 0;
		
		int peso = 0;
		
		ricercaRicorsiva(parziale, daVisitare, peso, partenza);
	
	}


	public void ricercaRicorsiva(List<String> parziale, List<String> daVisitare, int peso, String precedente ) {
		
		if(peso > this.pesoBest) {
			this.best = new ArrayList<>();
			this.best.addAll(parziale);
			this.pesoBest = peso;
			System.out.println("Trovata soluzione: "+pesoBest);
			System.out.println(best.toString());
			
		}
		
		for(String vertice : daVisitare) {
			if(!parziale.contains(vertice)) {
				parziale.add(vertice);
				
				int aggiunto = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(vertice, precedente));
				ricercaRicorsiva(parziale, Graphs.neighborListOf(this.grafo, vertice), peso + aggiunto, vertice);
				
				parziale.remove(vertice);
			}
		}
	}

	public List<String> getBest() {
		return best;
	}

	public int getPesoBest() {
		return pesoBest;
	}
	
	
}