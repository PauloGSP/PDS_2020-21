//
//  UNIVERSITY OF AVEIRO
//
//  ACADEMIC YEAR:  2020-2021
//
//  SUBJECT:    PDS - PADRÃO E DESENHO DE SOFTWARE
//
//  AUTHORS:    TIAGO MATOS - 98134
//              THEO MAGNO  - 96145
//

package lab03.Voos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        ArrayList<Voo> voos = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("Escolha uma opção: (H para ajuda)");
            Voo voo;
            switch (sc.next()){
                case "H":
                    System.out.println(
                            "I filename - Lê um ficheiro de texto contento informação sobre um voo. A primeira\n" +
                                    "linha do ficheiro deve começar com o caracter '>' e indicar o código de voo, o\n" +
                                    "número de filas e lugares por fila em classe executiva (caso exista) e o número de\n" +
                                    "filas e lugares por fila em classe turística. As linhas seguintes, caso existam, contêm\n" +
                                    "reservas já efetuada.\n\n" +
                            "M flight_code - exibe o mapa das reservas de um voo, conforme mostra o exemplo.\n" +
                                    "Os lugares reservados são identificados pelo número sequencial da reserva; os lugares\n" +
                                    "livres são identificados pelo número 0.\n\n" +
                            "F flight_code num_seats_executive num_seats_tourist: acrescenta um novo voo,\n" +
                                    "com código, lugares em executiva (p.ex. 4x3, representando 4 filas com 3 lugares\n" +
                                    "por fila), e lugares em turística. Os lugares em classe executiva são opcionais,\n" +
                                    "podendo existir apenas lugares em turística.\n\n" +
                            "R flight_code class number_seats - acrescenta uma nova reserva a um voo, com\n" +
                                    "indicação do código do voo, da classe (T / E), e do número de lugares. O programa\n" +
                                    "deve verificar se há lugares disponíveis na classe pretendida. Caso a reserva seja\n" +
                                    "efetuada deve ser apresentado no ecrã o código da reserva no formato\n" +
                                    "flight_code:sequential_reservation_number e os lugares atribuídos.\n\n" +
                            "C reservation_code - cancela uma reserva. O código de reserva tem o formato " +
                                    "flight_code:sequential_reservation_number.\n\n" +
                            "Q: termina o programa.\n\n"
                    );
                    break;

                case "M":
                    voo = getFlight(voos, sc.next());
                    if (voo == null){
                        System.err.println("Voo não encontrado");
                        break;
                    }
                    System.out.println(voo.getReservationMap());
                    break;

                case "I":
                    voo = createVooFromTxt(sc.next());
                    if (voo != null)
                        voos.add(voo);
                    break;

                case "F":
                    String code = sc.next();
                    int[] input = Arrays.stream(sc.nextLine().trim().split("[\\sx]+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    voo = new Voo(code, input);
                    voos.add(voo);
                    break;


                case "C":
                    String[] reservation = sc.next().trim().split(":");
                    voo = getFlight(voos, reservation[0]);
                    if (voo == null){
                        System.err.println("Voo " + reservation[0] + " não encontrado");
                        break;
                    }
                    voo.cancelReservation(Integer.parseInt(reservation[1]));

                    break;

                case "R":

                    String tempCode = sc.next();
                    voo = getFlight(voos, tempCode);
                    if(voo == null) {
                        System.err.println("Voo " + tempCode + " não encontrado");
                        break;
                    }
                    SeatClass seatClass = SeatClass.valueOf(sc.next());
                    ArrayList<Lugar> lugaresRes = voo.makeReservation(sc.nextInt(), seatClass);
                    if (lugaresRes == null) {
                        System.out.println("Não foi possível efetuar a reserva.");
                        break;
                    }
                    System.out.print(tempCode + ":" + lugaresRes.get(0).getReservation());
                    lugaresRes.forEach(lugar -> System.out.print(" " + lugar.getCode() +" |"));
                    System.out.println();
                    break;

                case "Q":
                    return;
                default:
                    System.err.println("Invalid option, use H for help");
            }

        }
    }

    public static Voo getFlight(ArrayList<Voo> voos, String code){
        for (Voo voo: voos){
            if (code.equals(voo.getCode()))
                return voo;
        }
        return null;
    }

    public static Voo createVooFromTxt(String filename){

        try {
            File file = new File(filename);
            Scanner f = new Scanner(file);
            String code = f.next();

            if(code.charAt(0) != '>') {
                System.err.println("The file does not start with '>'");
                return null;
            }

            code = code.substring(1);
            int[] input = Arrays.stream(f.nextLine().trim().split("[\\sx]+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            Voo voo = new Voo(code, input);
            while (f.hasNext()){
                String[] line = f.nextLine().split("[\\s]+");
                voo.makeReservation(Integer.parseInt(line[1]), SeatClass.valueOf(line[0]));
            }
            return voo;
        } catch (FileNotFoundException e){
            System.err.println("An error occurred while trying to find file "+filename);
            e.printStackTrace();
        }
        return null;
    }
}
