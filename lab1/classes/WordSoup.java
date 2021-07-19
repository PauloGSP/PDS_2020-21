package classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WordSoup {
  private static boolean isValid(int[][] ws, boolean testMode) {
    // Check if size bigger than max
    if (ws.length > 40) {
      System.err.println("Puzzle demasiado grande!");
      return false;
    }

    for (int[] characters : ws) {
      // Check if any line has different length from the word soup height
      if (characters.length != ws.length) {
        System.err.println("Puzzle não quadrado!");
        return false;
      }

      // Check if all characteres are upper case letters
      for (int character : characters) {
        // Accept only letters or . (representing empty) in test mode
        if (!(testMode && character == '.') && (character < 'A' || character > 'Z')) {
          System.err.println("Puzzle deve ser composto por letras maiúsculas!");
          return false;
        }
      }
    }

    return true;
  }

  // Loads WordSoup from file
  public static HashMap<String, Solution> solve(Path file) {
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

    Integer wsSize = lines.get(0).length();

    if (lines.size() <= wsSize) {
      System.err.println("Ficheiro não tem linhas suficientes!");
      return null;
    }

    int[][] ws = new int[wsSize][wsSize];
    List<String> words = new ArrayList<String>();

    int i;

    for (i = 0; i < wsSize; i++) {

      // Look for empty lines
      if (lines.get(i).length() == 0) {
        System.err.println("Ficheiro não pode ter linhas vazias!");
        return null;
      }

      ws[i] = lines.get(i).chars().toArray();

    }

    for (; i < lines.size(); i++) {

      // Look for empty lines
      if (lines.get(i).length() == 0) {
        System.err.println("Ficheiro não pode ter linhas vazias!");
        return null;
      }

      for (String word : Arrays.asList(lines.get(i).split("[; ]"))) {
        words.add(word);
      }
    }

    return solve(ws, words.toArray(new String[0]));
  }

  // Start solving without testMode and (empty solution Map)
  public static HashMap<String, Solution> solve(int[][] ws, String[] words) {
    if (!isValid(ws, false))
      return null;

    boolean success = true;

    String[] originalWords = words.clone();
    HashMap<String, Solution> solutions = solve(ws, words, false);

    for (String key : solutions.keySet()) {
      if (solutions.get(key) == null) {
        System.out.println(key + " está repetida!");
        success = false;
      }
    }

    if (success) {
      if (solutions == null) {
        System.err.println("Existem palavra(s) inválidas!");
        success = false;
      } else if (solutions.size() != words.length) {
        System.err.println("Não foi possível encontrar todas as palavras!");
        success = false;
      }
    }

    if (success) {
      printSolved(ws.length, originalWords, solutions);
      return solutions;
    }

    return null;
  }

  // Start solving (with empty solution Map)
  public static HashMap<String, Solution> solve(int[][] ws, String[] words, boolean testMode) {
    if (!isValid(ws, testMode))
      return null;

    for (String string : words) {
      if (!string.matches("[A-Z]*[a-z]+[A-Z]*")) {
        return null;
      }
    }

    HashMap<String, Solution> solutions = new HashMap<>();
    solve(ws, words, testMode, solutions);

    for (String key : solutions.keySet()) {
      Solution s = solutions.get(key);
      Position p = s.getPosition();
      findWord(ws, key.toUpperCase(), new Position(p.getX() - 1, p.getY() - 1), s.getDirection());
    }

    for (int i = 0; i < ws.length; i++) {
      for (int j = 0; j < ws.length; j++) {
        for (String word : words) {
          Direction dir = findWord(ws, word.toUpperCase(), new Position(j, i), Direction.None);
          if (dir != Direction.None) {
            solutions.put(word, null);
            unfindWord(ws, word, new Solution(new Position(j + 1, i + 1), dir));
          }
        }
      }
    }

    for (String key : solutions.keySet()) {
      Solution s = solutions.get(key);
      if (s != null)
        unfindWord(ws, key, s);
    }

    return solutions;
  }

  // Recursion, private to enforce starting with empty solution Map
  private static boolean solve(int[][] ws, String[] words, boolean testMode, HashMap<String, Solution> solutions) {
    if (words.length == 0)
      return true;

    Arrays.sort(words, (a, b) -> -Integer.compare(a.length(), b.length()));
    String word = words[0].toUpperCase();
    boolean found = false;
    for (int i = 0; i < ws.length && !found; i++) {
      for (int j = 0; j < ws.length && !found; j++) {
        Direction wordDirection = Direction.None;
        wordDirection = findWord(ws, word, new Position(j, i), Direction.None);
        if (wordDirection != Direction.None) {
          solutions.put(words[0], new Solution(new Position(j + 1, i + 1), wordDirection));
          boolean solved = solve(ws, Arrays.copyOfRange(words, 1, words.length), true, solutions);
          unfindWord(ws, words[0], solutions.get(words[0]));

          if (solved)
            found = true;
        }
      }
    }

    return found;
  }

  public static void unfindWord(int[][] ws, String word, Solution sol) {
    Position pos = sol.getPosition();
    word = word.toUpperCase();
    for (int i = 0; i < word.length(); i++) {
      ws[pos.getY() - 1][pos.getX() - 1] = word.charAt(i);
      pos = pos.nextPosition(sol.getDirection());
    }
  }

  public static Direction findWord(int[][] ws, String word, Position pos, Direction direction) {
    if (direction == Direction.None) {
      for (Direction dir : Direction.values()) {
        if (dir != Direction.None) {
          Direction temp = findWord(ws, word, pos, dir);

          if (temp != Direction.None)
            return temp;
        }
      }

      return Direction.None;
    }

    if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() >= ws.length || pos.getY() >= ws.length)
      return Direction.None;

    if (ws[pos.getY()][pos.getX()] == word.charAt(0)) {
      int temp = ws[pos.getY()][pos.getX()];
      ws[pos.getY()][pos.getX()] = '.';

      if (word.length() == 1)
        return direction;

      String missingWord = word.substring(1);
      Direction result = findWord(ws, missingWord, pos.nextPosition(direction), direction);

      if (result == Direction.None)
        ws[pos.getY()][pos.getX()] = temp;

      return result;
    }

    return Direction.None;
  }

  private static void printSolved(int wsSize, String[] words, HashMap<String, Solution> solutions, int[][] ws) {
    for (String word : words) {
      Solution s = solutions.get(word);

      Position pos = s.getPosition();

      for (int i = 0; i < word.length(); i++) {
        ws[pos.getY() - 1][pos.getX() - 1] = word.toUpperCase().charAt(i);
        pos = pos.nextPosition(s.getDirection());
      }

      System.out.println(String.format("%-15s %-5d %-10s %-10s", word, word.length(), s.getPosition().toString(),
          s.getDirection().toString()));
    }

    System.out.println();

    for (int i = 0; i < wsSize; i++) {
      for (int j = 0; j < wsSize; j++) {
        System.out.print(String.format("%c", ws[i][j]));
      }
      System.out.println();
    }

  }

  private static void printSolved(int wsSize, String[] words, HashMap<String, Solution> solutions) {
    int[][] ws = new int[wsSize][wsSize];
    for (int i = 0; i < wsSize; i++)
      for (int j = 0; j < wsSize; j++)
        ws[i][j] = '.';

    printSolved(wsSize, words, solutions, ws);
  }
}
