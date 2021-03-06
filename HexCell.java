/*
 * CS 560
 * Ann Dinh
 * Grace Narvaza
 * Aaron Acosta
 * Jobren Deguzman
 */
import java.util.LinkedList;

public class HexCell implements Comparable<HexCell>{
	private HexCell top, bottom, topRight, topLeft, bottomRight, bottomLeft;
	private int position, cost, distanceFromOrigin;
	private boolean wasVisited;
	
	public HexCell(int pos) {
		this(pos, 1000);   // 
	}

	public HexCell(int pos, int cost) {
		top = bottom = topRight = topLeft = bottomRight = bottomLeft = null;
		position = pos;
		wasVisited = false;	// HexCell was not visited
		this.cost = cost;
		distanceFromOrigin = 99999999; // Big number represents infinity, or unvisited
	} // End constructor
	
	public int compareTo(HexCell o) {
		return this.cost - o.cost;
	}
	

	//***************** getter methods **************//

	public int getDistance() {
		return distanceFromOrigin;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int getCost() {
		return cost;
	}
	
	public HexCell getTop() {
		return top;
	}
	
	public HexCell getBottom() {
		return bottom;
	}
	
	public HexCell getTopRight() {
		return topRight;
	}
	
	public HexCell getTopLeft() {
		return topLeft;
	}
	
	public HexCell getBottomRight() {
		return bottomRight;
	}
	
	public HexCell getBottomLeft() {
		return bottomLeft;
	}
	
	public void setCost(int c) {
		cost = c;
	}
	
	public boolean wasVisited() {
		return wasVisited;
	}
	
	public LinkedList<HexCell> getNeighbors() {
		LinkedList<HexCell> tmpList = new LinkedList<HexCell>();
		tmpList = addNeighbor(tmpList, top);
		tmpList = addNeighbor(tmpList, bottom);
		tmpList = addNeighbor(tmpList, topRight);
		tmpList = addNeighbor(tmpList, topLeft);
		tmpList = addNeighbor(tmpList, bottomRight);
		tmpList = addNeighbor(tmpList, bottomLeft);
		return tmpList;
		
	} // End getNeighbors
	
	private LinkedList<HexCell> addNeighbor(LinkedList<HexCell> list, HexCell c) {
		if(c != null)
			list.add(c);
		return list;
	}
	
	

	
	//****************** Setters *****************//
	
	public void setTop(HexCell c) {
		top = c;
	}
	
	public void setBottom(HexCell c) {
		bottom = c;
	}
	
	public void setTopRight(HexCell c) {
		topRight = c;
	}
	
	public void setTopLeft(HexCell c) {
		topLeft = c;
	}
	
	public void setBottomRight(HexCell c) {
		bottomRight = c;
	}
	
	public void setBottomLeft(HexCell c) {
		bottomLeft = c;
	}
	
	public void setDistance(int d) {
		distanceFromOrigin = d;
	}

	public void setVisited(boolean b) {
		wasVisited = b;
	}

	public String toString() {
		return "HexCell Number: " + position;
	}

	public boolean equals(HexCell c) {
		return position == c.position;
	}
}


