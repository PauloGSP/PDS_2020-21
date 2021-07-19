package lab03;

public class Jogo implements JGaloInterface {
	
	private char bt[];
	
	public Jogo() {
		char bt[] = new char[9];
		this.bt=bt;
	}
	
	public char getActualPlayer() {
		char charr;
		int nJogadas=0;
		for (int i=0; i<9; i++) {
			if (this.bt[i]=='X' || this.bt[i]=='O') {
				nJogadas++;
			}
		}
		if (nJogadas%2==0) {
			charr = 'X';
		}
		else {
			charr = 'O';
		}
		return charr;
	}
	
	public boolean setJogada(int lin, int col, String charr) {
		this.bt[lin+col+((lin-2)*2)]=charr.charAt(0);
		return true;
	}
	
	public boolean isFinished() {
		int Jogadas=0;
		for (int i=0; i<9; i++) {
			if (this.bt[i]=='X' || this.bt[i]=='O') {
				Jogadas++;
			}
			if (i%3==0 && ( bt[i]=='X' || bt[i]=='O')) {
				if (this.bt[i]==bt[i+1] && this.bt[i]==bt[i+2]) {
					return true;
				}
				if (i==0 && bt[0]==bt[4] && bt[0]==bt[8]) {
					return true;
				}
				if (i==6 && bt[6]==bt[4] && bt[6]==bt[2]) {
					return true;
				}
			}
			if (i/3<1 && ( bt[i]=='X' || bt[i]=='O')) {
				if (this.bt[i]==bt[i+3] && this.bt[i]==bt[i+6]) {
					return true;
				}
			}
		}
		if (Jogadas==9) {
			return true;
		}
		return false;
	}
	
	public char checkResult() {
		char charr = ' ';
		for (int i=0; i<9; i++) {
			if (i%3==0) {
				if (this.bt[i]==bt[i+1] && this.bt[i]==bt[i+2]) {
					charr=this.bt[i];
				}
				if (i==0 && bt[0]==bt[4] && bt[0]==bt[8]) {
					charr=this.bt[i];
				}
				if (i==6 && bt[6]==bt[4] && bt[6]==bt[2]) {
					charr=this.bt[i];
				}
			}
			if (i/3<1) {
				if (this.bt[i]==bt[i+3] && this.bt[i]==bt[i+6]) {
					charr=this.bt[i];
				}
			}
		}
		return charr;
	}
}
