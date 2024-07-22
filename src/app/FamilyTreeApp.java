package app;

import model.*;
import service.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FamilyTreeApp {
    private FamilyTree<Person> familyTree;
    private FamilyTreeStorage storage;
    private Scanner scanner;

    public FamilyTreeApp() {
        this.storage = new FamilyTreeIO();
        this.scanner = new Scanner(System.in);
        // Загрузка данных при старте программы
        try {
            familyTree = loadTypedFamilyTree("family_tree.dat");
            System.out.println("Данные успешно загружены из файла family_tree.dat");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке данных: " + e.getMessage());
            familyTree = new FamilyTree<>();
        }
    }

    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Используем новую строку
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
        // Сохранение данных при выходе из программы
        try {
            saveTypedFamilyTree("family_tree.dat");
            System.out.println("Данные успешно сохранены в файле family_tree.dat");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных: " + e.getMessage());
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
        System.out.print("Сделайте ваш выбор: ");
    }

    private void addMember() {
    System.out.print("Введите тип члена семьи (1 - Человек, 2 - Собака): ");
    int type = scanner.nextInt();
    scanner.nextLine(); // Используем новую строку

    System.out.print("Введите имя: ");
    String firstName = scanner.nextLine();

    String middleName = null; // Отчество не требуется для собак
    String lastName = null; // Фамилия не требуется для собак

    if (type == 1) { // Человек
        System.out.print("Введите отчество: ");
        middleName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        lastName = scanner.nextLine();
    }

    System.out.println("Введите дату рождения в формате ГГГГ-ММ-ДД (например, 1990-09-08): ");
    String birthDateString = scanner.nextLine();
    Date birthDate = null;

    // Парасинг даты
    try {
        birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
    } catch (ParseException e) {
        System.err.println("Неверный формат даты. Пожалуйста, используйте формат ГГГГ-ММ-ДД.");
        return;
    }

    Gender gender = null;
    System.out.print("Введите пол (MALE/FEMALE): ");
    String genderStr = scanner.nextLine();
    try {
        gender = Gender.valueOf(genderStr.toUpperCase());
    } catch (IllegalArgumentException e) {
        System.err.println("Неверное значение для пола. Пожалуйста, используйте MALE или FEMALE.");
        return;
    }

    if (type == 1) { // Человек
        Person person = new Person(firstName, middleName, lastName, birthDate, gender);
        familyTree.addMember(person);
        System.out.println("Член семьи добавлен.");
    } else if (type == 2) { // Собака
        Dog dog = new Dog(firstName, middleName, lastName, birthDate, gender);
        familyTree.addMember(dog);
        System.out.println("Собака добавлена.");
    } else {
        System.out.println("Неверный тип члена семьи.");
    }
    }

    private void saveTree() {
        try {
            saveTypedFamilyTree("family_tree.dat");
            System.out.println("Семейное дерево сохранено в family_tree.dat");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении семейного дерева: " + e.getMessage());
        }
    }

    private void loadTree() {
        try {
            familyTree = loadTypedFamilyTree("family_tree.dat");
            System.out.println("Семейное дерево загружено из family_tree.dat");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке семейного дерева: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private FamilyTree<Person> loadTypedFamilyTree(String filePath) throws IOException, ClassNotFoundException {
        return (FamilyTree<Person>) storage.loadFamilyTree(filePath);
    }

    private void saveTypedFamilyTree(String filePath) throws IOException {
        storage.saveFamilyTree(familyTree, filePath);
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
