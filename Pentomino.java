package data;

import java.awt.Point;

public class Pentomino {

	private int[][] shape;
	private Pentomino[] allFormats;

	public Pentomino(int[][] shape) {
		this.shape = shape;
	}
	
	public int[][] getShape() {
		return shape;
	}
	
	public Point getPinPoint() {
		for (int i = 0; i < shape[0].length; i++) {
			for (int j = 0; j < shape.length; j++) {
				if (shape[j][i] != 0)
					return new Point(i, j);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param rotation
	 *            For a rotation to clockwise 90 degrees: 1
	 * 
	 */
	public int[][] getRotatedFormat(int rotation) {
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

	public int[][] getHorizontalMirroredFormat() {
		int[][] tempShape = new int[shape.length][shape[0].length];
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				tempShape[i][(shape[i].length - 1) - j] = shape[i][j];
			}
		}
		return tempShape;
	}
	
	public void computeAllFormats() {
		Pentomino[] pentominoes = new Pentomino[8];
		pentominoes[0] = this;
		int currentIndex = 1;
		boolean canBeAdded = true;
		for (int rotation = 0; rotation < 4; rotation++) {
			for (int i = 0; i < pentominoes.length; i++) {
				if (isEqual(pentominoes[i], new Pentomino(getRotatedFormat(rotation)))) {
					canBeAdded = false;
				}
			}
			if (canBeAdded) {
				pentominoes[currentIndex] = new Pentomino(getRotatedFormat(rotation));
				currentIndex++;
			}
			canBeAdded = true;
			for (int i = 0; i < pentominoes.length; i++) {
				if (isEqual(pentominoes[i], new Pentomino(new Pentomino(getRotatedFormat(rotation)).getHorizontalMirroredFormat()))) {
					canBeAdded = false;
				}
			}
			if (canBeAdded) {
				pentominoes[currentIndex] = new Pentomino(new Pentomino(getRotatedFormat(rotation)).getHorizontalMirroredFormat());
				currentIndex++;
			}
		}
		allFormats = pentominoes;
	}
	
	boolean isEqual(Pentomino pent1, Pentomino pent2) {
		if (pent1 == null || pent2 == null)
			return false;
		if (pent1.getShape().length != pent2.getShape().length || pent1.getShape()[0].length != pent2.getShape()[0].length)
			return false;
		for (int i = 0; i < pent1.getShape().length; i++) {
			for (int j = 0; j < pent1.getShape()[0].length; j++) {
				if (pent1.getShape()[i][j] != pent2.getShape()[i][j])
					return false;
			}
		}
		return true;
	}
	
	public Pentomino[] getAllFormats() {
		return allFormats;
	}
}
