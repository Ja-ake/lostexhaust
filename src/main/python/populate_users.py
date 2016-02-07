#!/usr/bin/python3
# -*- coding: utf-8 -*-

import MySQLdb as mdb
import sys

with mdb.connect(user="all") as db:
    with open("records.txt", "r") as f:
        records = f.readlines()
        for record in records:
            elements = record.split("|")
            db.execute("UPDATE lostexhaust.households SET place_id=\'{}\', latitude={}, longitude={} WHERE id={}".format(elements[2], elements[3], elements[4], elements[0]))
