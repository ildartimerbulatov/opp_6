package app;

import model.*;
import service.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FamilyTreeApp {
    private FamilyTree<Person> familyTree;
    private FamilyTreeStorage storage;
    private Scanner scanner;

    public FamilyTreeApp() {
        this.familyTree = new FamilyTree<>();
        this.storage = new FamilyTreeIO();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    saveTree();
                    break;
                case 3:
                    loadTree();
                    break;
                case 4:
                    displayMembers();
                    break;
                case 5:
                    findChildren();
                    break;
                case 6:
                    sortByName();
                    break;
                case 7:
                    sortByBirthDate();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private void showMenu() {
        System.out.println("1. Добавить члена семьи");
        System.out.println("2. Сохранить дерево");
        System.out.println("3. Загрузить дерево");
        System.out.println("4. Показать всех членов семьи");
        System.out.println("5. Найти детей по имени");
        System.out.println("6. Сортировать членов семьи по имени");
        System.out.println("7. Сортировать членов семьи по дате рождения");
        System.out.println("0. Выход");
        System.out.print("Введите ваш выбор: ");
    }

    private void addMember() {
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите отчество: ");
        String middleName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите пол (MALE/FEMALE): ");
        String genderStr = scanner.nextLine();
        Gender gender = Gender.valueOf(genderStr.toUpperCase());
        System.out.print("Введите год рождения: ");
        int birthYear = scanner.nextInt();
        System.out.print("Введите месяц рождения (1-12): ");
        int birthMonth = scanner.nextInt() - 1; // Calendar months are 0-based
        System.out.print("Введите день рождения: ");
        int birthDay = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Calendar calendar = Calendar.getInstance();
        calendar.set(birthYear, birthMonth, birthDay);
        Date birthDate = calendar.getTime();

        Person person = new Person(firstName, middleName, lastName, birthDate, gender);
        familyTree.addMember(person);
        System.out.println("Член семьи добавлен.");
    }

    private void saveTree() {
        System.out.print("Введите путь к файлу для сохранения: ");
        String filePath = scanner.nextLine();
        try {
            storage.saveFamilyTree(familyTree, filePath);
            System.out.println("Семейное дерево сохранено в " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении семейного дерева: " + e.getMessage());
        }
    }

    private void loadTree() {
        System.out.print("Введите путь к файлу для загрузки: ");
        String filePath = scanner.nextLine();
        try {
            familyTree = loadTypedFamilyTree(filePath);
            System.out.println("Семейное дерево загружено из " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке семейного дерева: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private FamilyTree<Person> loadTypedFamilyTree(String filePath) throws IOException, ClassNotFoundException {
        return (FamilyTree<Person>) storage.loadFamilyTree(filePath);
    }

    private void displayMembers() {
        for (Person person : familyTree) {
            System.out.println(person);
        }
    }

    private void findChildren() {
        System.out.print("Введите полное имя родителя: ");
        String fullName = scanner.nextLine();
        List<Person> children = familyTree.getChildren(fullName);
        System.out.println("Дети " + fullName + ":");
        for (Person child : children) {
            System.out.println(child.getFullName());
        }
    }

    private void sortByName() {
        familyTree.sortByName();
        System.out.println("Члены семьи отсортированы по имени.");
    }

    private void sortByBirthDate() {
        familyTree.sortByBirthDate();
        System.out.println("Члены семьи отсортированы по дате рождения.");
    }

    public static void main(String[] args) {
        FamilyTreeApp app = new FamilyTreeApp();
        app.start();
    }
}
