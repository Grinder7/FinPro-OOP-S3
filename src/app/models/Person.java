package app.models;

public abstract class Person {
    private int _id;
    public String name;
    public int age;
    public char gender;

    public Person(int i, String n, int a, char g) {
        _id = i;
        name = n;
        age = a;
        gender = g;
    }
}