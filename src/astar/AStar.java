package astar;

import java.util.*;
//import java.io.*;

// really doesn't need to be its own class, but it works
public class AStar {
	public AStar() {
		
	}
	
	// The heuristics:
	// These have been anonymized: moved to interface LambdaHeuristic
	// Euclidian distance (direct)
	@SuppressWarnings("unused")
	private double euclidean_distance (int i, int j, int endRow, int endCol) {
		return Math.sqrt(Math.pow(i - endRow, 2) + Math.pow(j - endCol, 2));
	}

	// diagonal distance
	@SuppressWarnings("unused")
	private double diagonal_distance (int i, int j, int endRow, int endCol) {
		return Math.max(Math.abs(i - endRow), Math.abs(j - endCol));
	}

	// Manhattan distance
	// works correctly when only able to go n,s,e,w
	@SuppressWarnings("unused")
	private double manhattan_distance (int i, int j, int endRow, int endCol) {
		return (Math.abs(i - endRow) + Math.abs(j - endCol));
	}
	
	// load the graph of Nodes from array of ints created from given length
	public ArrayList<Node> loadGraphRandom(int rows, int cols, int endRow, int endCol, int density, LambdaHeuristic l) {
		// the graph
		ArrayList<Node> graph = new ArrayList<Node>();
		
		Random rand = new Random();
		
		Node temp;
		
		// populate graph array with nodes
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				//index = (i * cols) + j;
				temp = new Node(i + "_" + j);
				
				// set the block randomly based on density parameter
				temp.setBlock(rand.nextInt(101) > density ? 0 : 1);
				
				graph.add(temp);
				
				//System.out.println(graph.get(graph.indexOf(temp)).getBlock());
				
				// precompute heuristic
				graph.get(graph.indexOf(temp)).setH(l.distance(i, j, endRow, endCol));
			}
		}
		
		Edge edgeTemp;
		int index;
		int edgeIndex;
		
		// this is not the best way to do this, but it worked in c++
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				index = (i * cols) + j;

				// check surroundings
				for (int curRow = i - 1; curRow <= i + 1; curRow++) {
					for (int curCol = j - 1; curCol <= j + 1; curCol++) {
						edgeIndex = (curRow * cols) + curCol;	

						if (index != edgeIndex && 
							curRow >= 0 && curRow < rows &&
							curCol >= 0 && curCol < cols &&
							graph.get(index).getBlock() != 1 &&
							graph.get(edgeIndex).getBlock() != 1 &&
							edgeIndex >= 0 && edgeIndex < (rows * cols)) {

							edgeTemp = new Edge(graph.get(edgeIndex));
							graph.get(index).addNeighbor(edgeTemp);
						}
					}
				}
				
				// TODO: Manhattan: only nsew
				/*if (i >= 1 && i < rows - 1 &&
					graph.get(index).getBlock() != 1) {
					
					edgeTemp = new Edge(graph.get(((i - 1) * cols) + j));
					graph.get(index).addNeighbor(edgeTemp);
					edgeTemp = new Edge(graph.get(((i + 1) * cols) + j));
					graph.get(index).addNeighbor(edgeTemp);
					edgeTemp = new Edge(graph.get((i * cols) + (j - 1)));
					graph.get(index).addNeighbor(edgeTemp);
					edgeTemp = new Edge(graph.get((i * cols) + j + 1));
					graph.get(index).addNeighbor(edgeTemp);
					
				}
				
				if (j >= 1 && i < rows - 1 &&
					graph.get(index).getBlock() != 1) {
					
				}*/
			}
		}
		
		// TODO: Fix this
		// start and end always open
		graph.get(cols - 1).setBlock(0);
		graph.get((rows - 1) * cols).setBlock(0);
		
		if (rows > 2) {
			// set end edges
			if (graph.get(cols - 2).getBlock() == 0) {
				edgeTemp = new Edge(graph.get(cols - 2));
				graph.get(cols - 1).addNeighbor(edgeTemp);
			}
			
			if (graph.get(cols + cols - 2).getBlock() == 0) {
				edgeTemp = new Edge(graph.get((2 * cols) + cols - 2));
				graph.get(cols - 1).addNeighbor(edgeTemp);
			}
			
			if (graph.get(cols + cols - 1).getBlock() == 0) {
				edgeTemp = new Edge(graph.get((2 * cols) + cols - 1));
				graph.get(cols - 1).addNeighbor(edgeTemp);
			}
			
			// set start edges
			if (graph.get((rows - 2) * cols).getBlock() == 0) {
				edgeTemp = new Edge(graph.get((rows - 2) * cols));
				graph.get((rows - 1) * cols).addNeighbor(edgeTemp);
			}
			
			if (graph.get((rows - 2) * cols + 1).getBlock() == 0) {
				edgeTemp = new Edge(graph.get((rows - 2) * cols + 1));
				graph.get((rows - 1) * cols).addNeighbor(edgeTemp);
			}
			
			if (graph.get((rows - 1) * cols + 1).getBlock() == 0) {
				edgeTemp = new Edge(graph.get((rows - 1) * cols + 1));
				graph.get((rows - 1) * cols).addNeighbor(edgeTemp);
			}
		}
		
		return graph;
	}
	
	// load the graph of Nodes from scanner to file
	public ArrayList<Node> loadGraph(Scanner sc, int endRow, int endCol, LambdaHeuristic l) {
		// rows and columns from the top of the line
		String line = sc.nextLine();
		String[] tokens = line.split(" ");
		int rows = Integer.parseInt(tokens[0]);
		int cols = Integer.parseInt(tokens[1]);
		
		// the graph
		ArrayList<Node> graph = new ArrayList<Node>();
				
		//System.out.println(rows + " " + cols);
		
		//int index;
		Node temp;
		
		// populate graph array with nodes
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				//index = (i * cols) + j;
				temp = new Node(i + "_" + j);
				temp.setBlock(sc.nextInt());
				
				graph.add(temp);
				
				//System.out.println(graph.get(graph.indexOf(temp)).getBlock());
				
				// precompute heuristic
				graph.get(graph.indexOf(temp)).setH(l.distance(i, j, endRow, endCol));
			}
		}
		
		Edge edgeTemp;
		int index;
		int edgeIndex;
		
		// this is not the best way to do this, but it worked in c++
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				index = (i * cols) + j;

				// check surroundings
				for (int curRow = i - 1; curRow <= i + 1; curRow++) {
					for (int curCol = j - 1; curCol <= j + 1; curCol++) {
						edgeIndex = (curRow * cols) + curCol;	

						if (index != edgeIndex && 
							curRow >= 0 && curRow < rows &&
							curCol >= 0 && curCol < cols &&
							graph.get(index).getBlock() != 1 &&
							edgeIndex >= 0 && edgeIndex < (rows * cols)) {

							edgeTemp = new Edge(graph.get(edgeIndex));
							graph.get(index).addNeighbor(edgeTemp);
						}
					}
				}
			}
		}
		
		return graph;
	}
	
	public double aStar(Node start, Node goal, ArrayList<Node> traceList) {
		// the set of currently discovered nodes yet to be evaluated
		/*PriorityQueue<Node> openSet = new PriorityQueue<Node>(new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Double.compare(node0.getF(), node1.getF());
            }
		}));*/
		
		// priority queue was acting odd: this works
		ArrayList<Node> openSet = new ArrayList<Node>();
		
		// set of evaluated nodes
		ArrayList<Node> closedSet = new ArrayList<Node>();
		
		start.setG(0.0);
		// modify start f to h in main
		//start.setF(start.getH());
		
		//System.out.println(start.getF());
		
		openSet.add(start);
		
		Node current;
		Node trace;
		double tempGScore;
		
		// evaluate while the openSet still has elements
		while(!openSet.isEmpty()) {
			current = openSet.get(0);
			
			// this works much better than priority queue
			for (Node n : openSet) {
				if (n.getF() < current.getF()) {
					current = n;
				}
				
				//System.out.print(n.getF() + ", ");
			}
			//System.out.println();
			
			//current = openSet.poll();
			//System.out.println("pulled: " + current.getF());

			// goal has been reached, return the shortest path
			if (current == goal) {
				// print the shortest path through graph
				trace = goal;
				while (trace.getPi() != null) {
					traceList.add(trace);
					trace = trace.getPi();
				}
				
				traceList.add(trace);
				
				// total distance of shortest path
				return current.getF();
			}
			
			//openSet.remove();
			openSet.remove(current);
			closedSet.add(current);
			
			// iterate over each neighbor of the current node
			for (Edge e : current.getNeighbors()) {
				// if neighbor is in the set of closed nodes: do not iterate
				if (closedSet.contains(e.getVertex())) {
					continue;
				}
				
				// tentative g score
				tempGScore = current.getG() + e.getWeight();
				
				// if neighbor is not in the openSet, push it on
				// else if temp g >= neighbor's g, done with neighbor
				if (!openSet.contains(e.getVertex())) {
					e.getVertex().setChecked(true);
					openSet.add(e.getVertex());
				} else if (tempGScore >= e.getVertex().getG()) {
					continue;
				}
				
				// path is designated as best: set it
				e.getVertex().setPi(current);
				e.getVertex().setG(tempGScore);
				
				// add the heuristic
				e.getVertex().setF(e.getVertex().getG() + e.getVertex().getH());
			}
		}
		
		// failed to find the end
		return 0;
	}
}
