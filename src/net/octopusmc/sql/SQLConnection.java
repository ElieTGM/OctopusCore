/*
 * Copyright (c) 2016, OctopusMC and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of OctopusMC or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package net.octopusmc.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.octopusmc.OctoCore;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *  This class is the main SQLConnection class. Use this class by doing OctoCore#getMainSQLManager() to get all the api here.
 *  It stores all the database stuff here. We're gonna call it main SQLConnection as there might be other SQL connections
 *  such as minigames using this api.
 */
public class SQLConnection {

    /**
     * The SQLDatabase class will be invoked as MySQL (public method).
     */
	public SQLDatabase MySQL;

    /**
     * The Connection c for the MySQL java connection will be invoked as c (public method).
     */
	public static Connection c;

    /**
     *
     * @param plugin The instance of a JavaPlugin, in this case (this) referring to OctoCore.
     * @param host The hostname for our MySQL database.
     * @param port The port for our MySQL database.
     * @param database The database name for our MySQL database.
     * @param username The username for our MySQL database.
     * @param password The password for our MySQL database.
     */
	public SQLConnection(Plugin plugin, String host, String port, String database, String username, String password) {
		MySQL = new SQLDatabase(plugin, host, port, database, username, password);
	}

    /**
     * Open the connection or close the connection if already opened. Creates the table if it doesn't exist in a delayed
     * runnable.
     */
	public void openConnection() {
		if(isConnected()) {
			closeConnection();
		}
		
		try {
			c = MySQL.openConnection();
			
			//(servername, status, players)
			
			executeUpdate("CREATE TABLE IF NOT EXISTS `Account` (playername VARCHAR(255) NOT NULL, rank VARCHAR(255) NOT NULL, fishies VARCHAR(255) NOT NULL, firstjoined VARCHAR(255) NOT NULL, lastseen VARCHAR(255) NOT NULL, timeonline VARCHAR(255) NOT NULL, ip VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, language VARCHAR(255) NOT NULL);");
			executeUpdate("CREATE TABLE IF NOT EXISTS `Servers` (servername VARCHAR(255) NOT NULL, status VARCHAR(255) NOT NULL, players VARCHAR(255) NOT NULL);");
			
			new BukkitRunnable() {
				public void run() {
					openConnection();
				}
			}.runTaskLater(OctoCore.getInstance(), 100L);
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace(); }

	}

    /**
     * Check if the plugin is connected to the MySQL database.
     * @return boolean
     */
	public boolean isConnected() {
		try {
			if(c.isClosed()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * Close the connection, you can try debugging using this method or mysql connection errors occur.
     */
	public void closeConnection() {
		if(isConnected()) {
			try {
				c.close();
			} catch (SQLException e) {}
		}
	}

    /**
     * Execute a Query from the given database in SQLConnection
     * @param statement MySQL string statement
     * @param next boolean.
     * @return a resultset.
     */
	public ResultSet executeQuery(String statement, boolean next) {
		if(isConnected()) {
			try {
				Statement s = c.createStatement();
				ResultSet res = s.executeQuery(statement);
				if(next) {res.next(); }
				return res;
			} catch (SQLException e) {
				return null;
			}
		} else {
			return null;
		}
	}

    /**
     * Execute an update if there is already a database and is filled.
     * @param statement MySQL string statement
     * @return a resultset.
     */
	public boolean executeUpdate(String statement) {
		if(isConnected()) {
			try {
				Statement s = c.createStatement();
				s.executeUpdate(statement);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

}
