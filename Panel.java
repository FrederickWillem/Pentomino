package data;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4121946950366401500L;
	
	public Panel() {
		setBounds(0, 0, Solver.publicBoard.length * 50, Solver.publicBoard[0].length * 50);
		setVisible(true);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color[] colors = new Color[]{new Color(0, 150, 0), Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.BLACK, Color.DARK_GRAY, Color.GRAY, Color.YELLOW, Color.WHITE};
		for (int i = 0; i < Solver.publicBoard.length; i++) {
			for (int j = 0; j < Solver.publicBoard[0].length; j++) {
				g.setColor(colors[Solver.publicBoard[i][j]]);
				g.fillRect(i * 50, j * 50, 50, 50);
			}
		}
	}
}
