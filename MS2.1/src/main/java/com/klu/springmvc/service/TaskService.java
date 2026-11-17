package com.klu.springmvc.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.klu.springmvc.model.Task;
import com.klu.springmvc.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private JWTService jwts;

    @Autowired
    private TaskRepository repo;

    public Object createTask(Task task, String token) {
        Map<String, String> response = new HashMap<>();
        try {
            if (token == null || token.trim().isEmpty()) {
                response.put("code", "401");
                response.put("message", "Token is missing");
                return response;
            }

            Map<String, String> parsedJWT = jwts.validateJWT(token);
            task.setCreatedby(Integer.parseInt(parsedJWT.get("id")));
            repo.save(task);

            response.put("message", "Task created successfully for ms2.1");
        } catch (Exception e) {
            response.put("code", "501");
            response.put("message", e.getMessage());
        }
        return response;
    }

    public Object getTask(Integer id, String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                Map<String, String> res = new HashMap<>();
                res.put("code", "401");
                res.put("message", "Token is missing");
                return res;
            }
            jwts.validateJWT(token);
            Optional<Task> task = repo.findById(id);
            if (task.isPresent()) {
                return task.get();
            } else {
                Map<String, String> res = new HashMap<>();
                res.put("code", "404");
                res.put("message", "Task not found");
                return res;
            }
        } catch (Exception e) {
            Map<String, String> res = new HashMap<>();
            res.put("code", "501");
            res.put("message", e.getMessage());
            return res;
        }
    }

    public Object getAllTasks(int page, int size, String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                Map<String, String> res = new HashMap<>();
                res.put("code", "401");
                res.put("message", "Token is missing");
                return res;
            }
            jwts.validateJWT(token);
            Page<Task> taskPage = repo.findAll(PageRequest.of(page, size));
            return taskPage;
        } catch (Exception e) {
            Map<String, String> res = new HashMap<>();
            res.put("code", "501");
            res.put("message", e.getMessage());
            return res;
        }
    }
}