
import java.io.*;
import java.nio.file.Path;
import classes.SoupMaker;

public class WSGenerator {
  public static void main(String[] args) throws IOException {

    String input_txt = "wlist1.txt"; // default value
    String output_txt = "sopa1.txt"; // default value
    int grid_size = 10;

    for (int i = 0; i < args.length; i++) {

      switch (args[i]) {
      case "-i":
        input_txt = args[i + 1];

        break;
      case "-s":
        try {
          grid_size = Integer.parseInt(args[i + 1]);
        } catch (NumberFormatException e) {
        }

        break;
      case "-o":
        output_txt = args[i + 1];
        break;
      default:

      }
    }

    Path file = Path.of(input_txt);
    PrintStream outfile = new PrintStream(new File(output_txt));
    System.setOut(outfile);
    SoupMaker.make(file, grid_size);

  }

}
