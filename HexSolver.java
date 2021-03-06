/*
 * CS 560
 * Ann Dinh
 * Grace Narvaza
 * Aaron Acosta
 * Jobren Deguzman
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class HexSolver {
	private HexCell begin;
	private HexCell end;
	private HexMap map;
	private PriorityQueue<HexCell> unvisited; // a priority queue
	private LinkedList<HexCell> solution; // a stack
	private long sTime, eTime;

	public HexSolver() {
		map = new HexMap(); // Makes a new map
		begin = map.startCell;
		end = map.endCell;
		unvisited = new PriorityQueue<HexCell>(); // Priority queue to hold min
													// distance in the front
		solution = new LinkedList<HexCell>(); // Solution acts as a stack

		sTime = System.nanoTime(); // Recording time before running algorithm
		runAlgorithm(); // Marks distances and then finds solution
		eTime = System.nanoTime();

		writeSolution(); // To file called solution.txt
		printSolution(); // To console
	}

	
	private void runAlgorithm() {
		markDistances();
		getSolution();
	}

	
	private void markDistances() {
		unvisited.add(begin); // Start at beginning cell
		begin.setDistance(0); // Beginning cell has a distance of 0
		while (!unvisited.isEmpty()) {
			HexCell current = unvisited.remove(); // Grabs the smallest distance
			calculateDistance(current); // For all of its neighbors
			current.setVisited(true); // Current cell was now visited
		}
	} // End markDistances

	
	private void getSolution() {
		solution.addFirst(end); // Back tracking
		// Final distance excludes end's cost but includes begin's cost
		int finalDistance = end.getDistance() - end.getCost() + begin.getCost();
		while (finalDistance > 0) {
			HexCell s = getShortest(solution.peek()); // Grabs shorter distance
			if (s != null)
				solution.addFirst(s);
			finalDistance -= s.getCost(); // Distance is subtracted by cost
		}
	} // End getSolution

	
	private void writeSolution() {
		try {
			File fout = new File("solution.txt");
			FileOutputStream fos = new FileOutputStream(fout);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

			// Pops everything off of the stack
			for (HexCell c : solution) {
				bw.write(c.toString());
				bw.newLine();
			}

			bw.write("Total cost: " + (end.getDistance() + begin.getCost()));
			bw.newLine();
			bw.write("Time elapsed: " + Double.valueOf((eTime - sTime) / 1000000) + " ms");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // End writeSolution

	
	private void printSolution() {
		// Pops everything off of the stack
		for (HexCell c : solution)
			System.out.println(c.toString());
		System.out.println("Total cost: " + (end.getDistance() + begin.getCost()));
		System.out.println("Time elapsed: " + Double.valueOf((eTime - sTime) / 1000000) + " ms");
	} // End printSolution

	private void calculateDistance(HexCell c) {
		LinkedList<HexCell> nbors = c.getNeighbors();
		for (HexCell adj : nbors) { // Adjacent neighbor
			if (!adj.wasVisited()) {	// Only add if it has not been visited
				int currDistance = c.getDistance() + adj.getCost();
				if (currDistance < adj.getDistance()) // Update adjacent's
														// distance if smaller
					adj.setDistance(currDistance);
				unvisited.add(adj); // Add to unvisited queue
			}
		}
	} // End calculateDistance

	
	private HexCell getShortest(HexCell c) {
		LinkedList<HexCell> nbors = c.getNeighbors();
		HexCell min = nbors.getFirst(); // Set min to first neighbor
		for (HexCell adj : nbors) {
			int diff = adj.getDistance() - min.getDistance();
			// If difference is smaller than min and not already in the solution stack
			if (diff < 0 && !solution.contains(adj)) 
				min = adj;
		}
		return min;
	} // End getShortest

	public static void main(String[] args) {
		new HexSolver();
	}

} // End HexSolver


