package com.svartvalp.demo;

public class Human {
    private int id;
    private String name;
    private String lastName;
    private int age;

    public Human(int id, String name, String lastName, int age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Human(String name, String lastName, int age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Human() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(obj instanceof Human) {
            var human = (Human) obj;
            return human.getName().equals(this.name) &&
                    human.getLastName().equals(this.lastName) &&
                    human.getAge() == this.getAge();
        } else return false;
    }

    @Override
    public int hashCode() {
        int prime = 97;
        int result = 1;
        result = result * prime + (name == null ? 0 : name.hashCode());
        result = result * prime + (lastName == null ? 0 : lastName.hashCode());
        result = result * prime + age;
        return result;
    }
}
