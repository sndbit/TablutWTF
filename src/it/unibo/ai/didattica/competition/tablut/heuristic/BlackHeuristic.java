package it.unibo.ai.didattica.competition.tablut.heuristic;


import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class BlackHeuristic extends Heuristic {

	
	private final int WEIGHT_BLACKS_SURVIVED=0;
	private final int WEIGHT_WHITES_EATEN=1;
	private final int WEIGHT_AROUND_KING=2;
	private final int WEIGHT_STRATEGIC_POSITIONS=3;
	
	private final int[][] strategicPosition= {
			 		{1,2},       {1,6},
             {2,1},                   {2,7},

             {6,1},                   {6,7},
                   {7,2},       {7,6}
	};
	
	private double[] weight_values;
	private double totalBlacks;
	private double whitesEaten;
	
	
	
	
	public BlackHeuristic(State state) {
		super(state);
		weight_values=new double[4];
		weight_values[WEIGHT_BLACKS_SURVIVED]=35;
		weight_values[WEIGHT_WHITES_EATEN]=48;
		weight_values[WEIGHT_AROUND_KING]=15;
		weight_values[WEIGHT_STRATEGIC_POSITIONS]=2;
		
		
	}

	@Override
	public double evaluateState() {
		//Turn turn=state.getTurn();
		if (state.getTurn().equals(State.Turn.BLACKWIN))
			return Double.POSITIVE_INFINITY;
		else if (state.getTurn().equals(State.Turn.WHITEWIN))
			return Double.NEGATIVE_INFINITY;
		else {
			totalBlacks= (double) state.getNumberOf(State.Pawn.BLACK) / Heuristic.NUM_BLACK;
			whitesEaten= (double) (Heuristic.NUM_WHITE- state.getNumberOf(State.Pawn.WHITE) ) / Heuristic.NUM_WHITE;
			double pawnsAroundKing= (double) checkPawnsNearKing(state) / positionsAroundKing(state) ;
			double blackPawnsOnRhombus= getStrategicPositionsOccupied();
		
			return weight_values[WEIGHT_BLACKS_SURVIVED]*totalBlacks+
				weight_values[WEIGHT_WHITES_EATEN]* whitesEaten+
				weight_values[WEIGHT_AROUND_KING]*pawnsAroundKing+
				weight_values[WEIGHT_STRATEGIC_POSITIONS]*blackPawnsOnRhombus;
	
		}
		
		
	}
	
	public double getStrategicPositionsOccupied() {
		double count=0;
		for(int i=0;i<strategicPosition.length;i++) {
			if(state.getPawn(strategicPosition[i][0], strategicPosition[i][1]).equalsPawn("B") ) {
				count++;
			}
		}
		return count/8;
	}
	
	
	
	
	
}
