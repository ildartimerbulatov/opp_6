import java.io.*;
import java.nio.charset.StandardCharsets;

public class FamilyTreeIO implements FamilyTreeStorage {
    @Override
    public void saveFamilyTree(FamilyTree familyTree, String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
             OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            out.writeObject(familyTree);
        }
    }

    @Override
    public FamilyTree loadFamilyTree(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (FamilyTree) in.readObject();
        }
    }
}

