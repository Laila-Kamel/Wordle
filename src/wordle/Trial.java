package wordle;

public class Trial {
	int trials;
	boolean win;

	public Trial(int trials, boolean win) {
		this.trials=trials;
		this.win=win;
	}
	public void setTrials(int newTrial) {
		this.trials=newTrial;
	}
	public void setWin(boolean newWin) {
		this.win=newWin;
	}

}
