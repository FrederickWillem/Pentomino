package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;

public class Startup {
	
	public static long startTime = 0;
	public static Panel p;
	
	public static void main(String[] args) {
		//Creating a scanner and asking the user for the letters to be used. These are then transformed into Pentomino's, which the program can use.
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input the letters you wish to use as if writing a word (not case sensitive):");
		String input = sc.nextLine();
		Pentomino[] pentominoes;
		if (input.toLowerCase().equals("all")) {
			pentominoes = new Pentomino[]{Pentominoes.F.getPentomino(), Pentominoes.I.getPentomino(), Pentominoes.L.getPentomino(), Pentominoes.N.getPentomino(), Pentominoes.P.getPentomino(),
					Pentominoes.T.getPentomino(), Pentominoes.U.getPentomino(), Pentominoes.V.getPentomino(), Pentominoes.W.getPentomino(), Pentominoes.X.getPentomino(),
					Pentominoes.Y.getPentomino(), Pentominoes.Z.getPentomino()};
		} else {
			pentominoes = new Pentomino[input.length()];
			for (int i = 0; i < input.length(); i++) {
				for (Pentominoes p : Pentominoes.values()) {
					if (input.substring(i, i + 1).toUpperCase().equals(p.name()))
						pentominoes[i] = p.getPentomino();
				}
			}
		}
		
		//Asking the user for any other input which are required, such as the boaard's width, height and whether the user wishes to see every solution or not.
		System.out.println("Please give the width of the board:");
		int width = sc.nextInt();
		System.out.println("Please give the height of the board:");
		int height = sc.nextInt();
		sc.nextLine();
		System.out.println("Do you want all the solutions or just the first? If so, type all. If not, type whatever you want.");
		input = sc.nextLine();
		Solver.allSolutions = (input.toLowerCase().equals("all") ? true : false);
		
		//Setting the start time to keep track of speed.
		startTime = System.nanoTime();
		
		//Computing every possible orientation for every pentomino used. Done here so that it needs not be done every loop.
		for (Pentomino p : pentominoes) {
			p.computeAllFormats();
		}
		
		//Creating a new Thread to draw the board and the pentominoes, so as to make sure we can update the frame while looping through solutions
		new Thread(new Runnable() {
			@Override
			public void run() {
				drawBoard();				
			}
		}).start();
		
		//Calling the solve method.
		Solver.solve(new boolean[pentominoes.length], new int[height][width], pentominoes);
		
		//Checking whether any solutions have been found. If not, the program will shut down.
		if (!Solver.solved && Solver.solutions.isEmpty()) {
			System.out.println("Didn't manage to solve it... it took me " + (System.nanoTime() - startTime) * Math.pow(10, -6) + " milliseconds to realise this.");
			System.exit(0);
		}
		
		//Removing any symmetrical solutions which may still be present in the list of solutions (dependent on user input).
		ArrayList<int[][]> solutions = Solver.removeSymmetry();
		
		//Returning the output to the user. Depending on the amount of solutions found, the user can scroll through them as they please, using an input.
		System.out.println("I managed to find " + solutions.size() + " solution" + (solutions.size() > 1 ? "s" : "") + " for you!");
		System.out.println("It took me a total of " + (System.nanoTime() - startTime) * Math.pow(10, -6) + " milliseconds!");
		if (solutions.size() > 1)
			System.out.println("Which solution would you like to see? Input the number in the list (1<=input<=" + solutions.size() + ")");
		while (solutions.size() > 1) {
			Solver.publicBoard = solutions.get(sc.nextInt() - 1);
			p.repaint();
		}
		sc.close();
	}
	
	//Simple method creating a new frame of the correct size and adding a panel to it, which will be responsible for drawing the board.
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
