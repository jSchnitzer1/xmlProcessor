/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.degree;

/**
 *
 * @author khaled
 */
public class Course {
    private String name;
    private String code;
    private String period;
    private String mark;
    private String date;
    private String credit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Course(String name, String code, String period, String mark, String date, String credit) {
        this.name = name;
        this.code = code;
        this.period = period;
        this.mark = mark;
        this.date = date;
        this.credit = credit;
    }

    public Course() {
    }
    
}
