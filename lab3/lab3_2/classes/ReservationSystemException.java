package classes;

public class ReservationSystemException extends Exception {
  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_ERROR_MSG = "Something went wrong with the system!";
  private static final String UNKNOWN_COMMAND_ERROR_MSG = "The command inserted was not recognized!";
  private static final String ARGUMENTS_ERROR_MSG = "Wrong arguments were passed to the argument!";
  private static final String UNKNOWN_FLIGHT_ERROR_MSG = "Specified flight does not exist!";
  private static final String UNKNOWN_RESERVATION_ERROR_MSG = "Specified reservation does not exist!";
  private static final String FILE_NOT_FOUND_ERROR_MSG = "Specified file does not exist!";

  public enum Types {
    UnknownCommand, Arguments, UnknownFlight, UnknownReservation, FileNotFound
  }

  private static String getMessage(Types type) {
    String message = DEFAULT_ERROR_MSG;

    switch (type) {
    case UnknownCommand:
      message = UNKNOWN_COMMAND_ERROR_MSG;
      break;
    case Arguments:
      message = ARGUMENTS_ERROR_MSG;
      break;
    case UnknownFlight:
      message = UNKNOWN_FLIGHT_ERROR_MSG;
      break;
    case UnknownReservation:
      message = UNKNOWN_RESERVATION_ERROR_MSG;
      break;
    case FileNotFound:
      message = FILE_NOT_FOUND_ERROR_MSG;
      break;
    }

    return message;
  }

  public ReservationSystemException() {
    super(DEFAULT_ERROR_MSG);
  }

  public ReservationSystemException(Types type) {
    super(getMessage(type));
  }
}
