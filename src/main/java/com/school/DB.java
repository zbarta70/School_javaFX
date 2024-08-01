
package com.school;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DB {
    
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    
    public DB() {
        //Create connection
        try {
            conn = DriverManager.getConnection(URL);
         } catch (SQLException ex) {
            System.out.println("Valami baj van a connection létrehozásakor.");
            System.out.println(""+ex);
        }
        
        //Create Statement
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        //Create tables, if not exist
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rsClasses = dbmd.getTables(null, "APP", "CLASSES", null);
            if(!rsClasses.next())
            { 
             createStatement.execute("create table classes(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),lastname varchar(20), firstname varchar(20), email varchar(30))");
            }
            ResultSet rsTeachers = dbmd.getTables(null, "APP", "TEACHERS", null);
            if(!rsTeachers.next())
            { 
             createStatement.execute("create table teachers(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),lastname varchar(20), firstname varchar(20), email varchar(30), subject varchar(30))");
            }
        }    catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }       
    }
    
    
    public ArrayList<Person> getAllClasses(){
        String sql = "select * from classes";
        ArrayList<Person> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            
            while (rs.next()){
                Person actualPerson = new Person(rs.getInt("id"),rs.getString("lastname"),rs.getString("firstname"),rs.getString("email"));
                users.add(actualPerson);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a diákok kiolvasásakor");
            System.out.println(""+ex);
        }
      return users;
    }
    
    public ArrayList<Teacher> getAllTeachers(){
        String sql = "select * from teachers";
        ArrayList<Teacher> teachers = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            teachers = new ArrayList<>();
            
            while (rs.next()){
                Teacher actualTeacher = new Teacher(rs.getInt("id"),rs.getString("lastname"),rs.getString("firstname"),rs.getString("email"),rs.getString("subject"));
                teachers.add(actualTeacher);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a tanárok kiolvasásakor");
            System.out.println(""+ex);
        }
      return teachers;
    }
    
    public void addStudent(Person person){
      try {
        String sql = "insert into classes (lastname, firstname, email) values (?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, person.getLastName());
        preparedStatement.setString(2, person.getFirstName());
        preparedStatement.setString(3, person.getEmail());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a diák hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    public void addTeacher(Teacher teacher){
      try {
        String sql = "insert into teachers (lastname, firstname, email, subject) values (?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, teacher.getLastName());
        preparedStatement.setString(2, teacher.getFirstName());
        preparedStatement.setString(3, teacher.getEmail());
        preparedStatement.setString(4, teacher.getSubject());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a tanár hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    public void updateClass(Person person){
      try {
            String sql = "update classes set lastname = ?, firstname = ? , email = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getLastName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, Integer.parseInt(person.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a diák módosításakor");
            System.out.println(""+ex);
        }
    }
    
    public void updateTeacher(Teacher teacher){
      try {
            String sql = "update teachers set lastname = ?, firstname = ? , email = ? , subject = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, teacher.getLastName());
            preparedStatement.setString(2, teacher.getFirstName());
            preparedStatement.setString(3, teacher.getEmail());
            preparedStatement.setString(4, teacher.getSubject());
            preparedStatement.setInt(5, Integer.parseInt(teacher.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a tanár módosításakor");
            System.out.println(""+ex);
        }
    }
    
    public void removeClass(Person person){
      try {
            String sql = "delete from classes where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(person.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a diák törlésekor");
            System.out.println(""+ex);
        }
    }
    
    public void removeTeacher(Teacher teacher){
      try {
            String sql = "delete from teachers where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(teacher.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a tanár törlésekor");
            System.out.println(""+ex);
        }
    }
}
