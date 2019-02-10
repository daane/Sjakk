package game;

import java.util.ArrayList;

public abstract class Piece {
	char color;
	char type;
	Cell parent;
	private String id;
	protected ArrayList<Cell> validMoves = new ArrayList<Cell>();
	protected ArrayList<Cell> defendedCells = new ArrayList<Cell>();
	
	public abstract ArrayList<Cell> setValidMoves();

	public char getType() {
		return type;
	}
	
	public void setParent(Cell parent) {
		this.parent = parent;
	}
	
	private void removeParent() {
		this.parent = null;
	}
	
	public ArrayList<Cell> getValidMoves() {
		return setValidMoves();
	}
	
	public ArrayList<Cell> getDefendedCells() {
		setValidMoves();
		return defendedCells;
	}
	
	public char getColor() {
		return color;
	}
	
	public Cell getParent() {
		return parent;
	}
}
