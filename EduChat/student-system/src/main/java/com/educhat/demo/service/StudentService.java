package com.educhat.demo.service;

import com.educhat.demo.model.Student;
import com.educhat.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }
}
