package view;

import presenter.FamilyTreePresenter;

public interface FamilyTreeView {
    void setPresenter(FamilyTreePresenter presenter);
    void showMenu();
    void displayMessage(String message);
    String getUserInput();
    void displayMembers(String string);
}
