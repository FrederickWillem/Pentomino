package data;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4121946950366401500L;
	
	//Constructor for the panel which extends JPanel. This is done so as to be able to Override paintComponent().
	public Panel() {
		setBounds(0, 0, Solver.publicBoard.length * 50, Solver.publicBoard[0].length * 50);
		setVisible(true);
		repaint();
	}
	
	//Method responsible for drawing the board, using the number displayed in a cell as indication for the color that should be used to display a pentomino.
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color[] colors = new Color[]{Color.BLACK, new Color(255, 177, 125), new Color(254, 188, 205), new Color(166, 255, 186),
				new Color(206, 238, 108), new Color(136, 196, 255), new Color(251, 140, 143), new Color(183, 228, 240), new Color(239, 228, 176),
				new Color(209, 191, 230), new Color(207, 207, 207), new Color(255, 255, 186), new Color(251, 179, 233)};
		for (int i = 0; i < Solver.publicBoard.length; i++) {
			for (int j = 0; j < Solver.publicBoard[0].length; j++) {
				g.setColor(colors[Solver.publicBoard[i][j]]);
				g.fillRect(j * 50, i * 50, 50, 50);
				g.setColor(Color.BLACK);
				g.drawRect(j * 50, i * 50, 50, 50);
			}
		}
	}
}
