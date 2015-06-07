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
		DirectedGraph d2 = new DirectedGraph("AC7, CE4, AE2, CA10, DB9, EB1, DE5, CB3, BA5, EC3");
		
		assertTrue(d1.getTrips('C', 'C', 3, true) == 2);
		assertTrue(d1.getTrips('A', 'C', 4, true) == 6);
		assertTrue(d1.getTrips('D', 'B', 2, true) == 1);
		assertTrue(d1.getTrips('D', 'B', 1, true) == 0);
		assertTrue(d1.getTrips('A', 'A', 4, true) == 0);
		assertTrue(d1.getTrips('D', 'B', 1, true) == 0);
		assertTrue(d1.getTrips('B', 'C', 1, true) == 1);
		assertTrue(d1.getTrips('B', 'C', 2, true) == 1);
		assertTrue(d1.getTrips('B', 'C', 3, true) == 2);
		assertTrue(d1.getTrips('B', 'C', 4, true) == 3);
		assertTrue(d1.getTrips('B', 'C', 5, true) == 5);
		assertTrue(d2.getTrips('C', 'C', 3, true) == 4);
		assertTrue(d2.getTrips('D', 'B', 2, true) == 2);
	}
	
	@Test
	public void testGetTripsExact() {
		DirectedGraph d1 = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		
		assertTrue(d1.getTrips('A', 'C', 4, false) == 3);
		assertTrue(d1.getTrips('D', 'B', 1, false) == 0);
		assertTrue(d1.getTrips('D', 'B', 2, false) == 1);
		assertTrue(d1.getTrips('D', 'B', 3, false) == 1);
		assertTrue(d1.getTrips('D', 'B', 4, false) == 1);
		assertTrue(d1.getTrips('D', 'B', 5, false) == 2);
		
	}
	
	@Test
	public void testGetShortest() {
		DirectedGraph d1 = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		
		assertTrue(d1.getShortestRoute('A', 'C') == 9);
		assertTrue(d1.getShortestRoute('B', 'B') == 9);
		assertTrue(d1.getShortestRoute('C', 'C') == 9);
		assertTrue(d1.getShortestRoute('C', 'E') == 2);
		assertTrue(d1.getShortestRoute('A', 'B') == 5);
		assertTrue(d1.getShortestRoute('B', 'D') == 12);
	}
	
	@Test
	public void testNumRoutes() {
		DirectedGraph d1 = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		
		System.out.println("TEST " + d1.getNumRoutes('C', 'C', 30));
		
		assertTrue(d1.getNumRoutes('C', 'C', 30) == 7);
	}
	

}
