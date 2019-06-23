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
public class Employer {
    private String employerName;
    private String employerAddress;
    private String position;
    private String startDate;
    private String endDate;
    private List<Skill> skills;

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Employer(String employerName, String employerAddress, String position, String startDate, String endDate, List<Skill> skills) {
        this.employerName = employerName;
        this.employerAddress = employerAddress;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.skills = skills;
    }

    public Employer() {
    }
}
