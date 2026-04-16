package com.chip.todo_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chip.todo_api.dto.CreateTodoRequest;
import com.chip.todo_api.dto.UpdateTodoRequest;
import com.chip.todo_api.model.TodoItem;
import com.chip.todo_api.service.TodoService;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoItem> getAll() {
        return todoService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateTodoRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Title không được rỗng");
        }

        TodoItem item = todoService.add(request.getTitle().trim());
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<?> toggle(@PathVariable Long id) {
        boolean success = todoService.toggle(id);

        if (!success) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id,
            @RequestBody UpdateTodoRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Title không được rỗng");
        }

        TodoItem updatedItem = todoService.updateTitle(id, request.getTitle().trim());

        if (updatedItem == null) {
            return ResponseEntity.status(404).body("ID không hợp lệ");
        }

        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean success = todoService.delete(id);

        if (!success) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        todoService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
