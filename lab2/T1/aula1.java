import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class aula1 {
  public static void main(String[] args) {
	  int size = 0;
	  String data = "";
	  String data2 = "";
	  String keywords = "";
    try {
      File SopaLetras = new File("text.txt");
      Scanner scan = new Scanner(SopaLetras);
      Scanner scan2 = new Scanner(SopaLetras);
      
      size = scan2.nextLine().length(); // criar a matriz sopa de letras
      char[][] puzzle = new char [size][size]; 
      
      while (scan.hasNextLine()) { 
        	 data = data.concat(scan.nextLine());
        }
      while (scan2.hasNextLine()) { 
     	 data2 = scan2.nextLine();
     	 if(Character.isLowerCase(data2.charAt(0)))
     		 keywords = keywords.concat(data2);
      }
      
      String[] keys = keywords.split("[  ; ]") ;
      int i = 0;	
      for(int row = 0; row < size; row++) { 	//  preencher a matriz sopa de letras
    	  for(int col = 0; col < size; col++) {
    		  puzzle[row][col] = data.charAt(i);
    		  i++;
    	  }
      }   

        System.out.println(keys[4]);
        if(size>40) {
        	System.out.println("Sopa de Letras Demasiado Grande");
        	System.exit(1);
        }
      scan.close();
      scan2.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
   }
 }

