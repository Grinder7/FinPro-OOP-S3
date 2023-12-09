package app.models;

public class Patient extends Person {
    private int _handledByCaretakerId;

    public Patient(int i, String n, int a, char g, int cti) {
        super(i, n, a, g);

        _handledByCaretakerId = cti;
    } 
}