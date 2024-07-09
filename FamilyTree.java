import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyTree {
    private Map<String, Person> members;

    public FamilyTree() {
        this.members = new HashMap<>();
    }

    public void addPerson(Person person) {
        this.members.put(person.getFullName(), person);
    }

    public Person getPerson(String fullName) {
        return this.members.get(fullName);
    }

    public List<Person> getChildren(String fullName) {
        Person person = this.members.get(fullName);
        if (person != null) {
            return person.getChildren();
        }
        return new ArrayList<>();
    }
}



