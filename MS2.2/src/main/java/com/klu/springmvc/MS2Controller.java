package com.klu.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klu.springmvc.model.Task;
import com.klu.springmvc.service.TaskService;

@CrossOrigin
@RestController
@RequestMapping
public class MS2Controller {

    @Autowired
    private TaskService ts;

    @PostMapping({"/ms2/createtask", "/createtask"})
    public Object createTask(@RequestBody Task task, @RequestHeader(value = "Token", required = false) String token) {
        return ts.createTask(task, token);
    }

    @GetMapping({"/ms2/gettask/{id}", "/gettask/{id}"})
    public Object getTask(@PathVariable("id") Integer id, @RequestHeader(value = "Token", required = false) String token) {
        return ts.getTask(id, token);
    }

    @GetMapping({"/ms2/getalltasks/{page}/{size}", "/getalltasks/{page}/{size}"})
    public Object getAllTasks(@PathVariable("page") int page, @PathVariable("size") int size, @RequestHeader(value = "Token", required = false) String token) {
        return ts.getAllTasks(page, size, token);
    }
}