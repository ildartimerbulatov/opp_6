package service;

import model.FamilyTree;

import java.io.IOException;

public interface FamilyTreeStorage {
    void saveFamilyTree(FamilyTree<?> familyTree, String fileName) throws IOException;
    FamilyTree<?> loadFamilyTree(String fileName) throws IOException, ClassNotFoundException;
}
