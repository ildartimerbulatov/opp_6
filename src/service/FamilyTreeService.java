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

    public void saveFamilyTree(String filePath) throws IOException {
        storage.saveFamilyTree(familyTree, filePath);
    }

    @SuppressWarnings("unchecked")
    public void loadFamilyTree(String filePath) throws IOException, ClassNotFoundException {
        this.familyTree = (FamilyTree<Person>) storage.loadFamilyTree(filePath);
    }

    public FamilyTree<Person> getFamilyTree() {
        return familyTree;
    }

    public void addMember(Person person) {
        familyTree.addMember(person);
    }

    public List<Person> getAllMembers() {
        return familyTree.getMembers();
    }

    public Person findMemberByName(String parentFullName) {
       
        throw new UnsupportedOperationException("Unimplemented method 'findMemberByName'");
    }

    public List<Person> getChildren(String parentName) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getChildren'");
    }
}
