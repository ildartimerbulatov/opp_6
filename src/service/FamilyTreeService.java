package service;

import model.FamilyTree;
import model.Person;

import java.io.IOException;

public class FamilyTreeService {
    private FamilyTree<Person> familyTree;
    private FamilyTreeStorage storage;

    public FamilyTreeService(FamilyTree<Person> familyTree, FamilyTreeStorage storage) {
        this.familyTree = familyTree;
        this.storage = storage;
    }

    public void saveFamilyTree(String filePath) throws IOException {
        storage.saveFamilyTree(familyTree, filePath);
    }

    public void loadFamilyTree(String filePath) throws IOException, ClassNotFoundException {
        this.familyTree = (FamilyTree<Person>) storage.loadFamilyTree(filePath);
    }

    public FamilyTree<Person> getFamilyTree() {
        return familyTree;
    }

    public void addMember(Person person) {
        familyTree.addMember(person);
    }
}
