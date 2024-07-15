package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person implements Serializable {
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private List<Person> parents;
    private List<Person> children;

    public Person(String firstName, String middleName, String lastName, Date birthDate, Gender gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.parents = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void addChild(Person child) {
        children.add(child);
        child.parents.add(this);
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public List<Person> getParents() {
        return parents;
    }

    public List<Person> getChildren() {
        return children;
    }
}






