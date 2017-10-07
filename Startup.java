package data;

import javax.swing.JFrame;

public class Startup {
	
	public static long startTime = 0;
	public static Panel p;
	
	public static void main(String[] args) {
		startTime = System.nanoTime();
		Pentomino[] allPentominoes = {Pentominoes.F.getPentomino(), Pentominoes.I.getPentomino(), Pentominoes.L.getPentomino(), Pentominoes.N.getPentomino(), Pentominoes.P.getPentomino(),
									Pentominoes.T.getPentomino(), Pentominoes.U.getPentomino(), Pentominoes.V.getPentomino(), Pentominoes.W.getPentomino(), Pentominoes.X.getPentomino(),
									Pentominoes.Y.getPentomino(), Pentominoes.Z.getPentomino()};
		//Pentomino[] pentominoes = {Pentominoes.U.getPentomino(), Pentominoes.X.getPentomino(), Pentominoes.I.getPentomino(), Pentominoes.W.getPentomino(), Pentominoes.F.getPentomino(), Pentominoes.P.getPentomino(), Pentominoes.N.getPentomino(), Pentominoes.V.getPentomino()};
		new Thread(new Runnable() {
			@Override
			public void run() {
				drawBoard();				
			}
		}).start();
		Solver.solve(new boolean[allPentominoes.length], new int[5][12], allPentominoes);
	}
	
	public static void drawBoard() {
		JFrame frame = new JFrame();
		frame.setSize((Solver.publicBoard.length + 1) * 50, (Solver.publicBoard[0].length + 1) * 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new Panel();
		frame.add(p);
		frame.setVisible(true);
		frame.repaint();
	}
}
