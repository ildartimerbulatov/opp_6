package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class FamilyTree implements Serializable, Iterable<Person> {
    private List<Person> persons;

    public FamilyTree() {
        this.persons = new ArrayList<>();
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public List<Person> getChildren(String fullName) {
        List<Person> children = new ArrayList<>();
        for (Person person : persons) {
            if (person.getParents().stream().anyMatch(p -> p.getFullName().equals(fullName))) {
                children.add(person);
            }
        }
        return children;
    }

    public void sortByName() {
        Collections.sort(persons, Comparator.comparing(Person::getFullName));
    }

    public void sortByBirthDate() {
        Collections.sort(persons, Comparator.comparing(Person::getBirthDate));
    }

    @Override
    public Iterator<Person> iterator() {
        return persons.iterator();
    }
}






