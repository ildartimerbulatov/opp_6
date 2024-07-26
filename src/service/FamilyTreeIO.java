package service;

import model.FamilyTree;

import java.io.*;

public class FamilyTreeIO implements FamilyTreeStorage {

    @Override
    public void saveFamilyTree(FamilyTree<?> familyTree, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(familyTree);
        }
    }

    @Override
    public FamilyTree<?> loadFamilyTree(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (FamilyTree<?>) ois.readObject();
        }
    }
}
