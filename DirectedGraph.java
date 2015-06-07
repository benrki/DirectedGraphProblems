import java.util.ArrayList;

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
			int distance = Integer.parseInt(s.substring(2)); // substr for 2 digits
			// Update adjacency matrix
			adjMat[n1][n2] = distance;
			adjMat[n2][n1] = distance;
		}
		
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
