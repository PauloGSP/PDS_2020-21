package classes;

public enum ReservationClass {

  T("Turistíca"), E("Executiva");

  private String designation;

  private ReservationClass(String designation) {
    this.designation = designation;
  }

  public String getDesignation() {
    return this.designation;
  }

}
