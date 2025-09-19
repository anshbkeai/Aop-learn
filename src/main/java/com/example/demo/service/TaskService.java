package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.pojo.Task;
import com.example.demo.repo.taskRepo;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final taskRepo taskRepo;
    public TaskService(taskRepo taskRepo ){
        this.taskRepo =taskRepo;
    }

    public Task getTask(String id) {
        return taskRepo.findById(id).orElse(null);
    }

    @Transactional
    public void saveTask(String name, String desc) {
        Task task = new Task(UUID.randomUUID().toString(), name, desc);
        taskRepo.save(task);
    }

    @Transactional
    public void updateTask(String id,String name, String desc) {
        Task task = taskRepo.findById(id).orElse(null);
        if(task != null) {
            if(name!= null && !name.isEmpty()) task.setName(name);
            if(desc != null && !desc.isEmpty()) task.setDescription(desc);
        }else {
            throw new RuntimeException("Task Not Found ");
        }
    }

    public List<Task> getAllTask() {
        return taskRepo.findAll();
    }
}
