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

public class AviaoComExecutivo extends Aviao{

    private Lugar[][] lugaresExec;
    private int lugaresExecLivres;

    protected AviaoComExecutivo(int[] nLugares) {
        super(Arrays.copyOfRange(nLugares, 2,4));
        this.lugaresExec = initArray(nLugares[0],nLugares[1]);
        this.lugaresExecLivres = nLugares[0] * nLugares[1];
    }

    public Lugar[] makeReservation(int amountOfSeats, SeatClass seatClass) {
        Lugar[] ret = null;
        if (seatClass == SeatClass.E) {
            ret = search(0, amountOfSeats, lugaresExec);
            if (ret == null)
                return null;
            setSeatValue(new ArrayList<>(Arrays.asList(ret)), numReserva);
            numReserva++;
            lugaresExecLivres -= amountOfSeats;
        }
        if (seatClass == SeatClass.T) {
            ret = search(0, amountOfSeats, lugaresTur);
            if (ret == null)
                return null;
            setSeatValue(new ArrayList<>(Arrays.asList(ret)), numReserva);
            numReserva++;
            lugaresTurLivres -= amountOfSeats;

        }
        return ret;
    }

    @Override
    public void cancelReservation(int reservation){
        ArrayList<Lugar> list = search(reservation, lugaresTur);
        list.addAll(search(reservation, lugaresExec));
        setSeatValue(list , 0);
    }

    public int getLugaresExecLivres() {
        return lugaresExecLivres;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        IntStream.range(1, lugaresTur.length + lugaresExec.length + 1).forEach(i -> sb.append(String.format("%3d", i)));
        sb.append("\n");
        for (int seat = 0; seat < Math.max(lugaresTur[0].length, lugaresExec[0].length); seat++){
            sb.append(Character.toString((char ) (seat + 65)));
            for (Lugar[] row : lugaresExec) {
                if (seat >= lugaresExec[0].length) {
                    sb.append(String.format("%3s", " "));
                    continue;
                }
                sb.append(String.format("%3d", row[seat].getReservation()));
            }
            for (Lugar[] row : lugaresTur) {
                if (seat >= lugaresTur[0].length) {
                    sb.append(String.format("%3s", " "));
                    continue;
                }
                sb.append(String.format("%3d", row[seat].getReservation()));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
