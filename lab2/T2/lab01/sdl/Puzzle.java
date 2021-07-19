package sdl;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Puzzle {
  char[][] letters = new char[40][40];
  char[][] solvedPuzzle = new char[40][40];
  int size;
  ArrayList<String> wordListString = new ArrayList<>();
  ArrayList<Word> wordList = new ArrayList<>();
  int numberOfSolutions = 0;
  boolean solved = false;

  /**
   * @param wordList Words to include on the WordSearchPuzzle
   * @param size     Size of the puzzle between 0 and 40
   *                 <p> Constructs an object of the type puzzle and generates a puzzle which later can be solved using
   *                 the other developed program </p>
   */
  public Puzzle( List<String> wordList, int size ) {

    /*
     *  matches lines containing one of the 3 separators
     *  after that it iterates over each String slicing it into different words
     *  during each iteration the word is added to the wordSet also during each iteration this.numberOfSolutions is
     *  incremented so that we later on can confirm if there were words written to time in the solution list
     */
    if ( size >= 40 || size < 0 ) {
      System.err.println( "Invalid size plz try again with a value ranging between 0 and 40" );
      System.exit( - 1 );
    }
    Random rand = new Random();
    for (String s : wordList) {
      String[] ws = s.split( "[,;\\s]" );
      for (String w : ws) {
        if ( w.length() > size ) {
          System.err.println( "Word too long" );
          System.exit( - 1 );
        }
        if ( ! this.wordListString.contains( w ) ) {
          this.wordListString.add( w.toUpperCase( Locale.ROOT ) );
          this.numberOfSolutions++;
        }
        else {
          System.err.println( "This word appears twice in the solutions list" + w );
          System.exit( - 1 );
        }
      }
    }
    this.size = size;
    // fill every bit of puzzle
    for (int X = 0 ; X < this.size ; X++) {
      for (int Y = 0 ; Y < this.size ; Y++) {
        this.setLetter( X, Y, ' ' ); // sets a random letter en every position
        // of the unsolved puzzle
        this.solvedPuzzle[X][Y] = '.';// sets a dot in every position of the solved puzzle
      }
    }

    // create Words based on random directions and in random starting points
    int startX = - 1, startY = - 1, endX, endY;
    Direction dir = null;
    Direction[] directions =
      {Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWNLEFT, Direction.DOWNRIGHT,
        Direction.UPLEFT, Direction.UPRIGHT};
    boolean wordFits = false;
    ArrayList<String> wordListSorted = new ArrayList<>();
    wordListSorted.addAll( this.wordListString );
    wordListSorted.sort( Comparator.comparing( String::length ).reversed() );

    for (String w : wordListSorted) {
      while (wordFits == false) { // wordFits == false means do again
        startX = rand.nextInt( size ); //will aways be within limits
        startY = rand.nextInt( size ); //will aways be within limits

        dir = directions[rand.nextInt( 8 )];
        // Ending positions
        endX = startX + ( w.length() - 1 ) * ( dir.getX() );
        endY = startY + ( w.length() - 1 ) * ( dir.getY() );

        if ( endX >= 0 && endX < this.size && endY >= 0 && endY < this.size ) { // inbounds
          wordFits = this.InsertWord( new Word( w, dir, startX + 1, startY + 1 ) );
        }
        else { // out of bounds
          wordFits = false; // do again
        }
      }

      this.wordList.add( new Word( w, dir, startX + 1, startY + 1 ) );
      wordFits = false;
    }
    for (int X = 0 ; X < this.size ; X++) {
      for (int Y = 0 ; Y < this.size ; Y++) {
        if ( this.getLetter( X, Y ) == ' ' ) {
          this.setLetter( X, Y, (char) ( rand.nextInt( 26 ) + (int) 'A' ) ); // sets a random letter en every position
        }
        // of the unsolved puzzle
      }
    }

  }

  /**
   * @param fullFile All lines of the file that should contain the puzzle itself and also include his solution
   *                 Creates a puzzle instance that can later be solved using the method solve puzzle
   */
  public Puzzle( List<String> fullFile ) {
    /*
       matches lines containing only Capital letters
       after that it iterates over each String filtered in the stream
       during each iteration the line is sliced into a char array and i is incremented
     */
    for (int i = 0 ; i < fullFile.size() ; i++) {
      /*
       * Checks if the puzzle is a square
       */
      if ( fullFile.get( i ).matches( "^[A-Z]*$" ) ) {
        this.letters[i] = fullFile.get( i ).toCharArray();
        this.size++;
      }
    }

    /*
     *  matches lines containing one of the 3 separators
     *  after that it iterates over each String slicing it into different words
     *  during each iteration the word is added to the wordSet also during each iteration this.numberOfSolutions is
     *  incremented so that we later on can confirm if there were words written to time in the solution list
     */
    for (String s : fullFile) {
      if ( s.toUpperCase( Locale.ROOT ).compareTo( s ) != 0 ) {
        String[] ws = s.split( "[,;\\s]" );
        for (String w : ws) {
          if ( ! this.wordListString.contains( w ) ) {
            this.wordListString.add( w.toUpperCase( Locale.ROOT ) );
            this.numberOfSolutions++;
          }
          else {
            System.err.println( "This word appears two times making the puzzle not solvable." );
            System.exit( - 1 );
          }
        }
      }
    }
    /*
      Puzzle is created
     */
  }

  /*
    Assigns a specific letter in specific point of the puzzle
   */
  public void setLetter( int X, int Y, char letter ) {
    this.letters[X][Y] = letter;
  }

  /**
   * @param w word to include in the puzzle
   *          This method uses a Word already completely instantiated to display the solved puzzle
   *          Sets a complete word in both puzzle the unsolved one and the solved one
   */
  public boolean InsertWord( Word w ) {
    int length = w.getLength();
    int startX = w.getStartX() - 1;
    int startY = w.getStartY() - 1;
    int dirX = w.getDirection().getX();
    int dirY = w.getDirection().getY();
    for ( ; length > 0 ; length--) {
      if ( this.getLetter( startX, startY ) != ' ' &&
        this.getLetter( startX, startY ) != w.getCharAt( w.getLength() - length ) ) {
        for ( ; length < w.getLength() ; length++) {
          startX -= dirX; // they might be adding negative numbers don't be naive
          startY -= dirY;
          this.solvedPuzzle[startX][startY] = '.';
          this.letters[startX][startY] = ' ';
        }
        return false; // Do again
      }
      this.solvedPuzzle[startX][startY] = w.getCharAt( w.getLength() - length );
      this.letters[startX][startY] = w.getCharAt( w.getLength() - length );
      startX += dirX; // they might be adding negative numbers don't be naive
      startY += dirY;
    }
    return true; //done
  }

  /**
   * @param X row
   * @param Y column
   * @return a character which occupies the position (X,Y)
   */
  public char getLetter( int X, int Y ) {
    return this.letters[X][Y];
  }

  /**
   * Finds every positions were word can be found at
   * At last checks if every words was found only on time
   */
  public void solvePuzzle() {
    Direction[] directions =
      {Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWNLEFT, Direction.DOWNRIGHT,
        Direction.UPLEFT, Direction.UPRIGHT};
    String found = "";
    for (int X = 0 ; X < this.size ; X++) {
      for (int Y = 0 ; Y < this.size ; Y++) {
        for (int i = 0 ; i < directions.length ; ++ i) {
          found = this.searchWord( X, Y, this.wordListString, "", directions[i].getX(), directions[i].getY() );
          if ( found != null ) {
            Word wordFound = new Word( found, directions[i], X + 1, Y + 1 );
            if ( wordList.contains( wordFound ) ) {
              System.err.println( "This solution appears twice within the puzzle" + wordFound );
              System.exit( - 1 );
            }
            else {
              this.wordList.add( wordFound );
            }
          }
        }
      }
    }
    // verify if there was a word that appeared more than once
    if ( this.wordList.size() != this.wordListString.size() ) {
      System.err.println( "Some words don't appear at all or appear one or more time: "  );
      System.exit( - 1 );
    }
    this.solved = true;
  }

  /**
   * @param x           row
   * @param y           column
   * @param viablewords contains words that have the same character as the variable word
   * @param word        String which is used to find accumulate charactes of the puzzle
   * @param Xdir        Direction to go according to rows
   * @param Ydir        Direction to go according to columns
   * @return String word that was found
   */
  public String searchWord( int x, int y, ArrayList<String> viablewords, String word, int Xdir,
                            int Ydir ) {
    // Out of bounds
    if ( x < 0 || x >= this.size || y < 0 || y >= this.size ) { return null; }

    word += this.getLetter( x, y );
    int wordLength = word.length();
    ArrayList<String> filterWords = new ArrayList<>();

    for (String w : viablewords) {
      if ( wordLength <= w.length() ) {
        if ( w.charAt( wordLength - 1 ) == word.charAt( wordLength - 1 ) ) {
          filterWords.add( w );
        }
      }
    }

    // If the word that is being created can only form on of the words belonging to the word list
    if ( filterWords.size() == 1 && filterWords.contains( word ) ) {
      return word;
    }

    // If there's still multiple options
    if ( filterWords.size() >= 1 ) {
      return this.searchWord( x + Xdir, y + Ydir, filterWords, word, Xdir, Ydir );
    }
    /*
    Since this must bre greedy in order to find the largest possible word first we might end up finding "stack" but
    the next letter might be an "s" making the filterWords structure empty so we must search __viable_words__ in order
     to guarantee that no word was actually found
     */
    /*
    Problems:  slow
               Always executes
     */
    if ( filterWords.size() == 0 ) {
      word = word.substring( 0, wordLength - 1 );
      for (String w : viablewords) {
        if ( word.length() == w.length() && word.compareTo( w ) == 0 ) {
          return w;
        }
      }
      return null;
    }
    return null;
  }

  /**
   * @return String containg the final solution of the puzzle
   */
  public String getSolvedPuzzle() {
    String solvedPuzzleString = "";
    for (Word w : this.wordList) {
      int length = w.getLength();
      int startX = w.getStartX() - 1;
      int startY = w.getStartY() - 1;
      int dirX = w.getDirection().getX();
      int dirY = w.getDirection().getY();
      while (length > 0) {
        this.solvedPuzzle[startX][startY] = w.getCharAt( w.getLength() - length );
        startX += dirX;
        startY += dirY;
        length--;
      }
    }

    for (int X = 0 ; X < this.size ; X++) {
      for (int Y = 0 ; Y < this.size ; Y++) {
        if ( ! Character.isAlphabetic( this.solvedPuzzle[X][Y] ) ) {
          this.solvedPuzzle[X][Y] = '.';
        }
        solvedPuzzleString += this.solvedPuzzle[X][Y];
      }
      solvedPuzzleString += '\n';
    }
    return solvedPuzzleString;
  }

  public String solvedPuzzleToString() {
    String solvedPuzzleString = "";
    for (int X = 0 ; X < this.size ; X++) {
      for (int Y = 0 ; Y < this.size ; Y++) {
        if ( ! Character.isAlphabetic( this.solvedPuzzle[X][Y] ) ) {
          this.solvedPuzzle[X][Y] = '.';
        }
        solvedPuzzleString += this.solvedPuzzle[X][Y];
      }
      solvedPuzzleString += '\n';
    }
    return solvedPuzzleString;
  }

  /**
   * Prints the unsolved puzzle plus the words that such puzzle contains
   *
   * @return String
   */
  public String printPuzzle() {
    String ret = "";
    for (int i = 0 ; i < this.size ; i++) {
      for (int j = 0 ; j < this.size ; j++) {
        ret += this.getLetter( i, j );
      }
      ret += "\n";
    }
    for (int i = 0 ; i < this.numberOfSolutions ; i++) {
      ret +=
        this.wordListString.get( i ).toLowerCase( Locale.ROOT ) + ( ( i == this.numberOfSolutions - 1 ) ? "" : ";" );
    }
    return ret;
  }

  /**
   * Standard method of representation of this class
   * returns puzzle solutions after being solved
   *
   * @return
   */
  @Override public String toString() {
    String ret = "";
    int indexOfObject = - 1;
    for (String w : this.wordListString) {
      for (int i = 0 ; i < this.wordList.size() ; i++) {
        if ( w.equals( this.wordList.get( i ).getWord() ) ) {
          indexOfObject = i;
        }
      }
      ret += ( indexOfObject == - 1 ) ? "" : this.wordList.get( indexOfObject );
    }

    return ret;
  }
}
