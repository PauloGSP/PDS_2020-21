package ex2;

import java.util.ArrayList;

public class Plane {
    private String touristSeats, executiveSeats; // nLugares é do tipo AxB
    private int freeTouristSeats, freeExecutiveSeats, rows, TseatsPerRow, EseatsPerRow;
    private int touristRows, executiveRows, freeSeats;
    private ArrayList<String> Tseats = new ArrayList<String>();
    private ArrayList<String> Eseats = new ArrayList<String>();

    public static Plane createPlane(String touristSeats, String executiveSeats) {
        return new Plane(touristSeats, executiveSeats);
    }

    public static Plane createPlane(String touristSeats) {
        return new Plane(touristSeats);
    }

    private Plane(String touristSeats, String executiveSeats) {
        this.touristSeats = touristSeats;
        this.executiveSeats = executiveSeats;
        String[] temp = touristSeats.split("x");
        this.freeTouristSeats = Integer.parseInt(temp[0]) * Integer.parseInt(temp[1]);
        this.TseatsPerRow = Integer.parseInt(temp[1]);
        this.touristRows = Integer.parseInt(temp[0]);

        String[] tmp = executiveSeats.split("x");
        this.freeExecutiveSeats = Integer.parseInt(tmp[0]) * Integer.parseInt(tmp[1]);
        this.EseatsPerRow = Integer.parseInt(tmp[1]);
        this.executiveRows = Integer.parseInt(tmp[0]);

        this.rows = Integer.parseInt(temp[0]) + Integer.parseInt(tmp[0]);
        this.freeSeats = this.freeTouristSeats + this.freeExecutiveSeats;
        addSeats(); // add seats to list
    }

    private Plane(String touristSeats) {
        this.touristSeats = touristSeats;
        this.executiveSeats = null;
        String[] temp = touristSeats.split("x");
        this.freeTouristSeats = Integer.parseInt(temp[0]) * Integer.parseInt(temp[1]);
        this.TseatsPerRow = Integer.parseInt(temp[1]);
        this.touristRows = Integer.parseInt(temp[0]);
        this.executiveRows = 0;

        this.rows = Integer.parseInt(temp[0]);
        this.freeSeats = this.freeTouristSeats;
        addSeats(); // add seats to list
    }

    private void addSeats() { // add seats to list
        for (int i = 0; i < this.touristRows; i++) // for tourist rows -> letter
            for (int j = 0; j < this.TseatsPerRow; j++) { // for TseatsPerRow -> number
                String seat = Character.toString((char) ('A' + i)) + Integer.toString(j);
                Tseats.add(seat);
            }

        for (int i = 0; i < this.executiveRows; i++) // for executive rows -> letter
            for (int j = 0; j < this.EseatsPerRow; j++) { // for EseatsPerRow -> number
                String seat = Character.toString((char) ('A' + i)) + Integer.toString(j);
                Eseats.add(seat);
            }
    }

    public String getTouristSeats() {
        return this.touristSeats;
    }

    public void setTouristSeats(String touristSeats) {
        this.touristSeats = touristSeats;
    }

    public String getExecutiveSeats() {
        return this.executiveSeats;
    }

    public void setExecutiveSeats(String executiveSeats) {
        this.executiveSeats = executiveSeats;
    }

    public int getFreeTouristSeats() {
        return this.freeTouristSeats;
    }

    public void setFreeTouristSeats(int freeTouristSeats) {
        this.freeTouristSeats = freeTouristSeats;
    }

    public int getFreeExecutiveSeats() {
        return this.freeExecutiveSeats;
    }

    public void setFreeExecutiveSeats(int freeExecutiveSeats) {
        this.freeExecutiveSeats = freeExecutiveSeats;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTseatsPerRow() {
        return this.TseatsPerRow;
    }

    public void setTseatsPerRow(int TseatsPerRow) {
        this.TseatsPerRow = TseatsPerRow;
    }

    public int getEseatsPerRow() {
        return this.EseatsPerRow;
    }

    public void setEseatsPerRow(int EseatsPerRow) {
        this.EseatsPerRow = EseatsPerRow;
    }

    public int getTouristRows() {
        return this.touristRows;
    }

    public void setTouristRows(int touristRows) {
        this.touristRows = touristRows;
    }

    public int getExecutiveRows() {
        return this.executiveRows;
    }

    public void setExecutiveRows(int executiveRows) {
        this.executiveRows = executiveRows;
    }

    public int getFreeSeats() {
        return this.freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public ArrayList<String> getTseats() {
        return this.Tseats;
    }

    public void setTseats(ArrayList<String> Tseats) {
        this.Tseats = Tseats;
    }

    public ArrayList<String> getEseats() {
        return this.Eseats;
    }

    public void setEseats(ArrayList<String> Eseats) {
        this.Eseats = Eseats;
    }

    @Override
    public String toString() {
        return "{" + " touristSeats='" + getTouristSeats() + "'" + ", executiveSeats='" + getExecutiveSeats() + "'"
                + ", freeTouristSeats='" + getFreeTouristSeats() + "'" + ", freeExecutiveSeats='"
                + getFreeExecutiveSeats() + "'" + ", rows='" + getRows() + "'" + ", TseatsPerRow='" + getTseatsPerRow()
                + "'" + ", EseatsPerRow='" + getEseatsPerRow() + "'" + ", touristRows='" + getTouristRows() + "'"
                + ", executiveRows='" + getExecutiveRows() + "'" + ", freeSeats='" + getFreeSeats() + "'" + ", Tseats='"
                + getTseats() + "'" + ", Eseats='" + getEseats() + "'" + "}";
    }
}
