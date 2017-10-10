package data;

import java.util.Scanner;

import javax.swing.JFrame;

public class Startup {
	
	public static long startTime = 0;
	public static Panel p;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input the letters you wish to use as if writing a word:");
		String input = sc.nextLine();
		Pentomino[] pentominoes = new Pentomino[input.length()];
		for (int i = 0; i < input.length(); i++) {
			for (Pentominoes p : Pentominoes.values()) {
				if (input.substring(i, i + 1).toUpperCase().equals(p.name()))
					pentominoes[i] = p.getPentomino();
			}
		}
		System.out.println("Please give the width of the board:");
		int width = sc.nextInt();
		System.out.println("Please give the height of the board:");
		int height = sc.nextInt();
		sc.nextLine();
		System.out.println("Do you want all the solutions or just the first? If so, type all. If not, type whatever you want.");
		input = sc.nextLine();
		Solver.allSolutions = (input.toLowerCase().equals("all") ? true : false);
		startTime = System.nanoTime();
		Pentomino[] allPentominoes = {Pentominoes.F.getPentomino(), Pentominoes.I.getPentomino(), Pentominoes.L.getPentomino(), Pentominoes.N.getPentomino(), Pentominoes.P.getPentomino(),
									Pentominoes.T.getPentomino(), Pentominoes.U.getPentomino(), Pentominoes.V.getPentomino(), Pentominoes.W.getPentomino(), Pentominoes.X.getPentomino(),
									Pentominoes.Y.getPentomino(), Pentominoes.Z.getPentomino()};
		//Pentomino[] pentominoes = {Pentominoes.U.getPentomino(), Pentominoes.X.getPentomino(), Pentominoes.I.getPentomino(), Pentominoes.W.getPentomino(), Pentominoes.F.getPentomino(), Pentominoes.V.getPentomino(), Pentominoes.P.getPentomino(), Pentominoes.N.getPentomino()};
		for (Pentomino p : allPentominoes) {
			p.computeAllFormats();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				drawBoard();				
			}
		}).start();
		Solver.solve(new boolean[pentominoes.length], new int[height][width], pentominoes);
		if (!Solver.solved && Solver.solutions.isEmpty())
			System.out.println("Didn't manage to solve it... it took me " + (System.nanoTime() - startTime) * Math.pow(10, -6) + " milliseconds to realise this.");
		System.out.println("I managed to find " + Solver.solutions.size() + " solution" + (Solver.solutions.size() > 1 ? "s" : "") + " for you!");
		System.out.println("Which solution would you like to see? Input the number in the list (1<=input<=" + Solver.solutions.size() + ")");
		while (true) {
			Solver.publicBoard = Solver.solutions.get(sc.nextInt() - 1);
			p.repaint();
		}
	}
	
	public static void drawBoard() {
		JFrame frame = new JFrame();
		frame.setSize((Solver.publicBoard[0].length + 1) * 50, (Solver.publicBoard.length + 1) * 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new Panel();
		frame.add(p);
		frame.setVisible(true);
		frame.repaint();
	}
}
