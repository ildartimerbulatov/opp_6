import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FamilyTree implements Serializable {
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
}






