package game;

public class Game {
	public static Piece createPiece(char type, Cell cell) {
		boolean isWhite = Character.isUpperCase(type);
		type = Character.toLowerCase(type);

		switch (type) {
		case 'p':
			return new Pawn(isWhite, cell);
		case 'n':
			return new Knight(isWhite, cell);
		case 'b':
			return new Bishop(isWhite, cell);
		case 'r':
			return new Rook(isWhite, cell);
		case 'q':
			return new Queen(isWhite, cell);
		case 'k':
			return new King(isWhite, cell);
		default:
			throw new IllegalArgumentException("Invalid piece type");
		}
	}

}
