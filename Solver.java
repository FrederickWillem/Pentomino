package data;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {

	public static boolean solved;
	public static int[][] publicBoard;
	public static ArrayList<int[][]> solutions = new ArrayList<>();
	public static boolean allSolutions;
	public static int delay;

	public static void solve(boolean[] used, int[][] board, Pentomino[] pentominoes) {
		//Checking whether the given board is a multiple of 5 (otherwise it cannot be solved).
		if (board.length * board[0].length % 5 != 0 || board.length == 0 || board[0].length == 0)
			return;

		//Updating several int[][]'s. publicBoard is the board Panel.java uses to draw the current state of the board.
		//tempBoard is the board used in the gapSizeCheck method. Its 0's will be changed to -1's and for this reason, tempBoard must be reset on every loop.
		if (Startup.flip)
			publicBoard = new int[board[0].length][board.length];
		else
			publicBoard = new int[board.length][board[0].length];
		tempBoard = new int[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				tempBoard[i][j] = board[i][j];
				if (!Startup.flip)
					publicBoard[i][j] = board[i][j];
			}
		}
		if (Startup.flip)
			publicBoard = getRotatedFormat(1, board);
		//Count is also used in gapCheckSize and must be reset.
 		count = 0;

 		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

 		//If the panel has been created (!= null check), it needs to be updated on every loop.
 		if (Startup.p != null)
 			Startup.p.repaint();

 		//Checking whether every cell has been filled.
 		//If so, the puzzle is solved and, depending on the user input, we can stop checking entirely or keep going and looking for more solutions.
 		solved = true;
 		for (int i = 0; i < board.length; i++) {
 			for (int j = 0; j < board[0].length; j++) {
 				if (board[i][j] == 0)
 					solved = false;
 			}
 		}
		if (solved) {
			solutions.add(board);
			if (!allSolutions)
				return;
		}

		for (int col = 0; col < board[0].length; col++) {
			for (int row = 0; row < board.length; row++) {
				if (board[row][col] == 0) {
					if (gapSizeCheck(row, col) % 5 != 0)
						return;
					for (int i = 0; i < pentominoes.length; i++) {
						if (!used[i]) {
							for (Pentomino p : pentominoes[i].getAllFormats()) {
								if (p != null) {
									if (inBounds(row, col, p, board) && checkPiece(row, col, p, board)) {
										int[][] newBoard = placePiece(row, col, p, board);
										boolean[] newUsed = new boolean[used.length];
										for (int j = 0; j < newUsed.length; j++) {
											newUsed[j] = used[j];
										}
										newUsed[i] = true;
										solve(newUsed, newBoard, pentominoes);
										if (!allSolutions && solved)
											return;
									}
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
	static int count = 0;

	static int gapSizeCheck(int y, int x) {
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
		return count;
	}

	static boolean inBounds(int y, int x, Pentomino p, int[][] board) {
		if (y - p.getPinPointY() < 0 || y + p.getShape().length - p.getPinPointY() > board.length || x + p.getShape()[0].length > board[0].length) {
			return false;
		}
		return true;
	}

	static boolean checkPiece(int y, int x, Pentomino p, int[][] board) {
		for (int i = 0; i < p.getShape().length; i++) {
			for (int j = 0; j < p.getShape()[0].length; j++) {
				if (p.getShape()[i][j] != 0 && board[y + i - p.getPinPointY()][x + j] != 0) {
					return false;
				}
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
					newBoard[y + i - p.getPinPointY()][x + j] = p.getShape()[i][j];
			}
		}
		return newBoard;
	}

	public static int[][] getRotatedFormat(int rotation, int[][] shape) {
		rotation %= 4;
		if (rotation == 0)
			return shape;
		int[][] tempShape;
		if (rotation == 1 || rotation == 3)
			tempShape = new int[shape[0].length][shape.length];
		else
			tempShape = new int[shape.length][shape[0].length];
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				switch (rotation) {
				case 1: // turn 90 degrees
					tempShape[j][(shape.length - 1) - i] = shape[i][j];
					break;
				case 2: // turn 180 degrees
					tempShape[(shape.length - 1) - i][(shape[i].length - 1) - j] = shape[i][j];
					break;
				case 3: // turn 270 degrees
					tempShape[(shape[i].length - 1) - j][i] = shape[i][j];
					break;
				}
			}
		}
		return tempShape;
	}

	public static int[][] getHorizontalMirroredFormat(int[][] shape) {
		int[][] tempShape = new int[shape.length][shape[0].length];
		for (int i = 0; i < shape.length; i++) {
			tempShape[i] = mirrorArray(shape[i]);
		}
		return tempShape;
	}

	public static boolean isEqual(int[][] shape1, int[][] shape2) {
		if (shape1 == null || shape2 == null)
			return false;
		if (shape1.length != shape2.length || shape1[0].length != shape2[0].length)
			return false;
		for (int i = 0; i < shape1.length; i++) {
			if (!arrayEquals(shape1[i], shape2[i]))
				return false;
		}
		return true;
	}


	public static int[] mirrorArray(int[] array) {
		int[] newArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[array.length - 1 - i];
		}
		return newArray;
	}

	public static ArrayList<int[][]> removeSymmetry() {
		ArrayList<int[][]> result = new ArrayList<>();
		if (solutions.size() > 1) {
			System.out.println("Alright, I managed to find " + solutions.size() + " solutions for you! Just give a moment while I remove symmetry...");
			ArrayList<Integer> toBeRemoved = new ArrayList<>();
			for (int i = 0; i < solutions.size(); i++)
				if (!toBeRemoved.contains(i)) {
					int[][] solution1 = solutions.get(i);
					for (int j = 0; j < solutions.size(); j++) {
						int[][] solution2 = solutions.get(j);
						if (arrayEquals(solution1[0], solution2[solution2.length - 1]) && arrayEquals(solution1[solution1.length - 1], solution2[0]))
							toBeRemoved.add(j);
						if (arrayEquals(mirrorArray(solution1[0]), solution2[0]) && arrayEquals(mirrorArray(solution1[solution1.length - 1]), solution2[solution2.length - 1]))
							toBeRemoved.add(j);
						if (arrayEquals(mirrorArray(solution1[0]), solution2[solution2.length - 1]) && arrayEquals(mirrorArray(solution1[solution1.length - 1]), solution2[0]))
							toBeRemoved.add(j);
					}
				}

			//Actually removing all the symmetrical inputs and only putting the unique ones in a new list: solutions.
			for (int i = 0; i < solutions.size(); i++)
				if (!toBeRemoved.contains(i))
					result.add(solutions.get(i));
			return result;
		} else {
			return solutions;
		}
	}

	public static boolean arrayEquals(int[] array1, int[] array2) {
		if (array1 == null || array2 == null)
			return false;
		if (array1.length != array2.length)
			return false;
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i])
				return false;
		}
		return true;
	}
}
