package it.unibo.ai.didattica.competition.tablut.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class WhiteHeuristic extends Heuristic {
	
	private final int WEIGHT_BLACKS_EATEN=0;
	private final int WEIGHT_WHITES_SURVIVED=1;
	private final int WEIGHT_BLACKS_AROUND_KING=2;
	private final int WEIGHT_WHITE_POSITIONS=3;
	private final int WEIGHT_STAR_POSITIONS=4;
	
	private double[] weight_values;
	
	private double totalWhites;
	private double blacksEaten;
	
	private final static int[][] starPositions = {
			      {0,1},{0,2}, 
			      {0,6},{0,7},
			      {1,0},{1,8},
			      {2,0},{2,8},
                  {6,0},{6,8},
                  {7,0},{7,8},
                  {8,1},{8,2}, 
                  {8,6},{8,7}
	};
	
	private final static int[][] whitePositions = {
			{5,7},{7,5}	
	};
	
	
	public WhiteHeuristic(State state) {
		super(state);
		weight_values=new double[5];
		weight_values[WEIGHT_BLACKS_EATEN]=20;   //25 ;//27.40;
		weight_values[WEIGHT_WHITES_SURVIVED]=30;   //45;//47.95;
		weight_values[WEIGHT_BLACKS_AROUND_KING]=17;  //22;//24.65;
		weight_values[WEIGHT_WHITE_POSITIONS]=8;    //8;
		weight_values[WEIGHT_STAR_POSITIONS]=25 ;
	}

	@Override
	public double evaluateState() {
		totalWhites=(double) state.getNumberOf(State.Pawn.WHITE) / Heuristic.NUM_WHITE;
		blacksEaten=(double) (Heuristic.NUM_BLACK - state.getNumberOf(State.Pawn.BLACK)) / Heuristic.NUM_BLACK;
		double blacksAroundKing= (double) (positionsAroundKing(state) - checkPawnsNearKing(state)) / positionsAroundKing(state);
		double whitePawnsOnWhitePositions= getWhitePositionsOccupied();
		double kingNearEscapeStarPositions= calculateEscapeValue();
		
		
		return weight_values[WEIGHT_BLACKS_EATEN]*blacksEaten+
				weight_values[WEIGHT_WHITES_SURVIVED]*totalWhites+
				weight_values[WEIGHT_BLACKS_AROUND_KING]*blacksAroundKing+
				weight_values[WEIGHT_WHITE_POSITIONS]*whitePawnsOnWhitePositions+
				weight_values[WEIGHT_STAR_POSITIONS]*kingNearEscapeStarPositions;

	}
	
	
	public double getWhitePositionsOccupied() {
		double count=0;
		for(int i=0;i<whitePositions.length;i++) {
			if(state.getPawn(whitePositions[i][0], whitePositions[i][1]).equalsPawn("W") ) {
				count++;
			}
		}
		return count/2;
	}
	
	public double calculateEscapeValue() {
		int[] kingposition=new int[2];
    	kingposition=getKingPosition(state);
    	int numberOfPathsOnRow=0;
    	int numberOfPathsOnColumn=0;
		if(kingposition[0]==1 || kingposition[0]==2 || kingposition[0]==6 || kingposition[0]==7) {
			numberOfPathsOnRow=pathsRowWithNoBlacks(kingposition);
		}
		if(kingposition[1]==1 || kingposition[1]==2 || kingposition[1]==6 || kingposition[1]==7) {
			numberOfPathsOnColumn=pathsColumnWithNoBlacks(kingposition);
		}
		return (numberOfPathsOnRow+numberOfPathsOnColumn)/4;
			
	}
	
	
	private int pathsRowWithNoBlacks(int[] kingPosition) { //qua ciclo su riga
		State.Pawn[][] board = state.getBoard();
		boolean occupiedPath=false;
		int openPaths=0;
		for (int j = 0; j < kingPosition[1]; j++) {
            	if(!state.getPawn(kingPosition[0], j).equalsPawn(State.Pawn.EMPTY.toString())){
            		occupiedPath=true;
            		break;
            	}
		}
		if(!occupiedPath)
			openPaths++;
		
		occupiedPath=false;
		for(int j=kingPosition[1]+1; j<board.length;j++) {
			if(!state.getPawn(kingPosition[0], j).equalsPawn(State.Pawn.EMPTY.toString())){
        		occupiedPath=true;
        		break;
        	}
		}
		if(!occupiedPath)
			openPaths++;
		
		return openPaths;
	}
	
	private int pathsColumnWithNoBlacks(int[] kingPosition) {
		State.Pawn[][] board = state.getBoard();
		boolean occupiedPath=false;
		int openPaths=0;
		for(int i=0;i<kingPosition[0];i++) {
			if(!state.getPawn(i,kingPosition[1]).equalsPawn(State.Pawn.EMPTY.toString())) {
				occupiedPath=true;
        		break;
			}
		}
		if(!occupiedPath)
			openPaths++;
		
		occupiedPath=false;
		for(int i=kingPosition[0]+1; i<board.length;i++) {
			if(!state.getPawn(i,kingPosition[1]).equalsPawn(State.Pawn.EMPTY.toString())){
        		occupiedPath=true;
        		break;
        	}
		}
		
		if(!occupiedPath)
			openPaths++;
		
		return openPaths;
		
		
	}

}