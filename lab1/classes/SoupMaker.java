package classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SoupMaker {
  public static int[][] make(Path file, int size) {
    List<String> lines;

    try {
      lines = Files.readAllLines(file);
    } catch (IOException e) {
      System.err.println("Ficheiro não existe!");
      return null;
    }

    if (lines.isEmpty()) {
      System.err.println("Ficheiro está vazio!");
      return null;
    }

    ArrayList<String> words = new ArrayList<String>();

    for (String line : lines) {
      for (String word : Arrays.asList(line.split("[; ]"))) {
        words.add(word);
      }
    }

    return make(size, words);
  }

  public static int[][] make(int size, ArrayList<String> words) {
    int[][] grid = new int[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        grid[i][j] = '.';
      }
    }

    String[] wordsArray = words.toArray(new String[0]);
    Arrays.sort(wordsArray, (a, b) -> -Integer.compare(a.length(), b.length()));

    placeWords(grid, new ArrayList<String>(Arrays.asList(wordsArray)));
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (grid[i][j] == '.') {
          grid[i][j] = randomLetterPermutation().get(0);
        }
        System.out.print(String.format("%c", grid[i][j]));
      }
      System.out.println();
    }

    System.out.println(String.join(";", wordsArray));

    return grid;
  }

  public static boolean placeWords(int[][] ws, List<String> words) {
    if (words.size() == 0)
      return true;

    String word = words.get(0).toUpperCase();
    Solution sol = null;
    for (Position pos : randomPositionPermutation(ws.length)) {
      for (Direction dir : randomDirectionPermutation()) {
        if (placeWord(ws, word, pos, dir)) {
          sol = new Solution(pos, dir);
          if (placeWords(ws, words.subList(1, words.size())))
            return true;
          else
            unplaceWord(ws, word, sol);
        }
      }

    }
    return false;
  }

  public static int num = 0;

  public static ArrayList<Integer> randomLetterPermutation() {
    ArrayList<Integer> letters = new ArrayList<>();
    for (int i = 'A'; i <= 'Z'; i++)
      letters.add(i);

    Collections.shuffle(letters);

    return letters;
  }

  public static ArrayList<Position> randomPositionPermutation(int size) {
    ArrayList<Position> pos = new ArrayList<>();
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++)
        pos.add(new Position(i, j));

    Collections.shuffle(pos);

    return pos;
  }

  public static ArrayList<Direction> randomDirectionPermutation() {
    ArrayList<Direction> dirs = new ArrayList<>();

    for (Direction dir : Direction.values())
      if (dir != Direction.None)
        dirs.add(dir);

    Collections.shuffle(dirs);

    return dirs;
  }

  public static void unplaceWord(int[][] ws, String word, Solution sol) {
    Position pos = sol.getPosition();
    for (int i = 0; i < word.length(); i++) {
      ws[pos.getY()][pos.getX()] = '.';
      pos = pos.nextPosition(sol.getDirection());
    }
  }

  public static boolean placeWord(int[][] ws, String word, Position pos, Direction direction) {
    if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() >= ws.length || pos.getY() >= ws.length)
      return false;

    if (word.length() == 0)
      return true;

    if (ws[pos.getY()][pos.getX()] == '.') {

      ws[pos.getY()][pos.getX()] = word.charAt(0);

      String missingWord = word.substring(1);

      if (!placeWord(ws, missingWord, pos.nextPosition(direction), direction)) {
        ws[pos.getY()][pos.getX()] = '.';
        return false;
      }

      return true;
    }

    return false;
  }
}