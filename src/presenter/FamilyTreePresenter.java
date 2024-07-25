package presenter;

import model.FamilyTree;
import model.Person;
import service.FamilyTreeService;
import service.FamilyTreeStorage;
import view.FamilyTreeView;

import java.io.IOException;
import java.text.ParseException;

public class FamilyTreePresenter {
    private FamilyTreeView view;
    private FamilyTreeService service;
    private FamilyTreeStorage storage;

    public FamilyTreePresenter(FamilyTreeView view, FamilyTree<Person> familyTree, FamilyTreeStorage storage) {
        this.view = view;
        this.service = new FamilyTreeService(familyTree);
        this.storage = storage;
    }

    public void start() throws ParseException {
        try {
            service.loadTree(storage, "family_tree.dat");
            view.displayMessage("Семейное дерево загружено из family_tree.dat");
        } catch (IOException | ClassNotFoundException e) {
            view.displayMessage("Ошибка при загрузке семейного дерева: " + e.getMessage());
        }
        
        boolean running = true;
        while (running) {
            view.showMenu();
            int choice = Integer.parseInt(view.getUserInput());
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
                    saveTreeOnExit();
                    break;
                default:
                    view.displayMessage("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private void saveTreeOnExit() {
        try {
            service.saveTree(storage, "family_tree.dat");
            view.displayMessage("Данные успешно сохранены в файле family_tree.dat");
        } catch (IOException e) {
            view.displayMessage("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    private void addMember() throws ParseException {
        try {
            view.displayMessage("Введите тип члена семьи (1 - Человек, 2 - Собака): ");
            int type = Integer.parseInt(view.getUserInput());

            view.displayMessage("Введите имя: ");
            String firstName = view.getUserInput();

            String middleName = null;
            String lastName = null;

            if (type == 1) {
                view.displayMessage("Введите отчество: ");
                middleName = view.getUserInput();
                view.displayMessage("Введите фамилию: ");
                lastName = view.getUserInput();
            }

            view.displayMessage("Введите дату рождения в формате ГГГГ-ММ-ДД (например, 1990-09-08): ");
            String birthDateString = view.getUserInput();

            view.displayMessage("Введите пол (MALE/FEMALE): ");
            String genderStr = view.getUserInput();

            view.displayMessage("Введите полное имя одного из родителей (или оставьте пустым, если не известно): ");
            String parentFullName = view.getUserInput();

            service.addMember(type, firstName, middleName, lastName, birthDateString, genderStr, parentFullName);
            view.displayMessage("Член семьи добавлен.");
        } catch (IllegalArgumentException e) {
            view.displayMessage("Ошибка: " + e.getMessage());
        }
    }

    private void saveTree() {
        try {
            service.saveTree(storage, "family_tree.dat");
            view.displayMessage("Семейное дерево сохранено в family_tree.dat");
        } catch (IOException e) {
            view.displayMessage("Ошибка при сохранении семейного дерева: " + e.getMessage());
        }
    }

    private void loadTree() {
        try {
            service.loadTree(storage, "family_tree.dat");
            view.displayMessage("Семейное дерево загружено из family_tree.dat");
        } catch (IOException | ClassNotFoundException e) {
            view.displayMessage("Ошибка при загрузке семейного дерева: " + e.getMessage());
        }
    }

    private void displayMembers() {
        view.displayMembers(service.getFamilyTreeMembers());
    }

    private void findChildren() {
        view.displayMessage("Введите полное имя родителя: ");
        String fullName = view.getUserInput();
        view.displayMembers(service.getChildrenOf(fullName));
    }

    private void sortByName() {
        service.sortFamilyTreeByName();
        view.displayMessage("Члены семьи отсортированы по имени.");
    }

    private void sortByBirthDate() {
        service.sortFamilyTreeByBirthDate();
        view.displayMessage("Члены семьи отсортированы по дате рождения.");
    }
}
