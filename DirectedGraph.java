import java.util.ArrayList;

/**
 * Various directed graph problems.
 * 07/06/15
 * 
 * @author Benjamin Ki
 *
 */
public class DirectedGraph {
	private int[][] adjMat = null;
	private ArrayList<Character> nodes = new ArrayList<Character>();

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
	 * Breadth first search to get the number of trips with a 
	 * certain start and end point either not exceeding the
	 * max stops or exactly the number max stops
	 * 
	 * @param start
	 * @param end
	 * @param max
	 * @param less	true for finding valid trips less than 
	 * the number of max stops, false for exactly max
	 * @return
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
