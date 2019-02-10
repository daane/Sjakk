package game;

public class Cell {
	int x, y;
	public Piece piece;
	Board parent;
	boolean check = false;

	public Cell(Board board, int y, int x) {
		this.x = x;
		this.y = y;
		this.parent = board;
	}
	
	public void setCheck() {
		this.check = true;
	}
	
	public void removeCheck() {
		this.check = false;
	}
	
	public boolean isCheck() {
		return check;
	}

	public void setPiece(Piece p)
	{
		piece = p;
	}
	public void removePiece(Piece p)
	{
		piece = null;
	}
	public Piece getPiece() {
		return piece;
	}
	public String toString() {
		if(piece == null) {
			return "\u2001";
		} else {
			return piece.toString();
		}
	}
}
