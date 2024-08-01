package com.school;

import javafx.beans.property.SimpleStringProperty;

public class Teacher {
 
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty id;
    private final SimpleStringProperty subject;

    public Teacher() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
        this.subject = new SimpleStringProperty("");
    }
 
    public Teacher(String lName, String fName, String email, String subject) {
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(email);
        this.id = new SimpleStringProperty("");
        this.subject = new SimpleStringProperty(subject);
    }
    
    public Teacher(Integer id, String lName, String fName, String email, String subject) {
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(email);
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.subject = new SimpleStringProperty(subject);
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String fName) {
        firstName.set(fName);
    }
        
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String fName) {
        lastName.set(fName);
    }
    
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String fName) {
        email.set(fName);
    }

    public String getId(){
        return id.get();
    }
    
    public void setId(String fId){
        id.set(fId);
    }
    
    public String getSubject() {
        return subject.get();
    }
    
    public void setSubject(String fSubject) {
        subject.set(fSubject);
    }
}
