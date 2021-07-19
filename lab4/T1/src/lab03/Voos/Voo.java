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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Voo {
    private final String code;
    private final Aviao aviao;

    public Voo (String code, int[] nLugares){
        this.code = code;
        this.aviao = Aviao.createAviao(nLugares);
    }

    public ArrayList<Lugar> makeReservation(int amountOfSeats, SeatClass seatClass){
        if (seatClass == SeatClass.E) {
            if (aviao instanceof AviaoComExecutivo){
                Lugar[] ret = ((AviaoComExecutivo) aviao).makeReservation(amountOfSeats, seatClass);
                if (ret != null)
                    return new ArrayList<>(Arrays.asList(ret));
                else
                    return null;
            }
            else
                return null;
        }
        else
            return new ArrayList<>(aviao.makeReservation(amountOfSeats));
    }

    public void cancelReservation(int reservation) {
        aviao.cancelReservation(reservation);
    }

    public String getCode(){
        return code;
    }

    public String getReservationMap(){
        return aviao.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return "Código de voo" + this.code + ". Lugares disponíveis:" +
                (aviao instanceof AviaoComExecutivo ? ((AviaoComExecutivo )aviao).getLugaresExecLivres() + "lugares em classe Executiva; " : "") +
                aviao.getLugaresTurLivres() + " lugares em classe ";
    }

}
