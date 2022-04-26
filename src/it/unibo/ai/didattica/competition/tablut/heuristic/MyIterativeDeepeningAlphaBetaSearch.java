package it.unibo.ai.didattica.competition.tablut.heuristic;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MyIterativeDeepeningAlphaBetaSearch extends IterativeDeepeningAlphaBetaSearch<State, Action, State.Turn> {

	public MyIterativeDeepeningAlphaBetaSearch(Game<State, Action, Turn> game, double utilMin, double utilMax,
			int time) {
		super(game, utilMin, utilMax, time);
	}

	@Override
    protected double eval(State state, State.Turn player) {
        super.eval(state, player);
        return game.getUtility(state, player);
    }
	
	@Override
	public Action makeDecision(State state) {
	    Action a = super.makeDecision(state);
	    System.out.println("Explored a total of " + getMetrics().get(METRICS_NODES_EXPANDED) + " nodes, reaching a depth limit of " + getMetrics().get(METRICS_MAX_DEPTH));
	    return  a;
	}

	
}
