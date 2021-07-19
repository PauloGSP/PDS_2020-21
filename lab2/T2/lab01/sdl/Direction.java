package sdl;

/*                    X (UP)
                    -1
 *                    |
 *                    |
 *                    |
 *                    |
 * -1                 |                        1
 * -------------------+------------------------->
 * (LEFT)             |                         Y(RIGHT)
 *                    |
 *                    |
 *                    |
 *                    |
 *                    v (DOWN)
 *                    1
 */
public enum Direction {
  UP       ( - 1, 0, "Up" ),
  DOWN     ( 1, 0, "Down" ),
  LEFT     ( 0, - 1, "Left" ),
  RIGHT    ( 0, 1, "Right" ),
  UPLEFT   ( - 1, - 1, "UpLeft" ),
  UPRIGHT  ( - 1, 1, "UpRight" ),
  DOWNLEFT ( 1, - 1, "DownLeft" ),
  DOWNRIGHT( 1, 1, "DownRight" ),
  ;
  private final int x;
  private final int y;
  private final String dir;

  Direction( int x, int y, String dir ) {
    this.x = x;
    this.y = y;
    this.dir = dir;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override public String toString() {
    return this.dir;
  }
}
