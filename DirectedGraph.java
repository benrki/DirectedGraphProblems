import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Various directed graph problems.
 * 07/06/15
 * 
 * 
 * @author Benjamin Ki
 *
 */
public class DirectedGraph {
	private int[][] adjMat = null;
	private ArrayList<Character> nodes = new ArrayList<Character>();
	// Arbitrarily high number representing infinity
	// Required for Djikstra's algorithm
	private static final int INFINITY = 99999;
	// PriorityQueues require a default initial capacity
	// when using comparators
	private static final int QUEUE_INIT = 11;

	public static void main(String[] args) throws IOException {
		Scanner inputScanner = new Scanner(System.in);
		String line = null;

		System.out.println("Please enter a space separated list of vertices/edges.");
		
		// Read input
		while(!(line = inputScanner.nextLine()).isEmpty()) {
			if (line.matches("[A-z]{2}[0-9]+.*")) {
				DirectedGraph dg = new DirectedGraph(line);
	
				// Run questions
				System.out.println("Q1. The distance of the route A-B-C.");
				System.out.println("A: " + dg.getDistance("A-B-C"));
	
				System.out.println("Q2. The distance of the route A-D.");
				System.out.println("A: " + dg.getDistance("A-D"));
	
				System.out.println("Q3. The distance of the route A-D-C.");
				System.out.println("A: " + dg.getDistance("A-D-C"));
	
				System.out.println("Q4. The distance of the route A-E-B-C-D.");
				System.out.println("A: " + dg.getDistance("A-E-B-C-D"));
	
				System.out.println("Q5. The distance of the route A-E-D.");
				System.out.println("A: " + dg.getDistance("A-E-D"));
	
				System.out.println("Q6. The number of trips starting at C and ending at C with a maximum of 3 stops.");
				System.out.println("A: " + dg.getTrips('C', 'C', 3, true));
	
				System.out.println("Q7. The number of trips starting	 at A and ending at C with exactly 4 stops.");
				System.out.println("A: " + dg.getTrips('A', 'C', 3, false));
	
				System.out.println("Q8. The length of the shortest route (in terms of distance to travel) from A to C.");
				System.out.println("A: " + dg.getShortestRoute('A', 'C'));
	
				System.out.println("Q9. The length of the shortest route (in terms of distance to travel) from B to B.");
				System.out.println("A: " + dg.getShortestRoute('B', 'B'));
	
				System.out.println("Q10. The number of different routes from C to C with a distance of less than 30.");
				System.out.println("A: " + dg.getNumRoutes('C', 'C', 30));
			}
			System.out.println("\nPlease enter a space separated list of vertices/edges.");
		}
		inputScanner.close();
	}

	public DirectedGraph(String input) {
		String[] inputArray = input.split(", ");

		// Add to our list of nodes if we don't already have them
		for (String s : inputArray) {
			char n1 = s.charAt(0);
			char n2 = s.charAt(1);

			if (!containsNode(n1)) {
				nodes.add(n1);
			}

			if (!containsNode(n2)) {
				nodes.add(n2);
			}
		}

		// Create adjacency matrix representation
		adjMat = new int[nodes.size()][nodes.size()];

		for (String s : inputArray) {
			int n1 = nodes.indexOf(s.charAt(0));
			int n2 = nodes.indexOf(s.charAt(1));
			int distance = Integer.parseInt(s.substring(2)); // substr for 2+ digit distances
			// Update adjacency matrix
			adjMat[n1][n2] = distance;
		}

	}

	/**
	 * Gets the total distance of a given path
	 * 
	 * @param path	input string separated by hyphens
	 * @return the distance as an integer, -1 for
	 * non-existent routes
	 */
	public int getDistance(String path) {
		String[] pathArray = path.split("-");
		int distance = 0;

		for (int i = 0; i < pathArray.length - 1; i++) {
			int el = nodes.indexOf(pathArray[i].charAt(0));
			int nextEl = nodes.indexOf(pathArray[i+1].charAt(0));
			if (el == nextEl || adjMat[el][nextEl] != 0) {
				distance += adjMat[el][nextEl];
			} else {
				System.out.println("NO SUCH ROUTE");
				return -1;
			}
		}

		return distance;
	}

	/**
	 * Djikstra's algorithm implementation to find 
	 * the length of the shortest path between
	 * two nodes
	 * 
	 * @param p	the start point
	 * @param end	the end point
	 * @return	the length of the shortest route
	 */
	public int getShortestRoute(char start, char end) {
		Path p = new Path(nodes.indexOf(start));
		int endIndex = nodes.indexOf(end);
		int shortest = INFINITY;
		Comparator<Path> pathComparator = new NodeDistanceComparator();
		PriorityQueue<Path> toVisit = new PriorityQueue<Path>(QUEUE_INIT, pathComparator);

		// Populate toVisit with each neighbour so paths to itself do not give 0
		toVisit.addAll(p.getNeighbours(adjMat, false));

		while(!toVisit.isEmpty() && toVisit.peek().getLength() < shortest) {
			Path next = toVisit.poll();

			if (next.getLastNode() == endIndex) {
				shortest = next.getLength();
			}

			// Add all neighbours and loop
			toVisit.addAll(next.getNeighbours(adjMat, false));
		}

		return shortest;
	}

	/**
	 * Djikstra's algorithm implementation of finding
	 * the number of routes between two nodes under a 
	 * given distance.
	 * 
	 * @param start	start node (can be same as end)
	 * @param end	end node
	 * @param distance	distance to be limited under
	 * @return	the number of routes
	 */
	public int getNumRoutes(char start, char end, int distance) {
		Path p = new Path(nodes.indexOf(start));
		int endIndex = nodes.indexOf(end);
		Comparator<Path> pathComparator = new NodeDistanceComparator();
		PriorityQueue<Path> toVisit = new PriorityQueue<Path>(QUEUE_INIT, pathComparator);
		int numRoutes = 0;

		// Add all neighbours to toVisit instead of just p
		// to avoid a false positive on the initial poll of itself
		toVisit.addAll(p.getNeighbours(adjMat, true));

		while(!toVisit.isEmpty() && toVisit.peek().getLength() < distance) {
			Path next = toVisit.poll();

			if (next.getLastNode() == endIndex) {
				numRoutes++;
			}

			// Add all neighbours and loop
			toVisit.addAll(next.getNeighbours(adjMat, true));
		}

		return numRoutes;
	}

	/**
	 * Breadth first search to get the number of trips with a 
	 * certain start and end point either not exceeding the
	 * max stops or exactly the number max stops
	 * 
	 * @param start	start point of our trips
	 * @param end	end point of our trips
	 * @param max	maximum stops of our trips
	 * @param less	true for finding valid trips less than 
	 * the number of max stops, false for exactly max
	 * @return	the number of trips possible
	 */
	public int getTrips(char start, char end, int max, boolean less) {
		int startIndex = nodes.indexOf(start);
		int trips = 0;

		// Explore neighbours (so we don't get the first node returning true
		for (int i = 0; i < adjMat[startIndex].length; i++) {
			if (adjMat[startIndex][i] > 0) {
				trips += getTripsRecursive(nodes.get(i), end, max - 1, less);
			}
		}

		return trips;
	}

	/**
	 * Recursive helper function for getTrips
	 * 
	 */
	public int getTripsRecursive(char start, char end, int max, boolean less) {
		int startIndex = nodes.indexOf(start);
		int trips = 0;

		// Base case
		if (start == end) {
			if (less && max >= 0) {
				trips++;
			} else if (max == 0) {
				trips++;
			}
		}


		// Recursive case
		if (max > 0) {
			for (int i = 0; i < adjMat[startIndex].length;i++) {
				if (adjMat[startIndex][i] > 0) {
					trips += getTripsRecursive(nodes.get(i), end, max - 1, less);
				}
			}
		}
		return trips;
	}


	private boolean containsNode(char c) {
		boolean containsNode = false;
		for (char n : nodes) {
			if (n == c) {
				containsNode = true;
			}
		}
		return containsNode;
	}

	public String toString() {
		String s = " ";

		// Top line
		for (int i = 0 ; i < adjMat.length; i++) {
			s = s.concat(" " + Character.toString(nodes.get(i)));
		}
		s = s.concat("\n");

		for (int i = 0 ; i < adjMat.length; i++) {
			s = s.concat(Character.toString(nodes.get(i)) + " "); // Side line
			for (int j = 0; j < adjMat[i].length; j++) {
				s = s.concat(Integer.toString(adjMat[i][j]) + " ");
			}
			s = s.concat("\n");
		}
		return s;
	}

}
