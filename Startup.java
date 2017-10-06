package data;

public class Startup {
	
	public static void main(String[] args) {
		Pentomino[] allPentominoes = {Pentominoes.F.getPentomino(), Pentominoes.I.getPentomino(), Pentominoes.L.getPentomino(), Pentominoes.N.getPentomino(), Pentominoes.P.getPentomino(),
									Pentominoes.T.getPentomino(), Pentominoes.U.getPentomino(), Pentominoes.V.getPentomino(), Pentominoes.W.getPentomino(), Pentominoes.X.getPentomino(),
									Pentominoes.Y.getPentomino(), Pentominoes.Z.getPentomino()};
		//Pentomino[] pentominoes = {Pentominoes.U.getPentomino(), Pentominoes.X.getPentomino(), Pentominoes.I.getPentomino(), Pentominoes.W.getPentomino(), Pentominoes.F.getPentomino(), Pentominoes.P.getPentomino(), Pentominoes.N.getPentomino(), Pentominoes.V.getPentomino()};
		Solver.solve(allPentominoes, new boolean[allPentominoes.length], new int[12][5]);
	}
}