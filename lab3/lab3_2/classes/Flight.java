package classes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Flight {

  private Plane plane;
  private Map<Integer, Reservation> reservations;

  private int[][] seatMap;

  public Flight(Plane plane) {
    this.plane = plane;
    // Uses Integer as key so as to keep the reservation "index" even if other
    // reservation is cancelled
    this.reservations = new HashMap<>();

    this.initSeatMap();
  }

  /**
   * Prints the seat map formatted
   */
  public void showSeatMap() {
    ClassSize touristicSize = plane.getTouristicSize(), executiveSize = plane.getExecutiveSize();
    int totalQueues = executiveSize.getQueues() + touristicSize.getQueues();

    int maxQueueCHairs = touristicSize.getQueueChairs();
    if (executiveSize.getQueueChairs() > touristicSize.getQueueChairs())
      maxQueueCHairs = executiveSize.getQueueChairs();

    // Print the queue numbers
    System.out.print(String.format("%2s ", ""));
    for (int i = 1; i <= totalQueues; i++) {
      System.out.print(String.format("%2d ", i));
    }
    System.out.println();

    String symbol;
    for (int chair = 0; chair < maxQueueCHairs; chair++) {
      // Print the column letter
      System.out.print(String.format("%2c ", HelperFuncs.mapIdxToSeatLetter(chair)));
      for (int queue = 0; queue < totalQueues; queue++) {
        symbol = " ";

        if (this.seatMap[queue].length > chair)
          symbol = String.valueOf(this.seatMap[queue][chair]);

        System.out.print(String.format("%2s ", symbol));
      }

      System.out.println();
    }

  }

  /**
   * Initialize the Seat Map
   */
  private void initSeatMap() {
    ClassSize touristicSize = plane.getTouristicSize(), executiveSize = plane.getExecutiveSize();

    int totalQueues = executiveSize.getQueues() + touristicSize.getQueues();

    this.seatMap = new int[totalQueues][];

    int i = 0;

    for (; i < executiveSize.getQueues(); i++)
      this.seatMap[i] = new int[executiveSize.getQueueChairs()];

    for (; i < totalQueues; i++)
      this.seatMap[i] = new int[touristicSize.getQueueChairs()];
  }

  /**
   * Call the main bookConsecutiveSeats function without saving the seats, just
   * checking if it is possible
   * 
   * @param queue
   * @param chair
   * @param maxQueue
   * @param missingSeats
   * @return boolean
   */
  private boolean bookConsecutiveSeats(int queue, int chair, int maxQueue, int missingSeats) {
    return this.bookConsecutiveSeats(queue, chair, maxQueue, missingSeats, 0);
  }

  /**
   * Recursive function to reserve consecutive seats
   * 
   * @param queue          Queue of the seat to be reserved
   * @param chair          Seat number to be reserved in the queue
   * @param maxQueue       Max queue we can reserve, used in case we should search
   *                       for executive class only
   * @param missingSeats   Seats that still need to be reserved
   * @param reservationIdx Reservation number associated with the reservation
   * @return boolean true if the operation was successful
   */
  private boolean bookConsecutiveSeats(int queue, int chair, int maxQueue, int missingSeats, int reservationIdx) {
    if (queue >= maxQueue)
      return false;

    if (missingSeats == 0)
      return true;

    if (chair >= this.seatMap[queue].length)
      return this.bookConsecutiveSeats(queue + 1, 0, maxQueue, missingSeats, reservationIdx);

    if (this.seatMap[queue][chair] == 0) {
      // If there is a valid reservatio number, reserve the seat
      if (reservationIdx > 0) {
        this.seatMap[queue][chair] = reservationIdx;
        this.reservations.get(reservationIdx).addSeatCode(HelperFuncs.getSeatCode(queue, chair));
      }

      boolean success = this.bookConsecutiveSeats(queue, chair + 1, maxQueue, missingSeats - 1, reservationIdx);

      if (!success && reservationIdx >= 0)
        this.seatMap[queue][chair] = 0;

      return success;
    } else
      return false;
  }

  /**
   * Add a reservation to the list, booking the seats and adding the associated
   * seat codes and reservation number
   * 
   * @param reservation Reservation to be inserted
   * @return Reservation Reservation inserted with the reservation data
   */
  public Reservation addReservation(Reservation reservation) {
    ClassSize touristicSize = plane.getTouristicSize(), executiveSize = plane.getExecutiveSize();
    int startQueue = 1, endQueue = executiveSize.getQueues() + touristicSize.getQueues();

    int reservNum = 1;
    if (this.reservations.size() > 0)
      reservNum = Collections.max(this.reservations.keySet()) + 1;

    // Select the array indexes to be used
    switch (reservation.getReservationClass()) {
    case E:
      endQueue = executiveSize.getQueues();
      break;
    case T:
      startQueue = executiveSize.getQueues() + 1;
      break;
    }

    int queue, chair, freeChairs = 0;
    boolean freeQueue;

    // Search for empty queues and count the number of empty seats
    for (queue = startQueue - 1; queue < endQueue; queue++) {

      freeQueue = true;

      for (chair = 0; chair < this.seatMap[queue].length; chair++) {
        if (this.seatMap[queue][chair] == 0)
          freeChairs++;
        else
          freeQueue = false;
      }

      // Try to book the seats if the queue is empty
      if (freeQueue && this.bookConsecutiveSeats(queue, 0, endQueue, reservation.getNumberPassegers())) {
        reservation.setReservationNumber(reservNum);
        this.reservations.put(reservNum, reservation);
        this.bookConsecutiveSeats(queue, 0, endQueue, reservation.getNumberPassegers(), reservNum);

        return reservation;
      }
    }

    // If the reservation was not successful with the last method and there are not
    // enough free chairs the reservation is not possible
    if (freeChairs < reservation.getNumberPassegers())
      return null;

    // If there are enough free chairs we can book the empty seats in any order
    reservation.setReservationNumber(reservNum);
    this.reservations.put(reservNum, reservation);

    int missing = reservation.getNumberPassegers();

    for (queue = startQueue - 1; queue < endQueue; queue++) {

      for (chair = 0; chair < this.seatMap[queue].length; chair++) {
        if (this.seatMap[queue][chair] == 0) {
          this.seatMap[queue][chair] = reservNum;
          reservation.addSeatCode(HelperFuncs.getSeatCode(queue, chair));
          missing--;
        }

        if (missing == 0)
          return reservation;
      }

    }

    return null;
  }

  /**
   * Cancel a reservation
   * 
   * @param reservationNumber
   * @throws ReservationSystemException
   */
  public void cancelReservation(int reservationNumber) throws ReservationSystemException {
    if (!this.reservations.containsKey(reservationNumber))
      throw new ReservationSystemException(ReservationSystemException.Types.UnknownReservation);

    for (int i = 0; i < seatMap.length; i++) {
      for (int j = 0; j < seatMap[i].length; j++) {
        if (seatMap[i][j] == reservationNumber)
          seatMap[i][j] = 0;
      }
    }
    this.reservations.remove(reservationNumber);
  }

  /**
   * @return Plane
   */
  public Plane getPlane() {
    return this.plane;
  }

  /**
   * @return Map<Integer, Reservation>
   */
  public Map<Integer, Reservation> getReservations() {
    return this.reservations;
  }

}