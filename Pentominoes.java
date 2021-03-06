package data;

public enum Pentominoes {
		F(new Pentomino(new int[][]{{0, 1, 1},
		 	   						{1, 1, 0},
		 	   						{0, 1, 0}})),
		I(new Pentomino(new int[][] {{2},
									 {2},
									 {2},
									 {2},
									 {2}})),
		L(new Pentomino(new int[][] {{3, 0},
		 		   		   			 {3, 0},
		 		   		   			 {3, 0},
		 		   		   			 {3, 3}})),
		N(new Pentomino(new int[][] {{0, 4},
						   			 {0, 4},
						   			 {4, 4},
						   			 {4, 0}})),
		P(new Pentomino(new int[][] {{5, 5},
		 		   		   			 {5, 5},
		 		   		   			 {5, 0}})),
		T(new Pentomino(new int[][] {{6, 6, 6},
		 		   		   			 {0, 6, 0},
		 		   		   			 {0, 6, 0}})),
		U(new Pentomino(new int[][] {{7, 0, 7},
		 		   		   		 	 {7, 7, 7}})),
		V(new Pentomino(new int[][] {{0, 0, 8},
		 		   		   			 {0, 0, 8},
		 		   		   			 {8, 8, 8}})),
		W(new Pentomino(new int[][] {{0, 0, 9},
		 		   		   		 	 {0, 9, 9},
		 		   		   		 	 {9, 9, 0}})),
		X(new Pentomino(new int[][] {{0, 10, 0},
		 		   		   			 {10, 10, 10},
		 		   		   			 {0, 10, 0}})),
		Y(new Pentomino(new int[][] {{0, 11},
		 		   		   			 {11, 11},
		 		   		   			 {0, 11},
		 		   		   			 {0, 11}})),
		Z(new Pentomino(new int[][] {{12, 12, 0},
		 		   		   			 {0, 12, 0},
		 		   		   			 {0, 12, 12}}));
	
		private Pentomino p;
		
		Pentominoes(Pentomino p){
			this.p = p;
		}
		
		public Pentomino getPentomino() {
			return p;
		}
}
