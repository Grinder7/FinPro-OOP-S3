package app.models;

public class Patient extends Person {
    private int _handledByCaretakerId;

    public Patient(int id, String name, int age, String gender, int careTakerId) {
        super(id, name, age, gender);

        _handledByCaretakerId = careTakerId;
    }
}