package ex2;

import java.util.ArrayList;
import java.util.Iterator;

public class Flight {
    private String code;
    private Plane p1;

    public static Flight createFlight(String code, Plane plane) {
        return new Flight(code, plane);
    }

    private Flight(String code, Plane p1) {
        this.code = code;
        this.p1 = p1;
    }

    public static boolean bookFlight(Plane p1, String classe, int passengers) {
        int freeSeats, seatsPerRow;
        ArrayList<String> seats;

        if (classe.equals("executive")) {
            seats = p1.getEseats(); // ArrayList of seats
            freeSeats = p1.getFreeExecutiveSeats(); // int
            seatsPerRow = p1.getEseatsPerRow(); // int
        }

        else if (classe.equals("tourist")) {
            seats = p1.getTseats(); // ArrayList of seats
            freeSeats = p1.getFreeTouristSeats(); // int
            seatsPerRow = p1.getTseatsPerRow(); // int
        }

        else
            return false;

        if (freeSeats < passengers)
            return false;

        ArrayList<Integer> indexes = findElegibleSeats(passengers, seats, freeSeats);

        if (indexes == null) // TODO testar null
            return false;

        for (Integer index : indexes)
            seats.remove(index);

        return true;
    }

    private static ArrayList<Integer> findElegibleSeats(int passengers, ArrayList<String> seats, int freeSeats) {
        int sameRowSeats = 0;
        char letter;
        char previousLetter = ' ';
        ArrayList<Integer> indexes = new ArrayList<Integer>();

        for (int i = 0; i < freeSeats; i++) { // TRY TO FIND EMPTY ROWS
            seat = seats.get(i); //
            letter = seat.charAt(0);

            if (letter == previousLetter || previousLetter == ' ') { // if same row
                sameRowSeats++;
                indexes.add(i);
            } else {
                sameRowSeats = 0;
                indexes.clear();
            }

            if (sameRowSeats == seatsPerRow) { // Empty row found
                for (int j = i; j < freeSeats; i++) // TODO parar quando adicionar n suficiente
                    indexes.add(j);

                return indexes;
            }
            previousLetter = letter;
        }

        previousLetter = ' ';
        for (int i = 0; i < freeSeats; i++) { // TRY TO GET SEQUENTIAL SEATS
            seat = seats.get(i); //
            letter = seat.charAt(0);

            if (letter == previousLetter || previousLetter == ' ') { // if same row
                elegibleSeats++;
                indexes.add(i);
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Plane getP1() {
        return this.p1;
    }

    public void setP1(Plane p1) {
        this.p1 = p1;
    }

    @Override
    public String toString() {
        return "{" + " code='" + getCode() + "'" + ", p1='" + getP1() + "'" + "}";
    }
}
