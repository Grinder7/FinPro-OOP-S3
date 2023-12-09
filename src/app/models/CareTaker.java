package app.models;

public class Caretaker extends Person {
    public String phoneNum;

    public Caretaker(int i, String n, int a, char g, String pn) {
        super(i, n, a, g);

        phoneNum = pn;
    }
}