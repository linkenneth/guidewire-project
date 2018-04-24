package com.guidewire.wordy.impl;

import com.guidewire.wordy.util.ArgCheck;

public class TestBoard extends BoardImpl {
	
	private char[][] _cells = new char[][] {
			{'G','U','I','D'},
			{'S','I','W','E'},
			{'G','R','E','A'},
			{'K','E','N','T'}
	};
	
	@Override
	public char getCell(int row, int column) {
		ArgCheck.between(row, -1, BOARD_ROWS, "row");
	    ArgCheck.between(column, -1, BOARD_COLUMNS, "column");
	    return _cells[row][column];
	}
}