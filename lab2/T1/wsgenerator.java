import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;   
import java.io.IOException;  


public class wsgenerator {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);  // Criar Scanner
	    System.out.println("Nome do ficheiro com as palavras:");
	    String filein = scan.nextLine();  // Ler imput
	    System.out.println("Dimensões:");
	    int size = scan.nextInt();  // Ler tamanho
	    System.out.println("Nome do ficheiro do output:");
	    String fileout = scan.nextLine();  // Ler imput
	    scan.close();
	    String keywords="";
	    char[][] ws = new char [size][size];
	    
	    try {
	    	
	        File input = new File(filein);
	        Scanner read = new Scanner(input);
	        while (read.hasNextLine()) { 
	        	keywords = keywords.concat(read.nextLine());
	        }
	        read.close();
	        String[] keys = keywords.split("[  ;]");
	        
	        int x = 0;
	        int y = 0;
	        int direction = 0;
	        int a = 0;
	        
	        for(int i = 0; i < keys.length;i++) {
			        direction = (int)(Math.random() * (3 + 1)); 
			         
			        switch (direction) {
			    	case 0: // horizontal
			    		x = (int)(Math.random() * ((size-keys[i].length()) + 1)); // Math.random() * (max - min + 1) + min
			    		y = (int)(Math.random() * (size + 1)); // min = 0 & max = 12 (not included)
			    		a = 0;
			    		for(int j = x ;j<keys[i].length();j++) {
			    			ws[j][y]=keys[i].charAt(a);
			    			a++;
			    		}
			    		break;
			    	case 1: // vertical
			    		x = (int)(Math.random() * (size + 1));
			    		y = (int)(Math.random() * ((size-keys[i].length()) + 1));
			    		a = 0;
			    		for(int j = y ;j<keys[i].length();j++) {
			    			ws[x][j]=keys[i].charAt(a);
			    			a++;
			    		}
			    		break;
			    	case 2: // diagonal
			    		x = (int)(Math.random() * ((size-keys[i].length()) + 1)); 
			    		y = (int)(Math.random() * ((size-keys[i].length()) + 1));
			    		a = 0;
			    		for(int j = x ;j<keys[i].length();j++) {
			    			for(int k = y;k<keys[i].length();k++) {
			    			ws[j][k]=keys[i].charAt(a);
			    			a++;
			    			}
			    		}
			    		break;
	        	}  
			        
			for(int row = 0; row < size; row++) { 	
				for(int col = 0; col < size; col++) {
					if(ws[row][col]== 0) {
						Random r = new Random();
						char c = (char)(r.nextInt(26) + 'a');
						ws[row][col]=c;
					}
			     }
			  }   
		  }
	        
	      try {
	            FileWriter myWriter = new FileWriter(fileout);
	            for(int row = 0; row < size; row++) { 	
					for(int col = 0; col < size; col++) {
						myWriter.write(ws[row][col]);
					}
	            }
	            myWriter.write(keywords);
	            myWriter.close();
	          } catch (IOException e) {
	            System.out.println("An error occurred.");
	            e.printStackTrace();
	          } 
	        
	    }catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
	}

}
