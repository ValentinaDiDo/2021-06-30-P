package it.polito.tdp.genes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.genes.model.Model;
import it.polito.tdp.genes.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	Graph<String, DefaultWeightedEdge> grafo;
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStatistiche;

    @FXML
    private Button btnRicerca;

    @FXML
    private ComboBox<String> boxLocalizzazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRicerca(ActionEvent event) {
    	if(this.grafo == null) {
    		txtResult.setText("ASPETTA LA CREAZIONE DEL GRAFO");
    	}else {
    		if(this.boxLocalizzazione.getValue().equals(null)) {
    			txtResult.appendText("\nSCEGLI UNA LOCALIZZAZIONE");
    		}else {
    			txtResult.setText("ricerca ricorsiva in corso...");
    			this.model.cercaPercorso(this.boxLocalizzazione.getValue());
    			
    			List<String> best = this.model.getBest();
    			int peso = this.model.getPesoBest();
    			
    			for(String s : best) {
    				txtResult.appendText("\n"+s);
    			}
    			txtResult.appendText("\npeso totale :"+peso);
    		
    		}
    	
    	}
    }

    @FXML
    void doStatistiche(ActionEvent event) {
    	if(this.grafo == null) {
    		txtResult.setText("ASPETTA LA CREAZIONE DEL GRAFO");
    	}else {
    		if(this.boxLocalizzazione.getValue().equals(null)) {
    			txtResult.appendText("\nSCEGLI UNA LOCALIZZAZIONE");
    		}else {
    			String scelto = this.boxLocalizzazione.getValue();
    			//CERCO I VICINI
    			txtResult.setText("CERCO I VICINI DI :"+scelto+"\n");
    			List<Vicino> vicini = new ArrayList<>();
    			for(String l : Graphs.neighborListOf(this.grafo, scelto)) {
    				int peso = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(l, scelto));
    				Vicino v = new Vicino(l, peso);
    				vicini.add(v);
    			}
    			
    			for(Vicino v : vicini) {
    				txtResult.appendText("\n"+v.toString());
    			}
    		}
    	}
    }

    @FXML
    void initialize() {
        assert btnStatistiche != null : "fx:id=\"btnStatistiche\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxLocalizzazione != null : "fx:id=\"boxLocalizzazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		this.model.creaGrafo();
		this.grafo = this.model.getGrafo();
		txtResult.setText("grafo creato");
		txtResult.appendText("\nvertici: "+this.grafo.vertexSet().size());
		txtResult.appendText("\narchi: "+this.grafo.edgeSet().size());
		
		this.boxLocalizzazione.getItems().clear();
		this.boxLocalizzazione.getItems().addAll(this.model.getLocalizations());
	}
}
