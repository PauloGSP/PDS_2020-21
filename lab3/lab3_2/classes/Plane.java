package classes;

public class Plane {

  private ClassSize executiveSize;
  private ClassSize touristicSize;

  public Plane(ClassSize touristicSize) {
    this.touristicSize = touristicSize;
    this.executiveSize = ClassSize.empty();
  }

  public Plane(ClassSize touristicSize, ClassSize executiveSize) {
    this.touristicSize = touristicSize;
    this.executiveSize = executiveSize;
  }

  /**
   * @return ClassSize
   */
  public ClassSize getExecutiveSize() {
    return this.executiveSize;
  }

  /**
   * @return ClassSize
   */
  public ClassSize getTouristicSize() {
    return this.touristicSize;
  }

}
