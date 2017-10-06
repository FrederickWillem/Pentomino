package data;

import java.util.Arrays;

public class Solver {

	public static void solve(Pentomino[] pentominoes, boolean[] used, int[][] board) {
		for (int i = 0; i < board.length; i++)
			System.out.println(Arrays.toString(board[i]));
		System.out.println("Done!");
		boolean solved = false;
		if (board.length * board[0].length / 5 != pentominoes.length)
			return;
		for (Boolean b : used) {
			if (b)
				solved = true;
			else {
				solved = false;
				break;
			}
		}
		if (solved) {
			System.out.println("Solved!");
			System.exit(0);
		}
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] == 0) {
					System.out.println("Now checking: " + row + ":" + col);
					for (int i = 0; i < pentominoes.length; i++) {
						//System.out.println("Trying next shape: " + i + " at " + col + ":" + row + "...");
						System.out.println("Has " + i + " been used? " + (used[i] ? "Yes!" : "No!"));
						if (!used[i]) {
							System.out.println(i);
							for (Pentomino p : getAllFormats(pentominoes[i])) {
								if (inBounds(row, col, p, board) && checkPiece(row, col, p, board)) {
									System.out.println(i + " works at " + col + " : " + row + "!");
									int[][] newBoard = placePiece(row, col, p, board);
									boolean[] newUsed = new boolean[used.length];
									for (int j = 0; j < newUsed.length; j++) {
										newUsed[j] = used[j];
									}
									newUsed[i] = true;
									solve(pentominoes, newUsed, newBoard);
								}
							}
						}
					}
					System.out.println("No solutions were found... backing up...");
					return;
				}
			}
		}
	}

	static boolean inBounds(int x, int y, Pentomino p, int[][] board) {
		//System.out.println((x - p.getPinPoint().x) + " : " + (y - p.getPinPoint().y) + " : " + (x + p.getShape().length) + " : " + (y + p.getShape()[0].length - p.getPinPoint().y) + " : " + board.length + " : " + board[0].length);
		if (x - p.getPinPoint().x < 0 || y - p.getPinPoint().y < 0 || x + p.getShape().length > board.length || y + p.getShape()[0].length - p.getPinPoint().y > board[0].length) {
			//System.out.println("Does not fit in bounds");
			return false;
		}
		//System.out.println("Does fit in bounds!");
		return true;
	}

	static boolean checkPiece(int x, int y, Pentomino p, int[][] board) {
		for (int i = 0; i < p.getShape().length; i++) {
			for (int j = 0; j < p.getShape()[0].length; j++) {
				if (p.getShape()[i][j] != 0 && board[x + i - p.getPinPoint().x][y + j - p.getPinPoint().y] != 0)
					return false;
			}
		}
		return true;
	}

	static int[][] placePiece(int x, int y, Pentomino p, int[][] board) {
		int[][] newBoard = new int[board.length][board[0].length];
		for (int i = 0; i < newBoard.length; i++) {
			for (int j = 0; j < newBoard[0].length; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		for (int i = 0; i < p.getShape().length; i++) {
			for (int j = 0; j < p.getShape()[0].length; j++) {
				if (p.getShape()[i][j] != 0)
					newBoard[x + i - p.getPinPoint().x][y + j - p.getPinPoint().y] = p.getShape()[i][j];
			}
		}
		return newBoard;
	}

	public static Pentomino[] getAllFormats(Pentomino p) {
		Pentomino[] pentominoes = new Pentomino[8];
		int currentIndex = 0;
		for (int i = 0; i < 4; i++) {
			pentominoes[currentIndex] = new Pentomino(p.getRotatedFormat(i));
			pentominoes[currentIndex + 1] = new Pentomino(new Pentomino(p.getHorizontalMirroredFormat()).getRotatedFormat(i));
			currentIndex += 2;
		}
		return pentominoes;
	}
}
