package classes;

import java.util.ArrayList;
import java.util.List;

public class Reservation {

  private ReservationClass reservationClass;
  private int numberPassegers;
  private Integer reservationNumber;
  private List<String> seatCodes;

  public Reservation(ReservationClass reservationClass, int numberPassegers) {
    this.reservationClass = reservationClass;
    this.numberPassegers = numberPassegers;
    this.reservationNumber = null;
    this.seatCodes = new ArrayList<>();
  }

  /**
   * @return ReservationClass
   */
  public ReservationClass getReservationClass() {
    return this.reservationClass;
  }

  /**
   * @return int
   */
  public int getNumberPassegers() {
    return this.numberPassegers;
  }

  /**
   * @return Integer
   */
  public Integer getReservationNumber() {
    return this.reservationNumber;
  }

  /**
   * @param reservationNumber
   */
  public void setReservationNumber(Integer reservationNumber) {
    this.reservationNumber = reservationNumber;
  }

  /**
   * @return List<String>
   */
  public List<String> getSeatCodes() {
    return this.seatCodes;
  }

  /**
   * @param seatCode
   * @return boolean
   */
  public boolean addSeatCode(String seatCode) {
    if (this.seatCodes.size() < this.numberPassegers) {
      if (!this.seatCodes.contains(seatCode)) {
        this.seatCodes.add(seatCode);
        return true;
      }
    }

    return false;
  }

}
