package data;

public enum Pentominoes {
		F(new Pentomino(new int[][]{{0, 1, 1},
		 	   						{1, 1, 0},
		 	   						{0, 1, 0}})),
		I(new Pentomino(new int[][] {{1},
									 {1},
									 {1},
									 {1},
									 {1}})),
		L(new Pentomino(new int[][] {{1, 0},
		 		   		   			 {1, 0},
		 		   		   			 {1, 0},
		 		   		   			 {1, 1}})),
		N(new Pentomino(new int[][] {{0, 1},
						   			 {0, 1},
						   			 {1, 1},
						   			 {1, 0}})),
		P(new Pentomino(new int[][] {{1, 1},
		 		   		   			 {1, 1},
		 		   		   			 {1, 0}})),
		T(new Pentomino(new int[][] {{1, 1, 1},
		 		   		   			 {0, 1, 0},
		 		   		   			 {0, 1, 0}})),
		U(new Pentomino(new int[][] {{1, 0, 1},
		 		   		   		 	 {1, 1, 1}})),
		V(new Pentomino(new int[][] {{0, 0, 1},
		 		   		   			 {0, 0, 1},
		 		   		   			 {1, 1, 1}})),
		W(new Pentomino(new int[][] {{0, 0, 1},
		 		   		   		 	 {0, 1, 1},
		 		   		   		 	 {1, 1, 0}})),
		X(new Pentomino(new int[][] {{0, 1, 0},
		 		   		   			 {1, 1, 1},
		 		   		   			 {0, 1, 0}})),
		Y(new Pentomino(new int[][] {{0, 1},
		 		   		   			 {1, 1},
		 		   		   			 {0, 1},
		 		   		   			 {0, 1}})),
		Z(new Pentomino(new int[][] {{1, 1, 0},
		 		   		   			 {0, 1, 0},
		 		   		   			 {0, 1, 1}}));
	
		Pentomino p;
		
		Pentominoes(Pentomino p){
			this.p = p;
		}
		
		public Pentomino getPentomino() {
			return p;
		}
}
