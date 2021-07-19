package sdl;

import org.apache.commons.cli.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WSGenerator {
  public static void main( String[] args ) throws org.apache.commons.cli.ParseException {
    Options options = new Options();
    options.addOption( "i", "file", true, "File to read word list from (not optional)" );
    options.addOption( "s", true, "size ( optional )  defaults to 39" );
    options.addOption( "o", "output", true, "File to write the new puzzle to (not optional)" );
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse( options, args );
    HelpFormatter formatter = new HelpFormatter();

    Integer size = 39;
    String puzzle_file = "sdl_01.txt";
    String list_file = null;

    if ( cmd.hasOption( "s" ) ) {
      size = Integer.parseInt( cmd.getOptionValue( "s" ) );
    }
    else {
      System.out.println( "Using default size (39)" );
    }
    if ( cmd.hasOption( "o" ) ) {
      puzzle_file = cmd.getOptionValue( "o" );
    }
    else {
      formatter.printHelp( "WSGenerator", options );
      System.exit( -1 );
    }
    if ( cmd.hasOption( "i" ) ) {
      list_file = cmd.getOptionValue( "i" );
    }
    else {
      formatter.printHelp( "WSGenerator", options );
      System.exit( -1 );
    }
    Path file_path_write = Paths.get( puzzle_file );

    Path file_path = Paths.get( cmd.getOptionValue( "i" ) );
    List<String> puzzle_and_words = null; //just so it doesn't complain
    try {
      puzzle_and_words = Files.readAllLines( file_path );
    } catch (IOException e) {
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
    Puzzle wordSearch = new Puzzle( puzzle_and_words, size );
    try {
      Files.writeString( file_path_write, wordSearch.printPuzzle() );
    } catch (IOException e) {
      System.out.println( "File Could not be created or written to" );
      e.printStackTrace();
    }

    System.out.println( "Puzzle Successfully originated" );
    System.exit( 0 );

  }
}
