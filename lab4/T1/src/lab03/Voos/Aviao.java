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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Aviao {
    protected Lugar[][] lugaresTur;
    protected static int numReserva = 1;
    protected int lugaresTurLivres;

    protected Aviao(int[] nLugares) {
        this.lugaresTur = initArray(nLugares[0],nLugares[1]);
        lugaresTurLivres = nLugares[0] * nLugares[1];
    }

    protected Lugar[][] initArray(int nRows, int nSeats){
        Lugar[][] lugares = new Lugar[nRows][nSeats];
        for (int row = 0; row < lugares.length; row++) {
            for (int seat = 0; seat < lugares[row].length; seat++) {
                lugares[row][seat] = new Lugar(Character.toString(row + (char )(seat + 65)));
            }
        }
        return lugares;
    }

    public static Aviao createAviao(int[] nLugares){
        if (nLugares.length == 4)
            return new AviaoComExecutivo(nLugares);
        else if (nLugares.length == 2)
            return new Aviao(nLugares);
        else
            throw new IllegalArgumentException(nLugares.length + " valores fornecidos para criar aviao");
    }

    public ArrayList<Lugar> makeReservation(int amountOfSeats) {
        ArrayList<Lugar> ret = new ArrayList<>(Arrays.asList(search(0, amountOfSeats, lugaresTur)));
        if (!ret.isEmpty()) {
            setSeatValue(ret, numReserva);
            numReserva++;
            lugaresTurLivres -= amountOfSeats;
        }
        return ret;
    }

    public void cancelReservation(int reservation){
        ArrayList<Lugar> ret = search(reservation, lugaresTur);
        setSeatValue(ret , 0);
    }

    /**
     * This method changes the reservation value of each element of a given array
     *
     * @param seats Array of Lugar instances
     * @return 0 if successful
     */
    protected int setSeatValue(ArrayList<Lugar> seats, int value){
        if (seats.isEmpty())
            return 1;
        seats.forEach(seat -> seat.setReservation(value));
        return 0;
    }

    /**
     * Search method that checks for n seats in a row that are reserved to searchValue
     *
     * @param searchValue reservation value to look for
     * @param amountOfSeats quantity of places to look for
     * @param lugares matrix of Lugar where to search
     * @return Lugar[]
     */
    protected Lugar[] search(int searchValue, int amountOfSeats, Lugar[][] lugares){
        int seatsCounter = 0;
        Lugar[] ret = new Lugar[amountOfSeats];
        for (Lugar[] lugar : lugares) {
            for (int seat = 0; seat < lugar.length; seat++) {
                if (lugar[seat].getReservation() != searchValue) {
                    if (seatsCounter != 0)
                        seatsCounter = 0;
                    continue;
                }
                ret[seatsCounter++] = lugar[seat];
                if (seatsCounter == amountOfSeats)
                    return ret;
            }
        }
        return null;
    }

    protected ArrayList<Lugar> search(int searchValue, Lugar[][] lugares){
        ArrayList<Lugar> list= new ArrayList<>();
        for (Lugar[] row : lugares) {
            for (Lugar lugar : row) {
                if (lugar.getReservation() != searchValue) {
                    continue;
                }
                list.add(lugar);
            }
        }
        return list;
    }
    public int getLugaresTurLivres() {
        return lugaresTurLivres;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        IntStream.range(1, lugaresTur.length + 1).forEach(i -> sb.append(String.format("%3d", i)));
        sb.append("\n");
        for (int seat = 0; seat < lugaresTur[0].length; seat++){
            sb.append(Character.toString((char )seat + 65));
            for (Lugar[] row : lugaresTur) sb.append(String.format("%3d", row[seat].getReservation()));
            sb.append("\n");
        }
        return sb.toString();
    }
}
