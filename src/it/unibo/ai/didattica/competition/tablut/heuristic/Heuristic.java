package it.unibo.ai.didattica.competition.tablut.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public abstract class Heuristic {
	
	protected State state;

	public final static int NUM_BLACK = 16;
	public final static int NUM_WHITE = 8;
	public final static int NUM_ESCAPES = 16;
	public final static int NUM_CITADELS = 16;
	
	
    public Heuristic(State state) {
        this.state = state;
    }
    
    public abstract double evaluateState(); 
    
    public int[] getKingPosition(State state) {
    	int[] king=new int[2];
    	State.Pawn[][] board = state.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (state.getPawn(i, j).equalsPawn(State.Pawn.KING.toString())) {
                    king[0] = i;
                    king[1] = j;
                }
            }
        }
        return king;
    }
    
    public int checkPawnsNearKing(State state) {
    	int c=0;
    	int[] kingposition=new int[2];
    	kingposition=getKingPosition(state);
    	State.Pawn[][] board=state.getBoard();
    	if(kingposition[1]!=8 && board[kingposition[0]][kingposition[1]+1].equalsPawn("B"))
    		c++;
    	if(kingposition[1]!=0 && board[kingposition[0]][kingposition[1]-1].equalsPawn("B"))
    		c++;
    	if(kingposition[0]!=8 && board[kingposition[0]+1][kingposition[1]].equalsPawn("B"))
    		c++;
    	if(kingposition[0]!=0 && board[kingposition[0]-1][kingposition[1]].equalsPawn("B"))
    		c++;
    	return c;
    		
    }
    
    public int positionsAroundKing(State state) {
    	int result;
    	int[] kingposition=new int[2];
    	kingposition=getKingPosition(state);
    	if(kingposition[0]==4 && kingposition[1]==4) {
    		result=4; //4 pawns required to eat the king
    	}
    	else if( (kingposition[0]==3 && kingposition[1]==4) ||
    			(kingposition[0]==5 && kingposition[1]==4)  ||  
    			(kingposition[0]==4 && kingposition[1]==3) ||
    			(kingposition[0]==4 && kingposition[1]==5)) {
    		result=3;
    	}
    	else {
    		result=2;
    	}
    	return result;
    }
    
}
