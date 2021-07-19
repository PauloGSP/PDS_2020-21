import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import classes.ReservationSystem;
import classes.ReservationSystemException;

public class Ex2 {

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {

    ReservationSystem system = new ReservationSystem();
    Scanner sc;
    if (args.length == 1)
      sc = new Scanner(new File(args[0]));
    else
      sc = new Scanner(System.in);

    while (system.getIsActive()) {
      System.out.println("Escolha uma opção: (H para ajuda)");

      if (!sc.hasNextLine())
        break;

      String[] l = sc.nextLine().split("\\s+");

      try {
        system.run(l);
      } catch (ReservationSystemException e) {
        System.err.println(e.getMessage());
      }
    }
    sc.close();

  }
}
