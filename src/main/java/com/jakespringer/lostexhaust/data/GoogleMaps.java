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
package com.jakespringer.lostexhaust.data;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.jakespringer.lostexhaust.LeService;
import com.jakespringer.lostexhaust.error.InvalidAddressElementException;
import com.jakespringer.lostexhaust.error.InvalidPlaceException;

public class GoogleMaps {
    private static final int CACHE_SIZE = 128;
    private static List<GooglePlaceInfo> placeCache = new ArrayList<>(CACHE_SIZE);
    private static int placeCachePosition = 0;
    
    public static Coordinates placeIdToCoordinates(String placeId) throws InvalidPlaceException {
        return placeIdToInfo(placeId).toCoordinates();
    }
    
    public static Address placeIdToAddress(String placeId) throws InvalidPlaceException {        
        try {
            return placeIdToInfo(placeId).toAddress();
        } catch (InvalidAddressElementException e) {
            throw new RuntimeException(e); // should be caught at development, not during production
        }
    }
    
    public static GooglePlaceInfo placeIdToInfo(String placeId) throws InvalidPlaceException {
        // check cache
        for (GooglePlaceInfo info : placeCache) {
            if (info.placeId.equals(placeId)) {
                return info;
            }
        }
        
        boolean throwPlaceException = false;
        
        String xmlPlace;
        String info_streetNumber = "";
        String info_route = "";
        String info_locality = "";
        String info_adminArea = "";
        String info_country = "";
        String info_postalCode = "";
        double info_latitude = 0.;
        double info_longitude = 0.;
        try {
            xmlPlace = LeService.fetchUrl("https://maps.googleapis.com/maps/api/place/details/xml" 
                    + "?placeid=" + placeId 
                    + "&key=" + LeService.getConfiguration().googleApiKey);
            
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlPlace.getBytes()));
            XPath xPath = XPathFactory.newInstance().newXPath();
            
            String status = xPath.compile("/PlaceDetailsResponse/status").evaluate(document);
            if (status.equals("OK")) {
                double latitude = Double.parseDouble(xPath.compile("/PlaceDetailsResponse//location/lat").evaluate(document));
                double longitude = Double.parseDouble(xPath.compile("/PlaceDetailsResponse//location/lng").evaluate(document));
                info_latitude = latitude;
                info_longitude = longitude;
                
                NodeList addressElements = (NodeList) xPath.compile("/PlaceDetailsResponse//address_component").evaluate(document, XPathConstants.NODESET);
                for (int i=0; i<addressElements.getLength(); ++i) {
                    Node current = addressElements.item(i);
                    NodeList children = current.getChildNodes();
                    List<String> type = new ArrayList<>();
                    String short_name = "";
                    String long_name = "";
                    for (int j=0; j<children.getLength(); ++j) {
                        Node currentChild = children.item(j);
                        if (currentChild.getNodeName().equals("type")) {
                            type.add(currentChild.getTextContent());
                        } else if (currentChild.getNodeName().equals("short_name")) {
                            short_name = currentChild.getTextContent();
                            if (short_name == null) short_name = "";
                        } else if (currentChild.getNodeName().equals("long_name")) {
                            long_name = currentChild.getTextContent();
                            if (long_name == null) long_name = "";
                        }
                    }
                    
                    if (type.contains("street_number")) info_streetNumber = short_name;
                    else if (type.contains("route")) info_route = long_name;
                    else if (type.contains("locality")) info_locality = long_name;
                    else if (type.contains("administrative_area_level_1")) info_adminArea = short_name;
                    else if (type.contains("country")) info_country = long_name;
                    else if (type.contains("postal_code")) info_postalCode = long_name;
                }
            } else throwPlaceException = true;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        
        GooglePlaceInfo info = new GooglePlaceInfo(placeId, info_streetNumber, 
                info_route, info_locality, info_adminArea, info_postalCode, 
                info_country, info_latitude, info_longitude);
        
        if (throwPlaceException) throw new InvalidPlaceException();
        
        // add to cache
        if (placeCache.size() > placeCachePosition) placeCache.set(placeCachePosition, info);
        else placeCache.add(info);
        placeCachePosition = (placeCachePosition+1) % CACHE_SIZE;
        
        return info;
    }
    
    public static String addressToPlaceId(Address address) throws InvalidPlaceException {
        boolean throwPlaceException = false;
        
        try {
            String xmlPlace = LeService.fetchUrl("https://maps.googleapis.com/maps/api/place/autocomplete/xml"
                    + "?input=" 
                        + (address.getStreetNumber() + " " 
                        + address.getRoute() + ", " 
                        + address.getLocality() + " " 
                        + address.getAdminArea() + " " 
                        + address.getCountry()).replace(" ", "%20")
                    + "&key=" + LeService.getConfiguration().googleApiKey);
            
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlPlace.getBytes()));
            XPath xPath = XPathFactory.newInstance().newXPath();
            
            String status = xPath.compile("/AutocompletionResponse/status").evaluate(document);

            if (status.equals("OK")) {
                String placeId = xPath.compile("/AutocompletionResponse/prediction/place_id").evaluate(document);
                if (placeId == null || placeId.length() == 0) throwPlaceException = true;
                else return placeId;
            } else throwPlaceException = true;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        
        if (throwPlaceException) throw new InvalidPlaceException();
        throw new RuntimeException("Should never reach here");
    }
}
