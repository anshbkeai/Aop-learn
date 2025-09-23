package com.example.demo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Task;
import com.example.demo.service.TaskService;
import com.example.demo.utils.Audit;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    private TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        return new ResponseEntity<Task>(service.getTask(id), HttpStatus.OK);
    }


    @Audit(event="CREATE-TASK")
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestParam String name , @RequestParam String desc) {
        //TODO: process POST request
        log.info("The name is {} and dec {}", name,desc);
        service.saveTask(name, desc);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable String id,@RequestParam(required = false) String name , @RequestParam(required = false) String desc) {
        //TODO: process PUT request
        service.updateTask(id, name, desc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

   
    @Audit(event = "All-task")
    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTask() {
        return new ResponseEntity<List<Task>>(service.getAllTask(), HttpStatus.OK);
    }
    
    
    
}
