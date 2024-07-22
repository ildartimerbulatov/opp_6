package view;

import presenter.FamilyTreePresenter;

public interface FamilyTreeView {
    void showMenu();
    void displayMessage(String message);
    void displayMembers(String members);
    String getUserInput();
    void setPresenter(FamilyTreePresenter presenter);
}
