package it.polito.tdp.metroparis.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph <Fermata, DefaultEdge> graph; 
	private List<Fermata> fermate; 
	
	public Model() { 
		this.graph = new SimpleDirectedGraph<>(DefaultEdge.class); 
		MetroDAO dao = new MetroDAO(); 
		this.fermate = dao.getAllFermate(); 
		
		Graphs.addAllVertices(graph, this.fermate); 
		
		for (Fermata fp : this.fermate) {
			for(Fermata fa : this.fermate) {
				if(dao.fermateConnesse(fp, fa)) {
					this.graph.addEdge(fp, fa);
				}
			}
		}
		
		
		
	}

}
