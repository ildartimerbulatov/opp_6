package presenter;

import model.FamilyTree;
import model.Gender;
import model.Person;
import service.FamilyTreeService;
import view.FamilyTreeView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FamilyTreePresenter {
    private FamilyTreeView view;
    private FamilyTreeService service;

    public FamilyTreePresenter(FamilyTreeView view, FamilyTreeService service) {
        this.view = view;
        this.service = service;
    }

    public void start() {
        loadTree();
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
                case 8:
                    findAndEditMember();
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
            service.saveFamilyTree("family_tree.dat");
            view.displayMessage("Данные успешно сохранены в файле family_tree.dat");
        } catch (IOException e) {
            view.displayMessage("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    private void addMember() {
        view.displayMessage("Введите имя: ");
        String firstName = view.getUserInput();

        view.displayMessage("Введите отчество: ");
        String middleName = view.getUserInput();
        view.displayMessage("Введите фамилию: ");
        String lastName = view.getUserInput();

        view.displayMessage("Введите дату рождения в формате ГГГГ-ММ-ДД (например, 1990-09-08): ");
        String birthDateString = view.getUserInput();
        Date birthDate;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
        } catch (ParseException e) {
            view.displayMessage("Неверный формат даты. Пожалуйста, используйте формат ГГГГ-ММ-ДД.");
            return;
        }

        view.displayMessage("Введите пол (MALE/FEMALE): ");
        String genderStr = view.getUserInput();
        Gender gender;
        try {
            gender = Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            view.displayMessage("Неверное значение для пола. Пожалуйста, используйте MALE или FEMALE.");
            return;
        }

        Person person = new Person(firstName, middleName, lastName, birthDate, gender);

        view.displayMessage("Введите полное имя одного из родителей (или оставьте пустым, если не известно): ");
        String parentFullName = view.getUserInput();

        if (!parentFullName.isEmpty()) {
            Person parent = service.getFamilyTree().findMemberByName(parentFullName);
            if (parent != null) {
                parent.addChild(person);
                view.displayMessage("Родитель добавлен.");
            } else {
                view.displayMessage("Родитель с таким именем не найден.");
            }
        }

        service.addMember(person);
        view.displayMessage("Член семьи добавлен.");
    }

    private void saveTree() {
        try {
            service.saveFamilyTree("family_tree.dat");
            view.displayMessage("Семейное дерево сохранено в family_tree.dat");
        } catch (IOException e) {
            view.displayMessage("Ошибка при сохранении семейного дерева: " + e.getMessage());
        }
    }

    private void loadTree() {
        try {
            service.loadFamilyTree("family_tree.dat");
            view.displayMessage("Семейное дерево загружено из family_tree.dat");
        } catch (IOException | ClassNotFoundException e) {
            view.displayMessage("Ошибка при загрузке семейного дерева: " + e.getMessage());
        }
    }

    private void displayMembers() {
        StringBuilder sb = new StringBuilder();
        for (Person person : service.getFamilyTree()) {
            sb.append(person).append("\n");
        }
        view.displayMembers(sb.toString());
    }

    private void findChildren() {
        view.displayMessage("Введите полное имя родителя: ");
        String fullName = view.getUserInput();
        List<Person> children = service.getFamilyTree().getChildren(fullName);
        StringBuilder sb = new StringBuilder("Дети ").append(fullName).append(":\n");
        for (Person child : children) {
            sb.append(child.getFullName()).append("\n");
        }
        view.displayMembers(sb.toString());
    }

    private void sortByName() {
        service.getFamilyTree().sortByName();
        view.displayMessage("Члены семьи отсортированы по имени.");
    }

    private void sortByBirthDate() {
        service.getFamilyTree().sortByBirthDate();
        view.displayMessage("Члены семьи отсортированы по дате рождения.");
    }

    private void findAndEditMember() {
        view.displayMessage("Введите полное имя члена семьи для редактирования: ");
        String fullName = view.getUserInput();
        Person person = service.getFamilyTree().findMemberByName(fullName);
        if (person != null) {
            view.displayMessage("Редактирование члена семьи: " + person.getFullName());
            view.displayMessage("Введите новое имя (или оставьте пустым, чтобы не изменять): ");
            String newFirstName = view.getUserInput();
            if (!newFirstName.isEmpty()) {
                person.setFirstName(newFirstName);
            }
            view.displayMessage("Введите новое отчество (или оставьте пустым, чтобы не изменять): ");
            String newMiddleName = view.getUserInput();
            if (!newMiddleName.isEmpty()) {
                person.setMiddleName(newMiddleName);
            }
            view.displayMessage("Введите новую фамилию (или оставьте пустым, чтобы не изменять): ");
            String newLastName = view.getUserInput();
            if (!newLastName.isEmpty()) {
                person.setLastName(newLastName);
            }
            view.displayMessage("Введите новую дату рождения в формате ГГГГ-ММ-ДД (или оставьте пустым, чтобы не изменять): ");
            String newBirthDateString = view.getUserInput();
            if (!newBirthDateString.isEmpty()) {
                try {
                    Date newBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(newBirthDateString);
                    person.setBirthDate(newBirthDate);
                } catch (ParseException e) {
                    view.displayMessage("Неверный формат даты. Пожалуйста, используйте формат ГГГГ-ММ-ДД.");
                }
            }
            view.displayMessage("Введите новый пол (MALE/FEMALE) (или оставьте пустым, чтобы не изменять): ");
            String newGenderStr = view.getUserInput();
            if (!newGenderStr.isEmpty()) {
                try {
                    Gender newGender = Gender.valueOf(newGenderStr.toUpperCase());
                    person.setGender(newGender);
                } catch (IllegalArgumentException e) {
                    view.displayMessage("Неверное значение для пола. Пожалуйста, используйте MALE или FEMALE.");
                }
            }
            view.displayMessage("Член семьи успешно обновлен.");
        } else {
            view.displayMessage("Член семьи с таким именем не найден.");
        }
    }
}
