//Authors: Pedro Monteiro | José Trigo
package ex2;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String choice;
        String [] input;
        Scanner sc = new Scanner(System.in);
        while (true) {
        System.out.println("Choose an option: (H for help)");
        System.out.print("Command: ");
        choice = sc.nextLine();
            if (choice.toUpperCase().startsWith("H")){
                System.out.println("\nCommands list: ");
                System.out.println("I <filename> - Flight information");
                System.out.println("M <flight code>- Map of reserves of one flight");
                System.out.println("F <flight code> <num executive seats> <num tourist seats> - Add new flight");
                System.out.println("R <flight code> <class (T or E)> <number seats> - Add new reservation");
                System.out.println("C <reservation code>  - Cancel reservation");
                System.out.println("Q - exit program\n");
            }
            else if (choice.toUpperCase().startsWith("I")){
                input = getInput(choice);
                if (input.length != 2){
                    System.err.println("Incorrect command (Should be I <filename>)");
                }
                else{
                    //TODO
                    System.out.println("Código de voo TP1920. Lugares disponíveis: 6 lugares em classe Executiva; 45 lugares em classe Turística.");
                }
                
            }
            else if (choice.toUpperCase().startsWith("M")){
                // exibir mapa de reservas de um voo
                //TODO
                input = getInput(choice);
                if (input.length != 2){
                    System.err.println("Incorrect command (Should be M <flight code>)");
                }else{
                    System.out.println("M TP1920");
                }
            }
            else if (choice.toUpperCase().startsWith("F")){
                // TODO
                input = getInput(choice);
                if (input.length != 4){
                    System.err.println("Incorrect command (Should be F <flight code> <num executive seats> <num tourist seats>)");
                }else{
                    System.out.println("");
                }
            }
            else if (choice.toUpperCase().startsWith("R")){
                //TODO
                input = getInput(choice);
                if (input.length != 4){
                    System.err.println("Incorrect command (Should be R <flight code> <class> <number seats>)");
                }else{
                    System.out.println("TP1930: 2 = 3A | 3B | 3C");
                }
            }
            else if (choice.toUpperCase().startsWith("C")){
                //TODO
                input = getInput(choice);
                if (input.length != 2){
                    System.err.println("Incorrect command (Should be C <reservation code>");
                }else{
                    System.out.println("");
                }
            }

            else if (choice.toUpperCase().startsWith("Q")){
                System.exit(0);
                break;
            }
            else{
                System.out.println("Command Not Found!");
            }
        }
        sc.close();
    
    }

    public static String[] getInput(String line){
        return line.split(" ");
    }
        /*
        Plane p1 = Plane.createPlane("12x3", "6x2");
        Plane p2 = Plane.createPlane("12x3");
        Flight f1 = Flight.createFlight("TP1920", p1);
        Flight f2 = Flight.createFlight("TP1921", p2);
        Flight.bookFlight(p1, "executive", 4);
        */
}