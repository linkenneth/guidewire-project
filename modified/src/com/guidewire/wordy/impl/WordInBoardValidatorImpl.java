package com.guidewire.wordy.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.guidewire.wordy.IBoard;
import com.guidewire.wordy.IWordInBoardValidator;
import com.guidewire.wordy.util.ArgCheck;
import com.guidewire.wordy.util.StringUtil;

public class WordInBoardValidatorImpl implements IWordInBoardValidator {

	@Override
	public boolean isWordInBoard(IBoard board, String word) {
		
		// search for all possible starting positions with starting char
		word = StringUtil.normalizeWord(word);
		backTracker.clear();
		
		for (int row=0; row < IBoard.BOARD_ROWS; row++) {
			for (int column=0; column < IBoard.BOARD_COLUMNS; column++) {
				if (word.charAt(0) == Character.toLowerCase(board.getCell(row, column))) {
					// start searching for connecting neighbors in neighbors HashMap
					int[] position = new int[] {row, column};
					backTracker.add(position);
					if (testNeighbors(board, word.substring(1), position)) {  // only returns true if complete word found
						return true;
					} else {
						backTracker.clear();
					}
				} // END IF
			} // END FOR
		} // END FOR
		
		// if both for loops fall through then first letter is not on board
		return false;
	} // END METHOD

	
	/**
	 * A helper function that tests whether the neighbors of a particular position contain
	 * the next letter in the word, then recursively calls itself until the end of the word
	 * is reached.
	 * 
	 * @param board
	 * @param word
	 * @param position
	 * @return
	 */
	
	private boolean testNeighbors(IBoard board, String word, int[] position) {
		
		char[] neighs;
		char c = word.charAt(0);
		if (neighbors.containsKey(position)) {
			// if the position has already been stored in cache, find the neighbors associated with the position
			neighs = (char[]) neighbors.get(position);
		} else {  // if the position isn't in cache, store it (memoization)
			neighs = addNeighbors(board, position);
		}
		
		outerloop:
		for (int index=0; index < neighs.length; index++) {
			// tests each neighbor for each possible connecting letter
			if (c == neighs[index]) {
				int[] newPosition = new int[] {position[0]+getPosition(index)[0], position[1]+getPosition(index)[1]};
				ArgCheck.between(newPosition[0],-1,IBoard.BOARD_ROWS, "newPosition[0]");
				ArgCheck.between(newPosition[1],-1,IBoard.BOARD_COLUMNS,"newPosition[1]");
				
				for (int[] coordinate: backTracker) {  // check whether the matched tile has already been used
					if (Arrays.equals(coordinate, newPosition)) {
						continue outerloop;
						}  // Arrays.equals is used instead of contains because the equals() method for Array doesn't compare values
				}
				
				backTracker.add(newPosition);  // record this position in backTracker to avoid words that repeat the same letters
				
				// make a copy of new word without the first letter
				if (word.length() > 1) {
					String restOfWord = word.substring(1);
					if (testNeighbors(board, restOfWord, newPosition)) {
						/*
						 * This if-recursion construct allows the game to check other possible intermediary letters,
						 * instead of having a result returned immediately upon detection of whether the word is
						 * true or false.
						 */
						return true;
					} else {
						backTracker.remove(newPosition);  // removes the backTracker entry so it doesn't affect other letters
					}
					
				} else {  // if all the letters were matched (i.e. word.length() <= 1)
					return true;
				}
			} // END IF
		} // END FOR
		
		// if the loop falls through, then no neighbors matched the char
		return false;
	} // END METHOD
	
	
	/**
	 * Maps the position with the position of its corresponding neighbors in the HashMap neighbors
	 * AND returns these neighbors
	 * 
	 * @param board
	 * @return the neighbors added
	 */
	
	private char[] addNeighbors(IBoard board, int[] pos) {
		
		char[] positions = new char[9];  // positions[4] should always be empty, useful for testing
		
		for (int i=-1; i <= 1; i++) {
			int row = pos[0] + i;
			if (row < 0 || row >= IBoard.BOARD_ROWS) { continue; }
			for (int j=-1; j <= 1; j++) {
				int col = pos[1] + j;
				if ((col < 0 || col >= IBoard.BOARD_COLUMNS) || (i == 0 && j == 0)) { continue; }
				positions[3*(i+1)+j+1] = Character.toLowerCase(board.getCell(row, col));
			}
		}
		
		assert (positions[4] != '\u0000'): "Adding neighbors failed - not valid state";
		neighbors.put(pos, positions);
		return positions;
	}
	
	
	/**
	 * Gets the position (row, column) of the index in neighs corresponding to the letter of a neighbor
	 * RELATIVE to the position of the original.
	 * 
	 * @param i - index of letter in neighs
	 * @return (row, column) with ranges [-1,0,1] for each one
	 */
	
	private static int[] getPosition(int i) {
		int row = (i-i%3)/3-1;
		int col = i%3-1;
		return new int[] {row, col};
	}
	
	public WordInBoardValidatorImpl() {
		neighbors = new HashMap<int[], char[]>(17, (float) 1.0);
	}
	
	private Map<int[], char[]> neighbors;
	private static Set<int[]> backTracker = new HashSet<int[]>();  // tracks the path less traveled by which has made all the difference
	// Arrays.equals() must be used to check array value-equality. Runs in O(n) time, so could be more efficient, but small sample size so irrelevant
	
}
