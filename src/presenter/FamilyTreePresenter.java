package presenter;

import model.Dog;
import model.Gender;
import model.FamilyTree;
import model.Person;
import service.FamilyTreeStorage;
import view.FamilyTreeView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FamilyTreePresenter {
    private FamilyTreeView view;
    private FamilyTree<Person> familyTree;
    private FamilyTreeStorage storage;

    public FamilyTreePresenter(FamilyTreeView view, FamilyTree<Person> familyTree, FamilyTreeStorage storage) {
        this.view = view;
        this.familyTree = familyTree;
        this.storage = storage;
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
            storage.saveFamilyTree(familyTree, "family_tree.dat");
            view.displayMessage("Данные успешно сохранены в файле family_tree.dat");
        } catch (IOException e) {
            view.displayMessage("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    private void addMember() {
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

        Person person;
        if (type == 1) {
            person = new Person(firstName, middleName, lastName, birthDate, gender);
        } else if (type == 2) {
            person = new Dog(firstName, middleName, lastName, birthDate, gender);
        } else {
            view.displayMessage("Неверный тип члена семьи.");
            return;
        }

        view.displayMessage("Введите полное имя одного из родителей (или оставьте пустым, если не известно): ");
        String parentFullName = view.getUserInput();

        if (!parentFullName.isEmpty()) {
            Person parent = familyTree.findMemberByName(parentFullName);
            if (parent != null) {
                parent.addChild(person);
                view.displayMessage("Родитель добавлен.");
            } else {
                view.displayMessage("Родитель с таким именем не найден.");
            }
        }

        familyTree.addMember(person);
        view.displayMessage("Член семьи добавлен.");
    }

    private void saveTree() {
        try {
            storage.saveFamilyTree(familyTree, "family_tree.dat");
            view.displayMessage("Семейное дерево сохранено в family_tree.dat");
        } catch (IOException e) {
            view.displayMessage("Ошибка при сохранении семейного дерева: " + e.getMessage());
        }
    }

    private void loadTree() {
        try {
            familyTree = (FamilyTree<Person>) storage.loadFamilyTree("family_tree.dat");
            view.displayMessage("Семейное дерево загружено из family_tree.dat");
        } catch (IOException | ClassNotFoundException e) {
            view.displayMessage("Ошибка при загрузке семейного дерева: " + e.getMessage());
        }
    }

    private void displayMembers() {
        StringBuilder sb = new StringBuilder();
        for (Person person : familyTree) {
            sb.append(person).append("\n");
        }
        view.displayMembers(sb.toString());
    }

    private void findChildren() {
        view.displayMessage("Введите полное имя родителя: ");
        String fullName = view.getUserInput();
        List<Person> children = familyTree.getChildren(fullName);
        StringBuilder sb = new StringBuilder("Дети ").append(fullName).append(":\n");
        for (Person child : children) {
            sb.append(child.getFullName()).append("\n");
        }
        view.displayMembers(sb.toString());
    }

    private void sortByName() {
        familyTree.sortByName();
        view.displayMessage("Члены семьи отсортированы по имени.");
    }

    private void sortByBirthDate() {
        familyTree.sortByBirthDate();
        view.displayMessage("Члены семьи отсортированы по дате рождения.");
    }

    private void findAndEditMember() {
        view.displayMessage("Введите полное имя члена семьи для редактирования: ");
        String fullName = view.getUserInput();
        Person person = familyTree.findMemberByName(fullName);
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

            // Новый код для редактирования родителей
            view.displayMessage("Введите полное имя нового родителя (или оставьте пустым, чтобы не изменять): ");
            String newParentName = view.getUserInput();
            if (!newParentName.isEmpty()) {
                Person newParent = familyTree.findMemberByName(newParentName);
                if (newParent != null) {
                    person.addParent(newParent);
                    newParent.addChild(person);
                    view.displayMessage("Родитель успешно добавлен.");
                } else {
                    view.displayMessage("Родитель с таким именем не найден.");
                }
            }

            view.displayMessage("Введите полное имя родителя для удаления (или оставьте пустым, чтобы не изменять): ");
            String removeParentName = view.getUserInput();
            if (!removeParentName.isEmpty()) {
                Person removeParent = familyTree.findMemberByName(removeParentName);
                if (removeParent != null) {
                    person.removeParent(removeParent);
                    removeParent.removeChild(person);
                    view.displayMessage("Родитель успешно удален.");
                } else {
                    view.displayMessage("Родитель с таким именем не найден.");
                }
            }

            view.displayMessage("Член семьи обновлен.");
        } else {
            view.displayMessage("Член семьи с таким именем не найден.");
        }
    }
}
