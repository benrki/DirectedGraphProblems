import java.util.Comparator;


public class NodeDistanceComparator implements Comparator<Path> {
	
	@Override
	public int compare(Path p1, Path p2) {
		if (p1.getLength() > p2.getLength()) {
			return 1;
		} else if (p1.getLength() < p2.getLength()) {
			return -1;
		}
		return 0;
	}

}
