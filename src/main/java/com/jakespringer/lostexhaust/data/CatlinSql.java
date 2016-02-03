package com.jakespringer.lostexhaust.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CatlinSql {
	private final String username = "lostexhaust";
	private final String password = "k7Dqu2zYV91aJhwlAB8fQ";
	private final String database = "lostexhaust";
	private final String hostname = "localhost";
	private final int port = 3306;
	private final String lookupTable = "lostexhaust.lookup";
	private final String householdsTable = "lostexhaust.households";
	
	private Connection connection;
	
	List<String> getHouseholdsFromUser(String userId) throws SQLException {
		// SELECT householdid FROM lostexhaust.lookup
		// WHERE userid = '?'
		
		tryReconnect();
		
		String statement = "SELECT householdid FROM " + lookupTable +
						   "WHERE userid = \'?\'";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setString(1, userId);
		ResultSet householdsResult = ps.executeQuery();

		List<String> householdIds = new ArrayList<>();
		while (householdsResult.next()) {
			String householdId = householdsResult.getString("householdid");
			householdIds.add(householdId);
		}
		
		return Collections.unmodifiableList(householdIds);
	}
	
	List<String> getUsersFromHousehold(String householdId) throws SQLException {
		// SELECT householdid FROM lostexhaust.lookup
		// WHERE userid = '?'
		
		tryReconnect();
		
		String statement = "SELECT userid FROM " + lookupTable +
						   "WHERE userid = \'?\'";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setString(1, householdId);
		ResultSet usersResult = ps.executeQuery();

		List<String> userIds = new ArrayList<>();
		while (usersResult.next()) {
			String userId = usersResult.getString("userid");
			userIds.add(userId);
		}
		
		return Collections.unmodifiableList(userIds);		
	}
	
	private void tryReconnect() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = connect(hostname, username, password);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Connection connect(String host, String user, String pass) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		return DriverManager.getConnection(host, user, pass);
	}
}
