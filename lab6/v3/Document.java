import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Document {
  private String[] content;

  private Document(String[] content) {
    this.content = content;
  }

  public String[] getContent() {
    return this.content;
  }

  public static Document fromFile(String fileName) {

    try {
      File file = new File(fileName);
      Scanner sc = new Scanner(file);

      List<String> lines = new ArrayList<>();
      while (sc.hasNext())
        lines.add(sc.nextLine());

      sc.close();

      return new Document(lines.toArray(new String[0]));
    } catch (IOException e) {
      return null;
    }
  }

}
