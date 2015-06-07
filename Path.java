import java.util.ArrayList;


public class Path {
	int length;
	int lastNode;
	ArrayList<Integer> visited;

	public Path(int start) {
		lastNode = start;
		length = 0;
		visited = new ArrayList<Integer>();
		// We do not count our first node as visited
		// For getShortest() calculations
		// visited.add(start);
		
	}

	public Path(Path p) {
		lastNode = p.getLastNode();
		length = p.getLength();
		visited = new ArrayList<Integer>(p.getVisited());
	}

	public int getLength() {
		return length;
	}

	public int getLastNode() {
		return lastNode;
	}

	public boolean visit(int node, int length) {
		if (!visited.contains(node)) {
			visited.add(node);
			lastNode = node;
			this.length += length;
			return true;
		}
		return false;
	}

	public int getStops() {
		return visited.size();
	}

	/**
	 * Gets all neighbours that haven't been visited yet
	 * @param adjMat
	 * @return
	 */
	public ArrayList<Path> getNeighbours(int[][] adjMat) {
		ArrayList<Path> neighbours = new ArrayList<Path>();

		for (int i = 0; i < adjMat[this.lastNode].length; i++) {
			if (adjMat[this.lastNode][i] > 0 && !visited.contains(i)) {
				Path neighbour = new Path(this);
				neighbour.visit(i, adjMat[this.lastNode][i]);
				neighbours.add(neighbour);
			}
		}

		return neighbours;
	}

	public ArrayList<Integer> getVisited() {
		return visited;
	}
}
