package app.models;

import app.interfaces.DBActions;

public class Patient extends Person implements DBActions<Patient> {
    private String _disabilityDetail;

    public Patient(int id, String name, int age, String gender, String detail) {
        super(id, name, age, gender);

        _disabilityDetail = detail;
    }

    public Patient(String name, int age, String gender, String detail) {
        super(name, age, gender);

        _disabilityDetail = detail;
    }

    public void setDisabilityDetail(String disabilitDet) {_disabilityDetail = disabilitDet;}

    public String getDisabilityDetail() {return _disabilityDetail;}

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