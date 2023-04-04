ABOUT THE PROGRAM:
1. This program contains 2 packages:
  - StringArray
  - SpellingChecker

2. The packages above are meant for storing self-defined methods which aid the
   program. Thus, they do not contain a "main()" class and cannot be run.

3. The main code for executing and running is in the "run.java" file, where a
   class named "Main" is housed.

4. To compile the packages, go to the "src" directory and do as stated:
 - javac StringArray/StringArray.java
 - javac SpellingChecker/SpellingChecker.java

5. Compile and run the main program as below (stay at the "src" directory):
  - javac run.java
  - java Main

6. Function of this program:
  - Checks for spelling error in sentences and returns possible
    spelling-corrected words.
  - The suggested words will be listed out following each incorrect words for
    the user's reference.
  - Takes sentence input from terminal or file.
  - Spelling error and suggestions will be written to a file in the "src"
    directory, named "newSpellings.txt".
  - Your own .txt file for spelling checking has to be in the "src" directory
    for the program to load it.
