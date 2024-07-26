package app;

import model.FamilyTree;
import model.Person;
import presenter.FamilyTreePresenter;
import service.FamilyTreeIO;
import service.FamilyTreeService;
import service.FamilyTreeStorage;
import view.FamilyTreeViewImpl;

public class Main {
    public static void main(String[] args) {
        FamilyTreeStorage storage = new FamilyTreeIO();
        FamilyTree<Person> familyTree = new FamilyTree<>();
        FamilyTreeService service = new FamilyTreeService(familyTree, storage);
        FamilyTreeViewImpl view = new FamilyTreeViewImpl();
        FamilyTreePresenter presenter = new FamilyTreePresenter(view, service);
        view.setPresenter(presenter);
        presenter.start();
    }
}
