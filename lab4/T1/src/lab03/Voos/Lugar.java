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

public class Lugar {
    private int reservation;
    private final String code;

    public Lugar(String code){
        reservation = 0;
        this.code = code;
    }

    public int getReservation(){
        return reservation;
    }

    public void setReservation(int reservation){
        this.reservation = reservation;
    }

    public String getCode() {
        return code;
    }
}
