package classes;

public class ClassSize {

  private int queues;
  private int queueChairs;

  public ClassSize(int queues, int queueChairs) {
    this.queues = queues;
    this.queueChairs = queueChairs;
  }

  /**
   * @return int
   */
  public int getQueues() {
    return this.queues;
  }

  /**
   * @return int
   */
  public int getQueueChairs() {
    return this.queueChairs;
  }

  /**
   * Multiplies the number of queues and seats per queue to get a total number of
   * seats
   * 
   * @return int Number of chairs
   */
  public int totalSeats() {
    return this.getQueues() * this.getQueueChairs();
  }

  /**
   * @return ClassSize
   */
  public static ClassSize empty() {
    return new ClassSize(0, 0);
  }

  /**
   * @param size Size in the format "(Number of queues)x(Number of chairs per
   *             queue)"
   * @return ClassSize
   */
  public static ClassSize fromString(String size) {
    String[] dimensions = size.split("x");

    if (dimensions.length == 2)
      return new ClassSize(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));

    return ClassSize.empty();
  }

}
