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

public enum SeatClass {
    E("EXECUTIVE"),
    T("TOURTIST");

    public final String s;

    private SeatClass(String s) {
        this.s = s;
    }
}
