package app.models;

import app.interfaces.DBActions;

public class Patient extends Person implements DBActions<Patient> {
    private int _handledByCaretakerId;

    public Patient(int id, String name, int age, String gender, int careTakerId) {
        super(id, name, age, gender);

        _handledByCaretakerId = careTakerId;
    }

    public Patient(String name, int age, String gender, int careTakerId) {
        super(name, age, gender);

        _handledByCaretakerId = careTakerId;
    }

    @Override
    public void insert() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void update(Patient newObj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}