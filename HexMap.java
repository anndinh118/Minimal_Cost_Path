/*
 * CS 560
 * Ann Dinh
 * Grace Narvaza
 * Aaron Acosta
 * Jobren Deguzman
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class HexMap {
	private final static int HEX_MAP_SIZE = 233;
	private final static int START_POS = 226;
	private final static int END_POS = 8;
	private int offsetTop, offsetBottom; // Case 1
	private int offsetTR, offsetBL; // Case 2
	private int offsetTL, offsetBR; // Case 3

	public HexCell[] map;
	public HexCell startCell, endCell;

	public HexMap() {
		map = new HexCell[HEX_MAP_SIZE];
		// Offsets get you the adjacent HexGrids
		offsetTop = -15;   // top = 15 
		offsetBottom = 15;
		offsetBL = 7;
		offsetTR = -7;  //since top is -15, TR and TL are also neg.
		offsetTL = -8;
		offsetBR = 8;

		fillMap();
		inputCost();
		startCell = map[START_POS-1];
		endCell = map[END_POS-1];
	}

	
	// Fills the array(our map) with HexCells; position is one more than index of array
	private void fillMap() {
		for (int i = 0; i < HEX_MAP_SIZE; i++)
			map[i] = new HexCell(i + 1);

		for (int i = 0; i < HEX_MAP_SIZE; i++)
			connectNeighbors(map[i]);
	} // End fillMap

	
	// Connects hexcells to each other (neighbors connected to each other in the map)
	private void connectNeighbors(HexCell c) {
		int pos = c.getPosition();
		// Keep in mind position is one more than index of array

		if (checkNextNode(pos, offsetTop, 1))
			c.setTop(map[pos - 1 + offsetTop]);

		if (checkNextNode(pos, offsetBottom, 1))
			c.setBottom(map[pos - 1 + offsetBottom]);
			
		if (checkNextNode(pos, offsetTR, 2))
			c.setTopRight(map[pos - 1 + offsetTR]);

		if (checkNextNode(pos, offsetBL, 2))
			c.setBottomLeft(map[pos - 1 + offsetBL]);

		if (checkNextNode(pos, offsetTL, 3))
			c.setTopLeft(map[pos - 1 + offsetTL]);

		if (checkNextNode(pos, offsetBR, 3))
			c.setBottomRight(map[pos - 1 + offsetBR]);
		
		setNullBorders(); //when there is no adjacent node

	} // End connectNeighbors

	private boolean checkNextNode(int pos, int offset, int caseNum) {
		int newPos = pos + offset;
		if(caseNum < 1 || caseNum > 3)
			throw new IllegalArgumentException("Only accepts case numbers 1-3");
		if(caseNum == 1) {	// Top/Bottom
			if(newPos < 1 || newPos > HEX_MAP_SIZE)
				return false;
		}
		// These next cases, tried to be cool by doing it mathematically
		// In the end we said screw it and just set null borders manually
		else if(caseNum == 2) {		// TR/BL
			if(newPos <= 0 || newPos > HEX_MAP_SIZE)
				return false;
		}
		
		else {		// TL/BR
			if(newPos <= 0  || newPos > HEX_MAP_SIZE)
				return false;
		} 
		return true;	// Valid neighbor
		
	} // End checkNeighbor
	
	
	// Adjust manually
	private void setNullBorders() {
		// Corners
		// Cell 1
		setNull(map[0], 1);
		setNull(map[0], 2);
		setNull(map[0], 3);
		setNull(map[0], 6);
		
		// Cell 8
		setNull(map[7], 1);
		setNull(map[7], 2);
		setNull(map[7], 3);
		setNull(map[7], 4);
		
		// Cell 226
		setNull(map[225], 1);
		setNull(map[225], 4);
		setNull(map[225], 5);
		setNull(map[225], 6);

		// Cell 233
		setNull(map[232], 3);
		setNull(map[232], 4);
		setNull(map[232], 5);
		setNull(map[232], 6);
		
		// Left Borders have no TL, BL (1, 6)
		LinkedList<Integer> list = new LinkedList<Integer>();
		for(int i = 16; i < 226; i = i+15) {
			setNull(map[i-1], 1);
			setNull(map[i-1], 6);
		}
		
		// Right Borders have no TR, BR (3, 4)
		list.clear();
		for (int i = 8; i < 233; i = i + 15) {
			setNull(map[i - 1], 3);
			setNull(map[i - 1], 4);
		}
		
		// Top Outer Borders have no (1, 2, 3)
		list.clear();
		for (int i = 2; i < 8; i++) {
			setNull(map[i - 1], 1);
			setNull(map[i - 1], 2);
			setNull(map[i - 1], 3);
		}
		
		// Bottom Outer Borders have no (4, 5, 6)
		list.clear();
		for (int i = 227; i < 233; i++) {
			setNull(map[i - 1], 4);
			setNull(map[i - 1], 5);
			setNull(map[i - 1], 6);
		}
		
		// Top Inner Borders have no (1)
		list.clear();
		for (int i = 9; i < 16; i++) 
			setNull(map[i - 1], 1);
		
		// Top Inner Borders have no (5)
		list.clear();
		for (int i = 227; i < 234; i++)
			setNull(map[i - 1], 5);
		
	} // End setNullBorders
	
	private void setNull(HexCell c, int caseNum) {
		// caseNum clockwise from top left (TL, T, TR, BR, B, BL)
		switch (caseNum) {
			case 1:
				c.setTopLeft(null);
				break;
			case 2:
				c.setTop(null);
				break;
			case 3:
				c.setTopRight(null);
				break;
			case 4:
				c.setBottomRight(null);
				break;
			case 5:
				c.setBottom(null);
				break;
			default:
				c.setBottomLeft(null);
				break;
		}
		
	} // End setNull
	
	
	
	private void inputCost() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("data.txt")); // Reads in data from this filezxfzx 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		try {
			String line = br.readLine();
			int index = 0;
			while(line != null) { 	// As long as there is a line to read
				String arr[] = line.split("\\ "); // Splits the line by space
				int cost = Integer.parseInt(arr[1]);
				map[index++].setCost(cost);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // End inputCost
	
	public static void main(String [] args) {
		new HexMap();
	}

} // End class

