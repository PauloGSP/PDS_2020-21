package classes;

public class Position {
  private int x, y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public Position nextPosition(Direction direction) {
    int x = this.getX(), y = this.getY();
    switch (direction) {
    case Up:
      return new Position(x, y - 1);
    case Down:
      return new Position(x, y + 1);
    case Left:
      return new Position(x - 1, y);
    case Right:
      return new Position(x + 1, y);
    case UpLeft:
      return new Position(x - 1, y - 1);
    case UpRight:
      return new Position(x + 1, y - 1);
    case DownLeft:
      return new Position(x - 1, y + 1);
    case DownRight:
      return new Position(x + 1, y + 1);
    default:
      return null;
    }
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return String.format("%d,%d", this.x, this.y);
  }
}
