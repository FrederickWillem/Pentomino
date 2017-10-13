package data;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class ControlPanel extends JFrame {
    
    public static long startTime = 0;
    public Panel p;
    public static boolean flip;

    public ControlPanel () {
        super("Pentomino Rectangle Builder");
        
        //set layout manager
        setVisible(true);
        //setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
        
        //create swing component
        JLabel inputLetterLB = new JLabel(" Input letters (no spaces): "), widthLB = new JLabel(" Input Width: "), heightLB = new JLabel(" Input Height: "), delayLB = new JLabel(" Delay: "); 
        JTextField inputLetter = new JTextField("pxfvwyitzunl", 15), widthInput = new JTextField("12", 15), heightInput = new JTextField("5", 15), delay = new JTextField("50", 15);
        JRadioButton all = new JRadioButton("All Solutions"), nothing = new JRadioButton ("First");
        ButtonGroup group = new ButtonGroup();
        JButton build = new JButton("Build Pentomino Rectangle!");

        //listening for events to happen
        group.add(all);
        group.add(nothing);
        
        //add components to pane
        Container cont = getContentPane();
        JPanel c = new JPanel();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        c.add(inputLetterLB, gbc);
        c.add(inputLetter, gbc);
        c.add(delayLB, gbc);
        c.add(delay, gbc);
        c.add(widthLB, gbc);
        c.add(widthInput, gbc);
        c.add(heightLB, gbc);
        c.add(heightInput, gbc);
        c.add(all, gbc);
        c.add(nothing, gbc);
        c.add(build, gbc);
        cont.add(c);
        pack();
        setState(JFrame.ICONIFIED);
        setState(JFrame.NORMAL);
        repaint();
        build.addActionListener(new ActionListener( ) {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                //storing letters
                String input = inputLetter.getText();
                Pentomino[] pentominoes;
                if (input.toLowerCase().equals("all")) {
                	input = "filnptuvwxyz";
                }
                pentominoes = new Pentomino[input.length()];
                for (int i = 0; i < input.length(); i++) {
                    for (Pentominoes p : Pentominoes.values()) {
                        if (input.substring(i, i + 1).toUpperCase().equals(p.name()))
                            pentominoes[i] = p.getPentomino();
                    }
                }
                
                //building the board
                int width = Integer.parseInt(widthInput.getText());
                int height = Integer.parseInt(heightInput.getText());
                
                if (height > width)
                	flip = true;
                
                Solver.allSolutions = (all.isSelected() ? true : false);
                startTime = System.nanoTime();
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                new Thread(new Runnable() {

					@Override
					public void run() {
		                start(width, height, pentominoes, flip, Integer.parseInt(delay.getText()));
					}
                	
                }).start();
            }
        });
    }
    
    
    
    public void start(int width, int height, Pentomino[] pentominoes, boolean flip, int delay) {
    	dispose();
    	for (Pentomino p : pentominoes) {
    		p.computeAllFormats();
    	}
    	new Thread(new Runnable() {
			@Override
			public void run() {
				PentominoBuilder.drawBoard();
			}
    	}).start();
    	Solver.solve(new boolean[pentominoes.length], new int[flip ? width : height][flip ? height : width], pentominoes, delay);
    	if (!Solver.solved && Solver.solutions.isEmpty())
    		JOptionPane.showMessageDialog(null,"Didn't manage to solve it... it took me " + (System.nanoTime() - startTime) * Math.pow(10, -6) + " milliseconds to realise this.");
    	System.out.println("I managed to find " + Solver.solutions.size() + " solution" + (Solver.solutions.size() > 1 ? "s" : "") + " for you! Give me a moment to remove the symmetry...");
    	System.out.println("It took me a total of " + (System.nanoTime() - startTime) * Math.pow(10, -6) + " milliseconds!");
    	//Removing any symmetrical solutions which may still be present in the list of solutions (dependent on user input).
    	ArrayList<int[][]> solutions = Solver.removeSymmetry();
    	System.out.println("Alright, that's that... I now have " + solutions.size() + " solutions left.");
    	//Returning the output to the user. Depending on the amount of solutions found, the user can scroll through them as they please, using an input.
    	Solver.publicBoard = solutions.get(0);
    	p.repaint();
    	while (solutions.size() > 1) {
    		int i;
    		String input = null;
    		try {
    			input = JOptionPane.showInputDialog(this, "Which solution would you like to see? Input the number in the list (1<=input<=" + solutions.size() + ")");
    			i = Integer.parseInt(input);
    		} catch (NumberFormatException e) {
    			if (input == null)
    				System.exit(0);
    			JOptionPane.showMessageDialog(null,"Invalid input! Now showing: solution 1");
    			i = 1;
    		}
    		i %= solutions.size();
    		if (i == 0)
    			i = solutions.size();
    		if (flip)
    			Solver.publicBoard = Solver.getRotatedFormat(1, solutions.get(i - 1));
    		else
    			Solver.publicBoard = solutions.get(i - 1);
    		p.repaint();
    	}
    			
    }
    

}