package game;

import java.util.ArrayList;

public class Rook extends Piece{
	public Rook(boolean isWhite, Cell cell) {
		this.parent = cell;
		type = 'r';
		color = isWhite ? 'w' : 'b';
	}
	
	public ArrayList<Cell> setValidMoves() {
		int x = this.parent.x;
		int y = this.parent.y;
		int tempx = x;
		int tempy = y;
		Cell[][] cells = this.parent.parent.cells;
		validMoves.clear();
		defendedCells.clear();

		while (tempy < 7) {
			defendedCells.add(cells[tempy + 1][tempx]);
			if (cells[tempy + 1][tempx].getPiece() == null) {
				validMoves.add(cells[tempy + 1][tempx]);
			} else if (cells[tempy + 1][tempx].getPiece().getColor() != this.getColor()) {
				validMoves.add(cells[tempy + 1][tempx]);
				break;
			} else {
				break;
			}
			tempy++;
		}
		tempy = y;
		while (tempy > 0) {
			defendedCells.add(cells[tempy - 1][tempx]);
			if (cells[tempy - 1][tempx].getPiece() == null) {
				validMoves.add(cells[tempy - 1][tempx]);
			} else if (cells[tempy - 1][tempx].getPiece().getColor() != this.getColor()) {
				validMoves.add(cells[tempy - 1][tempx]);
				break;
			} else {
				break;
			}
			tempy--;
		}
		
		while (tempx > 0) {
			defendedCells.add(cells[tempy][tempx - 1]);
			if (cells[tempy][tempx - 1].getPiece() == null) {
				validMoves.add(cells[tempy][tempx - 1]);
			} else if (cells[tempy][tempx - 1].getPiece().getColor() != this.getColor()) {
				validMoves.add(cells[tempy][tempx - 1]);
				break;
			} else {
				break;
			}
			tempx--;
		}
		tempx = x;
		while (tempx < 7) {
			defendedCells.add(cells[tempy][tempx + 1]);
			if (cells[tempy][tempx + 1].getPiece() == null) {
				validMoves.add(cells[tempy][tempx + 1]);
			} else if (cells[tempy][tempx + 1].getPiece().getColor() != this.getColor()) {
				validMoves.add(cells[tempy][tempx + 1]);
				break;
			} else {
				break;
			}
			tempx++;
		}
		
		return validMoves;
	}
	
	@Override
	public String toString() {
		if (color == 'w') {
			return "♖";
		} else {
			return "♜";
		}
	}
}
