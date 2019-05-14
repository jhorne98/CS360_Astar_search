package astar;

import java.util.*;

public class Node {
	private double f;
	private double g;
	private double h;
	
	private Node pi;
	
	private ArrayList<Edge> neighbors;
	
	private String name;
	
	private int block;
	private boolean checked;
	
	public Node() {
		f = Double.MAX_VALUE;
		g = Double.MAX_VALUE;
		neighbors = new ArrayList<Edge>();
		pi = null;
		block = 0;
	}
	
	public Node(String name) {
		f = Double.MAX_VALUE;
		g = Double.MAX_VALUE;
		neighbors = new ArrayList<Edge>();
		pi = null;
		this.name = name;
		block = 0;
	}
	
	// getters
	public double getF() {
		return f;
	}
	
	public double getG() {
		return g;
	}
	
	public double getH() {
		return h;
	}
	
	public ArrayList<Edge> getNeighbors() {
		return neighbors;
	}
	
	public String getName() {
		return name;
	}
	
	public Node getPi() {
		return pi;
	}
	
	public int getBlock() {
		return block;
	}
	
	public boolean getChecked() {
		return checked;
	}
	
	// setters
	public void setF(double f) {
		this.f = f;
	}
	
	public void setG(double g) {
		this.g = g;
	}
	
	public void setH(double h) {
		this.h = h;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPi(Node pi) {
		this.pi = pi;
	}
	
	public void setBlock(int block) {
		this.block = block;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	// add a neighbor
	public void addNeighbor(Edge in) {
		neighbors.add(in);
	}
}
