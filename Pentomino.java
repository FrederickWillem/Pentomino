package data;

import java.awt.Color;
import java.awt.Point;

public class Pentomino {

	int[][] shape;
	Color color;
	int id;

	public Pentomino(int[][] shape) {
		this.shape = shape;
	}
	
	public int getID() {
		return id;
	}
	
	public int[][] getShape() {
		return shape;
	}
	
	public Point getPinPoint() {
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				if (shape[i][j] == 1)
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
		int[][] p2;
		if (rotation == 1 || rotation == 3)
			p2 = new int[shape[0].length][shape.length];
		else
			p2 = new int[shape.length][shape[0].length];
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				switch (rotation) {
				case 1: // turn 90 degrees
					p2[j][(shape.length - 1) - i] = shape[i][j];
					break;
				case 2: // turn 180 degrees
					p2[(shape.length - 1) - i][(shape[i].length - 1) - j] = shape[i][j];
					break;
				case 3: // turn 270 degrees
					p2[(shape[i].length - 1) - j][i] = shape[i][j];
					break;
				}
			}
		}
		return p2;
	}

	public int[][] getHorizontalMirroredFormat() {
		int[][] p2 = new int[shape.length][shape[0].length];
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				p2[i][(shape[i].length - 1) - j] = shape[i][j];
			}
		}
		return p2;
	}
}
