package net.octopusmc.servers.status;

public enum GameStatus {

	LOBBY("LOBBY"),
	INGAME("INGAME"),
	RESTARTING("RESTARTING"),
	OFFLINE("OFFLINE");
	
	private String gS;
	
	private GameStatus(String gS) {
		this.gS = gS;
	}
	
	public String getGameStatusString() {
		return gS;
	}
	
	public GameStatus getGameStatus() {
		return this;
	}
	
}
