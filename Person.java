import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;
    private Date birthDate;
    private Date deathDate;
    private Person mother;
    private Person father;
    private List<Person> children;
    private String gender;

    public Person(String firstName, String middleName, String lastName, int age, Date birthDate, String gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
        this.birthDate = birthDate;
        this.gender = gender;
        this.children = new ArrayList<>();
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public int getAge() {
        return age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void addChild(Person child) {
        this.children.add(child);
        if (child.getMother() == null && this.gender.equals("female")) {
            child.setMother(this);
        }
        if (child.getFather() == null && this.gender.equals("male")) {
            child.setFather(this);
        }
    }
}



