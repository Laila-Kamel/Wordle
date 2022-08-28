# Project: Wordle


## Image of the Application
<img src="C:\Users\laila\eclipse-workspace\Wordle\src\wordle\screenshot.jpg">

## Outline

-   Recreate a simplified version of the game Wordle to be played in a Java console application
-   The game randomly selects a 5-letter word from the wordList file provided.
-   The user will be able to enter a guess word that is also 5 characters long
-   For each letter, the application will tell the user if that letter is correct (will have the green color), right letter in the wrong position (will have yellow color), or wrong letter (will leave its place empty).
-   After the user guesses 6 times incorrectly, the game is over and the user loses
-   If the user guesses the word correctly, a definition of the word is displayed, the game is over and the user wins.
- After the game ends, a history file that keeps track of user wins/losses and how many letters they guessed it in is created in a history file.
- The output of the game is copied into the user's clipboard so they can share it on socials.

## How to run the application
- You should put the path of the word-list.json file in the utils.java file line 41 to be able to read the words in the list.
- You should specify the history file path (in utils.java file line 131 where the output of the game is saved.

### Resources

-  Dictionary definition free API (https://dictionaryapi.dev/)
-  https://marketplace.eclipse.org/content/ansi-escape-console (to color the output according to the position of the letter)
- Word-list.json file.
