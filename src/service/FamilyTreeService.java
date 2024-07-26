package service;

import model.FamilyTree;
import model.Person;

import java.io.IOException;
import java.util.List;

public class FamilyTreeService {
    private FamilyTree<Person> familyTree;
    private FamilyTreeStorage storage;

    public FamilyTreeService(FamilyTree<Person> familyTree, FamilyTreeStorage storage) {
        this.familyTree = familyTree;
        this.storage = storage;
    }

    public void addMember(Person person) {
        familyTree.addMember(person);
    }

    public List<Person> getAllMembers() {
        return (List<Person>) familyTree.getMembers();
    }

    public List<Person> getChildren(String parentName) {
        return familyTree.getChildren(parentName);
    }

    public void sortByName() {
        familyTree.sortByName();
    }

    public void sortByBirthDate() {
        familyTree.sortByBirthDate();
    }

    public Person findMemberByName(String name) {
        return familyTree.findMemberByName(name);
    }

    public void saveFamilyTree(String fileName) throws IOException {
        storage.saveFamilyTree(familyTree, fileName);
    }

    @SuppressWarnings("unchecked")
    public void loadFamilyTree(String fileName) throws IOException, ClassNotFoundException {
        this.familyTree = (FamilyTree<Person>) storage.loadFamilyTree(fileName);
    }
}
