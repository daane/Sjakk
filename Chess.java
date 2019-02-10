package game;

public class Chess {
	public static void main(String[] args) {
		Board test = new Board("default");
		System.out.println(test.cells[7][5].toString());
		System.out.println(test.cells[7][5].getPiece().getValidMoves());
		
		test.move("f3");
		test.move("e6");
		test.move("g4");
		test.move("Qh4");
		//System.out.println(test.cells[0][4].getPiece().getValidMoves().toString());
		//test.move("Kd2");

		/*
		 * for (int i = 0; i < cells.length; i++) { for (int j = 0; j < cells[i].length;
		 * j++) { System.out.print(cells[i][j]); } System.out.print("/r");
		 */
	}
}
