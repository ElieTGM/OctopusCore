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
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

/**
 * This class is the SQLDatabase class. It hosts all the required information to connect to our sql database.
 *
 * It has only a couple of private Strings (user, database, password, port, hostname) to be able to connect.
 * This class is to only be used in the OctoCore#onEnable() post-startup.
 */

public class SQLDatabase extends Database {

    /**
     * The following are 5 private strings ranging from user to the hostname. These are essentials to be able to connect
     * to our database.
     */
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;

    /**
     * This method is the main method of the whole class file. It is used only on the onEnable method, to establish an
     * open connection to the database. The params are as follows:
     *
     * @param plugin The instance of a JavaPlugin, in this case (this) referring to OctoCore.
     * @param hostname The hostname for our MySQL database.
     * @param port The port for our MySQL database.
     * @param database The database name for our MySQL database.
     * @param username The username for our MySQL database.
     * @param password The password for our MySQL database.
     */
	public SQLDatabase(Plugin plugin, String hostname, String port, String database, String username, String password) {
		super(plugin);
		
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
	}

    /**
     * The #openConnection() method is used to initialize the connection. To be used only on the onEnable() method in
     * OctoCore. Additionally, it has an autoReconnect parameter. No need to panic about losing the connection after 7
     * hours.
     *
     * @return a connection instance.
     * @throws SQLException if the database information were incorrect.
     * @throws ClassNotFoundException if the class (com.mysql.jbdc.Driver) was not found.
     */
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		
		if (checkConnection()) {
			return connection;
		}
		
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
		return connection;
	}

}
