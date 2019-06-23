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
public class AcademicCourse {
    private String courseType;
    private List<Course> courses;

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public AcademicCourse(String courseType, List<Course> courses) {
        this.courseType = courseType;
        this.courses = courses;
    }

    public AcademicCourse() {
    }
}
