package com.jakespringer.lostexhaust.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;
import com.jakespringer.lostexhaust.util.Tup;

public class LeDatabase {
	private Connection connection;
	private String table;
	
	public LeDatabase(String _ip, String _port, String _user, String _pass, String _table) {
		table = _table;
	}
	
	public JSONObject getData(int id) throws SQLException {
		return new JSONObject(getField("data", id));
	}
	
	public void putData(int id, JSONObject obj) throws SQLException {
		if (setField("data", id, obj.toString()) == 0) {
			// TODO: handle invalid id
		}
	}
	
	private List<Tup<Integer, String>> getFieldList(String field) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(
				"SELECT id, "+ field +" FROM " + table);
		ResultSet result = ps.executeQuery();
		
		List<Tup<Integer, String>> resultList = new ArrayList<>();
		while (result.next()) {
			resultList.add(new Tup<Integer, String>(result.getInt(1), result.getString(1)));
		}
		result.close();
		ps.close();
		
		return Collections.unmodifiableList(resultList);
	}
	
	private String getField(String field, int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(
				"SELECT "+ field +" FROM " + table + " WHERE id=" + id);
		ResultSet result = ps.executeQuery();
		result.next();
		String data = result.getString(1);
		result.close();
		ps.close();
		return data;
	}
	
	private int setField(String field, int id, String value) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(
				"UPDATE " + table + " data=? WHERE id=" + id);
		ps.setString(1, value);
		int numUpdated = ps.executeUpdate();
		ps.close();
		return numUpdated;
	}
}
