package astar;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

class TestSimpleGraph {
	private static Node s;
	private static Node a;
	private static Node b;
	private static Node c;
	private static Node d;
	private static Node e;
	private static Node t;
	
	private static Edge s_a;
	private static Edge a_b;
	private static Edge b_c;
	private static Edge s_d;
	private static Edge d_e;
	private static Edge c_t;
	private static Edge e_t;
	
	private static AStar as;
	private static ArrayList<Node> traceList;

	@BeforeAll
	static void setup() {
		as = new AStar();
		traceList = new ArrayList<Node>();
		
		// wikipedia example
		s = new Node();
		s.setName("s");
		s.setH(5.0);
		s.setF(s.getH());
		
		a = new Node();
		a.setName("a");
		a.setH(4.0);

		s_a = new Edge(1.5, a);
		s.getNeighbors().add(s_a);

		b = new Node();
		b.setName("b");
		b.setH(2.0);
		
		a_b = new Edge(2.0, b);
		a.getNeighbors().add(a_b);
		
		c = new Node();
		c.setName("c");
		c.setH(4.0);
		
		b_c = new Edge(3.0, c);
		b.getNeighbors().add(b_c);
		
		d = new Node();
		d.setName("d");
		d.setH(4.5);
		
		s_d = new Edge(2.0, d);
		s.getNeighbors().add(s_d);
		
		e = new Node();
		e.setName("e");
		e.setH(2.0);
		
		d_e = new Edge(3.0, e);
		d.getNeighbors().add(d_e);
		
		t = new Node();
		t.setName("t");
		s.setH(0.0);

		c_t = new Edge(4, t);
		c.getNeighbors().add(c_t);
		
		e_t = new Edge(2.0, t);
		e.getNeighbors().add(e_t);
	}
	
	@Test
	void testSimpleGraph() {
		double shortestPath = as.aStar(s, t, traceList);
		assertEquals(7.0, shortestPath);
	}

}
