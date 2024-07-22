package model;

import java.util.Date;

public class Dog extends Person {
    public Dog(String firstName, String middleName, String lastName, Date birthDate, Gender gender) {
        super(firstName, middleName, lastName, birthDate, gender);
    }

    @Override
    public String toString() {
        return "Собака: " + super.toString();
    }
}
