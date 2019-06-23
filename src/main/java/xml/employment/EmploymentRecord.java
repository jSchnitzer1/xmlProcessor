/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.employment;

import java.util.List;

/**
 *
 * @author khaled
 */
public class EmploymentRecord {
    private String fullEmployeeName;
    private String personalId;
    private String gender;
    private String age;
    private String address;
    private String phone; 
    private String emailAddress; 
    private List<Employer> employer;

    public String getFullEmployeeName() {
        return fullEmployeeName;
    }

    public void setFullEmployeeName(String fullEmployeeName) {
        this.fullEmployeeName = fullEmployeeName;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public List<Employer> getEmployer() {
        return employer;
    }

    public void setEmployer(List<Employer> employer) {
        this.employer = employer;
    }

    public EmploymentRecord(String fullEmployeeName, String personalId, String gender, String age, String address, String phone, String emailAddress, List<Employer> employer) {
        this.fullEmployeeName = fullEmployeeName;
        this.personalId = personalId;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.employer = employer;
    }

    public EmploymentRecord() {
    }
}
