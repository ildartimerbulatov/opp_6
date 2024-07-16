package service;

import model.*;

import java.io.*;

public class FamilyTreeIO implements FamilyTreeStorage {
    @Override
    public void saveFamilyTree(FamilyTree<?> familyTree, String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(familyTree);
        }
    }

    @Override
    public FamilyTree<?> loadFamilyTree(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (FamilyTree<?>) in.readObject();
        }
    }
}

