lol. this was my first ever take-home project when I attempted to apply for an internship less than a month or two months after I started programming. The internship required Java, so the project given to me was in Java. Unfortunately, I knew zero Java. Instead of giving up, I said "Challenge Accepted!" and finished the project within a day and a half of all-nighting. This is some relic stuff.

The algorithm seems correct despite very strange coding conventions. There are some concerning points though.
(1) I used a HashSet, but I didn't use .get(). This removes the benefit of using a Set in the first place, and raises my complexity by a factor of O(N * M).
(2) I constructed a "neighbors map" to "cache" neighbors, but this isn't an expensive operation anyways. Complexity is still the same.
(3) Creating a new copy of the word every potential loop means that my solution is also now more complex by a factor of O(K), where K is the length of the maximum word in the dictionary, regardless of whether the word is in the dictionary.
(4) Given the constraints of the problem, I could have pre-calculated all the viable words on the board when the game is initialized and make isWordInBoard O(1).
