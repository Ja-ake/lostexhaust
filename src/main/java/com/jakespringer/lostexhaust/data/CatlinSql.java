package com.jakespringer.lostexhaust.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.jakespringer.lostexhaust.LeService;
import com.jakespringer.lostexhaust.util.Quick;
import com.jakespringer.lostexhaust.util.Timer;

public class CatlinSql {
    public static final CatlinSql inst = new CatlinSql();

    private final String username = LeService.getConfig().getString("sql_username");
    private final String password = LeService.getConfig().getString("sql_password");
    private final String database = LeService.getConfig().getString("sql_database");
    private final String hostname = LeService.getConfig().getString("sql_hostname");
    private final int port = 3306;
    private final String lookupTable = "lostexhaust.users";
    private final String householdsTable = "lostexhaust.households";

    private Connection connection;

    static {
        try {
            inst.connect(inst.hostname, inst.username, inst.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<String> getHouseholdsFromUser(String userId) throws SQLException {
        // SELECT householdid FROM lostexhaust.lookup
        // WHERE userid = '?'

        tryReconnect();

        String statement = "SELECT household_id FROM " + lookupTable + " WHERE person_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setString(1, userId);
            try (ResultSet householdsResult = ps.executeQuery()) {
                List<String> householdIds = new ArrayList<>();
                while (householdsResult.next()) {
                    String householdId = householdsResult.getString("household_id");
                    householdIds.add(householdId);
                }

                return Collections.unmodifiableList(householdIds);
            }
        }
    }

    public synchronized List<String> getUsersFromHousehold(String householdId) throws SQLException {
        // SELECT householdid FROM lostexhaust.lookup
        // WHERE userid = '?'
        // Timer t = Timer.start("getUsersFromHousehold()");
        tryReconnect();

        String statement = "SELECT person_id FROM " + lookupTable + " WHERE household_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setString(1, householdId);
            try (ResultSet usersResult = ps.executeQuery()) {
                List<String> userIds = new ArrayList<>();
                while (usersResult.next()) {
                    String userId = usersResult.getString("person_id");
                    userIds.add(userId);
                }
                // t.stop();
                return Collections.unmodifiableList(userIds);
            }
        }
    }

    public synchronized CatlinHousehold getHouseholdFromId(String householdId) throws SQLException {
        tryReconnect();

        String statement = "SELECT place_id,latitude,longitude,addressblock,city,state,postcode FROM " + householdsTable + " WHERE household_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setString(1, householdId);
            try (ResultSet householdResult = ps.executeQuery()) {
                if (householdResult.next()) {
                    String placeId = householdResult.getString("place_id");
                    double latitude = householdResult.getFloat("latitude");
                    double longitude = householdResult.getFloat("longitude");
                    String addressblock = householdResult.getString("addressblock");
                    String city = householdResult.getString("city");
                    String state = householdResult.getString("state");
                    String postcode = householdResult.getString("postcode");
                    String fullAddress = addressblock + ", " + city + ", " + state + " " + postcode;
                    if (Quick.notNull(placeId, latitude, longitude, fullAddress)) {
                        return new CatlinHousehold(householdId, placeId, latitude, longitude, fullAddress);
                    }
                }
            }
        }

        return null;
    }

    public synchronized List<CatlinHousehold> getAllHouseholds() throws SQLException {
        // Timer t = Timer.start("getAllHouseholds()");
        tryReconnect();

        String statement = "SELECT household_id,place_id,latitude,longitude,addressblock,city,state,postcode FROM " + householdsTable;
        try (PreparedStatement ps = connection.prepareStatement(statement)) {
            try (ResultSet householdResult = ps.executeQuery()) {
                List<CatlinHousehold> households = new ArrayList<>();
                while (householdResult.next()) {
                    String id = householdResult.getString("household_id");
                    String placeId = householdResult.getString("place_id");
                    double latitude = householdResult.getFloat("latitude");
                    double longitude = householdResult.getFloat("longitude");
                    String addressblock = householdResult.getString("addressblock");
                    String city = householdResult.getString("city");
                    String state = householdResult.getString("state");
                    String postcode = householdResult.getString("postcode");
                    String fullAddress = addressblock + ", " + city + ", " + state + " " + postcode;
                    if (Quick.notNull(placeId, latitude, longitude, fullAddress)) {
                        households.add(new CatlinHousehold(id, placeId, latitude, longitude, fullAddress));
                    }
                }
                // t.stop();
                return Collections.unmodifiableList(households);
            }
        }
    }

    public synchronized boolean verifyUser(String userId) throws SQLException {
        tryReconnect();

        String statement = "SELECT id FROM " + lookupTable + " WHERE person_id= ? ;";
        try (PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setString(1, userId);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public synchronized boolean verifyHousehold(String userId) throws SQLException {
        tryReconnect();

        String statement = "SELECT id FROM " + lookupTable + " WHERE person_id= ? ;";
        try (PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setString(1, userId);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
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
