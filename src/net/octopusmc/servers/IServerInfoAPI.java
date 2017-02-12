package net.octopusmc.servers;

import java.util.ArrayList;
import net.octopusmc.servers.status.GameStatus;

public interface IServerInfoAPI {

	/**
	 * Check if the server exists in the database.
	 */
    public boolean existsInDatabase(String server);
    
	/**
	 * Returns the player count of the specified server
	 * @param server
	 * @return
	 */
	public int getServerPlayerCount(String server);
	
	/**
	 * Returns the Status of the specified server
	 * @param server
	 * @return
	 */
	public GameStatus getServerStatus(String server);
	
	/**
	 * Creates a server with a name.
	 */
	public void createServer(String server, GameStatus status, Integer playerCount);
	
	/**
	 * Sets the GameStatus for the specified server
	 * @param server
	 * @param status
	 * @param playerCount
	 */
	public void setServerStatus(String server, GameStatus status, int playerCount);
	
	/**
	 * Returns an ArrayList of Online Server Names
	 * @return
	 */
	public ArrayList<String> getOnlineServerNames();
}
