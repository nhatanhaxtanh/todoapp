package com.chip.todo_api.service;

import com.chip.todo_api.model.TodoItem;
import com.chip.todo_api.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoItem> getAll() {
        return todoRepository.findAll();
    }

    public TodoItem add(String title) {
        TodoItem item = new TodoItem();
        item.setTitle(title);
        item.setCompleted(false);
        return todoRepository.save(item);
    }

    public TodoItem updateTitle(Long id, String newTitle) {
        return todoRepository.findById(id)
                .map(item -> {
                    item.setTitle(newTitle);
                    return todoRepository.save(item);
                })
                .orElse(null);
    }

    public boolean toggle(Long id) {
        return todoRepository.findById(id)
                .map(item -> {
                    item.setCompleted(!item.isCompleted());
                    todoRepository.save(item);
                    return true;
                })
                .orElse(false);
    }

    public boolean delete(Long id) {
        if (!todoRepository.existsById(id)) {
            return false;
        }
        todoRepository.deleteById(id);
        return true;
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }
}
