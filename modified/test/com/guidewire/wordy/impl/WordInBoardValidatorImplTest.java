package com.guidewire.wordy.impl;

import com.guidewire.wordy.impl.TestBoard;
import junit.framework.TestCase;

public class WordInBoardValidatorImplTest extends TestCase {

	private WordInBoardValidatorImpl wb = new WordInBoardValidatorImpl();
	private TestBoard _board = new TestBoard();
	
	public void testIsWordInBoardIfWordsAreMixedCase() {
		assertValid(wb, "GuIdE", "wIRe", "is", "gREat");
		assertNotValid(wb, "Validation", "succEsS", "haS", "beEN", "coNfIRmeD");
	}
	
	public void testIsWordInBoardIfWordsAreNotRealWordsButOnBoard() {
		assertValid(wb, "gsgker", "RENEGK", "tnew","kenters");
		assertNotValid(wb, "apwrveoipanu", "aopvnewvni", "unupzoineawr");
	}
	
	public void testIsWordInBoardIfWordsRepeatItsOwnLetters() {
		assertNotValid(wb, "gegegeg", "teeeeeeeeeeeeee", "sirs");
	}
	
	public void testIsWordInBoardIfWordsAreOnBoardButLettersAreNotAdjacent() {
		assertNotValid(wb, "wired", "DARK", "tear", "teared", "kiwi");
	}
	
	public void testIsWordInBoardWithSpacesInBetweenLettersButOnBoard() {
		assertNotValid(wb, "guide wire", "re nt", "sir e", "WID E");
		// this should be the role of WordyFrame anyways, but should not pass isWordInBoard()
	}
	
	public void testIsWordInBoardForWordsThatContinueOnOtherSideOfBoard() {
		assertNotValid(wb, "WIN", "tees", "NTK", "GEI", "dwine");
	}
	
	/* 	For reference:
	 * private char[][] _cells = new char[][] {
			{'G','U','I','D'},
			{'S','I','W','E'},
			{'G','R','E','A'},
			{'K','E','N','T'}
	}; */
	
	public void assertValid(WordInBoardValidatorImpl validator, String... validWords) {
		for (String validWord: validWords) {
			assertTrue("Word " + validWord + " should have been valid", validator.isWordInBoard(_board, validWord));
		}
	}
	
	private void assertNotValid(WordInBoardValidatorImpl validator, String... invalidWords) {
		for (String invalidWord : invalidWords) {
			assertFalse("Word " + invalidWord + " should not have been valid", validator.isWordInBoard(_board, invalidWord));
		}
	}


}
