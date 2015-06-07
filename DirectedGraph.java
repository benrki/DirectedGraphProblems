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
	 * certain start and end point not exceeding the given max
	 * number of stops
	 * 
	 * @param start
	 * @param end
	 * @param max
	 * @return
	 */
	public int getTrips(char start, char end, int max) {
		int startIndex = nodes.indexOf(start);
		int trips = 0;

		// Create new paths for each adjacent node
		for (int i = 0; i < adjMat[startIndex].length; i++) {
			if (adjMat[startIndex][i] > 0) {
				trips += getTripsRecursive(nodes.get(i), end, max - 1);
			}
		}

		return trips;
	}

	public int getTripsRecursive(char start, char end, int max) {
		int startIndex = nodes.indexOf(start);
		int isValid = 0;

		// Base case
		if (start == end && max >= 0) {
			isValid = 1;
		}
		
		// Recursive case
		if (max > 0) {
			for (int i = 0; i < adjMat[startIndex].length; i++) {
				if (adjMat[startIndex][i] > 0) {
					return isValid + getTripsRecursive(nodes.get(i), end, max - 1);
		
				}
			}
		}
		return isValid;
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
