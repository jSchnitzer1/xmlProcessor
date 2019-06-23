/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.degree;

import java.util.List;

/**
 *
 * @author khaled
 */
public class DegreeAndTranscript {
    private String fullStudentName;
    private String personalId;
    private String gender;
    private String school;
    private String faculty;
    private String address;
    private String phone;
    private String emailAddress;
    private String graduationDate;
    private List<AcademicCourse> academicCourses;

    public String getFullStudentName() {
        return fullStudentName;
    }

    public void setFullStudentName(String fullStudentName) {
        this.fullStudentName = fullStudentName;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public List<AcademicCourse> getAcademicCourses() {
        return academicCourses;
    }

    public void setAcademicCourses(List<AcademicCourse> academicCourses) {
        this.academicCourses = academicCourses;
    }

    public DegreeAndTranscript(String fullStudentName, String personalId, String gender, String school, String faculty, String address, String phone, String emailAddress, String graduationDate, List<AcademicCourse> academicCourses) {
        this.fullStudentName = fullStudentName;
        this.personalId = personalId;
        this.gender = gender;
        this.school = school;
        this.faculty = faculty;
        this.address = address;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.graduationDate = graduationDate;
        this.academicCourses = academicCourses;
    }
    
    public DegreeAndTranscript() {
    }
    
}
