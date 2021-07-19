package classes;

/**
 * Helper funcs describe repetitive tasks that didn't fit any other class
 */
public class HelperFuncs {

  /**
   * Returns the correspondent Queue number from the flight array that maps the
   * seats
   * 
   * @param mapIdx Queue index
   * @return int Queue Number
   */
  public static int mapIdxToQueueNumber(int mapIdx) {
    return mapIdx + 1;
  }

  /**
   * Returns the correspondent Seat letter from the flight array that maps the
   * seats
   * 
   * @param mapIdx Seat index
   * @return int Seat leter (as character)
   */
  public static int mapIdxToSeatLetter(int mapIdx) {
    return 'A' + mapIdx;
  }

  /**
   * Retrives a seat code based on the queue number and seat letter
   * 
   * @param queueIdx Queue index
   * @param seatIdx  Seat index
   * @return String Seat code
   */
  public static String getSeatCode(int queueIdx, int seatIdx) {
    return String.format("%d%c", mapIdxToQueueNumber(queueIdx), mapIdxToSeatLetter(seatIdx));
  }

}
