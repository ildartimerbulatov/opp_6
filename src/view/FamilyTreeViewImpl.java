package view;

import presenter.FamilyTreePresenter;

import java.util.Scanner;

public class FamilyTreeViewImpl implements FamilyTreeView {
    private FamilyTreePresenter presenter;
    private Scanner scanner;

    public FamilyTreeViewImpl() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        System.out.println("1. Добавить члена семьи");
        System.out.println("2. Сохранить дерево");
        System.out.println("3. Загрузить дерево");
        System.out.println("4. Показать всех членов семьи");
        System.out.println("5. Найти детей по имени");
        System.out.println("6. Сортировать членов семьи по имени");
        System.out.println("7. Сортировать членов семьи по дате рождения");
        System.out.println("8. Найти и редактировать члена семьи");
        System.out.println("0. Выход");
        System.out.print("Сделайте ваш выбор: ");
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayMembers(String members) {
        System.out.println(members);
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void setPresenter(FamilyTreePresenter presenter) {
        this.presenter = presenter;
    }
}

