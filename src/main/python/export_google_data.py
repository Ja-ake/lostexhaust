#!/usr/bin/python3
# -*- coding: utf-8 -*-

import MySQLdb as mdb
import sys
from gmaps import Geocoding
from gmaps import errors

geo = Geocoding(api_key='AIzaSyB8iNxTT7H6JYCkjwsKqAwSIhGa-_byB-s')

with mdb.connect('localhost', 'testuser', '', 'lostexhaust') as con:
    f = open("records.txt", "w")
    con.execute("SELECT id,addressblock,city,state,postcode FROM lostexhaust.households")
    for i in range(con.rowcount):
        try:
            (id, address, city, state, zipcode) = con.fetchone()
            fulladdress = address + ", " + city + ", " + state + " " + zipcode
            place = geo.geocode(fulladdress)[0];
            print(str(id) + "|" + place['formatted_address'] + "|" + place['place_id'] + "|" + str(place['geometry']['location']['lat']) + "|" + str(place['geometry']['location']['lng']))
            f.write(str(id) + "|" + place['formatted_address'] + "|" + place['place_id'] + "|" + str(place['geometry']['location']['lat']) + "|" + str(place['geometry']['location']['lng']) + "\n")
        except:
            pass
    f.close()
