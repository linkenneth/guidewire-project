package com.guidewire.wordy;

import java.util.List;
import java.util.HashSet;

import com.guidewire.wordy.util.ArgCheck;
import com.guidewire.wordy.impl.*;
import com.guidewire.wordy.impl.WordInBoardValidatorImpl;
import com.guidewire.wordy.util.StringUtil;

/**
 * The client application that uses the Wordy implementation.
 */
public class WordyGame implements IWordy {

	@Override
	public IBoard generateNewBoard() {
		b = new BoardImpl();
		wb = new WordInBoardValidatorImpl();
		return b;
	}
	
	// assigning out here reduces runtime on multiple scoreWords calls but uses additional memory
	private WordScorerImpl ws = new WordScorerImpl();
	private IWordValidator wv = WordValidatorImpl.createStandardValidator();
	private WordInBoardValidatorImpl wb;
	private IBoard b;
	
	
	@Override
	public int scoreWords(List<String> words) {
		ArgCheck.nonNull(words, "words");
		HashSet<String> duplicatesRemoved = new HashSet<String>();
		for (String word: words) {
			duplicatesRemoved.add(StringUtil.normalizeWord(word));
		}
		int score = 0;
		for (String normalizedWord: duplicatesRemoved) {  // consider try method to catch IllegalArgumentException?
			if (wv.isRealWord(normalizedWord) && wb.isWordInBoard(b, normalizedWord)) {
				score += ws.scoreWord(normalizedWord);
			}
		}
		return score;
	}
	
	public WordyGame(IBoard board) {
		b = board;
		wb = new WordInBoardValidatorImpl();
	}
	
	public static void main(String[] args) {
		IWordy wordy = new WordyGame(new BoardImpl()); // Instantiate your Wordy implementation here
		new WordyFrame(wordy).setVisible(true);
  }

}
