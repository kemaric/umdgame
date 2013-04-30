package vinnie.vendemia.namespace;

public class HighScores {
	
	
	private String user;
	private String score; 
	private String numPlays;
	
	public HighScores(String usr, String score, String numPlays) {
		this.user = usr;
		this.score = score;
		this.numPlays = numPlays;
	}
	
	public String getScore() {
		return this.score;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getNumPlays() {
		return this.numPlays;
	}

}
