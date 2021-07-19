import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ex1 {
	
	public static Map<Character, String> arguments = new HashMap<Character, String>();
	public static ArrayList<String> textoLido = new ArrayList<String>( );
	public static Map<String, ArrayList<Integer> > palavrasNaSopa2 = new HashMap<String, ArrayList<Integer>>();
	public static ArrayList<String> palavrasNaSopa = new ArrayList<String>( );
	public static ArrayList<String> palavrasEncontrar = new ArrayList<String>();

	
	static void argumentsTreatmeant(String[] args) {
		
		if (args.length == 0 || args.length > 2) {
			 System.err.print("Arguments Error \nUSAGE: program [input file] \n");
		     System.exit(1);
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("-timming")) {
				System.err.print("Arguments Error. You Should Pass Input File Name \nUSAGE: program [input file] \n");
				System.exit(1);
			} else {
				arguments.put('t', "N");
				arguments.put('i', args[0]);
			}
		} else {
			if (args[0].equalsIgnoreCase("-timming")) {
				arguments.put('t', "S");
				arguments.put('i', args[1]);
			} else if (args[1].equalsIgnoreCase("-timming")) {
				arguments.put('t', "S");
				arguments.put('i', args[0]);
			}
		}
	}
	
	public static void main(String[] args) {
		
		
		argumentsTreatmeant(args);
		Long start = (long) 0;
		if (arguments.get('t').equalsIgnoreCase("S")) {
			start = System.currentTimeMillis();
		}
		
		try {
			 File myObj = null;
			 try {
		      myObj = new File(arguments.get('i'));
			 } catch (ArrayIndexOutOfBoundsException e) {
			      System.err.print("Arguments Error \nUSAGE: program [input file] \n");
			      System.exit(1);
			 }
		      Scanner scan = new Scanner(myObj);
		      while (scan.hasNextLine()) {
		        String data = scan.nextLine();
		        textoLido.add(data);
		      }
		      scan.close();
		    } catch (FileNotFoundException e) {
		      System.err.print("Error opening file\n");
		      System.exit(1);
		    }
		 
		 
		 	int tamanho = textoLido.get(0).length();
		 	if (tamanho > 50) {
		    	System.out.println("Size of Matrix exceed limit");
		    	System.exit(0);
	    	}
		 	
		 	
		 	// Criaçao da Sopa de Letras (Matriz)
		 	char[][] sopa = new char[tamanho][tamanho];
	    	int linha = 0;
	    	int coluna = 0;
		 	for (int l = 0; l<tamanho; l++) {
		    	char[] stringToChar = textoLido.get(l).toCharArray();
		    	if (stringToChar.length < tamanho) {
			    	System.out.println("Matrix must be quadratic");
			    	System.exit(0);
		    	}
		    	for (int i = 0; i<tamanho; i++) {
		    		if (Character.isLowerCase(stringToChar[i])) {
				    	System.out.println("Character must be uppercase");
				    	System.exit(0);
		    		}
		    		sopa[linha][coluna] = stringToChar[i];
			    	coluna +=1;
		    	}
		    	linha += 1;
		    	coluna = 0;
		 	}
		 	
		 	
		 	// Identificar Palavras a encontra. Coloca-las num array
		 	for (int l = tamanho; l<textoLido.size(); l++) {
		 		ArrayList<String> subStrings = new ArrayList<String>();
		 		Collections.addAll(subStrings, textoLido.get(l).split("[;, ]"));
			 	for (String palavra: subStrings) {
			 		if (!(palavra.length() >= 4 && palavra.matches("[a-zA-Z]*"))) {
			 			System.out.println("Words must have at least 4 characters.");
				    	System.exit(0);
			 		} else {
			 			palavrasEncontrar.add(palavra.toUpperCase());
			 		}
			 	}
		 	}
		 	
		 	// Remoção determinadas palavras que nao devem ser consideradas
		 	for (String palavra: palavrasEncontrar) {
		 		for (String palavra2: palavrasEncontrar) {
			 		if (palavra!=palavra2 && (palavra.contains(palavra2) || palavra2.contains(palavra))) {
			 			System.out.println("Words must not be redundant.");
				    	System.exit(0);
			 		}
			 	}
		 	}
		 	
		 	// Introdução das Strings linhas no arrayPalavras possiveis
		 	int contadorLinhas = 1;
		 	for (String sequencia: textoLido) {
		 		ArrayList<Integer> posicaoTemp = new ArrayList<Integer>();
		 		posicaoTemp.add(contadorLinhas);
		 		posicaoTemp.add(0);
		 		posicaoTemp.add(1);
		 		palavrasNaSopa2.put(sequencia, posicaoTemp);
		 		ArrayList<Integer> posicaoTemp2 = new ArrayList<Integer>();
		 		posicaoTemp2.add(contadorLinhas);
		 		posicaoTemp2.add(0);
		 		posicaoTemp2.add(-1);
		 		sequencia = new StringBuilder(sequencia).reverse().toString();
		 		palavrasNaSopa2.put(sequencia, posicaoTemp2);
		 		contadorLinhas++;
		 	}
		 	
		 	// Introdução das Strings colunas no arrayPalavras possiveis
		 	char[] charToString = new char[tamanho];
		 	int contador = 0;
		 	for (int c = 0; c<tamanho; c++) {
		 		for (int l = 0; l<tamanho; l++) {
		 			charToString[contador] = sopa[l][c];
		 			contador++;
		 		}
		 		ArrayList<Integer> posicaoTemp = new ArrayList<Integer>();
		 		posicaoTemp.add(0);
		 		posicaoTemp.add(c+1);
		 		posicaoTemp.add(1);
		 		String nova = new String(charToString);
		 		palavrasNaSopa2.put(nova,posicaoTemp);
		 		ArrayList<Integer> posicaoTemp2 = new ArrayList<Integer>();
		 		nova = new StringBuilder(nova).reverse().toString();
		 		posicaoTemp2.add(0);
		 		posicaoTemp2.add(c+1);
		 		posicaoTemp2.add(-1);
		 		palavrasNaSopa2.put(nova,posicaoTemp2);
		 		contador = 0;
		 	}
		 	
		 
			// Introdução de 1/4 diagonais no arrayPalavras possiveis
		 	String s = "";
		 	int linhaguardada = -1;
		 	int colunaguardada = -1;
		 	for (int t = 0; t<tamanho; t++ ) {
		 		for (int l = t; l<tamanho; l++) {
		 			for (int c = 0; c<tamanho; c++) {
		 				if(l == 12) {
		 					break;
		 				}
		 				if (s.equals("")) {
		 					linhaguardada = l;
		 					colunaguardada = c;
		 				}
		 				s+=sopa[l][c];
		 				l++;
		 			}
		 		}
		 		ArrayList<Integer> posicaoTemp = new ArrayList<Integer>();
		 		posicaoTemp.add(linhaguardada+1);
		 		posicaoTemp.add(1);
		 		posicaoTemp.add(1); // 1 = downright
		 		palavrasNaSopa2.put(s, posicaoTemp);
		 		s = new StringBuilder(s).reverse().toString();
		 		ArrayList<Integer> posicaoTemp2 = new ArrayList<Integer>();
		 		posicaoTemp2.add(12);
		 		posicaoTemp2.add(s.length()-colunaguardada);
		 		posicaoTemp2.add(2); // 2 = upleft
		 		palavrasNaSopa2.put(s, posicaoTemp2);
		 		s = "";
		 	}
		 	
			// Introdução de 1/4 diagonais no arrayPalavras possiveis
		 	for (int t = 0; t<tamanho; t++ ) {
		 		for (int c = t; c<tamanho; c++) {
		 			for (int l = 0; l<tamanho; l++) {
		 				if(c == 12) {
		 					break;
		 				}
		 				if (s.equals("")) {
		 					linhaguardada = l;
		 					colunaguardada = c;
		 				}
		 				s+=sopa[l][c];
		 				c++;
		 			}
		 		}
		 		ArrayList<Integer> posicaoTemp = new ArrayList<Integer>();
		 		posicaoTemp.add(1);
		 		posicaoTemp.add(colunaguardada+1);
		 		posicaoTemp.add(1); // 1 = downright
		 		palavrasNaSopa2.put(s, posicaoTemp);
		 		s = new StringBuilder(s).reverse().toString();
		 		ArrayList<Integer> posicaoTemp2 = new ArrayList<Integer>();
		 		posicaoTemp2.add(s.length()-linhaguardada);
		 		posicaoTemp2.add(12);
		 		posicaoTemp2.add(2); // 2 = upleft
		 		palavrasNaSopa2.put(s, posicaoTemp2);
		 		s = "";
		 	}
		 	
			// Introdução de 1/4 diagonais no arrayPalavras possiveis
		 	for (int t = 0; t<tamanho; t++ ) {
		 		for (int l = t; l<tamanho; l++) {
		 			for (int c = tamanho-1; c>=0; c--) {
		 				if(l == 12) {
		 					break;
		 				}
		 				if (s.equals("")) {
		 					linhaguardada = l;
		 					colunaguardada = c;
		 				}
		 				s+=sopa[l][c];
		 				l++;
		 			}
		 		}
		 		ArrayList<Integer> posicaoTemp = new ArrayList<Integer>();
		 		posicaoTemp.add(linhaguardada+1);
		 		posicaoTemp.add(12);
		 		posicaoTemp.add(3); // 3 = downleft
		 		palavrasNaSopa2.put(s, posicaoTemp);
		 		s = new StringBuilder(s).reverse().toString();
		 		ArrayList<Integer> posicaoTemp2 = new ArrayList<Integer>();
		 		posicaoTemp2.add(12);
		 		posicaoTemp2.add(s.length()-colunaguardada);
		 		posicaoTemp2.add(4); // 4 = upright
		 		palavrasNaSopa2.put(s, posicaoTemp2);
		 		s = "";
		 	}
		 	
			// Introdução de 1/4 diagonais no arrayPalavras possiveis
		 	for (int t = 0; t<tamanho; t++ ) {
		 		for (int c = tamanho-1-t; c>=0; c--) {
		 			for (int l = 0; l<tamanho-t; l++) {
		 				if(c == -1) {
		 					break;
		 				}
		 				if (s.equals("")) {
		 					linhaguardada = l;
		 					colunaguardada = c;
		 				}
		 				s+=sopa[l][c];
		 				c--;
		 			}
		 		}
		 		ArrayList<Integer> posicaoTemp = new ArrayList<Integer>();
		 		posicaoTemp.add(1);
		 		posicaoTemp.add(colunaguardada+1);
		 		posicaoTemp.add(3); // 3 = downleft
		 		palavrasNaSopa2.put(s, posicaoTemp);
		 		s = new StringBuilder(s).reverse().toString();
		 		ArrayList<Integer> posicaoTemp2 = new ArrayList<Integer>();
		 		posicaoTemp2.add(s.length()-colunaguardada);
		 		posicaoTemp2.add(1);
		 		posicaoTemp2.add(4); // 2 = upright
		 		palavrasNaSopa2.put(s, posicaoTemp2);
		 		s = "";
		 	}
		 	
		 	// Procura Diagonal horizontal e vertical das palavras
		 	for (String palavra: palavrasEncontrar) {
		 		for (String palavraSopa: palavrasNaSopa2.keySet()) {
		 			if (palavraSopa.contains(palavra)) {
		 				if (palavrasNaSopa2.get(palavraSopa).get(0) == 0) {
		 					if (palavrasNaSopa2.get(palavraSopa).get(2) == 1) {
		 						System.out.printf("%s %d %d,%d Down \n",palavra,palavra.length(), palavraSopa.indexOf(palavra)+1, palavrasNaSopa2.get(palavraSopa).get(1));
		 					} else if (palavrasNaSopa2.get(palavraSopa).get(2) == -1) {
		 						System.out.printf("%s %d %d,%d Up \n",palavra,palavra.length(), 12-palavraSopa.indexOf(palavra), palavrasNaSopa2.get(palavraSopa).get(1));
		 					}
		 				} else if (palavrasNaSopa2.get(palavraSopa).get(1) == 0) {
		 					if (palavrasNaSopa2.get(palavraSopa).get(2) == 1) {
		 						System.out.printf("%s %d %d,%d Right \n",palavra,palavra.length(), palavrasNaSopa2.get(palavraSopa).get(0), palavraSopa.indexOf(palavra)+1);
		 					} else if (palavrasNaSopa2.get(palavraSopa).get(2) == -1) {
		 						System.out.printf("%s %d %d,%d Left \n",palavra,palavra.length(), palavrasNaSopa2.get(palavraSopa).get(0), 12-palavraSopa.indexOf(palavra));
		 					}
		 				} else {
		 					if (palavrasNaSopa2.get(palavraSopa).get(2) == 1) {
		 						System.out.printf("%s %d %d,%d DownRight \n",palavra,palavra.length(), palavrasNaSopa2.get(palavraSopa).get(0)+palavraSopa.indexOf(palavra), palavrasNaSopa2.get(palavraSopa).get(1)+palavraSopa.indexOf(palavra));
		 					}
		 					if (palavrasNaSopa2.get(palavraSopa).get(2) == 2) {
		 						System.out.printf("%s %d %d,%d UpLeft \n",palavra,palavra.length(), palavrasNaSopa2.get(palavraSopa).get(0)-palavraSopa.indexOf(palavra), palavrasNaSopa2.get(palavraSopa).get(1)-palavraSopa.indexOf(palavra));
		 					}
		 					if (palavrasNaSopa2.get(palavraSopa).get(2) == 3) {
		 						System.out.printf("%s %d %d,%d DownLeft \n",palavra,palavra.length(), palavrasNaSopa2.get(palavraSopa).get(0)+palavraSopa.indexOf(palavra), palavrasNaSopa2.get(palavraSopa).get(1)-palavraSopa.indexOf(palavra));
		 					}
		 					if (palavrasNaSopa2.get(palavraSopa).get(2) == 4) {
		 						System.out.printf("%s %d %d,%d UpRight \n",palavra,palavra.length(), palavrasNaSopa2.get(palavraSopa).get(0)-palavraSopa.indexOf(palavra), palavrasNaSopa2.get(palavraSopa).get(1)+palavraSopa.indexOf(palavra));
		 					}
		 				}
		 			}
		 		}
		 	}
		 	
		 if (arguments.get('t').equalsIgnoreCase("S")) {
		 	Long end = System.currentTimeMillis();
		 	System.out.printf("\nTempo Execução: %.03fs \n",(end-start)*0.001);
		 }
		 
	}

}
