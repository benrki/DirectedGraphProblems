import static org.junit.Assert.*;

import org.junit.Test;


public class DirectedGraphTest {

	@Test
	public void graphCreation() {
		DirectedGraph d1 = new DirectedGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		System.out.println(d1);
		
		DirectedGraph d2 = new DirectedGraph("AB2, BC12, CD8, DC3, DE6, AD5, CE2, EB8, AE4, CE12, XY7");
		System.out.println(d2);
	}

}
