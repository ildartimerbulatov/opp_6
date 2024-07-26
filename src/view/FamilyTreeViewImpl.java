package view;

import presenter.FamilyTreePresenter;

import java.util.Scanner;

public class FamilyTreeViewImpl implements FamilyTreeView {
    private Scanner scanner;

    public FamilyTreeViewImpl() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void setPresenter(FamilyTreePresenter presenter) {
    }

    @Override
    public void showMenu() {
        System.out.println("1. Добавить члена семьи");
        System.out.println("2. Сохранить семейное дерево");
        System.out.println("3. Загрузить семейное дерево");
        System.out.println("4. Показать всех членов семьи");
        System.out.println("5. Найти детей");
        System.out.println("6. Отсортировать по имени");
        System.out.println("7. Отсортировать по дате рождения");
        System.out.println("8. Найти и изменить члена семьи");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void displayMembers(String string) {
        
        throw new UnsupportedOperationException("Unimplemented method 'displayMembers'");
    }
}
