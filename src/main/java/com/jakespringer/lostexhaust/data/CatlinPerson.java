package com.jakespringer.lostexhaust.data;

import java.util.Collections;
import java.util.List;
import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.Relationship;

public class CatlinPerson {
    public final String id;
    public final String firstname;
    public final String lastname;
    public final String gradeLevel;
    public final String classYear;
    public final String affiliation;
    public final List<Contact> contact;
    public final List<Relationship> relationships;

    public CatlinPerson(String _id, String _firstname, String _lastname, String _gradeLevel, String _classYear, String _affiliation, List<Contact> _contact, List<Relationship> _relationships) {
        id = _id;
        firstname = _firstname;
        lastname = _lastname;
        gradeLevel = _gradeLevel;
        classYear = _classYear;
        affiliation = _affiliation;
        contact = Collections.unmodifiableList(_contact);
        relationships = Collections.unmodifiableList(_relationships);
    }
}
