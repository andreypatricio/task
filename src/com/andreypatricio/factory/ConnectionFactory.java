package com.andreypatricio.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/db?useTimezone=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "";
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

	public Connection createConnection() {

		Connection connection = null;

		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Database connection problem: " + e);
		}

		return connection;

	}

	public void closeConnection(Connection connection, PreparedStatement pStatement, ResultSet resultSet) {

		try {

			if (connection != null) {
				connection.close();
			}

			if (pStatement != null) {
				pStatement.close();
			}

			if (resultSet != null) {
				resultSet.close();
			}

		} catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Database connection problem: " + e);
		}
	}

}
