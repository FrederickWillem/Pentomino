package data;

public class Pentomino {

	private int[][] shape;
	private Pentomino[] allFormats;

	//A simple object pentomino which holds naught more than its shape and every possible orientation of it.
	//These orientations could be stored anywhere, but it makes sense to store them locally.
	public Pentomino(int[][] shape) {
		this.shape = shape;
	}
	
	//Getter for the two-dimensional array stored in a pentomino.
	public int[][] getShape() {
		return shape;
	}
	
	
	//Getter for the list of formats stored in a pentomino.
	public Pentomino[] getAllFormats() {
		return allFormats;
	}
	
	//Getter for the y-coordinate of the pinpoint of a pentomino. In essence, the pinpoint is the left-most top-most (respectively) place of a pentomino. Therefore, the x-coordinate is always 0.
	public int getPinPointY() {
		for (int i = 0; i < shape[0].length; i++) {
			for (int j = 0; j < shape.length; j++) {
				if (shape[j][i] != 0)
					return j;
			}
		}
		return 0;
	}
	
	//Method which adds all possible orientations for every pentomino  (rotated and mirrored) to the array allFormats. It checks for any double ones and does not add these.
	public void computeAllFormats() {
		Pentomino[] pentominoes = new Pentomino[8];
		pentominoes[0] = this;
		int currentIndex = 1;
		boolean canBeAdded = true;
		for (int rotation = 0; rotation < 4; rotation++) {
			for (int i = 0; i < pentominoes.length; i++) {
				if (Solver.isEqual((pentominoes[i] == null ? null : pentominoes[i].getShape()), Solver.getRotatedFormat(rotation, shape))) {
					canBeAdded = false;
				}
			}
			if (canBeAdded) {
				pentominoes[currentIndex] = new Pentomino(Solver.getRotatedFormat(rotation, shape));
				currentIndex++;
			}
			canBeAdded = true;
			for (int i = 0; i < pentominoes.length; i++) {
				if (Solver.isEqual((pentominoes[i] == null ? null : pentominoes[i].getShape()), Solver.getHorizontalMirroredFormat(Solver.getRotatedFormat(rotation, shape)))) {
					canBeAdded = false;
				}
			}
			if (canBeAdded) {
				pentominoes[currentIndex] = new Pentomino(Solver.getHorizontalMirroredFormat(Solver.getRotatedFormat(rotation, shape)));
				currentIndex++;
			}
		}
		allFormats = pentominoes;
	}
}
