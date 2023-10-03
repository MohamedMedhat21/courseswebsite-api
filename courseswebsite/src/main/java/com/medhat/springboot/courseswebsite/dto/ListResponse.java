package com.medhat.springboot.courseswebsite.dto;

import com.medhat.springboot.courseswebsite.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse {
    private List<Course> courses;
    private Integer page;
    private Integer totalPages;
    private Integer totalRecords;
}
