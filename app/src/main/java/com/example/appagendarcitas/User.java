package com.example.appagendarcitas;

public abstract class User {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String password;
    private String birthday; // Nuevo campo para fecha de cumpleaños
    private String sex; // Nuevo campo para sexo

    public User(String name, String email, String address, String phoneNumber, String password, String birthday, String sex) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User: " + name + ", Email: " + email +
                "\nAddress: " + address + ". Phone: " + phoneNumber;
    }

    public abstract void showDataUser();
}
