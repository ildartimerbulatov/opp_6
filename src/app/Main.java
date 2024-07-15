package app;

import model.*;
import service.*;

import java.io.IOException;
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

        Person ivanIvanov = new Person("Иван", "Иванович", "Иванов", birthDateJohn, Gender.MALE);
        Person mariaIvanova = new Person("Мария", "Ивановна", "Иванова", birthDateJane, Gender.FEMALE);
        Person alice = new Person("Алиса", "Ивановна", "Иванова", birthDateAlice, Gender.FEMALE);
        Person bob = new Person("Боб", "Иванович", "Иванов", birthDateBob, Gender.MALE);

        familyTree.addPerson(ivanIvanov);
        familyTree.addPerson(mariaIvanova);
        familyTree.addPerson(alice);
        familyTree.addPerson(bob);

        ivanIvanov.addChild(alice);
        ivanIvanov.addChild(bob);

        FamilyTreeStorage storage = new FamilyTreeIO();
        String filePath = "family_tree.dat";

        try {
            storage.saveFamilyTree(familyTree, filePath);
            System.out.println("Семейное дерево сохранено в файл: " + filePath);

            FamilyTree loadedFamilyTree = storage.loadFamilyTree(filePath);
            System.out.println("Семейное дерево загружено из файла: " + filePath);

            List<Person> childrenOfIvan = loadedFamilyTree.getChildren(ivanIvanov.getFullName());

            System.out.println("Дети Ивана Ивановича Иванова:");
            for (Person child : childrenOfIvan) {
                System.out.println(child.getFullName());
            }

            System.out.println("Сортировка по имени:");
            loadedFamilyTree.sortByName();
            for (Person person : loadedFamilyTree) {
                System.out.println(person.getFullName());
            }

            System.out.println("Сортировка по дате рождения:");
            loadedFamilyTree.sortByBirthDate();
            for (Person person : loadedFamilyTree) {
                System.out.println(person.getFullName() + " - " + person.getBirthDate());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке семейного дерева: " + e.getMessage());
        }
    }
}



