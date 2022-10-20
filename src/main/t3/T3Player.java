package main.t3;

import java.util.*;

/**
 * Artificial Intelligence responsible for playing the game of T3!
 * Implements the alpha-beta-pruning mini-max search algorithm
 */
public class T3Player {
    
    /**
     * Workhorse of an AI T3Player's choice mechanics that, given a game state,
     * makes the optimal choice from that state as defined by the mechanics of the
     * game of Tic-Tac-Total. Note: In the event that multiple moves have
     * equivalently maximal minimax scores, ties are broken by move col, then row,
     * then move number in ascending order (see spec and unit tests for more info).
     * The agent will also always take an immediately winning move over a delayed
     * one (e.g., 2 moves in the future).
     * 
     * @param state
     *            The state from which the T3Player is making a move decision.
     * @return The T3Player's optimal action.
     */
    public T3Action choose (T3State state) {
    	
    	return alphabeta(state, Integer.MIN_VALUE, Integer.MAX_VALUE, false).action;
    	
    }


    /**
     * Workhorse of alpha-beta prunning, 
     * @param state
     * 			The state from which the T3Player is making a move decision.
     * @param α
     * 			The alpha value
     * @param β
     * 			The beta value
     * @param maximizeScore
     * 			If this boolean is true, it is our opponent's turn
     * 			If this boolean is false, it is our turn
     * @return the minimax score, and also, the optimal action leading to it.
     */
    //g
    private static  ActionAndscore alphabeta(T3State state, int α, int β, boolean maximizeScore) {    
    	int value;
    	T3Action bestAction = null;
    	if (state.isTie()) {
    		return new ActionAndscore(0, null); 	
    	}
    	if (!maximizeScore && state.isWin()) {
    		return new ActionAndscore(1, null); 
    	}
    	if (maximizeScore && state.isWin()){
    		return new ActionAndscore(-1, null); 
    	}
    	if (maximizeScore) {
    		value = Integer.MIN_VALUE;
    		Map<T3Action,T3State> transitions = state.getTransitions(state);
    		for (Map.Entry<T3Action, T3State> entry : transitions.entrySet()) {
    			ActionAndscore alphaBetascore = alphabeta(entry.getValue() ,α, β, maximizeScore);
    			if(entry.getKey() == null) {
    				bestAction = entry.getKey();
    				return new ActionAndscore(1, bestAction);
    			}
    			if (alphaBetascore.minimax > value) {
    				value = alphaBetascore.minimax;
    				bestAction = entry.getKey();
    				
    			}
    			α = Math.max(α, value);
    			if (β <= α) {
    				break;
    			}
    		}
    		return new ActionAndscore(value, bestAction); 
    	}
    	else {
    		value = Integer.MAX_VALUE;
    		Map<T3Action,T3State> transitions = state.getTransitions(state);
    		for (Map.Entry<T3Action, T3State> entry : transitions.entrySet()) {
    			ActionAndscore alphaBetascore = alphabeta(entry.getValue() ,α, β, !maximizeScore);
    			if(entry.getKey() == null) {
    				bestAction = entry.getKey();
    				return new ActionAndscore(1, bestAction);
    			}
    			if (alphaBetascore.minimax < value) {
    				value = alphaBetascore.minimax;
    				bestAction = entry.getKey();	
    			}
    			β = Math.min(β, value);
    			if (β <= α) {
    				break;
    			}	
    		}
    		return new ActionAndscore(value, bestAction);	
    	}
    }    
}

	/**
	 * @author sarrontadesse
	 * 
	 * The class ActionAndscore stores the minimax score we get from 
	 * the alphabeta prunning and the action that it can take from the current state
	 * 
	 */

	class ActionAndscore {
		int minimax;
		T3Action action;
		ActionAndscore(int minimax, T3Action action) {
			this.minimax = minimax;
			this.action = action;
		}
	}


