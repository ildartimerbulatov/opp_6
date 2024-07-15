import java.io.IOException;

public interface FamilyTreeStorage {
    void saveFamilyTree(FamilyTree familyTree, String filePath) throws IOException;
    FamilyTree loadFamilyTree(String filePath) throws IOException, ClassNotFoundException;
}
