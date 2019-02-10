package game;

import java.util.ArrayList;

public class Board {
	static final String charPieces = "RNBQKPrnbqkp";
	public Cell[][] cells = new Cell[8][8];
	char playerToMove; // w = white, b = black
	boolean[] castle = new boolean[4]; // [white o-o, white o-o-o, black o-o, black o-o-o]
	boolean checkmate = false;

	public Board(String FEN) {
		createEmptyBoard();
		applyFEN(FEN);
	}

	public void applyFEN(String FEN) {
		if (FEN == "default") {
			FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		}
		char indexChar;
		int x = 0;
		int y = 7;
		for (int i = 0; i < FEN.length(); i++) {
			indexChar = FEN.charAt(i);
			if (charPieces.contains(Character.toString(indexChar))) {
				cells[y][x].setPiece(Game.createPiece(indexChar, cells[y][x]));
				x++;
			} else if (indexChar == '/') {
				y--;
				x = 0;
			} else if (Character.isDigit(indexChar)) {
				x += indexChar - '0';
			} else if (indexChar == ' ') {
				parseSettings(FEN.substring(i + 1));
				break;
			}
		}
	}

	public void move(String move) {
		Piece p = locatePiece(move, playerToMove);
		Cell destination = locateDestination(move);
		if (checkTest(p, destination)) {
			Cell start = p.getParent();
			Piece piece = start.getPiece();
			start.removePiece(piece);
			destination.setPiece(piece);
			piece.setParent(destination);

			if (playerToMove == 'w') {
				playerToMove = 'b';
			} else {
				playerToMove = 'w';
			}
			setChecks();
			Piece king = null;
			for (int i = 0; i < 64; i++) { // Locates king
				if (cells[i % 8][i / 8].getPiece() != null) {
					if (cells[i % 8][i / 8].getPiece().getType() == 'k'
							&& cells[i % 8][i / 8].getPiece().getColor() == playerToMove) {
						king = cells[i % 8][i / 8].getPiece();
						break;
					}
				}
			}
			if (king != null && king.parent.isCheck()) {
				if (checkForMate()) {
					this.checkmate = true;
				}
			}
		} else {
			throw new IllegalArgumentException("King is still in check");
		}
	}

	private boolean checkForMate() {
		boolean checkmate = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (cells[i][j].getPiece() != null && cells[i][j].getPiece().getColor() == playerToMove) {
					for (Cell c : cells[i][j].getPiece().getValidMoves()) {
						if (checkTest(cells[i][j].getPiece(), c))
							checkmate = false;
					}
				}
			}
		}
		return checkmate;
	}

	private boolean checkTest(Piece p, Cell destination) { // returns false if king still is in check
		Cell start = p.getParent();
		Piece piece = start.getPiece();
		Piece pDest = destination.getPiece();
		start.removePiece(piece);
		destination.setPiece(piece);
		piece.setParent(destination);
		setChecks();
		Piece king = null;
		for (int i = 0; i < 64; i++) { // Locates king
			if (cells[i % 8][i / 8].getPiece() != null) {
				if (cells[i % 8][i / 8].getPiece().getType() == 'k'
						&& cells[i % 8][i / 8].getPiece().getColor() == playerToMove) {
					king = cells[i % 8][i / 8].getPiece();
					break;
				}
			}
		}
		if (king != null) {
			if (king.parent.isCheck()) {
				start.setPiece(piece);
				destination.setPiece(pDest);
				piece.setParent(start);
				return false;
			} else {
				start.setPiece(piece);
				destination.setPiece(pDest);
				piece.setParent(start);
				return true;
			}
		} else {
			throw new NullPointerException("Could not find king");
		}
	}

	public Piece locatePiece(String move, char color) {
		ArrayList<Piece> plausiblePieces = new ArrayList<Piece>();
		Cell destinationCell = locateDestination(move);
		int column = -1;

		if ("acdefgh".contains(move.substring(0, 1).toLowerCase())) {
			column = move.toLowerCase().charAt(0) - 'a';
			move = "p" + move.substring(1);
		} else if (move.substring(0, 1).contains("b")) {
			column = move.toLowerCase().charAt(0) - 'a';
			move = "p" + move.substring(1);
		}

		char targetPiece = move.toLowerCase().charAt(0);

		if (column != -1) {
			for (int i = 0; i < 8; i++) {
				if (cells[i % 8][column].getPiece() != null) {
					if (cells[i % 8][column].getPiece().getType() == targetPiece
							&& cells[i % 8][column].getPiece().getColor() == color) {
						plausiblePieces.add(cells[i % 8][column].getPiece());
					}
				}
			}
		} else {
			for (int i = 0; i < 64; i++) {
				if (cells[i % 8][i / 8].getPiece() != null) {
					if (cells[i % 8][i / 8].getPiece().getType() == targetPiece
							&& cells[i % 8][i / 8].getPiece().getColor() == color) {
						plausiblePieces.add(cells[i % 8][i / 8].getPiece());
					}
				}
			}
		}
		ArrayList<Piece> temp = new ArrayList<Piece>();
		for (Piece piece : plausiblePieces) {
			if (piece.getValidMoves().contains(destinationCell)) {
				temp.add(piece);
			}
		}

		if (temp.size() > 1) {
			throw new IllegalArgumentException("Ambigious input");
		} else if (plausiblePieces.size() == 0) {
			throw new IllegalArgumentException("Could not locate piece");
		}
		return temp.get(0);
	}

	public Cell locateCell(String coords) {
		int x = coords.charAt(0) - 'a';
		int y = coords.charAt(1) - '0' - 1;

		return cells[y][x];
	}

	public Cell locateDestination(String input) {
		Cell destination;
		if (input.contains("x")) {
			destination = locateCell(input.substring(input.indexOf("x") + 1, input.indexOf("x") + 3));
		} else if ("acdefgh".contains(input.substring(0, 1).toLowerCase())) {
			destination = locateCell(input.substring(0, 2));
		} else if (input.substring(0, 1).contains("b")) {
			destination = locateCell(input.substring(0, 2));
		} else {
			destination = locateCell(input.substring(1, 3));
		}

		return destination;
	}

	public void parseSettings(String settings) {
		if (settings.charAt(0) == 'w' || settings.charAt(0) == 'b') {
			playerToMove = settings.charAt(0);
		} else {
			throw new IllegalArgumentException("Invalid move settings");
		}

		castle[0] = castle[1] = castle[2] = castle[3] = false;
		if (settings.charAt(2) != '-') {
			for (int i = 2; i < settings.length(); i++) {
				if (settings.charAt(i) == ' ') {
					break;
				}
				switch (settings.charAt(i)) {
				case 'K':
					castle[0] = true;
					break;
				case 'Q':
					castle[1] = true;
					break;
				case 'k':
					castle[2] = true;
					break;
				case 'q':
					castle[3] = true;
					break;
				default:
					throw new IllegalArgumentException("Invalid castling settings");
				}
			}
		}
	}

	public void setChecks() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				cells[i][j].removeCheck();
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (cells[i][j].getPiece() != null) {
					if (cells[i][j].getPiece().getColor() != playerToMove) {
						for (Cell cell : cells[i][j].getPiece().getDefendedCells()) {
							cell.setCheck();
						}
					}
				}
			}
		}
	}

	public void createEmptyBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Cell cell = new Cell(this, i, j);
				cells[i][j] = cell;
			}
		}
	}

	public String toString() {
			String board = ""; // "-----------------\n|";
			for (int i = 7; i >= 0; i--) {
				board += i + 1 + " ";
				for (int j = 0; j < 8; j++) {
					board += (cells[i][j].toString() + " ");
				}
				board += "\n";
			}
			board += "\u2000\u2000\u2000";
			for (int i = 0; i < 8; i++) {
				board += (char) (65 + i) + "\u2001";
			}
			if (checkmate) {
				return board + "\nCheckmate!";
			}
			return board;
	}
}
