package classes;

public class Solution {
  private Position fisrtLetterPos;
  private Direction direction;

  public Solution(Position fisrtLetterPos, Direction dir) {
    this.fisrtLetterPos = fisrtLetterPos;
    this.direction = dir;
  }

  public Position getPosition() {
    return fisrtLetterPos;
  }

  public Direction getDirection() {
    return direction;
  }
}
