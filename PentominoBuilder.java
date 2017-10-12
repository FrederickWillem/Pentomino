package data;

import javax.swing.JFrame;

public class PentominoBuilder {

	public static ControlPanel cp;
	
	public static void main(String[] args) {
    	cp = new ControlPanel();
    }
	
    //Simple method creating a new frame of the correct size and adding a panel to it, which will be responsible for drawing the board.
  	public static void drawBoard() {
  		JFrame frame = new JFrame();
  		frame.setSize((Solver.publicBoard[0].length + 1) * 50, (Solver.publicBoard.length + 1) * 50);
  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		cp.p = new Panel();
  		frame.add(cp.p);
  		frame.setVisible(true);
  		frame.setState(JFrame.ICONIFIED);
  		frame.setState(JFrame.NORMAL);
  		frame.repaint();
  	}
}
