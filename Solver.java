package data;

public class Solver {
	
	static int count = 0;
	static boolean solved;
	public static int[][] publicBoard;

	public static void solve(boolean[] used, int[][] board, Pentomino... pentominoes) {
		publicBoard = new int[board.length][board[0].length];
		tempBoard = new int[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				tempBoard[i][j] = board[i][j];
				publicBoard[i][j] = board[i][j];
			}
		}
 		count = 0;
 		if (Startup.p != null)
 			Startup.p.repaint();
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
		if (solved)
			System.out.println("Solved in " + (System.nanoTime() - Startup.startTime) * Math.pow(10, -6) + " milliseconds");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] == 0) {
					gapSizeCheck(row, col);
					if (count % 5 != 0) {
						return;
					}
					for (int i = 0; i < pentominoes.length; i++) {
						if (!used[i]) {
							for (Pentomino p : getAllFormats(pentominoes[i])) {
								if (inBounds(row, col, p, board) && checkPiece(row, col, p, board)) {
									int[][] newBoard = placePiece(row, col, p, board);
									boolean[] newUsed = new boolean[used.length];
									for (int j = 0; j < newUsed.length; j++) {
										newUsed[j] = used[j];
									}
									newUsed[i] = true;
									solve(newUsed, newBoard, pentominoes);
									if (solved)
										return;
								}
							}
						}
					}
					return;
				}
			}
		}
	}
	
	static int[][] tempBoard;
	
	static void gapSizeCheck(int y, int x) {
		tempBoard[y][x] = -1;
		count++;
		if (tempBoard[Math.max(y - 1, 0)][x] == 0) {
			gapSizeCheck(Math.max(y - 1, 0), x);
		}
		if (tempBoard[y][Math.max(x - 1, 0)] == 0) {
			gapSizeCheck(y, Math.max(x - 1, 0));
		}
		if (tempBoard[Math.min(tempBoard.length - 1, y + 1)][x] == 0)
			gapSizeCheck(Math.min(tempBoard.length - 1, y + 1), x);
		if (tempBoard[y][Math.min(tempBoard[0].length - 1, x + 1)] == 0)
			gapSizeCheck(y, Math.min(tempBoard[0].length - 1, x + 1));
	}

	static boolean inBounds(int y, int x, Pentomino p, int[][] board) {
		if (y - p.getPinPoint().y < 0 || x - p.getPinPoint().x < 0 || y + p.getShape().length > board.length || x + p.getShape()[0].length - p.getPinPoint().y > board[0].length) {
			return false;
		}
		return true;
	}

	static boolean checkPiece(int y, int x, Pentomino p, int[][] board) {
		for (int i = 0; i < p.getShape().length; i++) {
			for (int j = 0; j < p.getShape()[0].length; j++) {
				if (p.getShape()[i][j] != 0 && board[y + i - p.getPinPoint().y][x + j - p.getPinPoint().x] != 0)
					return false;
			}
		}
		return true;
	}

	static int[][] placePiece(int y, int x, Pentomino p, int[][] board) {
		int[][] newBoard = new int[board.length][board[0].length];
		for (int i = 0; i < newBoard.length; i++) {
			for (int j = 0; j < newBoard[0].length; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		for (int i = 0; i < p.getShape().length; i++) {
			for (int j = 0; j < p.getShape()[0].length; j++) {
				if (p.getShape()[i][j] != 0)
					newBoard[y + i - p.getPinPoint().y][x + j - p.getPinPoint().x] = p.getShape()[i][j];
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
