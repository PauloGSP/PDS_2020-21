
package lab03;
public interface JGaloInterface {
	public abstract char getActualPlayer();
	public abstract boolean setJogada(int lin, int col, String charr);
	public abstract boolean isFinished();
	public abstract char checkResult();
}