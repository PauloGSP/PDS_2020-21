import java.nio.file.Path;
import classes.WordSoup;

public class WSSolver {
  public static void main(String[] args) {
    String input_txt = "sopa1.txt"; //default value
   
    int grid_size = 10;

   
    for (int i = 0; i < args.length; i++) {
      
    
      switch (args[i]) {
      case "-i":
        input_txt = args[i+1];

        break;
      case "-s":
        try {
          grid_size = Integer.parseInt(args[i+1]);
        } catch (NumberFormatException e) {
        }
        break;

      default:
      

      }
    }

    Path file = Path.of(input_txt);
    WordSoup.solve(file);
  }
}
