### WORDINBOARDVALIDATORIMPL.JAVA IMPLEMENTATION ###
------------------------------------------------------

As the code is not as efficient or as lucid as I would like it to be, I will be describing in a bit more detail the technicalities of the implementation of WordInBoardValidatorImpl.java.

The isWordInBoard method begins by normalizing "word". This is not strictly necessary in practice but makes testing easier. The backTracker HashSet, used to track the tiles that one has already "traversed" in creating the word, is cleared for ths new word.

Two nested for loops are initiated to check "board" to see whether the first character of "word" can be located. If not, then the algorithm directly shortcircuits to the end and returns false. If the character is found, the position of this character is recorded as "position", an integer array consisting of {row, column}, and position is added to backTracker.

Then, the helper function testNeighbors() is called in the if statement with the original word minus the first letter. Briefly, testNeighbors() tests whether each neighbor of the tile you are currently on ("position") contains the next connecting letter. If it does, then testNeighbors() is recursively called with one fewer letter until all the characters in the word run out (ie. all the letters were matched). If it does not, then the for loop continues until all the neighbors have been checked, THEN it returns false. Because of the way the if, for, and recursion works, the algorithm will branch off in difference directions for each possible character match, but also "backtrack" and check all the other neighbors for every possible character sequence that matches. This allows a thorough check of all possible characters. backTracker entries are added accordingly throughout.

Thus, if every single call to testNeighbors() - recursively, within the for loop, or within the larger isWordInBoard for loop - falls through with no match, then the return final return false propagates through all the loops/recursions and results in isWordInBoard to be false.