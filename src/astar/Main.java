package astar;

import java.util.*;
import java.io.*;

public class Main {
	public static final boolean DEBUG = true;
	
	public static void main(String[] args) {
		ArrayList<Node> traceList = new ArrayList<Node>();

		// these lambdas are so I can test these on the fly while only modifying main
		LambdaHeuristic euclidean = (i, j, endRow, endCol) -> {return Math.sqrt(Math.pow(i - endRow, 2) + Math.pow(j - endCol, 2));};
		// using Chebychev distance
		LambdaHeuristic diagonal = (i, j, endRow, endCol) -> {return Math.max(Math.abs(i - endRow), Math.abs(j - endCol));};
		// using octile distance
		LambdaHeuristic diagonalOct = (i, j, endRow, endCol) -> {return Math.max(Math.abs(i - endRow), Math.abs(j - endCol)) + (1 - Math.sqrt(2.0)) * Math.min(Math.abs(i - endRow), Math.abs(j - endCol));};
		LambdaHeuristic manhattan = (i, j, endRow, endCol) -> {return (Math.abs(i - endRow) + Math.abs(j - endCol));};
		LambdaHeuristic dijkstra = (i, j, endRow, endCol) -> {return 0.0;};
		
		// load information from file
		File file = new File("src/astar/obstacle.txt");
		
		AStar as = new AStar();
		ArrayList<Node> graph;
		
		// benchmarking by size of map, density of map
		//for (int size = 1; size < 151; size++) {
			//for (int density = 0; density <= 20; density+=10) {
			int size = 10;
			int density = 40;
		
				try {
					Scanner sc = new Scanner(file);
					
					// load graph with given heuristic
					graph = as.loadGraph(sc, 0, size, euclidean);
					//graph = as.loadGraphRandom(size, size, 0, size - 1, density, euclidean);
				
					//graph.get((12 - 1) * 12).setF(graph.get((12 - 1) * 12).getH());
					graph.get((size - 1) * size).setF(graph.get((size - 1) * size).getH());
					
					// time and run A*
					long start = System.nanoTime();
					
					// run A*
					// set start and end to always be unblocked
					//graph.get(size - 1).setBlock(0);
					//graph.get((size - 1) * size).setBlock(0);
					
					double shortestPath = as.aStar(graph.get((size - 1) * size), graph.get(size - 1), traceList);
					//double shortestPath = as.aStar(s, t, traceList);
					
					long end = System.nanoTime();
					
					// get the path back from traceList
					Collections.reverse(traceList);
					for (Node n : traceList) {
						System.out.print(n.getName() + "->");
					}
					
					System.out.print("\u001b[0m");
					if (DEBUG) {
						System.out.println("end\nSize: " + size + "^2");
						System.out.println("Density: " + density);
						System.out.println("Shortest path: " + shortestPath);
						System.out.print("Time: ");
					}
						
					System.out.println(end - start);
				
					// print the graph with trace from start to end
					if (DEBUG) {
						for (Node n : graph) {
							if (traceList.contains(n)) {
								System.out.print("\u001b[0m");
								System.out.print("X");
							} else {
								// colored output based on if the node has been evaled
								if (n.getChecked() == true) {
									System.out.print("\u001b[1;91m");
								} else {
									System.out.print("\u001b[0m");
								}
								System.out.print(n.getBlock());
							}
							
							if ((graph.indexOf(n) + 1) % size == 0) {
								System.out.println();
							} else {
								System.out.print(" ");
							}
						}
					}
					
				}catch (FileNotFoundException e) {
					System.err.println(e);
				}
				finally {
					System.gc();
				}
			//}
		//}
	}
}
