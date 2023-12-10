package app.models;

public abstract class Person {
    protected int _id;
    protected String name;
    protected int age;
    protected String gender;

    public Person(int id, String name, int age, String gender) {
        _id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public void setId(int id) {this._id = id;}

    public int getId() {return _id;}

    public String getName() {return name;}

    public int getAge() {return age;}

    public String getGender() {return gender;}
}