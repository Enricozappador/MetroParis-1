package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

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
		
		System.out.format("Grafo caricato con %d vertici %d archi", this.graph.vertexSet().size(), this.graph.edgeSet().size()); 
		
		
	}
	
	public List<Fermata> VisitaAmpiezza(Fermata source) { 
		List<Fermata> visita = new ArrayList<>(); 
		
		GraphIterator<Fermata, DefaultEdge> bfv = new BreadthFirstIterator<>(graph, source);  
		while (bfv.hasNext()) {
			visita.add(bfv.next()); 
		}
		return visita; 
	}
	public List<Fermata> VisitaProfondità(Fermata source) { 
		List<Fermata> visita = new ArrayList<>(); 
		
		GraphIterator<Fermata, DefaultEdge> dfv = new DepthFirstIterator<>(graph, source);  
		while (dfv.hasNext()) {
			visita.add(dfv.next()); 
		}
		return visita; 
	}
	
	public Map<Fermata,Fermata> alberodiVista (Fermata source){
		Map<Fermata, Fermata> albero = new HashMap<>(); 
		albero.put(source, null); 
		
		GraphIterator<Fermata, DefaultEdge> bfv = new BreadthFirstIterator<>(graph, source); 
		bfv.addTraversalListener(new TraversalListener<Fermata, DefaultEdge>(){
			public void vertexTraversed(VertexTraversalEvent<Fermata> e) {
				
			}

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
								
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				DefaultEdge edge  = e.getEdge();
				Fermata a = graph.getEdgeSource(edge); 
				Fermata b = graph.getEdgeTarget(edge); 
				if(albero.containsKey(a)) {
					albero.put(b, a); 
				}
				else {
					albero.put(a, b); 
				}
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Fermata> e) {
								
			}
		});
		
		
		while(bfv.hasNext()) {
			bfv.next(); 
			
		}
		
		return albero; 
		
		
	}
	public static void main(String args[]) {
		Model m = new Model(); 
		
		List<Fermata> visita = m.VisitaAmpiezza(m.fermate.get(0)); 
		System.out.println(visita);
		List<Fermata> visita1 = m.VisitaProfondità(m.fermate.get(0)); 
		System.out.println(visita1);
		
		Map<Fermata, Fermata> albero = m.alberodiVista(m.fermate.get(0)); 
		for(Fermata f : albero.keySet()) {
			System.out.format("%s -> %s\n", f, albero.get(f)); 
		}
	}

}
