package com.medhat.springboot.courseswebsite.dto;

public class CourseDTO {

    private Integer courseId;
    private String Name;
    private String description;

    public CourseDTO(Integer courseId, String name, String description) {
        this.courseId = courseId;
        Name = name;
        this.description = description;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "courseId=" + courseId +
                ", Name='" + Name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
