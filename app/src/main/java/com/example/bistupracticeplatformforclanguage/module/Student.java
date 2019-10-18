package com.example.bistupracticeplatformforclanguage.module;

public class Student
{
    private String id;
    private String studentId;
    private String studentName;
    private String studentGrade;
    private String password;
    private String teacherId;

    public Student() {
    }

    public Student(String id, String studentId, String studentName, String studentGrade, String password, String teacherId) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentGrade = studentGrade;
        this.password = password;
        this.teacherId = teacherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
