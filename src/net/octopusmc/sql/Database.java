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

import org.bukkit.plugin.Plugin;

/**
 *  This is the Database class. It's an abstract class since it has all the methods which will be used in the
 *  SQLConnection class. If needed, you can use the following methods only in important and non-api added cases. If not,
 *  just use the API found in the SQLConnection class file.
 */
public abstract class Database {

    /**
     * A protected instance for the java connection. We don't want loopholes and RAM issues, so we're gonna use a
     * protected instance.
     */
    protected Connection connection;

    /**
     * A protected instance for the java plugin. We don't want loopholes and RAM issues, so we're gonna use a
     * protected instance.
     */
	protected Plugin plugin;

    /**
     * The main method of our class. Use the protected Database to have an even more extensive api and liberties than
     * the SQLConnection class. If needed, you can use the following methods only in important and non-api added cases. If not,
     *  just use the API found in the SQLConnection class file.
     *
     * @param plugin a plugin instance usually found at OctoCore#getInstance() or (this) if in the OctoCore class.
     */
	protected Database(Plugin plugin) {
		this.plugin = plugin;
		this.connection = null;
	}

    /**
     * The #openConnection() abstract method is used to initialize the connection. To be used only on the onEnable() method in
     * OctoCore. It's the abstract to initialize a connection to the SQL database.
     *
     * @return a connection instance.
     * @throws SQLException if the database information were incorrect.
     * @throws ClassNotFoundException if the class (com.mysql.jbdc.Driver) was not found.
     */
	public abstract Connection openConnection() throws SQLException, ClassNotFoundException;

    /**
     * This is a boolean to check if the connection is open between the server and the SQL database. To be used for
     * debugging and as resolvent if the SQL database crashed.
     *
     * @return a boolean to check the connection
     * @throws SQLException if database informations are wrong
     */
	public boolean checkConnection() throws SQLException {
		return connection != null && !connection.isClosed();
	}

    /**
     * Using this method will give you the complete liberties with SQL databases. Use this to your own will, doing this
     * will return the SQL connection.
     *
     * @return the Connection
     */
	public Connection getConnection() {
		return connection;
	}

    /**
     * Using this method will give out a boolean  whether the connection was really closed or not.
     *
     * @return a boolean to check whether the connection was truly closed or not.
     * @throws SQLException if SQL database informations are wrong.
     */
	public boolean closeConnection() throws SQLException {
		if (connection == null) {
			return false;
		}
		
		connection.close();
		return true;
	}

    /**
     * This method is a ResultSet which does changes to the SQL database given the query written.
     *
     * @param query String of MySQL.
     * @return ResultSet
     * @throws SQLException if SQL informations are wrong
     * @throws ClassNotFoundException if the SQL java class was not found.
     */
	public ResultSet querySQL(String query) throws SQLException, ClassNotFoundException {
		if (!checkConnection()) {
			openConnection();
		}

		Statement statement = connection.createStatement();

		ResultSet result = statement.executeQuery(query);

		return result;
	}

    /**
     * Updates an existing query in the SQL database
     *
     * @param query String of MySQL.
     * @return integer
     * @throws SQLException if SQL informations are wrong
     * @throws ClassNotFoundException if the SQL java class was not found.
     */
	public int updateSQL(String query) throws SQLException, ClassNotFoundException {
		if (!checkConnection()) {
			openConnection();
		}

		Statement statement = connection.createStatement();

		int result = statement.executeUpdate(query);

		return result;
	}


}
