package it.polito.tdp.metroparis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph <Fermata, DefaultEdge> graph; 
	private List<Fermata> fermate; 
	private Map<Integer, Fermata> fermateIdMap; 
	
	public Model() { 
		this.graph = new SimpleDirectedGraph<>(DefaultEdge.class); 
		MetroDAO dao = new MetroDAO(); 
		this.fermate = dao.getAllFermate(); 
		this.fermateIdMap = new HashMap<>(); 
		
		for(Fermata f : this.fermate ) {
			fermateIdMap.put(f.getIdFermata(), f); 
		}
		
		Graphs.addAllVertices(graph, this.fermate); 
		
		/*for (Fermata fp : this.fermate) {
			for(Fermata fa : this.fermate) {
				if(dao.fermateConnesse(fp, fa)) {
					this.graph.addEdge(fp, fa);
				}
			}
		}
		
		SECONDO METODO 
		for(Fermata fp : this.fermate) {
		List<Fermata> connesse = dao.fermateSuccessive(fp, fermateIdMap); 
		
		for (Fermata fa : connesse) {
			this.graph.addEdge(fp, fa); 
			
		}
		} */
		
		// 3 metodo
		
		List<CoppieFermate> coppie = dao.coppieFermate(fermateIdMap); 
		for(CoppieFermate c : coppie){
			this.graph.addEdge(c.getFp(), c.getFp()); 
		}
		
		
	}

}
