package sdl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WSSolver {

  public static void main( String[] args ) {
    Path file_path = Paths.get( args[0] );
    List<String> puzzle_and_words = null; //just so it doesn't complain
    try {
      puzzle_and_words = Files.readAllLines( file_path );
    } catch ( IOException e) {
      System.err.flush();
      System.out.flush();
      System.err.printf(
        """
          File could not be read, check if the file is present in the following\s
          path %s,
          if the file is not present in this directory provide a full path
          """,
        System.getenv( "PWD" ) );
      System.exit( - 1 );
    }
    Puzzle wordSearch = new Puzzle(puzzle_and_words);

    wordSearch.solvePuzzle();
    System.out.println(wordSearch);
    System.out.println(wordSearch.getSolvedPuzzle());

    System.exit( 0 );


  }
}
