package service;

import model.FamilyTree;
import model.Gender;
import model.Person;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FamilyTreeService {
    private FamilyTree<Person> familyTree;

    public FamilyTreeService(FamilyTree<Person> familyTree) {
        this.familyTree = familyTree;
    }

    public void addMember(String firstName, String middleName, String lastName, String birthDateString, String genderStr, String parentFullName) throws ParseException, IllegalArgumentException {
        Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
        Gender gender = Gender.valueOf(genderStr.toUpperCase());

        Person person = new Person(firstName, middleName, lastName, birthDate, gender);

        if (!parentFullName.isEmpty()) {
            Person parent = familyTree.findMemberByName(parentFullName);
            if (parent != null) {
                parent.addChild(person);
            }
        }

        familyTree.addMember(person);
    }

    public void saveTree(FamilyTreeStorage storage, String filePath) throws IOException {
        storage.saveFamilyTree(familyTree, filePath);
    }

    public void loadTree(FamilyTreeStorage storage, String filePath) throws IOException, ClassNotFoundException {
        familyTree = (FamilyTree<Person>) storage.loadFamilyTree(filePath);
    }

    public String getFamilyTreeMembers() {
        StringBuilder sb = new StringBuilder();
        for (Person person : familyTree) {
            sb.append(person).append("\n");
        }
        return sb.toString();
    }

    public String getChildrenOf(String parentName) {
        List<Person> children = familyTree.getChildren(parentName);
        StringBuilder sb = new StringBuilder("Дети ").append(parentName).append(":\n");
        for (Person child : children) {
            sb.append(child.getFullName()).append("\n");
        }
        return sb.toString();
    }

    public void sortFamilyTreeByName() {
        familyTree.sortByName();
    }

    public void sortFamilyTreeByBirthDate() {
        familyTree.sortByBirthDate();
    }

    public void addMember(int type, String firstName, String middleName, String lastName, String birthDateString,
            String genderStr, String parentFullName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMember'");
    }
}
