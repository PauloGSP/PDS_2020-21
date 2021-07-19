public class PrintJob implements Runnable {
  private static int nextJobId = 0;

  private int jobId;
  private Document doc;

  public PrintJob(Document doc) {
    this.jobId = nextJobId;
    nextJobId++;

    this.doc = doc;
  }

  public int getJobId() {
    return this.jobId;
  }

  public Document getDoc() {
    return this.doc;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.err.println(e.getStackTrace());
    }
  }

}
