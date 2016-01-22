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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Contact {
    public static class Entry {
        public String type;
        public String data;
        
        public Entry(String _type, String _data) {
            type = _type;
            data = _data;
        }
    }
    
    private List<Entry> emails = new ArrayList<>();
    private List<Entry> numbers = new ArrayList<>();
    
    public Contact() {
    }
    
    public void addEmail(Entry entry) throws InvalidEmailException {
        // trim leading & trailing whitespace
        entry.data = entry.data.replaceAll("\\s", "");
        if (verifyEmail(entry.data) && verifyEmailType(entry.type)) {
            emails.add(entry);
        } else {
            throw new InvalidEmailException(entry.data);
        }
    }
    
    public void addNumber(Entry entry) throws InvalidNumberException {
        // remove all non-alphanumeric characters
        entry.data = entry.data.replaceAll("[!@#$%^&*()_+=\\[{\\]};:<>|./?,\\\'\"\"\\s-]", "");
        if (verifyNumber(entry.data) && verifyNumberType(entry.type)
                /*&& numbers.stream().noneMatch(e -> e.data.equals(entry.data))*/) {
            numbers.add(entry);
        } else {
            throw new InvalidNumberException(entry.data);
        }
    }
    
    public Collection<Entry> getEmails() {
        return Collections.unmodifiableCollection(emails);
    }
    
    public Collection<Entry> getNumbers() {
        return Collections.unmodifiableCollection(numbers);
    }
    
    public static boolean verifyEmail(String email) {
        // trust me, it works
        return Pattern.matches("\\A[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\z", email);
    }
    
    public static boolean verifyEmailType(String emailType) {
        return Pattern.matches("(?:work|home)", emailType);
    }
    
    public static boolean verifyNumber(String number) {
        // All phone numbers are at max 15 numbers, we'll be safe with 19.
        // The regex matches any string of numbers w/o letters;
        // not great but a good start -- deal with this client side?
        return ((number.length() == 7) || (number.length() >= 10 && number.length() < 20)) && Pattern.matches("^([0-9]*)$", number);
    }
    
    public static boolean verifyNumberType(String numberType) {
        return Pattern.matches("(?:work|home|cell)", numberType);
    }
    
    public static String formatNumber(String number, String countryCode) throws InvalidCountryCodeException, InvalidNumberException {
        number = number.replaceAll("[!@#$%^&*()_+=\\[{\\]};:<>|./?,\\\'\"\"-]", "");
        if (verifyNumber(number)) {
            countryCode = countryCode.toLowerCase();
            switch (countryCode) {
            case "us":
                if (number.length() == 7) return number.substring(0, 3) + "-" + number.substring(3);
                else {
                    String local = number.substring(number.length()-10);
                    String country = number.substring(0, number.length()-10);
                    return (country.length() > 0 ? ("+"+country+" ") : "") + "(" + local.substring(0, 3) + ") " + local.substring(3, 6) + "-" + local.substring(6);
                }
            default:
                throw new InvalidCountryCodeException("Invalid country: " + countryCode);
            }
        } else {
            throw new InvalidNumberException();
        }
    }
}
