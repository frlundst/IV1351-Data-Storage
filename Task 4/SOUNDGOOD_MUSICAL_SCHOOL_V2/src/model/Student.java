package model;

public class Student {
    private final String ID;
    private String personal_number;
    private String name;
    private String age;
    private String address;
    private String skill_level;
    private String sibling;
    private String is_accepted;

    public Student(String ID, String personal_number, String name, String age, String address, String skill_level, String sibling, String is_accepted){
        this.ID = ID;
        this.personal_number = personal_number;
        this.name = name;
        this.age = age;
        this.address = address;
        this.skill_level = skill_level;
        this.sibling = sibling;
        this.is_accepted = is_accepted;
    }

    public String toString(){
        return "[" + ID + ", " + personal_number + ", " + name + ", " + age + ", " + address + ", " + skill_level + ", " + sibling + ", " + is_accepted + "]";
    }
}
