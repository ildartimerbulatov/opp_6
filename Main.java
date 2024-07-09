import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FamilyTree familyTree = new FamilyTree();

        Calendar calendar = Calendar.getInstance();
        
        calendar.set(1984, Calendar.MAY, 15);
        Date birthDateJohn = calendar.getTime();
        
        calendar.set(1986, Calendar.JUNE, 20);
        Date birthDateJane = calendar.getTime();
        
        calendar.set(2005, Calendar.AUGUST, 25);
        Date birthDateAlice = calendar.getTime();
        
        calendar.set(2007, Calendar.SEPTEMBER, 10);
        Date birthDateBob = calendar.getTime();

        Person ivanIvanov = new Person("Иван", "Иванович", "Иванов", 40, birthDateJohn, "male");
        Person mariaIvanova = new Person("Мария", "Ивановна", "Иванова", 38, birthDateJane, "female");
        Person annaIvanova = new Person("Анна", "Ивановна", "Иванова", 18, birthDateAlice, "female");
        Person sergeyIvanov = new Person("Сергей", "Иванович", "Иванов", 16, birthDateBob, "male");

        ivanIvanov.addChild(annaIvanova);
        ivanIvanov.addChild(sergeyIvanov);

        mariaIvanova.addChild(annaIvanova);
        mariaIvanova.addChild(sergeyIvanov);

        familyTree.addPerson(ivanIvanov);
        familyTree.addPerson(mariaIvanova);
        familyTree.addPerson(annaIvanova);
        familyTree.addPerson(sergeyIvanov);

        List<Person> childrenOfIvan = familyTree.getChildren(ivanIvanov.getFullName());

        System.out.println("Дети Ивана Ивановича Иванова:");
        for (Person child : childrenOfIvan) {
            System.out.println(child.getFullName());
        }
    }
}
