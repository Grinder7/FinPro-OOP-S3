package app.models;

public abstract class Person {
    private int _id;
    public String name;
    public int age;
    public String gender;

    public Person(int id, String name, int age, String gender) {
        _id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public int getId() {return _id;}

    public String getName() {return name;}

    public int getAge() {return age;}

    public String getGender() {return gender;}
}