package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.pojo.Task;

public interface taskRepo extends JpaRepository<Task,String>{

}
