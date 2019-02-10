package game;

import java.util.ArrayList;

public class King extends Piece {
	public King(boolean isWhite, Cell cell) {
		this.parent = cell;
		type = 'k';
		color = isWhite ? 'w' : 'b';
	}

	public ArrayList<Cell> setValidMoves() {
		int x = this.parent.x;
		int y = this.parent.y;
		Cell[][] cells = this.parent.parent.cells;

		validMoves.clear();
		defendedCells.clear();
		
		int posx[] = { x, x, x + 1, x + 1, x + 1, x - 1, x - 1, x - 1 };
		int posy[] = { y - 1, y + 1, y - 1, y, y + 1, y - 1, y, y + 1 };
		for (int i = 0; i < 8; i++)
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8))
				if ((cells[posy[i]][posx[i]].getPiece() == null
						|| cells[posy[i]][posx[i]].getPiece().getColor() != this.getColor())
						&& !cells[posy[i]][posx[i]].isCheck())
					validMoves.add(cells[posy[i]][posx[i]]);
		return validMoves;
	}

	@Override
	public String toString() {
		if (color == 'w') {
			return "♔";
		} else {
			return "♚";
		}
	}
}
