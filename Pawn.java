package game;

import java.util.ArrayList;

public class Pawn extends Piece {

	public Pawn(boolean isWhite, Cell cell) {
		this.parent = cell;
		type = 'p';
		color = isWhite ? 'w' : 'b';
	}

	public ArrayList<Cell> setValidMoves() {
		int x = this.parent.x;
		int y = this.parent.y;
		Cell[][] cells = this.parent.parent.cells;

		validMoves.clear();
		defendedCells.clear();
		
		if (getColor() == 'b') {
			if (x > 0 && y < 7)
				defendedCells.add(cells[y - 1][x - 1]);
			if (x < 7 && y < 7)
				defendedCells.add(cells[y - 1][x + 1]);
			if (cells[y - 1][x].getPiece() == null) {
				validMoves.add(cells[y - 1][x]);
				if (y == 6) {
					if (cells[4][x].getPiece() == null)
						validMoves.add(cells[4][x]);
				}
			}
			if ((x > 0) && (cells[y - 1][x - 1].getPiece() != null)
					&& (cells[y - 1][x - 1].getPiece().getColor() != this.getColor())) {
				validMoves.add(cells[y - 1][x - 1]);
			}
			if ((x < 7) && (cells[y - 1][x + 1].getPiece() != null)
					&& (cells[y - 1][x + 1].getPiece().getColor() != this.getColor())) {
				validMoves.add(cells[y - 1][x + 1]);
			}
		} else {
			if (x > 0 && y < 7)
				defendedCells.add(cells[y + 1][x - 1]);
			if (x < 7 && y < 7)
				defendedCells.add(cells[y + 1][x + 1]);

			if (y == 8)
				return validMoves;
			if (cells[y + 1][x].getPiece() == null) {
				validMoves.add(cells[y + 1][x]);
				if (y == 1) {
					if (cells[3][x].getPiece() == null)
						validMoves.add(cells[3][x]);
				}
			}
			if ((x > 0) && (cells[y + 1][x - 1].getPiece() != null)
					&& (cells[y + 1][x - 1].getPiece().getColor() != this.getColor())) {
				validMoves.add(cells[y + 1][x - 1]);
			}
		}
		if ((x < 7) && (cells[y + 1][x + 1].getPiece() != null)
				&& (cells[y + 1][x + 1].getPiece().getColor() != this.getColor())) {
			validMoves.add(cells[y + 1][x + 1]);
		}
		return validMoves;
	}

	@Override
	public String toString() {
		if (color == 'w') {
			return "♙";
		} else {
			return "♟";
		}
	}
}
