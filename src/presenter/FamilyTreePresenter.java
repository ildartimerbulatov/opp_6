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
            Person parent = service.findMemberByName(parentFullName);
            if (parent != null) {
                parent.addChild(person);
            } else {
                view.displayMessage("Родитель с указанным именем не найден.");
            }
        }

        service.addMember(person);
        view.displayMessage("Член семьи добавлен: " + person);
    }

    private void saveTree() {
        view.displayMessage("Введите имя файла для сохранения: ");
        String fileName = view.getUserInput();
        try {
            service.saveFamilyTree(fileName);
            view.displayMessage("Данные успешно сохранены в файле " + fileName);
        } catch (IOException e) {
            view.displayMessage("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    private void loadTree() {
        view.displayMessage("Введите имя файла для загрузки (или нажмите Enter для загрузки по умолчанию): ");
        String fileName = view.getUserInput();
        if (fileName.isEmpty()) {
            fileName = "family_tree.dat";
        }
        try {
            service.loadFamilyTree(fileName);
            view.displayMessage("Данные успешно загружены из файла " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            view.displayMessage("Ошибка при загрузке данных: " + e.getMessage());
        }
    }

    private void displayMembers() {
        List<Person> members = service.getAllMembers();
        if (members.isEmpty()) {
            view.displayMessage("Список членов семьи пуст.");
        } else {
            view.displayMessage("Члены семьи:");
            for (Person member : members) {
                view.displayMessage(member.toString());
            }
        }
    }

    private void findChildren() {
        view.displayMessage("Введите имя родителя для поиска детей: ");
        String parentName = view.getUserInput();
        List<Person> children = service.getChildren(parentName);
        if (children.isEmpty()) {
            view.displayMessage("Дети для указанного родителя не найдены.");
        } else {
            view.displayMessage("Дети:");
            for (Person child : children) {
                view.displayMessage(child.toString());
            }
        }
    }

    private void sortByName() {
        service.sortByName();
        view.displayMessage("Список членов семьи отсортирован по имени.");
    }

    private void sortByBirthDate() {
        service.sortByBirthDate();
        view.displayMessage("Список членов семьи отсортирован по дате рождения.");
    }

    private void findAndEditMember() {
        view.displayMessage("Введите имя члена семьи для поиска: ");
        String partialName = view.getUserInput();
        Person person = service.findMemberByName(partialName);
        if (person == null) {
            view.displayMessage("Член семьи с указанным именем не найден.");
            return;
        }
        view.displayMessage("Найден член семьи: " + person);
        view.displayMessage("Вы хотите изменить его данные? (yes/no)");
        String answer = view.getUserInput().toLowerCase();
        if (answer.equals("yes") || answer.equals("y")) {
            view.displayMessage("Введите новое имя (оставьте пустым, чтобы не менять): ");
            String firstName = view.getUserInput();
            if (!firstName.isEmpty()) person.setFirstName(firstName);

            view.displayMessage("Введите новое отчество (оставьте пустым, чтобы не менять): ");
            String middleName = view.getUserInput();
            if (!middleName.isEmpty()) person.setMiddleName(middleName);

            view.displayMessage("Введите новую фамилию (оставьте пустым, чтобы не менять): ");
            String lastName = view.getUserInput();
            if (!lastName.isEmpty()) person.setLastName(lastName);

            view.displayMessage("Введите новую дату рождения (оставьте пустым, чтобы не менять): ");
            String birthDateString = view.getUserInput();
            if (!birthDateString.isEmpty()) {
                try {
                    Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
                    person.setBirthDate(birthDate);
                } catch (ParseException e) {
                    view.displayMessage("Неверный формат даты. Данные не изменены.");
                }
            }

            view.displayMessage("Введите новый пол (MALE/FEMALE, оставьте пустым, чтобы не менять): ");
            String genderStr = view.getUserInput();
            if (!genderStr.isEmpty()) {
                try {
                    Gender gender = Gender.valueOf(genderStr.toUpperCase());
                    person.setGender(gender);
                } catch (IllegalArgumentException e) {
                    view.displayMessage("Неверное значение для пола. Данные не изменены.");
                }
            }

            view.displayMessage("Данные обновлены: " + person);
        }
    }
}
