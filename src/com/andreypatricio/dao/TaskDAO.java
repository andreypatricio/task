package com.andreypatricio.dao;

import com.andreypatricio.factory.ConnectionFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDAO extends ConnectionFactory {

	private static TaskDAO instance;
    private static final Logger LOGGER = Logger.getLogger(TaskDAO.class.getName());

	public static TaskDAO getInstance() {

		if (instance == null) {
			instance = new TaskDAO();
		}

		return instance;
	}

	public String getTasks() {

		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		String result = "";

		try {
			connection = createConnection();
			String query = "SELECT * FROM task";
			pStatement = connection.prepareStatement(query);
			resultSet = pStatement.executeQuery();
			result = convertResultSetToJson(resultSet);
		} catch (SQLException | JSONException e) {
            LOGGER.log(Level.SEVERE,"Error! " + e);
		} finally {
			closeConnection(connection, pStatement, resultSet);
		}

		return result;
	}

	public void newTask(String json) {

		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = createConnection();

			String query = "INSERT INTO task (title, description, createdin, deadline, done) VALUES ";
			query += "(?, ?, ?, ?, ?)";

			JSONObject jsonObj = new JSONObject(json);

			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, jsonObj.getString("title"));
			pStatement.setString(2, jsonObj.getString("description"));
			pStatement.setString(3, getCurrentTime());
            pStatement.setString(4, jsonObj.getString("deadline"));
            pStatement.setInt(5, 0);

			pStatement.executeUpdate();

		} catch (SQLException | JSONException e) {
            LOGGER.log(Level.SEVERE,"Error! " + e);
		} finally {
			closeConnection(connection, pStatement, null);
		}

	}
	
	public void deleteTask(String id) {
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = createConnection();

			String query = "DELETE FROM task WHERE id = ?";

			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, Integer.valueOf(id));
			pStatement.executeUpdate();

		} catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Error! " + e);
		} finally {
			closeConnection(connection, pStatement, null);
		}
	}

	public void doneTask(String id) {
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = createConnection();

			String query = "UPDATE task SET done = 1 WHERE id = ?";

			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, Integer.valueOf(id));
			pStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Error! " + e);
		} finally {
			closeConnection(connection, pStatement, null);
		}
	}


	
	private static String convertResultSetToJson(ResultSet resultSet) throws SQLException, JSONException {
		if (resultSet == null) {
			return null;
		}

		JSONArray json = new JSONArray();
		ResultSetMetaData metadata = resultSet.getMetaData();
		int numColumns = metadata.getColumnCount();

		while (resultSet.next()) {
			JSONObject obj = new JSONObject();
			for (int i = 1; i <= numColumns; ++i) {
				String column_name = metadata.getColumnName(i);
				obj.put(column_name, resultSet.getObject(column_name));
			}
			json.put(obj);
		}
		return json.toString();
	}

	public String getCurrentTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }



}
