package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person implements Serializable {
    private static final long serialVersionUID = 4219696389020110797L;
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

    public void removeChild(Person child) {
        children.remove(child);
        child.parents.remove(this);
    }

    public String getFullName() {
        return firstName + " " + (middleName != null ? middleName + " " : "") + lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public List<Person> getParents() {
        return parents;
    }

    public void setParents(List<Person> parents) {
        this.parents = parents;
    }

    public void addParent(Person parent) {
        if (!parents.contains(parent)) {
            parents.add(parent);
        }
    }

    public void removeParent(Person parent) {
        parents.remove(parent);
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFullName()).append(" - ").append(birthDate).append(" - ").append(gender);
        if (!parents.isEmpty()) {
            sb.append(" - Родители: ");
            for (Person parent : parents) {
                sb.append(parent.getFullName()).append(" ");
            }
        }
        if (!children.isEmpty()) {
            sb.append(" - Дети: ");
            for (Person child : children) {
                sb.append(child.getFullName()).append(" ");
            }
        }
        return sb.toString();
    }
}
