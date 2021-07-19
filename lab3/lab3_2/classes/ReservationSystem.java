package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ReservationSystem {
  private Map<String, SystemCommand> commands;
  private Map<String, Flight> flights;
  private boolean active;

  public ReservationSystem() {
    this.active = true;
    this.flights = new HashMap<>();

    this.commands = new LinkedHashMap<>();

    // Add commands to the system
    this.commands.put("H", new SystemCommand(this::help, "Show available commands"));
    this.commands.put("I", new SystemCommand(this::readFile, "Import flight information from file"));
    this.commands.put("M", new SystemCommand(this::printSeatMap, "Show flight seat map"));
    this.commands.put("F", new SystemCommand(this::createFlight, "Add a new flight"));
    this.commands.put("R", new SystemCommand(this::createReservation, "Create a reservation"));
    this.commands.put("C", new SystemCommand(this::cancelReservation, "Cancel a reservation"));
    this.commands.put("Q", new SystemCommand(this::quit, "Quit system"));
  }

  /**
   * Returns the active boolean that checks if the command Q has been inserted
   * 
   * @return boolean
   */
  public boolean getIsActive() {
    return this.active;
  }

  /**
   * @param code
   * @param flight
   */
  public void addFlight(String code, Flight flight) {
    this.flights.put(code, flight);
  }

  /**
   * Runs a command defined by the first element of args
   * 
   * @param args
   * @throws ReservationSystemException
   */
  public void run(String[] args) throws ReservationSystemException {
    if (args.length == 0)
      throw new ReservationSystemException(ReservationSystemException.Types.Arguments);

    String command = args[0];
    if (!this.commands.containsKey(command))
      throw new ReservationSystemException(ReservationSystemException.Types.UnknownCommand);

    // Run the command-specific function without the identifier
    this.commands.get(command).run(Arrays.copyOfRange(args, 1, args.length));
  }

  /**
   * H command, lists all commands
   * 
   * @param args
   */
  private void help(String[] args) {
    for (String command : this.commands.keySet()) {
      System.out.println(String.format("%-5s %s", command, this.commands.get(command)));
    }
  }

  /**
   * I command, reads a flight from a file
   * 
   * @param args
   * @throws ReservationSystemException
   */
  private void readFile(String[] args) throws ReservationSystemException {
    if (args.length == 0)
      throw new ReservationSystemException(ReservationSystemException.Types.Arguments);

    File file = new File(args[0]);
    try {
      Scanner sc = new Scanner(file);
      String[] details = sc.nextLine().split(">")[1].split(" ");

      // Delegates the creation of a flight to the F command
      this.createFlight(details);
      Flight flight = this.flights.get(details[0]);
      ClassSize executiveSize = flight.getPlane().getExecutiveSize(),
          touristicSize = flight.getPlane().getTouristicSize();

      System.out.print(String.format("Código de voo %s. Lugares disponíveis: ", details[0]));
      if (executiveSize.totalSeats() > 0)
        System.out.print(String.format("%d lugares em classe Executiva; ", executiveSize.totalSeats()));
      System.out.println(String.format("%d lugares em classe Turística.", touristicSize.totalSeats()));

      while (sc.hasNextLine()) {
        String[] line = sc.nextLine().split(" ");
        this.createReservation(details[0], line[0], line[1]);
      }

      sc.close();
    } catch (FileNotFoundException e) {
      throw new ReservationSystemException(ReservationSystemException.Types.FileNotFound);
    }
  }

  /**
   * M command, print a map of the seats' state
   * 
   * @param args
   */
  private void printSeatMap(String[] args) {
    this.flights.get(args[0]).showSeatMap();
  }

  /**
   * F command, creates a flight
   * 
   * @param args
   * @throws ReservationSystemException
   */
  private void createFlight(String[] args) throws ReservationSystemException {
    if (args.length < 2)
      throw new ReservationSystemException(ReservationSystemException.Types.Arguments);

    ClassSize touristicSize, executiveSize;

    if (args.length == 2) {
      touristicSize = ClassSize.fromString(args[1]);
      executiveSize = ClassSize.empty();
    } else {
      touristicSize = ClassSize.fromString(args[2]);
      executiveSize = ClassSize.fromString(args[1]);
    }

    Flight flight = new Flight(new Plane(touristicSize, executiveSize));
    this.addFlight(args[0], flight);
  }

  /**
   * Creates a reservation
   * 
   * @param flightCode Code for the new flight
   * @param seatsClass String with either E or T for the class
   * @param nPassagers String with digits of the number of passegers
   * @return Reservation Class created or null if it wasn't possible
   * @throws ReservationSystemException
   */
  private Reservation createReservation(String flightCode, String seatsClass, String nPassagers)
      throws ReservationSystemException {

    Flight flight = this.flights.get(flightCode);
    if (flight == null)
      throw new ReservationSystemException(ReservationSystemException.Types.UnknownFlight);

    ClassSize executiveSize = flight.getPlane().getExecutiveSize();

    ReservationClass reservClass = ReservationClass.valueOf(seatsClass);
    if (reservClass == ReservationClass.E && executiveSize.totalSeats() == 0)
      System.out.println("Classe executiva não disponível neste voo.");

    int numPassagers = Integer.parseInt(nPassagers);

    Reservation r = flight.addReservation(new Reservation(reservClass, numPassagers));
    if (r == null) {
      System.out
          .println(String.format("Não foi possível obter lugares para a reserva: %s %d", seatsClass, numPassagers));
      return null;
    }

    return r;

  }

  /**
   * R command, creates a reservation
   * 
   * @param args
   * @throws ReservationSystemException
   */
  private void createReservation(String[] args) throws ReservationSystemException {
    if (args.length < 3)
      throw new ReservationSystemException(ReservationSystemException.Types.Arguments);

    Reservation r = createReservation(args[0], args[1], args[2]);
    if (r != null)
      System.out.println(
          String.format("%s:%d = %s", args[0], r.getReservationNumber(), String.join(" | ", r.getSeatCodes())));
  }

  /**
   * C command, cancels a reservation
   * 
   * @param args
   * @throws ReservationSystemException
   */
  private void cancelReservation(String[] args) throws ReservationSystemException {
    if (args.length == 0)
      throw new ReservationSystemException(ReservationSystemException.Types.Arguments);

    String[] code = args[0].split(":");

    if (code.length != 2)
      throw new ReservationSystemException(ReservationSystemException.Types.Arguments);

    Flight flight = flights.get(code[0]);
    if (flight == null)
      throw new ReservationSystemException(ReservationSystemException.Types.UnknownFlight);

    flight.cancelReservation(Integer.parseInt(code[1]));
  }

  /**
   * Q command, turns active boolean to false to notify the main class that it
   * should exit, but does not force
   * 
   * @param args
   */
  private void quit(String[] args) {
    this.active = false;
  }
}
