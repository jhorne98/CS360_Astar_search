package astar;

//import java.util.*;

public class Edge {
	private double weight;
	private Node vertex;
	
	public Edge(double weight, Node vertex) {
		this.weight = weight;
		this.vertex = vertex;
	}
	
	public Edge(Node vertex) {
		weight = 1.0;
		this.vertex = vertex;
	}
	
	// getters
	public double getWeight() {
		return weight;
	}
	
	public Node getVertex() {
		return vertex;
	}
	
	// edge values do not change during computation: no need for setters
}
