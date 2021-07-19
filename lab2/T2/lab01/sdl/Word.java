package sdl;

import java.util.Locale;
import java.util.Objects;

public class Word implements Comparable<Word> {

  private final String word;
  private int count = 0;
  private Direction direction;
  private int startX;
  private int startY;
  private int length;

  public Word( String w, String dir, int x, int y){
    this.word = w.toUpperCase( Locale.ROOT );
    this.length = w.length();
    this.setDirection( dir );
    this.startX = x;
    this.startY = y;
    this.count = 1;
  }

  public Word( String w, Direction dir, int x, int y){
    this.word = w.toUpperCase( Locale.ROOT );
    this.length = w.length();
    this.direction = dir;
    this.startX = x;
    this.startY = y;
    this.count = 1;
  }
  public int getLength() {
    return length;
  }

  public boolean contains( String w){
    return this.word.contains( w );
  }
  public int getCount() {
    return count;
  }

  public char getCharAt( int i ) {
    return this.getWord().charAt( i );
  }

  @Override public boolean equals( Object o ) {
    if ( this == o ) return true;
    if ( o == null || getClass() != o.getClass() ) return false;
    Word word1 = (Word) o;
    return word.equals( word1.word );
  }

  @Override public int hashCode() {
    return Objects.hash( word );
  }

  public void setDirection( String direction ) {
    switch (direction){
      case "Up":
        this.direction = Direction.UP;
        break;
      case "Down":
        this.direction = Direction.DOWN;
        break;
      case "Left":
        this.direction = Direction.LEFT;
        break;
      case "Right":
        this.direction = Direction.RIGHT;
        break;
      case "UpLeft":
        this.direction = Direction.UPLEFT;
        break;
      case "UpRight":
        this.direction = Direction.UPRIGHT;
        break;
      case "DownRight":
        this.direction = Direction.DOWNRIGHT;
        break;
      case "DownLeft":
        this.direction = Direction.DOWNLEFT;
        break;
    }
  }

  public String getWord() {
    return word;
  }
  public void incrementCount(){
    this.count++;
  }

  public void setStartX( int startX ) {
    this.startX = startX;
  }

  public void setStartY( int startY ) {
    this.startY = startY;
  }

  public int getStartX() {
    return startX;
  }

  public int getStartY() {
    return startY;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override public String toString() {
    return String.format( "%-12s\t%2d\t%2d,%2d\t%-16s\n",this.word,this.word.length(),this.startX,this.startY,
      this.direction );
  }

  @Override public int compareTo( Word o ) {
    return this.word.compareTo( o.getWord() );
  }
}
