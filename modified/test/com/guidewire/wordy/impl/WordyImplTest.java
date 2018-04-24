package com.guidewire.wordy.impl;

import com.guidewire.wordy.IBoard;
import com.guidewire.wordy.IWordy;
import com.guidewire.wordy.WordyGame;
import junit.framework.TestCase;

import java.util.Arrays;

public class WordyImplTest extends TestCase {
	
	private TestBoard _testBoard = new TestBoard();
	private IWordy wg = new WordyGame(_testBoard);
	
	public void testScoreWordsCountsWordsThatAreBothValidAndInBoard() {
		assertEquals("Score", wg.scoreWords(Arrays.asList("GuiDe","wiRe","Is","greAT")), 5);
	}

	public void testScoreWordsCountsDuplicatesOnlyOnce() {
		assertEquals("Score", wg.scoreWords(Arrays.asList("wide", "wide", "wide", "green", "Green", "gREEn", "sIre")), 4);
	}

	public void testScoreWordsWithLeadingAndTrailingSpaces() {
		assertEquals("Score", wg.scoreWords(Arrays.asList("wide"," wide ","  wide ","    wide     ")), 1);
	}

	public void testScoreWordsDoesNotCountWordsThatAreValidButNotInBoard() {
		assertEquals("Score", wg.scoreWords(Arrays.asList("random", "words", "akin", "rainbow", "truth")), 0);
		assertEquals("Score", wg.scoreWords(Arrays.asList("meaning", "universe", "forty", "two")), 0);
	}
	
	public void testScoreWordsDoesNotCountWordsThatAreNotValidButInBoard() {
		assertEquals("Score", wg.scoreWords(Arrays.asList("guiwer", "kenteaed", "gekrs", "siwerg")), 0);
	}
	
	public void testScoreWordsDoesNotCountWordsThatAreNotConnectedOnBoard() {
		assertEquals("Score", wg.scoreWords(Arrays.asList("WISE", "DENT", "gag", "area")), 0);
	}
	
	public void testScoreWordsDoesNotCountWordsThatRepeatItsOwnLetters() {
		assertEquals("Score", wg.scoreWords(Arrays.asList("tent","did", "dewed", "sis")), 0);
	}
	
	/* For reference:
	 * private char[][] _cells = new char[][] {
			{'G','U','I','D'},
			{'S','I','W','E'},
			{'G','R','E','A'},
			{'K','E','N','T'}
	};	 */
	
	public void testGenerateBoard() {
		IBoard randBoard = wg.generateNewBoard();
		assertFalse("Random Board Word Search", wg.scoreWords(Arrays.asList("GuiDe","wiRe","Is","greAT")) == 5);
		// technically there is a small probability of success, but it is infinitesimally small
		assertTrue("Identity Test", randBoard != _testBoard);
		
		try {
			randBoard.getCell(-1, 0);
			fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// expected for board dimension tests
		} try {
			randBoard.getCell(0, -1);
			fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// expected for board dimension tests
		} try {
			randBoard.getCell(IBoard.BOARD_ROWS+1, 0);
			fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// expected for board dimension tests
		} try {
			randBoard.getCell(0, IBoard.BOARD_COLUMNS+1);
			fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// expected for board dimension tests
		}
		
		for (int row = 0; row < IBoard.BOARD_ROWS; row++) {
	    	for (int column = 0; column < IBoard.BOARD_COLUMNS; column++) {
	    		char ch = randBoard.getCell(row, column);
	    		if (!('A' <= ch && ch <= 'Z')) {
	    			fail("Character at [" + row + "," + column + "] was not a letter: " + ch);
	    		}
	    	}
	    }
	}
}
