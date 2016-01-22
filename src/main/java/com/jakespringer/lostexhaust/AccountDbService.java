/*
 * Asphalt - A simple carpool matching service
 * Copyright (c) 2015 Jake Springer
 *
 * This file is part of Asphalt.
 *
 * Asphalt is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Asphalt is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Asphalt.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jakespringer.lostexhaust;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.jakespringer.lostexhaust.Contact.Entry;

public class AccountDbService {
    static final String DELIMETER = "__;__";
    
    static AccountDbService accountService;
    Connection connection;
    
    public static AccountDbService getInstance() {
        if (accountService == null) {
            accountService = new AccountDbService();
        }
        
        return accountService;
    }
    
    @SuppressWarnings("resource")
    public List<Account> getAllAccounts() {
        try {
            if (connection == null || connection.isClosed()) {
                connectToSqlServer(LeService.getConfiguration().sqlHostname,
                        LeService.getConfiguration().sqlUsername,
                        LeService.getConfiguration().sqlPassword);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        try {
            List<Account> accountList = new ArrayList<Account>();
            
            String statement = "SELECT * FROM asphalt.accounts";
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                String id = result.getString("id");
                String username = result.getString("username");
                String name = result.getString("name");
                String contact = result.getString("contact");
                String notes = result.getString("notes");
                String address = result.getString("address");
                String placeId = result.getString("placeid");
                String coordinates = result.getString("coordinates");
                
                Account account = new Account(id);
                try {
                    account.setUsername(username);
                    account.setName(name);
                    account.setContact(parseContact(contact));
                    account.setNotes(notes);
                    account.setAddress(parseAddress(address));
                    account.setPlaceIdPassive(placeId);
                    account.setCoordinates(parseCoordinates(coordinates));
                    accountList.add(account);
                } catch (Exception e) {
                    result.close();
                    ps.close();
                    throw new RuntimeException(e);
                }
            }
            
            ps.close();
            result.close();
            
            return accountList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        
    public void pushAccount(Account account) throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connectToSqlServer(LeService.getConfiguration().sqlHostname,
                        LeService.getConfiguration().sqlUsername,
                        LeService.getConfiguration().sqlPassword);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        String statement = "INSERT INTO asphalt.accounts (id, username, name, contact, notes, address, placeid, coordinates) VALUES( ? , ? , ? , ? , ? , ? , ? , ? )";
        @SuppressWarnings("resource")
        PreparedStatement ps = connection.prepareStatement(statement);
        ps.setString(1, account.getId());
        ps.setString(2, account.getUsername());
        ps.setString(3, account.getName());
        ps.setString(4, stringContact(account.getContact()));
        ps.setString(5, account.getNotes());
        ps.setString(6, stringAddress(account.getAddress()));
        ps.setString(7, account.getPlaceId());
        ps.setString(8, stringCoordinates(account.getCoordinates()));
        ps.execute();
        ps.close();
    }
    
    Contact parseContact(String contact) throws ParsingException, InvalidEmailException, InvalidNumberException {
        String[] bits = contact.split(DELIMETER);
        if (bits.length % 3 == 0) {
            Contact c = new Contact();
            for (int i=0; i*3<bits.length; ++i) {
                int j = i*3;
                if (bits[j].equals("e")) {
                    c.addEmail(new Entry(bits[j+1], bits[j+2]));
                } else if (bits[j].equals("n")) {
                    c.addNumber(new Entry(bits[j+1], bits[j+2]));
                }
            }
            
            return c;
        }
        
        if (bits.length == 1 && bits[0].equals("")) return new Contact();
        
        throw new ParsingException("Contact data \"" + contact + "\" could not be parsed");
    }
    
    Address parseAddress(String address) throws ParsingException, InvalidAddressElementException {
        String[] bits = address.split(DELIMETER);
        if (bits.length == 6) {
            Address a = new Address(bits[0], bits[1], bits[2], bits[3], bits[4], bits[5]);
            return a;
        }
        
        throw new ParsingException("Address data \"" + address + "\" could not be parsed");
    }
    
    Coordinates parseCoordinates(String coordinates) throws ParsingException {
        String[] bits = coordinates.split(DELIMETER);
        if (bits.length == 2) {
            try {
                Coordinates c = new Coordinates(
                        Double.parseDouble(bits[0]),
                        Double.parseDouble(bits[1]));
                return c;
            } catch (NullPointerException | NumberFormatException e) {
            }
        }
        
        throw new ParsingException("Coordinate data \"" + coordinates + "\" could not be parsed");
    }
    
    static String stringContact(Contact contact) {
        if (contact == null) return "";
        StringBuilder b = new StringBuilder();
        for (Entry e : contact.getEmails()) {
            b.append("e" + DELIMETER + e.type + DELIMETER + e.data + DELIMETER);
        }
        
        for (Entry e : contact.getNumbers()) {
            b.append("n" + DELIMETER + e.type + DELIMETER + e.data + DELIMETER);
        }
        
        if (b.length() > 0) b.replace(b.length()-DELIMETER.length(), b.length(), "");
        return b.toString();
    }
    
    String stringAddress(Address a) {
        if (a == null) return "null";
        return a.getStreetNumber()
                + DELIMETER + a.getRoute()
                + DELIMETER + a.getLocality()
                + DELIMETER + a.getAdminArea()
                + DELIMETER + a.getPostalCode()
                + DELIMETER + a.getCountry();
    }
    
    String stringCoordinates(Coordinates coordinates) {
        if (coordinates == null) return "null";
        return coordinates.latitude + DELIMETER + coordinates.longitude;
    }
    
    void connectToSqlServer(String host, String user, String pass) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        connection = DriverManager.getConnection(host, user, pass);
    }
}
