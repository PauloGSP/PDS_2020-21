package classes;

public class SystemCommand {
  private SystemCommandAction action;
  private String description;

  public SystemCommand(SystemCommandAction action, String description) {
    this.action = action;
    this.description = description;
  }

  /**
   * The function to be called when this command is called
   * 
   * @param args[]
   */
  public void run(String args[]) throws ReservationSystemException {
    this.action.run(args);
  }

  /**
   * @return String
   */
  @Override
  public String toString() {
    return this.description;
  }
}
