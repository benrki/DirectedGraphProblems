import static org.junit.Assert.*;

import org.junit.Test;


public class DirectedGraphTest {

	@Test
	public void testDirectedGraph() {
		DirectedGraph d1 = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		//System.out.println(d1);
		
		DirectedGraph d2 = new DirectedGraph("AB2, BC12, CD8, DC3, DE6, AD5, CE2, EB8, AE4, CE12, XY7");
		//System.out.println(d2);
		
		DirectedGraph d3 = new DirectedGraph("ZL6, LE3, KM8, TY4, XY5, YZ3, OI5, XM9, EK1, ZO4");
		//System.out.println(d3);
	}

	@Test
	public void testGetDistance() {
		DirectedGraph d1 = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		
		assertTrue(d1.getDistance("A-B-C") == 9);
		assertTrue(d1.getDistance("A-D") == 5);
		assertTrue(d1.getDistance("A-D-C") == 13);
		assertTrue(d1.getDistance("A-E-B-C-D") == 22);
		assertTrue(d1.getDistance("A-E-D") == -1);

	}
	
	@Test
	public void testGetTrips() {
		DirectedGraph d1 = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		
		assertTrue(d1.getTrips('C', 'C', 3) == 2);
		assertTrue(d1.getTrips('D', 'B', 2) == 1);
		assertTrue(d1.getTrips('D', 'B', 1) == 0);
		assertTrue(d1.getTrips('A', 'A', 4) == 0);
		assertTrue(d1.getTrips('D', 'B', 1) == 0);
		assertTrue(d1.getTrips('B', 'C', 1) == 1);
		assertTrue(d1.getTrips('B', 'C', 3) == 2);
		assertTrue(d1.getTrips('B', 'C', 4) == 2);
		assertTrue(d1.getTrips('B', 'C', 5) == 3);
	}
	
	

}
