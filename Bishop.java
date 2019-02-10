package game;

import java.util.ArrayList;

public class Bishop extends Piece{
	public Bishop(boolean isWhite, Cell cell) {
		this.parent = cell;
		type = 'b';
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

		while (tempy < 7 && tempx < 7) {
			defendedCells.add(cells[tempy + 1][tempx + 1]);
			if (cells[tempy + 1][tempx + 1].getPiece() == null) {
				validMoves.add(cells[tempy + 1][tempx + 1]);
			} else if (cells[tempy + 1][tempx + 1].getPiece().getColor() != this.getColor()) {
				validMoves.add(cells[tempy + 1][tempx + 1]);
				break;
			} else {
				break;
			}
			tempy++;
			tempx++;
		}
		tempy = y;
		tempx = x;
		while (tempy < 7 && tempx > 0) {
			if (cells[tempy + 1][tempx - 1].getPiece() == null) {
				validMoves.add(cells[tempy + 1][tempx-1]);
			} else if (cells[tempy + 1][tempx-1].getPiece().getColor() != this.getColor()) {
				validMoves.add(cells[tempy + 1][tempx-1]);
				break;
			} else {
				break;
			}
			tempy++;
			tempx--;
		}
		tempy = y;
		tempx = x;
		while (tempx > 0 && tempy > 0) {
			defendedCells.add(cells[tempy - 1][tempx - 1]);
			if (cells[tempy - 1][tempx - 1].getPiece() == null) {
				validMoves.add(cells[tempy][tempx - 1]);
			} else if (cells[tempy-1][tempx - 1].getPiece().getColor() != this.getColor()) {
				validMoves.add(cells[tempy - 1][tempx - 1]);
				break;
			} else {
				break;
			}
			tempx--;
			tempy--;
		}
		tempx = x;
		tempy = y;
		while (tempx < 7 && tempy > 0) {
			defendedCells.add(cells[tempy - 1][tempx + 1]);
			if (cells[tempy-1][tempx + 1].getPiece() == null) {
				validMoves.add(cells[tempy-1][tempx + 1]);
			} else if (cells[tempy-1][tempx + 1].getPiece().getColor() != this.getColor()) {
				validMoves.add(cells[tempy-1][tempx + 1]);
				break;
			} else {
				break;
			}
			tempx++;
			tempy--;
		}
		return validMoves;
	}
	
	@Override
	public String toString() {
		if (color == 'w') {
			return "♗";
		} else {
			return "♝";
		}
	}
}
