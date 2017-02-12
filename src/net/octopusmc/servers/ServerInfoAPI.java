package net.octopusmc.servers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.octopusmc.OctoCore;
import net.octopusmc.servers.status.GameStatus;
import net.octopusmc.sql.SQLConnection;

public class ServerInfoAPI implements IServerInfoAPI {

    @Override
    public boolean existsInDatabase(String server) {
        ResultSet result = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Servers` WHERE servername='" + server + "'", false);
        try {
            if(result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {return false; }
    }
    
	@Override
	public int getServerPlayerCount(String server) {
		int count = 0;
		
		try {
			ResultSet res = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Servers` WHERE servername='" + server + "'", false);
			
			if(res.next()) {
				count = res.getInt("players");
			}
		} catch (Exception e) {}
		
		return count;
	}

	@Override
	public GameStatus getServerStatus(String server) {
		GameStatus status = GameStatus.LOBBY;
		
		try {
			ResultSet res = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Servers` WHERE servername='" + server + "'", false);
			
			if(res.next()) {
				status = GameStatus.valueOf(res.getString("status").toUpperCase());
			}
		} catch (Exception e) {}
		
		return status;
	}
	
	@Override
	public void createServer(String server, GameStatus status, Integer playerCount) {
		
	      try {
	            PreparedStatement pS = SQLConnection.c.prepareStatement("INSERT INTO `Servers` (servername, status, players) VALUES ('" + server + "', '" + status.getGameStatusString().toLowerCase() + "', '" + playerCount +"')");
	            pS.execute();
	        } catch (SQLException e) {
	            System.out.print(e);
	        }
		
	}

	@Override
	public void setServerStatus(String server, GameStatus status, int playerCount) {
		try {
			ResultSet res = OctoCore.getMainSQLConnection().executeQuery("UPDATE `Servers` SET status='" + status.getGameStatusString() + "' AND players=" + playerCount + " WHERE servername='" + server + "'", false);
			
			res.next();
		} catch (Exception e) {}
	}

	@Override
	public ArrayList<String> getOnlineServerNames() {
		ArrayList<String> servers = new ArrayList<String>();
		
		try {
			ResultSet res = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Servers`", false);
			
			while(res.next()) {
				servers.add(res.getString("servername"));
			}
		} catch (Exception e) {}
		
		return servers;
	}
	

}
